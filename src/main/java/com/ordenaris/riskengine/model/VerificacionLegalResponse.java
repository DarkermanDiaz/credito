package com.ordenaris.riskengine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VerificacionLegalResponse {
    private int id;
    private SolicitanteResponse solicitante;
    private Boolean tieneProcesosJudiciales;
    private Integer numeroProcesos;
    private Boolean tieneDemandas;
    private Boolean tieneEmbargos;
    private String detalleProcesoLegal;
}
