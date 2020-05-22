package com.toures.balon.orden.persistencia.repositorio;

import com.toures.balon.orden.persistencia.entidades.InformacionCliente;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InformacionClienteRepositorio extends CrudRepository<InformacionCliente, String> {

    Optional<InformacionCliente> findByDocumentoAndTipoDocumento(String documento, Integer tipoDocumento);
}
