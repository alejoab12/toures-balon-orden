package com.toures.balon.orden.modelo;

import com.toures.balon.orden.persistencia.entidades.InformacionCliente;
import lombok.Data;

import java.util.Date;

@Data
public class InformacionClienteModelo {
    private String id;
    private Integer tipoDocumento;
    private String documento;
    private Date fechaNacimiento;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;


    public InformacionClienteModelo() {
    }

    public InformacionClienteModelo(InformacionCliente informacionCliente) {
        this.id=informacionCliente.getId();
        this.tipoDocumento = informacionCliente.getTipoDocumento();
        this.documento = informacionCliente.getDocumento();
        this.apellidos = informacionCliente.getApellidos();
        this.correo = informacionCliente.getCorreo();
        this.nombres = informacionCliente.getNombres();
        this.telefono = informacionCliente.getTelefono();
        this.fechaNacimiento = informacionCliente.getFechaNacimiento();
    }

    public InformacionCliente toEntity() {
        InformacionCliente informacionCliente = new InformacionCliente();
        informacionCliente.setApellidos(this.apellidos);
        informacionCliente.setCorreo(this.correo);
        informacionCliente.setId(this.id);
        informacionCliente.setNombres(this.nombres);
        informacionCliente.setFechaNacimiento(this.getFechaNacimiento());
        informacionCliente.setTelefono(this.getTelefono());
        informacionCliente.setDocumento(this.documento);
        informacionCliente.setTipoDocumento(this.tipoDocumento);
        return informacionCliente;
    }

}
