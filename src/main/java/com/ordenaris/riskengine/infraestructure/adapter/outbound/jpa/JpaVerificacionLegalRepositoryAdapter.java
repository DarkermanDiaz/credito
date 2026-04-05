package com.ordenaris.riskengine.infraestructure.adapter.outbound.jpa;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ordenaris.riskengine.domain.entity.VerificacionLegalEntity;
import com.ordenaris.riskengine.domain.port.outbound.VerificacionLegalRepositoryPort;
import com.ordenaris.riskengine.infraestructure.adapter.outbound.IVerificacionLegalProvider;

@Component
public class JpaVerificacionLegalRepositoryAdapter implements VerificacionLegalRepositoryPort {

    private final IVerificacionLegalProvider provider;

    public JpaVerificacionLegalRepositoryAdapter(IVerificacionLegalProvider provider) {
        this.provider = provider;
    }

    @Override
    public List<VerificacionLegalEntity> findByActivoTrue() {
        return provider.findByActivoTrue();
    }

    @Override
    public List<VerificacionLegalEntity> findBySolicitanteId(int id) {
        return provider.findBySolicitanteId(id);
    }

    @Override
    public VerificacionLegalEntity save(VerificacionLegalEntity entity) {
        return provider.save(entity);
    }
}