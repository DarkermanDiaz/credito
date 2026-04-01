package com.ordenaris.riskengine.service.implementacion;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ordenaris.riskengine.entity.DatosContablesEntity;
import com.ordenaris.riskengine.model.DatosContablesRequest;
import com.ordenaris.riskengine.model.DatosContablesResponse;
import com.ordenaris.riskengine.model.MensajeStrResponse;
import com.ordenaris.riskengine.model.SolicitanteResponse;
import com.ordenaris.riskengine.repository.IDatosContablesProvider;
import com.ordenaris.riskengine.repository.ISolicitanteRepository;
import com.ordenaris.riskengine.service.IDatosContablesService;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class DatosContablesService implements IDatosContablesService {

    private final IDatosContablesProvider datosContablesProvider;
    private final ISolicitanteRepository solicitanteRepository;

    public DatosContablesService(IDatosContablesProvider datosContablesProvider, ISolicitanteRepository solicitanteRepository) {
        this.datosContablesProvider = datosContablesProvider;
        this.solicitanteRepository = solicitanteRepository;
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
    public Optional<DatosContablesResponse> readBySolicitante(int id) {
        try {
            return datosContablesProvider.findBySolicitanteId(id)
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
            ));
        } catch (Exception e) {
            log.error("Error al buscar Datos Contables por Solicitante", e.getMessage());
            throw new EntityNotFoundException("Error al buscar Datos Contables por Solicitante"+ e.getMessage());
        }
    }

    public MensajeStrResponse create(Optional<DatosContablesRequest> entrada) {
        log.info("Guardando Datos Contables");
        DatosContablesEntity request = new DatosContablesEntity();

        if (entrada.isEmpty()) {
            log.error("Solcitud de Datos Contables vacia");
            throw new IllegalArgumentException("Solcitud de Datos Contables vacia");
        } else {
            try {
                request.setSolicitante(solicitanteRepository.findById(entrada.get().getSolicitante()).get());
            } catch (Exception e) {
                log.error("Error al buscar Solicitante", e.getMessage());
                throw new EntityNotFoundException("Error al buscar Solicitante "+ e.getMessage());
            }
            request.setVentasPromedio(entrada.get().getVentasPromedio());
            request.setPasivos(entrada.get().getPasivos());
            request.setActivos(entrada.get().getActivos());
            request.setActivo(true);
        }

        try {
            datosContablesProvider.save(request);
            return new MensajeStrResponse("Datos Contables guardados correctamente");
        } catch (Exception e) {
            log.error("Error al guardar Datos Contables", e.getMessage());
            throw new EntityNotFoundException("Error al guardar Datos Contables"+ e.getMessage());
        }
    }

    @Override
    public MensajeStrResponse updateById(Optional<DatosContablesRequest> entrada, int id) {
        log.info("Editando Datos Contables por Id");
        DatosContablesEntity request = new DatosContablesEntity();
        if (entrada.isEmpty()) {
            log.error("Solcitud de Datos Contables vacia");
            throw new IllegalArgumentException("Solcitud de Datos Contables vacia");
        }
        request.setId(id);
        request.setSolicitante(solicitanteRepository.findById(entrada.get().getSolicitante()).get());
        request.setVentasPromedio(entrada.get().getVentasPromedio());
        request.setPasivos(entrada.get().getPasivos());
        request.setActivos(entrada.get().getActivos());
        request.setActivo(true);

        try {
            datosContablesProvider.save(request);
            return new MensajeStrResponse("Datos Contables editados correctamente");
        } catch (Exception e) {
            log.error("Error al editar Datos Contables por Id", e.getMessage());
            throw new EntityNotFoundException("Error al editar Datos Contables por Id"+ e.getMessage());
        }
    }

    public MensajeStrResponse deleteById(int id) {
        log.info("Eliminando Datos Contables por Id");
        try {
            datosContablesProvider.findById(id).ifPresent(
                response -> {
                    response.setActivo(false);
                datosContablesProvider.save(response);}
            );
            return new MensajeStrResponse("Datos Contables eliminados correctamente");
        } catch (Exception e) {
            log.error("Error al eliminar Datos Contables por Id", e.getMessage());
            throw new EntityNotFoundException("Error al eliminar Datos Contables por Id"+ e.getMessage());
        }
    }
}
