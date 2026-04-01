package com.ordenaris.riskengine.motor;

import java.util.List;

import com.ordenaris.riskengine.entity.DatosContablesEntity;
import com.ordenaris.riskengine.entity.SolicitanteEntity;
import com.ordenaris.riskengine.entity.VerificacionLegalEntity;
import com.ordenaris.riskengine.entity.HistorialPagosEntity;
import com.ordenaris.riskengine.model.DatosContablesResponse;
import com.ordenaris.riskengine.model.HistorialPagosResponse;
import com.ordenaris.riskengine.model.RiskResults;
import com.ordenaris.riskengine.model.SolicitanteResponse;
import com.ordenaris.riskengine.model.VerificacionLegalResponse;
import com.ordenaris.riskengine.service.implementacion.DatosContablesService;
import com.ordenaris.riskengine.service.implementacion.HistorialPagosService;
import com.ordenaris.riskengine.service.implementacion.SolicitanteService;
import com.ordenaris.riskengine.service.implementacion.VerificacionLegalService;

public class RiskEngine {

    final private SolicitanteService solicitanteService;
    final private VerificacionLegalService verificacionLegalService;
    final private HistorialPagosService historialPagosService;
    final private DatosContablesService datosContablesService;

    public RiskEngine(SolicitanteService solicitanteService, VerificacionLegalService verificacionLegalService, HistorialPagosService historialPagosService, DatosContablesService datosContablesService) {
        this.solicitanteService = solicitanteService;
        this.verificacionLegalService = verificacionLegalService;
        this.historialPagosService = historialPagosService;
        this.datosContablesService = datosContablesService;
    }

    public RiskResults calculateRisk(int id) {
        SolicitanteResponse solicitante = solicitanteService.readById(id).get();
        List<DatosContablesResponse> datosContables = datosContablesService.readBySolicitante(id);
        List<HistorialPagosResponse> historialPagos = historialPagosService.readBySolicitante(id);
        List<VerificacionLegalResponse> verificacionLegal = verificacionLegalService.readAll();
        return new RiskResults();
    }

}
