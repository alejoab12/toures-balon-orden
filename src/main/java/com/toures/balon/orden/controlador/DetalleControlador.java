package com.toures.balon.orden.controlador;

import com.toures.balon.orden.modelo.ProductoVendido;
import com.toures.balon.orden.servicios.DetalleServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/orden/detalle")
public class DetalleControlador {
    @Autowired
    private DetalleServicio detalleServicio;
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<ProductoVendido>> rankingProductos(@RequestParam Timestamp fechaInicio, @RequestParam Timestamp fechaFin) {
        return ResponseEntity.ok(detalleServicio.rankingProductoVendido(fechaInicio, fechaFin));
    }
}
