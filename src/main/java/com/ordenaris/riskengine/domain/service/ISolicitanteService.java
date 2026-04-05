package com.ordenaris.riskengine.domain.service;

import java.util.List;
import java.util.Optional;

import com.ordenaris.riskengine.domain.model.MensajeStrResponse;
import com.ordenaris.riskengine.domain.model.SolicitanteRequest;
import com.ordenaris.riskengine.domain.model.SolicitanteResponse;

public interface ISolicitanteService {

    List<SolicitanteResponse> readAll();
    Optional<SolicitanteResponse> readById(int id);
    MensajeStrResponse create(SolicitanteRequest solicitanteRequest);
    MensajeStrResponse deleteById(int id);
    MensajeStrResponse editById(int id, Optional<SolicitanteRequest> solicitante);
}
