package com.ordenaris.riskengine.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordenaris.riskengine.service.implementacion.HistorialPagosService;
import com.ordenaris.riskengine.model.HistorialPagosResponse;

@RestController
@RequestMapping("/api/v1")

public class HistorialPagosController {

    private final HistorialPagosService historialPagosService;

    public HistorialPagosController(HistorialPagosService historialPagosService) {
        this.historialPagosService = historialPagosService;
    }

    @GetMapping("/historialpagos")
    public List<HistorialPagosResponse> readAll() {
        return historialPagosService.readAll();
    }

    @GetMapping("/historialpagos/{id}")
    public Optional<HistorialPagosResponse> readById(@PathVariable int id) {
        return historialPagosService.readById(id);
    }
}
