package com.ordenaris.riskengine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DatosContablesRequest {
    private int solicitante;
    private Double ventasPromedio;
    private Double pasivos;
    private Double activos;
}
