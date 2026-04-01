package com.ordenaris.riskengine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ordenaris.riskengine.entity.HistorialPagosEntity;

@Repository

public interface IHistorialPagosProvider extends JpaRepository<HistorialPagosEntity, Integer>  {
    List<HistorialPagosEntity> findByActivoTrue();
    List<HistorialPagosEntity> findByAcreedor(String acreedor);
    List<HistorialPagosEntity> findBySolicitanteId(int id);
}
