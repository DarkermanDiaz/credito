package com.ordenaris.riskengine.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "datos_contables")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class DatosContablesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @Column(name = "solicitante")
    private SolicitanteEntity solicitante;
    @Column(name = "ventas_promedio")
    private Double ventasPromedio;
    @Column(name = "pasivos")
    private Double pasivos;
    @Column(name = "activos")
    private Double activos;
}
