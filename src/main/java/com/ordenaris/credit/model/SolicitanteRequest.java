package com.ordenaris.credit.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SolicitanteRequest {

    private enum ProductoFinanciero {
        LINEA_OPERATIVA,
        CREDITO_REVOLVENTE,
        ARRENDAMIENTO_FINANCIERO
    }
    
    private String empresaId;
    private BigDecimal montoSolicitado;
    private ProductoFinanciero productoFinanciero;
    private LocalDate fechaSolicitud;
}
