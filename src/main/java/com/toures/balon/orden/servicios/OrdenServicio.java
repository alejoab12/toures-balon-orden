package com.toures.balon.orden.servicios;

import com.toures.balon.orden.modelo.*;

import java.sql.Timestamp;
import java.util.List;

public interface OrdenServicio {

    OrdenModelo consultar(String id);

    void Crear(OrdenModelo orden);

    Hash generarHash();

    List<String> consultarOrdenesPorIdProducto(String idProducto);

    InformacionClienteModelo consultarCliente(Integer tipoDocumento, String documento);

    List<RankingOrdenEstadoModelo> rankingOrdenesPorEstado();

    List<RankingClienteFacturadoModelo> rankingClientesOrdenesFacturadas(Timestamp fechaInicio, Timestamp fechaFin);

}
