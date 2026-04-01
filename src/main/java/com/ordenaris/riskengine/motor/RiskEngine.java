package com.ordenaris.riskengine.motor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.ordenaris.riskengine.model.DatosContablesResponse;
import com.ordenaris.riskengine.model.HistorialPagosResponse;
import com.ordenaris.riskengine.model.RiskResults;
import com.ordenaris.riskengine.model.RiskResults.Risk;
import com.ordenaris.riskengine.model.SolicitanteResponse;
import com.ordenaris.riskengine.model.VerificacionLegalResponse;
import com.ordenaris.riskengine.entity.ProductoFinanciero;
import com.ordenaris.riskengine.service.implementacion.DatosContablesService;
import com.ordenaris.riskengine.service.implementacion.HistorialPagosService;
import com.ordenaris.riskengine.service.implementacion.SolicitanteService;
import com.ordenaris.riskengine.service.implementacion.VerificacionLegalService;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import com.ordenaris.riskengine.entity.ProcesosLegales;
import org.springframework.stereotype.Service;

@Service
@Slf4j

public class RiskEngine {

    private final SolicitanteService solicitanteService;
    private final VerificacionLegalService verificacionLegalService;
    private final HistorialPagosService historialPagosService;
    private final DatosContablesService datosContablesService;

    public RiskEngine(SolicitanteService solicitanteService, VerificacionLegalService verificacionLegalService, HistorialPagosService historialPagosService, DatosContablesService datosContablesService) {
        this.solicitanteService = solicitanteService;
        this.verificacionLegalService = verificacionLegalService;
        this.historialPagosService = historialPagosService;
        this.datosContablesService = datosContablesService;
    }

    public RiskResults calculateRisk(int id) {
        log.info("Calculando riesgo para el solicitante con id: {}", id);
        SolicitanteResponse solicitante = solicitanteService.readById(id).orElseThrow(EntityNotFoundException::new);
        log.info("Solicitando Datos Contables para el solicitante con id: {}", id);
        DatosContablesResponse datosContables = datosContablesService.readBySolicitante(id).orElseThrow(EntityNotFoundException::new);
        List<HistorialPagosResponse> historialPagos = historialPagosService.readBySolicitante(id);
        List<HistorialPagosResponse> historialPagoLast = historialPagosService.readLastBySolicitanteId(id);
        List<VerificacionLegalResponse> verificacionLegal = verificacionLegalService.readBySolicitanteId(id);

        RiskResults results = new RiskResults();
        Risk riskLevel = Risk.BAJO;
        StringBuilder messages = new StringBuilder();

        if (historialPagos.isEmpty()) messages.append("No posee historial de pagos. \n");

        // Regla 1: Deuda Activa - Si existe deuda vencida > 90 días → RECHAZADO
        boolean deudaActiva = historialPagos.stream()
            .anyMatch(p -> p.getFecha().isBefore(LocalDate.now().minusDays(90)) && p.isActivo());
        
        
        if (deudaActiva) {
            results.setRisk(Risk.RECHAZADO);
            results.setMessage("RECHAZADO: Empresa posee deuda vencida mayor a 90 días");
            return results;
        }

        // Regla 2: Alta Solicitud vs Ventas - Si monto > 8 veces promedio mensual → ALTO
        if (solicitante.getMontoSolicitado()
            .compareTo(BigDecimal.valueOf(datosContables.getVentasPromedio() * 8)) > 0) {
            riskLevel = Risk.ALTO;
            messages.append("Monto solicitado excede 8 veces el promedio de ventas. \n");
        }

        // Regla 3: Empresa Nueva - Si tiene < 18 meses de existencia → mínimo MEDIO
        // Usar la fecha de solicitud como referencia de antigüedad de datos
        if (isEmpresaNueva(solicitante)) {
            if (riskLevel == Risk.BAJO) {
                riskLevel = Risk.MEDIO;
            }
            messages.append("Empresa nueva (< 18 meses). \n");
        }

        // Regla 4: Demanda Legal Abierta - Si existe juicio en curso → ALTO
        boolean demandaAbierta = verificacionLegal.stream()
            .anyMatch(v -> v.getTipoProceso().equals(ProcesosLegales.DEMANDAS));
        
        if (demandaAbierta) {
            riskLevel = Risk.ALTO;
            messages.append("Existe demanda legal abierta. \n");
        }

        // Regla 5: Historial Excelente - Si últimos 12 pagos fueron en tiempo sin refinanciamiento → bajar un nivel
        if (isHistorialExcelente(historialPagoLast)) {
            riskLevel = downgradeRisk(riskLevel);
            messages.append("Historial excelente de pagos (últimos 12 pagos en tiempo). \n");
        }

        // Regla 6: Producto Estricto - Si es ARRENDAMIENTO_FINANCIERO → +1 punto
        if (solicitante.getProductoFinanciero() == ProductoFinanciero.ARRENDAMIENTO_FINANCIERO) {
            riskLevel = upgradeRisk(riskLevel);
            messages.append("Producto ARRENDAMIENTO_FINANCIERO aumenta riesgo. \n");
        }

        results.setRisk(riskLevel);
        results.setMessage(messages.toString().trim());
        return results;
    }

    /**
     * Verifica si la empresa es nueva (menos de 18 meses de existencia).
     * Actualmente usa la fecha de solicitud como referencia.
     */
    private boolean isEmpresaNueva(SolicitanteResponse solicitante) {
        if (solicitante.getFechaSolicitud() == null) {
            return false;
        }
        LocalDate hace18Meses = LocalDate.now().minusMonths(18);
        return solicitante.getFechaSolicitud().isAfter(hace18Meses);
    }

    /**
     * Verifica si el historial de pagos es excelente:
     * - Al menos 12 pagos
     * - Todos los pagos están marcados como activos (sin refinanciamiento)
     */
    private boolean isHistorialExcelente(List<HistorialPagosResponse> ultimosPagos) {
        if (ultimosPagos == null || ultimosPagos.size() < 12) {
            return false;
        }
        return ultimosPagos.stream()
            .limit(12)
            .allMatch(HistorialPagosResponse::isActivo);
    }

    /**
     * Aumenta el nivel de riesgo en 1 punto.
     * BAJO → MEDIO → ALTO → ALTO
     */
    private Risk upgradeRisk(Risk currentRisk) {
        return switch (currentRisk) {
            case BAJO -> Risk.MEDIO;
            case MEDIO -> Risk.ALTO;
            case ALTO -> Risk.ALTO;
            case RECHAZADO -> Risk.RECHAZADO;
        };
    }

    /**
     * Disminuye el nivel de riesgo en 1 punto.
     * ALTO → MEDIO → BAJO → BAJO
     */
    private Risk downgradeRisk(Risk currentRisk) {
        return switch (currentRisk) {
            case ALTO -> Risk.MEDIO;
            case MEDIO -> Risk.BAJO;
            case BAJO -> Risk.BAJO;
            case RECHAZADO -> Risk.RECHAZADO;
        };
    }

}
