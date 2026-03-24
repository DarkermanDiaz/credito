package com.ordenaris.credit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ordenaris.credit.entity.SolicitanteEntity;

@Repository

public interface SolicitanteRepository extends JpaRepository<SolicitanteEntity, Integer> {
    List<SolicitanteEntity> findByActivoTrue();
}
