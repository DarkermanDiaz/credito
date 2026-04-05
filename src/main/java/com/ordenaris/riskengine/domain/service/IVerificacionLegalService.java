package com.ordenaris.riskengine.domain.service;

import java.util.List;
import java.util.Optional;

import com.ordenaris.riskengine.domain.model.MensajeStrResponse;
import com.ordenaris.riskengine.domain.model.VerificacionLegalRequest;
import com.ordenaris.riskengine.domain.model.VerificacionLegalResponse;

public interface IVerificacionLegalService {
    List<VerificacionLegalResponse> readAll();
    Optional<VerificacionLegalResponse> readById(int id);
    MensajeStrResponse create(Optional<VerificacionLegalRequest> entrada);
    MensajeStrResponse editById(int id, Optional<VerificacionLegalRequest> entrada);
    List<VerificacionLegalResponse> readBySolicitanteId(int id);
    MensajeStrResponse deleteById(int id);
}
