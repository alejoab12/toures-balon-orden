package com.toures.balon.orden.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.toures.balon.orden.persistencia.entidades.Detalle;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class DetalleModelo {
    private String id;
    private String tipoProveedor;
    private Double precio;
    private Double impuesto;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private String descripcion;
    private String idCliente;

    public DetalleModelo() {
    }

    public DetalleModelo(Detalle detalle) {
        this.id = detalle.getId();
        this.tipoProveedor = detalle.getTipoProveedor();
        this.precio = detalle.getPrecio();
        this.impuesto = detalle.getImpuesto();
        this.fechaInicio = detalle.getFechaInicio();
        this.fechaFin = detalle.getFechaFin();
        this.descripcion = detalle.getDescripcion();
        this.idCliente=detalle.getClienteId().getId();
    }

    public Detalle toEntity() {
        Detalle detalle = new Detalle();
        detalle.setId(this.id);
        detalle.setDescripcion(this.descripcion);
        detalle.setFechaInicio(this.fechaInicio);
        detalle.setFechaFin(this.fechaFin);
        detalle.setPrecio(this.precio);
        detalle.setTipoProveedor(this.tipoProveedor);
        detalle.setImpuesto(this.impuesto);
        return detalle;
    }
    @JsonIgnore
    public DetalleModelo getDetalle() {
        return this;
    }
}