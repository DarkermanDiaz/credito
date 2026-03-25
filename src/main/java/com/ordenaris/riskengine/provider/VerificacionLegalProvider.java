package com.ordenaris.riskengine.provider;

public interface VerificacionLegalProvider {
    public Boolean tieneProcesosJudiciales();  // ¿Existe(n) proceso(s) judicial(es)?
    public Integer getNumeroProcesos();  // Cantidad de procesos
    public Boolean tieneDemandas();  // ¿Hay demandas?
    public Boolean tieneEmbargos();  // ¿Hay embargos?
    public String getDetalleProcesoLegal();  // Descripción de procesos legales

}
