package com.toures.balon.orden.persistencia.repositorio;

import com.toures.balon.orden.persistencia.entidades.Orden;
import org.springframework.data.repository.CrudRepository;

public interface OrdenRepositorio extends CrudRepository<Orden,String> {
}
