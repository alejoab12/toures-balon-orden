package com.toures.balon.orden.controlador;

import com.toures.balon.orden.modelo.*;
import com.toures.balon.orden.servicios.OrdenServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/orden")
public class OrdenControlador {
    @Autowired
    private OrdenServicio ordenServicio;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> crearOrden(@RequestBody OrdenModelo ordenModelo) {
        ordenServicio.Crear(ordenModelo);
        return ResponseEntity.status(201).build();
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<OrdenModelo> consultar(@RequestParam String id) {
        return ResponseEntity.ok(ordenServicio.consultar(id));
    }

    @GetMapping(value = "/cliente", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<InformacionClienteModelo> consultarCliente(@RequestParam Integer tipoDocumento, @RequestParam String documento) {
        return ResponseEntity.ok(ordenServicio.consultarCliente(tipoDocumento, documento));
    }

    @GetMapping(value = "/hash", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Hash> generarHash() {
        return ResponseEntity.ok(ordenServicio.generarHash());
    }

    @GetMapping(value = "/producto", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<String>> consultaOrdenesPorIdProducto(String idProducto) {
        return ResponseEntity.ok(ordenServicio.consultarOrdenesPorIdProducto(idProducto));
    }

    @GetMapping(value = "/ranking/estado", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<RankingOrdenEstadoModelo>> rankingEstadoOrden() {
        return ResponseEntity.ok(ordenServicio.rankingOrdenesPorEstado());
    }

    @GetMapping(value="/ranking/cliente",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<RankingClienteFacturadoModelo>> rankingProductos(@RequestParam Timestamp fechaInicio, @RequestParam Timestamp fechaFin) {
        return ResponseEntity.ok(ordenServicio.rankingClientesOrdenesFacturadas(fechaInicio, fechaFin));
    }
}
