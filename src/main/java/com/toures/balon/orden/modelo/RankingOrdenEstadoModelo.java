package com.toures.balon.orden.modelo;

import lombok.Data;

@Data
public class RankingOrdenEstadoModelo {
    private Integer idEstado;
    private String estado;
    private Integer cantidad;

    public RankingOrdenEstadoModelo() {
    }

    public RankingOrdenEstadoModelo(Integer idEstado, String estado, Integer cantidad) {
        this.idEstado = idEstado;
        this.estado = estado;
        this.cantidad = cantidad;
    }
}
