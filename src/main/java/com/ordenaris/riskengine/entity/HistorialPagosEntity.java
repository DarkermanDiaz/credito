package com.ordenaris.riskengine.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "historial_pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class HistorialPagosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private SolicitanteEntity solicitante;
    @Column(name = "fecha")
    private LocalDate fecha;
    @Column(name = "monto")
    private Double monto;
    @Column(name = "acreedor")
    private String acreedor;
    @Column(name = "activo")
    private boolean activo = true;
}
