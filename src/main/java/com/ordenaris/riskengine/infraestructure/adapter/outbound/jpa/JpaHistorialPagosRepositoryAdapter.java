package com.ordenaris.riskengine.infraestructure.adapter.outbound.jpa;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.ordenaris.riskengine.domain.entity.HistorialPagosEntity;
import com.ordenaris.riskengine.domain.port.outbound.HistorialPagosRepositoryPort;
import com.ordenaris.riskengine.infraestructure.adapter.outbound.IHistorialPagosProvider;

@Component
public class JpaHistorialPagosRepositoryAdapter implements HistorialPagosRepositoryPort {

    private final IHistorialPagosProvider provider;

    public JpaHistorialPagosRepositoryAdapter(IHistorialPagosProvider provider) {
        this.provider = provider;
    }

    @Override
    public List<HistorialPagosEntity> findByActivoTrue() {
        return provider.findByActivoTrue();
    }

    @Override
    public List<HistorialPagosEntity> findByAcreedor(String acreedor) {
        return provider.findByAcreedor(acreedor);
    }

    @Override
    public List<HistorialPagosEntity> findBySolicitanteId(int id) {
        return provider.findBySolicitanteId(id);
    }

    @Override
    public List<HistorialPagosEntity> readLastBySolicitanteId(int id, Pageable pageable) {
        return provider.readLastBySolicitanteId(id, pageable);
    }

    @Override
    public HistorialPagosEntity save(HistorialPagosEntity entity) {
        return provider.save(entity);
    }
}