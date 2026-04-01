package com.ordenaris.riskengine.service.implementacion;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ordenaris.riskengine.entity.DatosContablesEntity;
import com.ordenaris.riskengine.model.DatosContablesResponse;
import com.ordenaris.riskengine.model.SolicitanteResponse;
import com.ordenaris.riskengine.repository.IDatosContablesProvider;
import com.ordenaris.riskengine.service.IDatosContablesService;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class DatosContablesService implements IDatosContablesService {

    private final IDatosContablesProvider datosContablesProvider;
    private final SolicitanteService solicitanteService;

    public DatosContablesService(IDatosContablesProvider datosContablesProvider, SolicitanteService solicitanteService) {
        this.datosContablesProvider = datosContablesProvider;
        this.solicitanteService = solicitanteService;
    }

    @Override
    public List<DatosContablesResponse> readAll() {
        log.info("Solicitando todos los datos contables"); 
        try {
            return datosContablesProvider.findByActivoTrue().stream()
            .map(response -> new DatosContablesResponse(
                    response.getId(), 
                    new SolicitanteResponse(
                        response.getSolicitante().getId(),
                        response.getSolicitante().getEmpresaId(),
                        response.getSolicitante().getMontoSolicitado(),
                        response.getSolicitante().getProductoFinanciero(),
                        response.getSolicitante().getFechaSolicitud()
                    ),
                    response.getVentasPromedio(),
                    response.getPasivos(),
                    response.getActivos())).toList();
        } catch (Exception e) {
            log.error("Error al buscar los datos contables", e.getMessage());
            throw new EntityNotFoundException("Error al buscar los datos contables"+ e.getMessage());
        }
    }

    @Override
    public Optional<DatosContablesResponse> readById(int id) {
        log.info("Buscando Datos Contables por Id");
        try {
            return datosContablesProvider.findById(id).map(
                response -> new DatosContablesResponse(
                    response.getId(), 
                    new SolicitanteResponse(
                        response.getSolicitante().getId(),
                        response.getSolicitante().getEmpresaId(),
                        response.getSolicitante().getMontoSolicitado(),
                        response.getSolicitante().getProductoFinanciero(),
                        response.getSolicitante().getFechaSolicitud()
                    ),
                    response.getVentasPromedio(),
                    response.getPasivos(),
                    response.getActivos()
                ));
        } catch (Exception e) {
            log.error("Error al buscar Datos Contables por Id", e.getMessage());
            throw new EntityNotFoundException("Error al buscar Datos Contables por Id"+ e.getMessage());
        }
    }

    @Override
    public List<DatosContablesResponse> readBySolicitante(int id) {
        try {
            return datosContablesProvider.findBySolicitanteId(id).stream()
                .map(response -> new DatosContablesResponse(
                    response.getId(), 
                    new SolicitanteResponse(
                        response.getSolicitante().getId(),
                        response.getSolicitante().getEmpresaId(),
                        response.getSolicitante().getMontoSolicitado(),
                        response.getSolicitante().getProductoFinanciero(),
                        response.getSolicitante().getFechaSolicitud()
                    ),
                    response.getVentasPromedio(),
                    response.getPasivos(),
                    response.getActivos()
            )).toList();
        } catch (Exception e) {
            log.error("Error al buscar Datos Contables por Solicitante", e.getMessage());
            throw new EntityNotFoundException("Error al buscar Datos Contables por Solicitante"+ e.getMessage());
        }
    }

}
