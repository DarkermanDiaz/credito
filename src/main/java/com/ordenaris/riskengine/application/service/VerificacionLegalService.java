package com.ordenaris.riskengine.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ordenaris.riskengine.domain.entity.VerificacionLegalEntity;
import com.ordenaris.riskengine.domain.model.MensajeStrResponse;
import com.ordenaris.riskengine.domain.model.SolicitanteResponse;
import com.ordenaris.riskengine.domain.model.VerificacionLegalRequest;
import com.ordenaris.riskengine.domain.model.VerificacionLegalResponse;
import com.ordenaris.riskengine.domain.service.IVerificacionLegalService;
import com.ordenaris.riskengine.infraestructure.adapter.outbound.ISolicitanteRepository;
import com.ordenaris.riskengine.infraestructure.adapter.outbound.IVerificacionLegalProvider;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class VerificacionLegalService implements IVerificacionLegalService {
    
    private final IVerificacionLegalProvider verificacionLegalProvider;
    private final ISolicitanteRepository solicitanteRepository;
    
    public VerificacionLegalService(IVerificacionLegalProvider verificacionLegalProvider, ISolicitanteRepository solicitanteRepository) {
        this.verificacionLegalProvider = verificacionLegalProvider;
        this.solicitanteRepository = solicitanteRepository;
    }

    @Override
    public List<VerificacionLegalResponse> readAll() {
        log.info("Buscando todos los Verificaciones Legales");
        try{
            return verificacionLegalProvider.findAll().stream()
                .map(response -> new VerificacionLegalResponse(
                    response.getId(),
                    new SolicitanteResponse(
                        response.getSolicitante().getId(),
                        response.getSolicitante().getEmpresaId(),
                        response.getSolicitante().getMontoSolicitado(),
                        response.getSolicitante().getProductoFinanciero(),
                        response.getSolicitante().getFechaSolicitud()),
                    response.getTipoProceso(),
                    response.getDetalleProcesoLegal()
                )).toList();
        }catch(Exception e){
            log.error("Error al buscar Verificaciones Legales", e.getMessage());
            throw new EntityNotFoundException("Error al buscar Verificaciones Legales"+ e.getMessage());
        }
    }

    @Override
    public Optional<VerificacionLegalResponse> readById(int id) {
        log.info("Buscando Verificacion Legal por Id");
        try {
            return verificacionLegalProvider.findById(id).map(
                response -> new VerificacionLegalResponse(
                    response.getId(),
                    new SolicitanteResponse(
                        response.getSolicitante().getId(),
                        response.getSolicitante().getEmpresaId(),
                        response.getSolicitante().getMontoSolicitado(),
                        response.getSolicitante().getProductoFinanciero(),
                        response.getSolicitante().getFechaSolicitud()),
                    response.getTipoProceso(),
                    response.getDetalleProcesoLegal()
                )
            );
        } catch (Exception e) {
            log.error("Error al buscar Verificacion Legal por Id", e.getMessage());
            throw new EntityNotFoundException("Error al buscar Verificacion Legal por Id"+ e.getMessage());
        }
    }

    @Override
    public MensajeStrResponse create(Optional<VerificacionLegalRequest> entrada) {
        log.info("Guardando Verificacion Legal");
        VerificacionLegalEntity request = new VerificacionLegalEntity();
        if (entrada.isEmpty()) {
            log.error("Solcitud de Verificacion Legal vacia");
            throw new IllegalArgumentException("Solcitud de Verificacion Legal vacia");
        } else {
            request.setSolicitante(solicitanteRepository.findById(entrada.get().getSolicitante().getId()).get());
            request.setTipoProceso(entrada.get().getTipoProceso());
            request.setDetalleProcesoLegal(entrada.get().getDetalleProcesoLegal());
        }

        try {
            verificacionLegalProvider.save(request);
            return new MensajeStrResponse("Verificacion Legal guardado correctamente");
        } catch (Exception e) {
            log.error("Error al guardar Verificacion Legal", e.getMessage());
            throw new EntityNotFoundException("Error al guardar Verificacion Legal"+ e.getMessage());
        }
    }

    @Override
    public MensajeStrResponse editById(int id, Optional<VerificacionLegalRequest> entrada) {
        log.info("Editando Verificacion Legal por Id");
        VerificacionLegalEntity request = new VerificacionLegalEntity();
        if (entrada.isEmpty()) {
            log.error("Solcitud de Verificacion Legal vacia");
            throw new IllegalArgumentException("Solcitud de Verificacion Legal vacia");
        }
        request.setId(id);
        request.setSolicitante(solicitanteRepository.findById(entrada.get().getSolicitante().getId()).get());
        request.setTipoProceso(entrada.get().getTipoProceso());
        request.setDetalleProcesoLegal(entrada.get().getDetalleProcesoLegal());

        try {
            verificacionLegalProvider.save(request);
            return new MensajeStrResponse("Verificacion Legal editado correctamente");
        } catch (Exception e) {
            log.error("Error al editar Verificacion Legal por Id", e.getMessage());
            throw new EntityNotFoundException("Error al editar Verificacion Legal por Id"+ e.getMessage());
        }
    }

    @Override
    public List<VerificacionLegalResponse> readBySolicitanteId(int id) {
        log.info("Buscando Verificaciones Legales por Solicitante Id");
        try{
            return verificacionLegalProvider.findBySolicitanteId(id).stream()
                .map(response -> new VerificacionLegalResponse(
                    response.getId(),
                    new SolicitanteResponse(
                        response.getSolicitante().getId(),
                        response.getSolicitante().getEmpresaId(),
                        response.getSolicitante().getMontoSolicitado(),
                        response.getSolicitante().getProductoFinanciero(),
                        response.getSolicitante().getFechaSolicitud()),
                    response.getTipoProceso(),
                    response.getDetalleProcesoLegal()
                )).toList();
        }catch(Exception e){
            log.error("Error al buscar Verificaciones Legales por Solicitante Id", e.getMessage());
            throw new EntityNotFoundException("Error al buscar Verificaciones Legales por Solicitante Id"+ e.getMessage());
        }
    }

    @Override
    public MensajeStrResponse deleteById(int id) {
        log.info("Eliminando Verificacion Legal por Id");
        try {
            verificacionLegalProvider.findById(id).ifPresent(
                response -> {
                    response.setActivo(false);
                    verificacionLegalProvider.save(response);
                }
            );
            return new MensajeStrResponse("Verificacion Legal eliminado correctamente");
        } catch (Exception e) {
            log.error("Error al eliminar Verificacion Legal por Id", e.getMessage());
            throw new EntityNotFoundException("Error al eliminar Verificacion Legal por Id"+ e.getMessage());
        }
    }
}
