package com.ordenaris.riskengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ordenaris.riskengine.entity.DatosContablesEntity;

@Repository

public interface IDatosContablesProvider extends JpaRepository<DatosContablesEntity, Integer> {

}
