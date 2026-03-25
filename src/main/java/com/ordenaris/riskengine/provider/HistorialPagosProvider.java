package com.ordenaris.riskengine.provider;

public interface HistorialPagosProvider {
    public Integer getDiasAtrasoPagos();  // Días máximo en atraso
    public Double getTasaIncumplimiento();  // Porcentaje de pagos incumplidos
    public Integer getCuotasIncumplidas();  // Número de cuotas sin pagar
    public Double getMontoAtrasado();  // Monto total en atraso
    public Integer getHistorialPorAcreedores();  // Número de acreedores en historial
}
