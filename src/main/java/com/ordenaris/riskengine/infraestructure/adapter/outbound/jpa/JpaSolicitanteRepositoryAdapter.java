package com.ordenaris.riskengine.infraestructure.adapter.outbound.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ordenaris.riskengine.domain.entity.SolicitanteEntity;
import com.ordenaris.riskengine.domain.port.outbound.SolicitanteRepositoryPort;
import com.ordenaris.riskengine.infraestructure.adapter.outbound.ISolicitanteRepository;

@Component
public class JpaSolicitanteRepositoryAdapter implements SolicitanteRepositoryPort {

    private final ISolicitanteRepository repository;

    public JpaSolicitanteRepositoryAdapter(ISolicitanteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SolicitanteEntity> findByActivoTrue() {
        return repository.findByActivoTrue();
    }

    @Override
    public Optional<SolicitanteEntity> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public SolicitanteEntity save(SolicitanteEntity entity) {
        return repository.save(entity);
    }
}