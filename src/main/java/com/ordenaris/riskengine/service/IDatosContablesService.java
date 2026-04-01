package com.ordenaris.riskengine.service;

import java.util.List;
import java.util.Optional;

import com.ordenaris.riskengine.model.DatosContablesResponse;

public interface IDatosContablesService {
    List<DatosContablesResponse> readAll();
    Optional<DatosContablesResponse> readById(int id);
    Optional<DatosContablesResponse> readBySolicitante(int id);
}
