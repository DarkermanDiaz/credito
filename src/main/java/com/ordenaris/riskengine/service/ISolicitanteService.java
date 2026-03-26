package com.ordenaris.riskengine.service;

import java.util.List;
import java.util.Optional;

import com.ordenaris.riskengine.model.SolicitanteResponse;

public interface ISolicitanteService {

    List<SolicitanteResponse> readAll();
    Optional<SolicitanteResponse> readById(int id);
}
