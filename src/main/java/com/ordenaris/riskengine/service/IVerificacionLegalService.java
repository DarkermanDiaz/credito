package com.ordenaris.riskengine.service;

import java.util.List;
import java.util.Optional;

import com.ordenaris.riskengine.model.VerificacionLegalResponse;

public interface IVerificacionLegalService {

    List<VerificacionLegalResponse> readAll();
    Optional<VerificacionLegalResponse> readById(int id);

}
