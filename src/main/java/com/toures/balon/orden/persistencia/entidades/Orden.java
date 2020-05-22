package com.toures.balon.orden.persistencia.entidades;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "orden")
@Data
public class Orden {
    @Id
    private String id;
    private Timestamp fechaCompra;
    private Double precioTotal;
    private Double impuestoTotal;
    private Double baseDevolucion;
    private String idUsuario;
    private Integer estado;
    @OneToMany(targetEntity = Detalle.class, cascade = {CascadeType.ALL})
    private List<Detalle> detalles;
}
