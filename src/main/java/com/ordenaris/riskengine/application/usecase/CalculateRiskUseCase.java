package com.ordenaris.riskengine.application.usecase;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ordenaris.riskengine.domain.model.DatosContablesResponse;
import com.ordenaris.riskengine.domain.model.HistorialPagosResponse;
import com.ordenaris.riskengine.domain.model.RiskResults;
import com.ordenaris.riskengine.domain.model.SolicitanteResponse;
import com.ordenaris.riskengine.domain.model.VerificacionLegalResponse;
import com.ordenaris.riskengine.domain.port.inbound.CalculateRiskUseCasePort;
import com.ordenaris.riskengine.domain.port.outbound.DatosContablesRepositoryPort;
import com.ordenaris.riskengine.domain.port.outbound.HistorialPagosRepositoryPort;
import com.ordenaris.riskengine.domain.port.outbound.SolicitanteRepositoryPort;
import com.ordenaris.riskengine.domain.port.outbound.VerificacionLegalRepositoryPort;
import com.ordenaris.riskengine.domain.service.RiskCalculator;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CalculateRiskUseCase implements CalculateRiskUseCasePort {

    private final SolicitanteRepositoryPort solicitanteRepository;
    private final DatosContablesRepositoryPort datosContablesRepository;
    private final HistorialPagosRepositoryPort historialPagosRepository;
    private final VerificacionLegalRepositoryPort verificacionLegalRepository;
    private final RiskCalculator riskCalculator;

    public CalculateRiskUseCase(SolicitanteRepositoryPort solicitanteRepository,
                                DatosContablesRepositoryPort datosContablesRepository,
                                HistorialPagosRepositoryPort historialPagosRepository,
                                VerificacionLegalRepositoryPort verificacionLegalRepository,
                                RiskCalculator riskCalculator) {
        this.solicitanteRepository = solicitanteRepository;
        this.datosContablesRepository = datosContablesRepository;
        this.historialPagosRepository = historialPagosRepository;
        this.verificacionLegalRepository = verificacionLegalRepository;
        this.riskCalculator = riskCalculator;
    }

    @Override
    public RiskResults calculateRisk(int id) {
        log.info("Calculando riesgo para el solicitante con id: {}", id);
        SolicitanteResponse solicitante = solicitanteRepository.findById(id)
            .map(entity -> new SolicitanteResponse(
                entity.getId(),
                entity.getEmpresaId(),
                entity.getMontoSolicitado(),
                entity.getProductoFinanciero(),
                entity.getFechaSolicitud()
            ))
            .orElseThrow(EntityNotFoundException::new);

        log.info("Solicitando Datos Contables para el solicitante con id: {}", id);
        DatosContablesResponse datosContables = datosContablesRepository.findBySolicitanteId(id)
            .map(entity -> new DatosContablesResponse(
                entity.getId(),
                new SolicitanteResponse(
                    entity.getSolicitante().getId(),
                    entity.getSolicitante().getEmpresaId(),
                    entity.getSolicitante().getMontoSolicitado(),
                    entity.getSolicitante().getProductoFinanciero(),
                    entity.getSolicitante().getFechaSolicitud()
                ),
                entity.getVentasPromedio(),
                entity.getPasivos(),
                entity.getActivos()
            ))
            .orElseThrow(EntityNotFoundException::new);

        List<HistorialPagosResponse> historialPagos = historialPagosRepository.findBySolicitanteId(id)
            .stream()
            .map(entity -> new HistorialPagosResponse(
                entity.getId(),
                new SolicitanteResponse(
                    entity.getSolicitante().getId(),
                    entity.getSolicitante().getEmpresaId(),
                    entity.getSolicitante().getMontoSolicitado(),
                    entity.getSolicitante().getProductoFinanciero(),
                    entity.getSolicitante().getFechaSolicitud()
                ),
                entity.getFecha(),
                entity.getMonto(),
                entity.getAcreedor(),
                entity.isActivo()
            ))
            .toList();

        Pageable pageable = PageRequest.of(0, 12);
        List<HistorialPagosResponse> historialPagoLast = historialPagosRepository.readLastBySolicitanteId(id, pageable)
            .stream()
            .map(entity -> new HistorialPagosResponse(
                entity.getId(),
                new SolicitanteResponse(
                    entity.getSolicitante().getId(),
                    entity.getSolicitante().getEmpresaId(),
                    entity.getSolicitante().getMontoSolicitado(),
                    entity.getSolicitante().getProductoFinanciero(),
                    entity.getSolicitante().getFechaSolicitud()
                ),
                entity.getFecha(),
                entity.getMonto(),
                entity.getAcreedor(),
                entity.isActivo()
            ))
            .toList();

        List<VerificacionLegalResponse> verificacionLegal = verificacionLegalRepository.findBySolicitanteId(id)
            .stream()
            .map(entity -> new VerificacionLegalResponse(
                entity.getId(),
                new SolicitanteResponse(
                    entity.getSolicitante().getId(),
                    entity.getSolicitante().getEmpresaId(),
                    entity.getSolicitante().getMontoSolicitado(),
                    entity.getSolicitante().getProductoFinanciero(),
                    entity.getSolicitante().getFechaSolicitud()
                ),
                entity.getTipoProceso(),
                entity.getDetalleProcesoLegal()
            ))
            .toList();

        return riskCalculator.calculateRisk(solicitante, datosContables, historialPagos, historialPagoLast, verificacionLegal);
    }
}