package com.ordenaris.riskengine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ordenaris.riskengine.entity.VerificacionLegalEntity;


@Repository

public interface IVerificacionLegalProvider extends JpaRepository<VerificacionLegalEntity, Integer> {
    List<VerificacionLegalEntity> findByActivoTrue();
    List<VerificacionLegalEntity> findBySolicitanteId(int id);
}
