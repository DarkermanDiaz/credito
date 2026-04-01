package com.ordenaris.riskengine.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "verificacion_legal")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class VerificacionLegalEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "solicitante")
    private SolicitanteEntity solicitante;
    @Column(name = "tipoProceso")
    @Enumerated(EnumType.STRING)
    private ProcesosLegales tipoProceso;
    @Column(name = "detalleProcesoLegal")
    private String detalleProcesoLegal;
    @Column(name = "activo")
    private boolean activo = true;
}
