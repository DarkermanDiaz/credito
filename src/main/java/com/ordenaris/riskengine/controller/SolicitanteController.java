package com.ordenaris.riskengine.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordenaris.riskengine.model.MensajeStrResponse;
import com.ordenaris.riskengine.model.SolicitanteRequest;
import com.ordenaris.riskengine.model.SolicitanteResponse;
import com.ordenaris.riskengine.service.implementacion.SolicitanteService;



@RestController
@RequestMapping("/api/v1")

public class SolicitanteController {

    private final SolicitanteService solicitanteService;

    public SolicitanteController(SolicitanteService solicitanteService) {
        this.solicitanteService = solicitanteService;
    }

    @GetMapping("/solicitantes")
    public List<SolicitanteResponse> readAll() {
        return solicitanteService.readAll();
    }

    @GetMapping("/solicitantes/{id}")
    public Optional<SolicitanteResponse> readById(@PathVariable int id) {
        return solicitanteService.readById(id);
    }

    @PostMapping("/solicitantes")
    public MensajeStrResponse create(@RequestBody SolicitanteRequest solicitante) {
        return solicitanteService.create(solicitante);
    }
}
