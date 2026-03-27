package com.ordenaris.riskengine.service.implementacion;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ordenaris.riskengine.model.HistorialPagosResponse;
import com.ordenaris.riskengine.model.SolicitanteResponse;
import com.ordenaris.riskengine.repository.IHistorialPagosProvider;
import com.ordenaris.riskengine.service.IHistorialPagosService;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class HistorialPagosService implements IHistorialPagosService {

    private final IHistorialPagosProvider historialPagosProvider;

    public HistorialPagosService(IHistorialPagosProvider historialPagosProvider) {
        this.historialPagosProvider = historialPagosProvider;
    }

    @Override
    public List<HistorialPagosResponse> readAll() {
        log.info("Buscando todos los HistorialPagos");
        try {
            return historialPagosProvider.findAll().stream()
                    .map(response -> new HistorialPagosResponse(
                            response.getId(),
                            new SolicitanteResponse(
                                    response.getSolicitante().getId(),
                                    response.getSolicitante().getEmpresaId(),
                                    response.getSolicitante().getMontoSolicitado(),
                                    response.getSolicitante().getProductoFinanciero(),
                                    response.getSolicitante().getFechaSolicitud()
                            ),
                            response.getFecha(),
                            response.getMonto(),
                            response.getAcreedor()
                            
            )).toList(); 
        } catch (Exception e) {
            log.error("Error al buscar HistorialPagos", e.getMessage());
            throw new EntityNotFoundException("Error al buscar HistorialPagos"+ e.getMessage());
        }
    }

    @Override
    public Optional<HistorialPagosResponse> readById(int id) {
        log.info("Buscando HistorialPagos por Id");
        try {
            return historialPagosProvider.findById(id).map(
                    response -> new HistorialPagosResponse(
                            response.getId(),
                            new SolicitanteResponse(
                                    response.getSolicitante().getId(),
                                    response.getSolicitante().getEmpresaId(),
                                    response.getSolicitante().getMontoSolicitado(),
                                    response.getSolicitante().getProductoFinanciero(),
                                    response.getSolicitante().getFechaSolicitud()
                            ),
                            response.getFecha(),
                            response.getMonto(),
                            response.getAcreedor()
                    ));
        } catch (Exception e) {
            log.error("Error al buscar HistorialPagos por Id", e.getMessage());
            throw new EntityNotFoundException("Error al buscar HistorialPagos por Id"+ e.getMessage());
        }
    }

    @Override
    public List<HistorialPagosResponse> readByAcreedor(String acreedor) {
        log.info("Buscando HistorialPagos por acreedor");
        try {
            return historialPagosProvider.findByAcreedor(acreedor).stream()
                    .map(response -> new HistorialPagosResponse(
                            response.getId(),
                            new SolicitanteResponse(
                                    response.getSolicitante().getId(),
                                    response.getSolicitante().getEmpresaId(),
                                    response.getSolicitante().getMontoSolicitado(),
                                    response.getSolicitante().getProductoFinanciero(),
                                    response.getSolicitante().getFechaSolicitud()
                            ),
                            response.getFecha(),
                            response.getMonto(),
                            response.getAcreedor()
                    )).toList();
        } catch (Exception e) {
            log.error("Error al buscar HistorialPagos por acreedor", e.getMessage());
            throw new EntityNotFoundException("Error al buscar HistorialPagos por acreedor"+ e.getMessage());
        }
    }

}
