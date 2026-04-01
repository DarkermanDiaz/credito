package com.ordenaris.riskengine.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordenaris.riskengine.model.MensajeStrResponse;
import com.ordenaris.riskengine.model.VerificacionLegalRequest;
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

    @GetMapping("/verificacionlegal/solicitante/{id}")
    public List<VerificacionLegalResponse> readBySolicitanteId(@PathVariable int id) {
        return verificacionLegalService.readBySolicitanteId(id);
    }

    @PostMapping("/verificacionlegal")
    public MensajeStrResponse create(@RequestBody Optional<VerificacionLegalRequest> entrada) {
        return verificacionLegalService.create(entrada);
    }

    @PutMapping("/verificacionlegal/{id}")
    public MensajeStrResponse editById(@PathVariable int id, @RequestBody Optional<VerificacionLegalRequest> entrada) {
        return verificacionLegalService.editById(id, entrada);
    }

    @DeleteMapping("/verificacionlegal/{id}")
    public MensajeStrResponse deleteById(@PathVariable int id) {
        return verificacionLegalService.deleteById(id);
    }
}
