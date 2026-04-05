package com.ordenaris.riskengine.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RiskResults {
    public enum Risk {
        RECHAZADO,
        BAJO,
        MEDIO,
        ALTO
    }

    Risk risk;
    String message;
}
