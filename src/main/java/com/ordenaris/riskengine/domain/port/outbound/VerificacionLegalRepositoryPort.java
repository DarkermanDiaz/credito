package com.ordenaris.riskengine.domain.port.outbound;

import java.util.List;

import com.ordenaris.riskengine.domain.entity.VerificacionLegalEntity;

public interface VerificacionLegalRepositoryPort {
    List<VerificacionLegalEntity> findByActivoTrue();
    List<VerificacionLegalEntity> findBySolicitanteId(int id);
    VerificacionLegalEntity save(VerificacionLegalEntity entity);
}