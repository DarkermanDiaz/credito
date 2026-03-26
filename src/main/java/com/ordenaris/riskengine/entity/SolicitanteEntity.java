package com.ordenaris.riskengine.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "solicitante")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class SolicitanteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "empresaId")
    private String empresaId;
    @Column(name = "montoSolicitado")
    private BigDecimal montoSolicitado;
    @Column(name = "productoFinanciero")
    @Enumerated(EnumType.STRING)
    private ProductoFinanciero productoFinanciero;
    @Column(name = "fechaSolicitud")
    private LocalDate fechaSolicitud;
    @Column(name = "activo")
    private boolean activo = true;
}
