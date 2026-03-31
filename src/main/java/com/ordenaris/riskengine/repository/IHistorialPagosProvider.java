package com.ordenaris.riskengine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ordenaris.riskengine.entity.HistorialPagosEntity;
import com.ordenaris.riskengine.model.HistorialPagosResponse;

@Repository

public interface IHistorialPagosProvider extends JpaRepository<HistorialPagosEntity, Integer>  {
    List<HistorialPagosResponse> findByAcreedor(String acreedor);
    List<HistorialPagosResponse> findBySolicitanteId(int id);
}
