package com.toures.balon.orden.servicios;

import com.toures.balon.orden.modelo.ProductoVendido;

import java.sql.Timestamp;
import java.util.List;

public interface DetalleServicio {
    List<ProductoVendido> rankingProductoVendido(Timestamp fechaInicio, Timestamp fechaFin);

}
