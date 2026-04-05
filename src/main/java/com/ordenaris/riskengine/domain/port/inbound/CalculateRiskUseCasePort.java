package com.ordenaris.riskengine.domain.port.inbound;

import com.ordenaris.riskengine.domain.model.RiskResults;

public interface CalculateRiskUseCasePort {
    RiskResults calculateRisk(int solicitanteId);
}