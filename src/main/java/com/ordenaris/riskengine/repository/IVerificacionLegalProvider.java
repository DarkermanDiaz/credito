package com.ordenaris.riskengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ordenaris.riskengine.entity.VerificacionLegalEntity;


@Repository

public interface IVerificacionLegalProvider extends JpaRepository<VerificacionLegalEntity, Integer> {

}
