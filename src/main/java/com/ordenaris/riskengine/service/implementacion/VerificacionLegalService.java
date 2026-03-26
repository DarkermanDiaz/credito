package com.ordenaris.riskengine.service.implementacion;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ordenaris.riskengine.model.SolicitanteResponse;
import com.ordenaris.riskengine.model.VerificacionLegalResponse;
import com.ordenaris.riskengine.repository.IVerificacionLegalProvider;
import com.ordenaris.riskengine.service.IVerificacionLegalService;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class VerificacionLegalService implements IVerificacionLegalService {
    
    private final IVerificacionLegalProvider verificacionLegalProvider;
    
    public VerificacionLegalService(IVerificacionLegalProvider verificacionLegalProvider) {
        this.verificacionLegalProvider = verificacionLegalProvider;
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
                    response.getTieneProcesosJudiciales(),
                    response.getNumeroProcesos(),
                    response.getTieneDemandas(),
                    response.getTieneEmbargos(),
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
                    response.getTieneProcesosJudiciales(),
                    response.getNumeroProcesos(),
                    response.getTieneDemandas(),
                    response.getTieneEmbargos(),
                    response.getDetalleProcesoLegal()
                )
            );
        } catch (Exception e) {
            log.error("Error al buscar Verificacion Legal por Id", e.getMessage());
            throw new EntityNotFoundException("Error al buscar Verificacion Legal por Id"+ e.getMessage());
        }
    }

}
