package com.ordenaris.riskengine.domain.port.outbound;

import java.util.List;
import java.util.Optional;

import com.ordenaris.riskengine.domain.entity.SolicitanteEntity;

public interface SolicitanteRepositoryPort {
    List<SolicitanteEntity> findByActivoTrue();
    Optional<SolicitanteEntity> findById(int id);
    SolicitanteEntity save(SolicitanteEntity entity);
}