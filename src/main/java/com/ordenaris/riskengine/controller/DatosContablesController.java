package com.ordenaris.riskengine.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordenaris.riskengine.model.DatosContablesRequest;
import com.ordenaris.riskengine.model.DatosContablesResponse;
import com.ordenaris.riskengine.model.MensajeStrResponse;
import com.ordenaris.riskengine.service.implementacion.DatosContablesService;

@RestController
@RequestMapping("/api/v1")

public class DatosContablesController {

    private final DatosContablesService datosContablesService;

    public DatosContablesController(DatosContablesService datosContablesService) {
        this.datosContablesService = datosContablesService;
    }

    @GetMapping("/datoscontables")
    public List<DatosContablesResponse> readAll() {
        return datosContablesService.readAll();
    }

    @GetMapping("/datoscontables/{id}")
    public Optional<DatosContablesResponse> readById(@PathVariable int id) {
        return datosContablesService.readById(id);
    }

    @GetMapping("/datoscontables/solicitante/{id}")
    public Optional<DatosContablesResponse> readBySolicitante(@PathVariable int id) {
        return datosContablesService.readBySolicitante(id);
    }

    @PostMapping("/datoscontables")
    public MensajeStrResponse create(@RequestBody Optional<DatosContablesRequest> datosContables) {
        return datosContablesService.create(datosContables);
    }


}
