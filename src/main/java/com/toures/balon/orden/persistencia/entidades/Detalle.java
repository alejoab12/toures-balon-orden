package com.toures.balon.orden.persistencia.entidades;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "detalle")
@Data
public class Detalle {
    @Id
    private String id;
    private String tipoProveedor;
    private Double precio;
    private Double impuesto;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private String descripcion;
    @ManyToOne(targetEntity = Orden.class, cascade = {CascadeType.ALL})
    @JoinColumn(name = "orden_id")
    private Orden orden;
    @ManyToOne(targetEntity = InformacionCliente.class, cascade = {CascadeType.ALL})
    @JoinColumn(name = "cliente_id")
    private InformacionCliente clienteId;
    public Detalle(String id) {
        this.id = id;
    }

    public Detalle() {
    }


}
