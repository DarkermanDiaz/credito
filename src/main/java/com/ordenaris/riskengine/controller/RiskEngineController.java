package com.ordenaris.riskengine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordenaris.riskengine.model.RiskResults;
import com.ordenaris.riskengine.motor.RiskEngine;


@RestController
@RequestMapping("/api/v1")

public class RiskEngineController {

    private final RiskEngine riskEngine;

    public RiskEngineController(RiskEngine riskEngine) {
        this.riskEngine = riskEngine;
    }

    @GetMapping("/risk/{id}")
    public RiskResults calculateRisk(@PathVariable int id) {
        return riskEngine.calculateRisk(id);
    }
    
}
