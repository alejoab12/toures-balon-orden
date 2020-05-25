package com.toures.balon.orden.util;

public enum EstadosOrden {
    VALIDACION("En Validación"), RESERVACION( "Reservada"),
    CERRADA( "Cerrada"), RECHAZADA( "Rechazada");
    private final String valor;

    EstadosOrden( String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return this.valor;
    }
}
