package com.ordenaris.riskengine.service.implementacion;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ordenaris.riskengine.entity.SolicitanteEntity;
import com.ordenaris.riskengine.model.MensajeStrResponse;
import com.ordenaris.riskengine.model.SolicitanteRequest;
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

    @Override
    public MensajeStrResponse create(SolicitanteRequest solicitante) {
        log.info("Creando Solicitante");
        SolicitanteEntity request = new SolicitanteEntity();
        request.setActivo(true);
        request.setEmpresaId(solicitante.getEmpresaId());
        request.setMontoSolicitado(solicitante.getMontoSolicitado());
        request.setProductoFinanciero(solicitante.getProductoFinanciero());
        request.setFechaSolicitud(LocalDate.now());

        try {
            solicitanteRepository.save(request);
            return new MensajeStrResponse("Solicitante creado correctamente");
        } catch (Exception e) {
            log.error("Error al crear Solicitante", e.getMessage());
            throw new EntityNotFoundException("Error al crear Solicitante"+ e.getMessage());
        }
    }

    @Override
    public MensajeStrResponse deleteById(int id) {
        log.info("Eliminando Solicitante por Id");
        try {
            solicitanteRepository.deleteById(id);
            return new MensajeStrResponse("Solicitante eliminado correctamente");
        } catch (Exception e) {
            log.error("Error al eliminar Solicitante por Id", e.getMessage());
            throw new EntityNotFoundException("Error al eliminar Solicitante por Id"+ e.getMessage());
        }
    }

    @Override
    public MensajeStrResponse editById(int id, SolicitanteRequest solicitante) {
        log.info("Editando Solicitante por Id");
        SolicitanteEntity request = new SolicitanteEntity();
        request.setId(id);
        request.setMontoSolicitado(solicitante.getMontoSolicitado());
        request.setProductoFinanciero(solicitante.getProductoFinanciero());
        request.setEmpresaId(solicitante.getEmpresaId());

        try {
            solicitanteRepository.save(request);
            return new MensajeStrResponse("Solicitante editado correctamente");
        } catch (Exception e) {
            log.error("Error al editar Solicitante por Id", e.getMessage());
            throw new EntityNotFoundException("Error al editar Solicitante por Id"+ e.getMessage());
        }
    }
}
