package com.ordenaris.riskengine.infraestructure.adapter.outbound;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ordenaris.riskengine.domain.entity.HistorialPagosEntity;

import feign.Param;

@Repository

public interface IHistorialPagosProvider extends JpaRepository<HistorialPagosEntity, Integer>  {
    List<HistorialPagosEntity> findByActivoTrue();
    List<HistorialPagosEntity> findByAcreedor(String acreedor);
    List<HistorialPagosEntity> findBySolicitanteId(int id);
    @Query("SELECT hp FROM HistorialPagosEntity hp WHERE hp.solicitante.id = :id AND hp.activo = true ORDER BY hp.fecha DESC")
    List<HistorialPagosEntity> readLastBySolicitanteId(@Param("id") int id, Pageable pageable);
}
