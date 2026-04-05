package com.ordenaris.riskengine.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ordenaris.riskengine.domain.entity.HistorialPagosEntity;
import com.ordenaris.riskengine.domain.model.HistorialPagosRequest;
import com.ordenaris.riskengine.domain.model.HistorialPagosResponse;
import com.ordenaris.riskengine.domain.model.MensajeStrResponse;
import com.ordenaris.riskengine.domain.model.SolicitanteResponse;
import com.ordenaris.riskengine.domain.service.IHistorialPagosService;
import com.ordenaris.riskengine.infraestructure.adapter.outbound.IHistorialPagosProvider;
import com.ordenaris.riskengine.infraestructure.adapter.outbound.ISolicitanteRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class HistorialPagosService implements IHistorialPagosService {

    private final IHistorialPagosProvider historialPagosProvider;
    private final ISolicitanteRepository solicitanteRepository;

    public HistorialPagosService(IHistorialPagosProvider historialPagosProvider, ISolicitanteRepository solicitanteRepository) {
        this.historialPagosProvider = historialPagosProvider;
        this.solicitanteRepository = solicitanteRepository;
    }

    @Override
    public List<HistorialPagosResponse> readAll() {
        log.info("Buscando todos los HistorialPagos");
        try {
            return historialPagosProvider.findByActivoTrue().stream()
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
                            response.getAcreedor(),
                            response.isActivo()
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
                            response.getAcreedor(),
                            response.isActivo()
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
                            response.getAcreedor(),
                            response.isActivo()
                    )).toList();
        } catch (Exception e) {
            log.error("Error al buscar HistorialPagos por acreedor", e.getMessage());
            throw new EntityNotFoundException("Error al buscar HistorialPagos por acreedor"+ e.getMessage());
        }
    }

    @Override
    public List<HistorialPagosResponse> readBySolicitante(int id) {
        log.info("Buscando HistorialPagos por Solicitante");
        try {
            return historialPagosProvider.findBySolicitanteId(id).stream()
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
                            response.getAcreedor(),
                            response.isActivo()
                    )).toList();
        } catch (Exception e) {
            log.error("Error al buscar HistorialPagos por Solicitante", e.getMessage());
            throw new EntityNotFoundException("Error al buscar HistorialPagos por Solicitante"+ e.getMessage());
        }
    }

    @Override
    public MensajeStrResponse create(Optional<HistorialPagosRequest> entrada){
        log.info("Guardando HistorialPagos");
        HistorialPagosEntity request = new HistorialPagosEntity();

        if (entrada.isEmpty()) {
            log.error("Solcitud de HistorialPagos vacia");
            throw new IllegalArgumentException("Solcitud de HistorialPagos vacia");
        } else {
            request.setSolicitante(solicitanteRepository.findById(entrada.get().getSolicitante().getId()).get());
            request.setFecha(entrada.get().getFecha());
            request.setMonto(entrada.get().getMonto());
            request.setAcreedor(entrada.get().getAcreedor());
            request.setActivo(entrada.get().isActivo());
        }

        try {
            historialPagosProvider.save(request);
            return new MensajeStrResponse("HistorialPagos guardado correctamente");
        } catch (Exception e) {
            log.error("Error al guardar HistorialPagos", e.getMessage());
            throw new EntityNotFoundException("Error al guardar HistorialPagos"+ e.getMessage());  }
    }

    @Override
    public MensajeStrResponse deleteById(int id) {
        log.info("Eliminando HistorialPagos por Id");
        
        try {
            HistorialPagosEntity request = historialPagosProvider.findById(id).get();
            request.setActivo(false);
            historialPagosProvider.save(request);
            return new MensajeStrResponse("HistorialPagos eliminado correctamente");
        } catch (Exception e) {
            log.error("Error al eliminar HistorialPagos por Id", e.getMessage());
            throw new EntityNotFoundException("Error al eliminar HistorialPagos por Id"+ e.getMessage());
        }
    }

    @Override
    public MensajeStrResponse editById(int id, Optional<HistorialPagosRequest> entrada) {

        log.info("Editando HistorialPagos por Id");
        HistorialPagosEntity request = new HistorialPagosEntity();
        if (entrada.isEmpty()) {
            log.error("Solcitud de HistorialPagos vacia");
            throw new IllegalArgumentException("Solcitud de HistorialPagos vacia");
        }
        request.setId(id);
        request.setSolicitante(solicitanteRepository.findById(entrada.get().getSolicitante().getId()).get());
        request.setFecha(entrada.get().getFecha());
        request.setMonto(entrada.get().getMonto());
        request.setAcreedor(entrada.get().getAcreedor());
        request.setActivo(entrada.get().isActivo());

        try {
            historialPagosProvider.save(request);
            return new MensajeStrResponse("HistorialPagos editado correctamente");
        } catch (Exception e) {
            log.error("Error al editar HistorialPagos por Id", e.getMessage());
            throw new EntityNotFoundException("Error al editar HistorialPagos por Id"+ e.getMessage());
        }
    }

    public List<HistorialPagosResponse> readLastBySolicitanteId(int id) {
        Pageable pageable =  PageRequest.of(0, 12);
        log.info("Solicitando los ultimos pagos");
        try {
            return historialPagosProvider.readLastBySolicitanteId(id, pageable).stream()
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
                            response.getAcreedor(),
                            response.isActivo()
                    )).toList();
        } catch (Exception e) {
            log.error("Error al buscar HistorialPagos por Solicitante", e.getMessage());
            throw new EntityNotFoundException("Error al buscar HistorialPagos por Solicitante"+ e.getMessage());
        }
    }
}
