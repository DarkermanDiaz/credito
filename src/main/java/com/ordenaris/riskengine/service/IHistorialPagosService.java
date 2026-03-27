package com.ordenaris.riskengine.service;

import java.util.List;
import java.util.Optional;

import com.ordenaris.riskengine.model.HistorialPagosResponse;

public interface IHistorialPagosService {
    List<HistorialPagosResponse> readAll();
    Optional<HistorialPagosResponse> readById(int id);
    List<HistorialPagosResponse> readByAcreedor(String acreedor);
}
