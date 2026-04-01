package com.ordenaris.riskengine.model;

import com.ordenaris.riskengine.entity.ProcesosLegales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VerificacionLegalRequest {
    private SolicitanteResponse solicitante;
    private ProcesosLegales tipoProceso;
    private String detalleProcesoLegal;
}
