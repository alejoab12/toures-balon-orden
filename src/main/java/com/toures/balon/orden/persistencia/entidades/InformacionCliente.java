package com.toures.balon.orden.persistencia.entidades;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cliente")
@Data
public class InformacionCliente {
    @Id
    private String id;
    @Column(unique = true)
    private Integer tipoDocumento;
    @Column(unique = true)
    private String documento;
    private Date fechaNacimiento;
    private String nombres;
    private String apellidos;
    @Column(unique = true)
    private String correo;
    private String telefono;
    @OneToMany(targetEntity = Detalle.class,cascade = CascadeType.ALL)
    private List<Detalle> detalles;
}
