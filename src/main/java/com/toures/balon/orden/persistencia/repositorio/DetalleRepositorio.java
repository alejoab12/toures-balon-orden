package com.toures.balon.orden.persistencia.repositorio;

import com.toures.balon.orden.persistencia.entidades.Detalle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface DetalleRepositorio extends CrudRepository<Detalle, String> {

    Optional<List<Detalle>> findByFechaInicioBetween(Timestamp fechaInicial, Timestamp fechaFinal);
    @Query("select orden.id from Detalle  where idProducto=:idProducto")
    Optional<List<String>> buscarOrdenesPorIdProducto(@Param("idProducto") String idProducto);
}
