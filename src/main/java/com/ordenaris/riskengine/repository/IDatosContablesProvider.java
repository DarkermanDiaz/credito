package com.ordenaris.riskengine.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ordenaris.riskengine.entity.DatosContablesEntity;

@Repository

public interface IDatosContablesProvider extends JpaRepository<DatosContablesEntity, Integer> {
    Optional<DatosContablesEntity> findBySolicitanteId(int id);
    List<DatosContablesEntity> findByActivoTrue();
}
