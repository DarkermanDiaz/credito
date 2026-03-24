package com.ordenaris.credit.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordenaris.credit.model.SolicitanteResponse;
import com.ordenaris.credit.service.implementacion.SolicitanteService;

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
}
