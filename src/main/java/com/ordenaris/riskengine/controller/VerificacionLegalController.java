package com.ordenaris.riskengine.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordenaris.riskengine.model.VerificacionLegalResponse;
import com.ordenaris.riskengine.service.implementacion.VerificacionLegalService;

@RestController
@RequestMapping("/api/v1")

public class VerificacionLegalController {

    private final VerificacionLegalService verificacionLegalService;

    public VerificacionLegalController(VerificacionLegalService verificacionLegalService) {
        this.verificacionLegalService = verificacionLegalService;
    }

    @GetMapping("/verificacionlegal")
    public List<VerificacionLegalResponse> readAll() {
        return verificacionLegalService.readAll();
    }

    @GetMapping("/verificacionlegal/{id}")
    public Optional<VerificacionLegalResponse> readById(@PathVariable int id) {
        return verificacionLegalService.readById(id);
    }
}
