package com.ordenaris.riskengine.motor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.ordenaris.riskengine.model.DatosContablesResponse;
import com.ordenaris.riskengine.model.HistorialPagosResponse;
import com.ordenaris.riskengine.model.RiskResults;
import com.ordenaris.riskengine.model.RiskResults.Risk;
import com.ordenaris.riskengine.model.SolicitanteResponse;
import com.ordenaris.riskengine.model.VerificacionLegalResponse;
import com.ordenaris.riskengine.service.implementacion.DatosContablesService;
import com.ordenaris.riskengine.service.implementacion.HistorialPagosService;
import com.ordenaris.riskengine.service.implementacion.SolicitanteService;
import com.ordenaris.riskengine.service.implementacion.VerificacionLegalService;

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
        SolicitanteResponse solicitante = solicitanteService.readById(id).orElseThrow();
        DatosContablesResponse datosContables = datosContablesService.readBySolicitante(id).orElseThrow();
        List<HistorialPagosResponse> historialPagos = historialPagosService.readBySolicitante(id);
        List<HistorialPagosResponse> historialPagoLast = historialPagosService.readLastBySolicitanteId(id);
        List<VerificacionLegalResponse> verificacionLegal = verificacionLegalService.readAll();

        RiskResults results = new RiskResults();
        AtomicInteger tempRisk = new AtomicInteger(0);

        historialPagos.stream().forEach(
            p -> {
                if (p.getFecha().isBefore(LocalDate.now().minusDays(90)) && p.isActivo()) { 
                    tempRisk.set(0);
                }
            }
        );

        historialPagoLast.stream().forEach(
            p -> {
                if (!p.isActivo()) { 
                    tempRisk.incrementAndGet();
                }
            }
        );

        if (solicitante.getMontoSolicitado().compareTo(BigDecimal.valueOf(datosContables.getVentasPromedio() * 8)) > 0) {
            results.setRisk(Risk.ALTO);
        } else if (tempRisk.get() > 0) {
            results.setRisk(Risk.MEDIO);
        } else {
            results.setRisk(Risk.BAJO);
        }
        
        return results;
    }

}
