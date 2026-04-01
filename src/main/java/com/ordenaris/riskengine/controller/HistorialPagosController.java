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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ordenaris.riskengine.service.implementacion.HistorialPagosService;
import com.ordenaris.riskengine.model.HistorialPagosRequest;
import com.ordenaris.riskengine.model.HistorialPagosResponse;
import com.ordenaris.riskengine.model.MensajeStrResponse;

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
    
    @GetMapping("/historialpagos/acreedor")
    public List<HistorialPagosResponse> readByAcreedor(@RequestParam String acreedor) {
        return historialPagosService.readByAcreedor(acreedor);
    }

    @GetMapping("/historialpagos/solicitante")
    public List<HistorialPagosResponse> readBySolicitante(@RequestParam int id) {
        return historialPagosService.readBySolicitante(id);
    }

    @PostMapping("/historialpagos")
    public MensajeStrResponse create(@RequestBody Optional<HistorialPagosRequest> entrada) {
        return historialPagosService.create(entrada);
    }

    @DeleteMapping("/historialpagos/{id}")
    public MensajeStrResponse deleteById(@PathVariable int id) {
        return historialPagosService.deleteById(id);
    }

    @PutMapping("/historialpagos/{id}")
    public MensajeStrResponse editById(@PathVariable int id, @RequestBody Optional<HistorialPagosRequest> entrada) {
        return historialPagosService.editById(id, entrada);
    }
}
