package com.ordenaris.riskengine.domain.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ordenaris.riskengine.domain.entity.ProcesosLegales;
import com.ordenaris.riskengine.domain.entity.ProductoFinanciero;
import com.ordenaris.riskengine.domain.model.DatosContablesResponse;
import com.ordenaris.riskengine.domain.model.HistorialPagosResponse;
import com.ordenaris.riskengine.domain.model.RiskResults;
import com.ordenaris.riskengine.domain.model.RiskResults.Risk;
import com.ordenaris.riskengine.domain.model.SolicitanteResponse;
import com.ordenaris.riskengine.domain.model.VerificacionLegalResponse;

@Component
public class RiskCalculator {

    public RiskResults calculateRisk(SolicitanteResponse solicitante, DatosContablesResponse datosContables,
                                     List<HistorialPagosResponse> historialPagos, List<HistorialPagosResponse> historialPagoLast,
                                     List<VerificacionLegalResponse> verificacionLegal) {
        RiskResults results = new RiskResults();
        Risk riskLevel = Risk.BAJO;
        StringBuilder messages = new StringBuilder();

        if (historialPagos.isEmpty()) messages.append("No posee historial de pagos. ");

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
            messages.append("Monto solicitado excede 8 veces el promedio de ventas. ");
        }

        // Regla 3: Empresa Nueva - Si tiene < 18 meses de existencia → mínimo MEDIO
        if (isEmpresaNueva(solicitante)) {
            if (riskLevel == Risk.BAJO) {
                riskLevel = Risk.MEDIO;
            }
            messages.append("Empresa nueva (< 18 meses). ");
        }

        // Regla 4: Demanda Legal Abierta - Si existe juicio en curso → ALTO
        boolean demandaAbierta = verificacionLegal.stream()
            .anyMatch(v -> v.getTipoProceso().equals(ProcesosLegales.DEMANDAS));

        if (demandaAbierta) {
            riskLevel = Risk.ALTO;
            messages.append("Existe demanda legal abierta. ");
        }

        // Regla 5: Historial Excelente - Si últimos 12 pagos fueron en tiempo sin refinanciamiento → bajar un nivel
        if (isHistorialExcelente(historialPagoLast)) {
            riskLevel = downgradeRisk(riskLevel);
            messages.append("Historial excelente de pagos (últimos 12 pagos en tiempo). ");
        }

        // Regla 6: Producto Estricto - Si es ARRENDAMIENTO_FINANCIERO → +1 punto
        if (solicitante.getProductoFinanciero() == ProductoFinanciero.ARRENDAMIENTO_FINANCIERO) {
            riskLevel = upgradeRisk(riskLevel);
            messages.append("Producto ARRENDAMIENTO_FINANCIERO aumenta riesgo. ");
        }

        results.setRisk(riskLevel);
        results.setMessage(messages.toString().trim());
        return results;
    }

    private boolean isEmpresaNueva(SolicitanteResponse solicitante) {
        if (solicitante.getFechaSolicitud() == null) {
            return false;
        }
        LocalDate hace18Meses = LocalDate.now().minusMonths(18);
        return solicitante.getFechaSolicitud().isAfter(hace18Meses);
    }

    private boolean isHistorialExcelente(List<HistorialPagosResponse> ultimosPagos) {
        if (ultimosPagos == null || ultimosPagos.size() < 12) {
            return false;
        }
        return ultimosPagos.stream()
            .limit(12)
            .allMatch(HistorialPagosResponse::isActivo);
    }

    private Risk upgradeRisk(Risk currentRisk) {
        return switch (currentRisk) {
            case BAJO -> Risk.MEDIO;
            case MEDIO -> Risk.ALTO;
            case ALTO -> Risk.ALTO;
            case RECHAZADO -> Risk.RECHAZADO;
        };
    }

    private Risk downgradeRisk(Risk currentRisk) {
        return switch (currentRisk) {
            case ALTO -> Risk.MEDIO;
            case MEDIO -> Risk.BAJO;
            case BAJO -> Risk.BAJO;
            case RECHAZADO -> Risk.RECHAZADO;
        };
    }
}