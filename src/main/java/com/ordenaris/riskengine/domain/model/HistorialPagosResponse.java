package com.ordenaris.riskengine.domain.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class HistorialPagosResponse {

    private int id;
    private SolicitanteResponse solicitante;
    private LocalDate fecha;
    private double monto;
    private String acreedor;
    private boolean activo;
}
