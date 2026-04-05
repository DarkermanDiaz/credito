package com.ordenaris.riskengine.domain.model;

import com.ordenaris.riskengine.domain.entity.ProcesosLegales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VerificacionLegalResponse {
    private int id;
    private SolicitanteResponse solicitante;
    private ProcesosLegales tipoProceso;
    private String detalleProcesoLegal;
}
