package com.toures.balon.orden.modelo;

import lombok.Data;

@Data
public class RankingClienteFacturadoModelo {
    private String idCliente;
    private Integer ordenesFacturadas;

    public RankingClienteFacturadoModelo(String idCliente, Integer ordenesFacturadas) {
        this.idCliente = idCliente;
        this.ordenesFacturadas = ordenesFacturadas;
    }

    public RankingClienteFacturadoModelo() {
    }
}
