package com.ordenaris.riskengine.service.implementacion;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ordenaris.riskengine.model.SolicitanteResponse;
import com.ordenaris.riskengine.repository.ISolicitanteRepository;
import com.ordenaris.riskengine.service.ISolicitanteService;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class SolicitanteService implements ISolicitanteService {

    private final ISolicitanteRepository solicitanteRepository;

    public SolicitanteService(ISolicitanteRepository solicitanteRepository) {
        this.solicitanteRepository = solicitanteRepository;
    }

    @Override
    public List<SolicitanteResponse> readAll() {
        log.info("Buscando todos los Solicitantes");
        try {
            return solicitanteRepository.findByActivoTrue().stream()
                    .map(solicitante -> new SolicitanteResponse(
                            solicitante.getId(),
                            solicitante.getEmpresaId(),
                            solicitante.getMontoSolicitado(),
                            solicitante.getProductoFinanciero(),
                            solicitante.getFechaSolicitud()
                    ))
                .toList();
        } catch (Exception e) {
            log.error("Error al buscar Solicitantes", e.getMessage());
            throw new EntityNotFoundException("Error al buscar Solicitantes"+ e.getMessage());
        }
    }

    @Override
    public Optional<SolicitanteResponse> readById(int id) {
        log.info("Buscando Solicitante por Id");
        try {
            return solicitanteRepository.findById(id)
                    .map(solicitante -> new SolicitanteResponse(
                            solicitante.getId(),
                            solicitante.getEmpresaId(),
                            solicitante.getMontoSolicitado(),
                            solicitante.getProductoFinanciero(),
                            solicitante.getFechaSolicitud()
                    ));
        } catch (Exception e) {
            log.error("Error al buscar Solicitante por Id", e.getMessage());
            throw new EntityNotFoundException("Error al buscar Solicitante por Id"+ e.getMessage());
        }
    }
}
