package com.ordenaris.riskengine.domain.port.outbound;

import java.util.List;
import java.util.Optional;

import com.ordenaris.riskengine.domain.entity.DatosContablesEntity;

public interface DatosContablesRepositoryPort {
    Optional<DatosContablesEntity> findBySolicitanteId(int id);
    List<DatosContablesEntity> findByActivoTrue();
    DatosContablesEntity save(DatosContablesEntity entity);
}