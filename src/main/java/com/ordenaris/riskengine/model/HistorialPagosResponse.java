package com.ordenaris.riskengine.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class HistorialPagosResponse {

    private int id;
    private LocalDate fecha;
    private double monto;
    private String acreedor;
}
