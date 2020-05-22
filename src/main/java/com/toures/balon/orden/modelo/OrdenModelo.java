package com.toures.balon.orden.modelo;

import com.toures.balon.orden.persistencia.entidades.Orden;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrdenModelo {
    private String id;
    private Timestamp fechaCompra;
    private Double precioTotal;
    private Double impuestoTotal;
    private Double baseDevolucion;
    private String idUsuario;
    private List<DetalleModelo> detalles;
    private List<InformacionClienteModelo> clientes;
    public OrdenModelo(){}
    public OrdenModelo(Orden orden) {
        this.id = orden.getId();
        this.fechaCompra = orden.getFechaCompra();
        this.precioTotal = orden.getPrecioTotal();
        this.impuestoTotal = orden.getImpuestoTotal();
        this.baseDevolucion = orden.getBaseDevolucion();
        this.idUsuario = orden.getIdUsuario();
    }

    public Orden toEntity() {
        Orden orden = new Orden();
        orden.setBaseDevolucion(this.baseDevolucion);
        orden.setFechaCompra(this.fechaCompra);
        orden.setIdUsuario(this.idUsuario);
        orden.setImpuestoTotal(this.impuestoTotal);
        orden.setPrecioTotal(this.precioTotal);
        orden.setEstado(1);
        orden.setId(this.id);
        return orden;
    }
}
