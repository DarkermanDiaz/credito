package com.ordenaris.riskengine.service;

import java.util.List;
import java.util.Optional;

import com.ordenaris.riskengine.model.MensajeStrResponse;
import com.ordenaris.riskengine.model.SolicitanteRequest;
import com.ordenaris.riskengine.model.SolicitanteResponse;

public interface ISolicitanteService {

    List<SolicitanteResponse> readAll();
    Optional<SolicitanteResponse> readById(int id);
    MensajeStrResponse create(SolicitanteRequest solicitanteRequest);
    MensajeStrResponse deleteById(int id);
    MensajeStrResponse editById(int id, SolicitanteRequest solicitante);
}
