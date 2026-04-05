package com.ordenaris.riskengine.domain.port.outbound;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ordenaris.riskengine.domain.entity.HistorialPagosEntity;

public interface HistorialPagosRepositoryPort {
    List<HistorialPagosEntity> findByActivoTrue();
    List<HistorialPagosEntity> findByAcreedor(String acreedor);
    List<HistorialPagosEntity> findBySolicitanteId(int id);
    List<HistorialPagosEntity> readLastBySolicitanteId(int id, Pageable pageable);
    HistorialPagosEntity save(HistorialPagosEntity entity);
}