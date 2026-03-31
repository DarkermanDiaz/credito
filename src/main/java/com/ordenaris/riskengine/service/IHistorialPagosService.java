package com.ordenaris.riskengine.service;

import java.util.List;
import java.util.Optional;

import com.ordenaris.riskengine.model.HistorialPagosRequest;
import com.ordenaris.riskengine.model.HistorialPagosResponse;
import com.ordenaris.riskengine.model.MensajeStrResponse;

public interface IHistorialPagosService {
    List<HistorialPagosResponse> readAll();
    Optional<HistorialPagosResponse> readById(int id);
    List<HistorialPagosResponse> readByAcreedor(String acreedor);
    List<HistorialPagosResponse> readBySolicitante(int id);
    MensajeStrResponse create(Optional<HistorialPagosRequest> entrada);
    MensajeStrResponse deleteById(int id);
    MensajeStrResponse editById(int id, Optional<HistorialPagosRequest> entrada);
}
