package com.ordenaris.riskengine.infraestructure.adapter.inbound;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordenaris.riskengine.application.usecase.CalculateRiskUseCase;
import com.ordenaris.riskengine.domain.model.RiskResults;


@RestController
@RequestMapping("/api/v1")

public class RiskEngineController {

    private final CalculateRiskUseCase calculateRiskUseCase;

    public RiskEngineController(CalculateRiskUseCase calculateRiskUseCase) {
        this.calculateRiskUseCase = calculateRiskUseCase;
    }

    @GetMapping("/risk/{id}")
    public RiskResults calculateRisk(@PathVariable int id) {
        return calculateRiskUseCase.calculateRisk(id);
    }
    
}
