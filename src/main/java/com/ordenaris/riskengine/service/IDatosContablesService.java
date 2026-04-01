package com.ordenaris.riskengine.service;

import java.util.List;
import java.util.Optional;

import com.ordenaris.riskengine.model.DatosContablesRequest;
import com.ordenaris.riskengine.model.DatosContablesResponse;
import com.ordenaris.riskengine.model.MensajeStrResponse;

public interface IDatosContablesService {
    List<DatosContablesResponse> readAll();
    Optional<DatosContablesResponse> readById(int id);
    Optional<DatosContablesResponse> readBySolicitante(int id);
    MensajeStrResponse updateById(Optional<DatosContablesRequest> entrada, int id);
}
