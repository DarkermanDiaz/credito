package com.ordenaris.riskengine.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ordenaris.riskengine.entity.ProductoFinanciero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SolicitanteResponse {
    private int id;
    private String empresaId;
    private BigDecimal montoSolicitado;
    private ProductoFinanciero productoFinanciero;
    private LocalDate fechaSolicitud;
}
