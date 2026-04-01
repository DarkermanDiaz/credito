package com.ordenaris.riskengine.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class HistorialPagosRequest {
    private SolicitanteResponse solicitante;
    private LocalDate fecha;
    private double monto;
    private String acreedor;
    private boolean activo;
}
