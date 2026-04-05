package com.ordenaris.riskengine.infraestructure.adapter.outbound;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ordenaris.riskengine.domain.entity.SolicitanteEntity;

@Repository

public interface ISolicitanteRepository extends JpaRepository<SolicitanteEntity, Integer> {
    List<SolicitanteEntity> findByActivoTrue();
}
