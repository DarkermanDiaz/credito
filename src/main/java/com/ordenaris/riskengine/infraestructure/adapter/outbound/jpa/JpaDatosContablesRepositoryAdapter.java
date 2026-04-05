package com.ordenaris.riskengine.infraestructure.adapter.outbound.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ordenaris.riskengine.domain.entity.DatosContablesEntity;
import com.ordenaris.riskengine.domain.port.outbound.DatosContablesRepositoryPort;
import com.ordenaris.riskengine.infraestructure.adapter.outbound.IDatosContablesProvider;

@Component
public class JpaDatosContablesRepositoryAdapter implements DatosContablesRepositoryPort {

    private final IDatosContablesProvider provider;

    public JpaDatosContablesRepositoryAdapter(IDatosContablesProvider provider) {
        this.provider = provider;
    }

    @Override
    public Optional<DatosContablesEntity> findBySolicitanteId(int id) {
        return provider.findBySolicitanteId(id);
    }

    @Override
    public List<DatosContablesEntity> findByActivoTrue() {
        return provider.findByActivoTrue();
    }

    @Override
    public DatosContablesEntity save(DatosContablesEntity entity) {
        return provider.save(entity);
    }
}