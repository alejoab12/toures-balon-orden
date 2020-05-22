package com.toures.balon.orden.persistencia.repositorio;

import com.toures.balon.orden.persistencia.entidades.Detalle;
import org.springframework.data.repository.CrudRepository;

public interface DetalleRepositorio extends CrudRepository<Detalle,String> {
}
