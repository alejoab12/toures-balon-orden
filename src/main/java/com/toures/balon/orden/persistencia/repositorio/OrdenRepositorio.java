package com.toures.balon.orden.persistencia.repositorio;

import com.toures.balon.orden.persistencia.entidades.Orden;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface OrdenRepositorio extends CrudRepository<Orden, String> {

    @Query("update Orden set estado=:estado where id=:id")
    void actualizarEstado(String id, Integer estado);

    Optional<List<Orden>> findByFechaCompraBetween(Timestamp fechaInicio, Timestamp fechaFin);


}
