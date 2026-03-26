package com.ordenaris.riskengine.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Column(name = "tieneProcesosJudiciales")
    private Boolean tieneProcesosJudiciales;
    @Column(name = "numeroProcesos")
    private Integer numeroProcesos;
    @Column(name = "tieneDemandas")
    private Boolean tieneDemandas;
    @Column(name = "tieneEmbargos")
    private Boolean tieneEmbargos;
    @Column(name = "detalleProcesoLegal")
    private String detalleProcesoLegal;
}
