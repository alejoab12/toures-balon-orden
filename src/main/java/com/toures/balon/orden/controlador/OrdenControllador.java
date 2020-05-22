package com.toures.balon.orden.controlador;

import com.toures.balon.orden.modelo.Hash;
import com.toures.balon.orden.modelo.InformacionClienteModelo;
import com.toures.balon.orden.modelo.OrdenModelo;
import com.toures.balon.orden.servicios.OrdenServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orden")
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, origins = "*")
public class OrdenControllador {
    @Autowired
    private OrdenServicio ordenServicio;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> crearOrden(@RequestBody OrdenModelo ordenModelo) {
        ordenServicio.Crear(ordenModelo);
        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<OrdenModelo> consultar(@RequestParam String id) {
        return ResponseEntity.ok(ordenServicio.consultar(id));
    }

    @GetMapping(value = "/cliente", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<InformacionClienteModelo> consultarCliente(@RequestParam Integer tipoDocumento, @RequestParam String documento) {
        return ResponseEntity.ok(ordenServicio.consultarCliente(tipoDocumento, documento));
    }
    @GetMapping(value = "/hash",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Hash> generarHash(){
        return ResponseEntity.ok(ordenServicio.generarHash());
    }
}
