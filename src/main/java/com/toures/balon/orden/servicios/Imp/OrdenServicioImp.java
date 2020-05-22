package com.toures.balon.orden.servicios.Imp;

import com.toures.balon.orden.excepcion.ExcepcionPersonalizada;
import com.toures.balon.orden.modelo.DetalleModelo;
import com.toures.balon.orden.modelo.Hash;
import com.toures.balon.orden.modelo.InformacionClienteModelo;
import com.toures.balon.orden.modelo.OrdenModelo;
import com.toures.balon.orden.persistencia.entidades.Detalle;
import com.toures.balon.orden.persistencia.entidades.InformacionCliente;
import com.toures.balon.orden.persistencia.entidades.Orden;
import com.toures.balon.orden.persistencia.repositorio.InformacionClienteRepositorio;
import com.toures.balon.orden.persistencia.repositorio.OrdenRepositorio;
import com.toures.balon.orden.servicios.OrdenServicio;
import com.toures.balon.orden.util.Utilitaria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service

public class OrdenServicioImp implements OrdenServicio {
    @Autowired
    private OrdenRepositorio ordenRepositorio;
    @Autowired
    private InformacionClienteRepositorio informacionClienteRepositorio;

    @Override
    public OrdenModelo consultar(String id) {
        Optional<Orden> optionalOrden = ordenRepositorio.findById(id);
        List<DetalleModelo> listaDetalleModelo = new ArrayList<>();
        Map<String, InformacionClienteModelo> informacionClienteModeloMap = new HashMap<>();
        if (!optionalOrden.isPresent()) {
            throw new ExcepcionPersonalizada(HttpStatus.NOT_FOUND.value(), Utilitaria.MSG_ORDEN_NO_ENCONTRADA.concat(id));
        }
        Orden orden = optionalOrden.get();
        for (Detalle detEntidad : orden.getDetalles()) {
            if (!informacionClienteModeloMap.containsKey(detEntidad.getClienteId().getId())) {
                informacionClienteModeloMap.put(detEntidad.getClienteId().getId(), new InformacionClienteModelo(detEntidad.getClienteId()));
            }
            listaDetalleModelo.add(new DetalleModelo(detEntidad));
        }
        OrdenModelo ordenModelo = new OrdenModelo(orden);
        ordenModelo.setClientes(new ArrayList<>(informacionClienteModeloMap.values()));
        ordenModelo.setDetalles(listaDetalleModelo);
        return ordenModelo;
    }

    @Override
    @Transactional
    public void Crear(OrdenModelo ordenModelo) {
        Orden orden = ordenModelo.toEntity();
        List<Detalle> detalles = new ArrayList<>();
        Map<String, InformacionCliente> mapInfoClienteEntidad = ordenModelo.getClientes().stream().
                collect(Collectors.toMap(InformacionClienteModelo::getId, InformacionClienteModelo::toEntity));
        for (DetalleModelo detModelo : ordenModelo.getDetalles()) {
            InformacionCliente infoClienteEntidad = mapInfoClienteEntidad.get(detModelo.getIdCliente());
            Detalle detEntidad = detModelo.toEntity();
            if (Objects.isNull(infoClienteEntidad.getDetalles())) {
                infoClienteEntidad.setDetalles(new ArrayList<>());
            }
            infoClienteEntidad.getDetalles().add(detEntidad);
            detEntidad.setOrden(orden);
            detEntidad.setClienteId(infoClienteEntidad);
            detalles.add(detEntidad);
        }
        orden.setDetalles(detalles);
        ordenRepositorio.save(orden);
    }

    @Override
    public Hash generarHash() {
        return new Hash(UUID.randomUUID().toString());
    }

    @Override
    public InformacionClienteModelo consultarCliente(Integer tipoDocumento, String documento) {
        Optional<InformacionCliente> infoClienteOptional = this.informacionClienteRepositorio.findByDocumentoAndTipoDocumento(documento, tipoDocumento);
        if (!infoClienteOptional.isPresent()) {
            throw new ExcepcionPersonalizada(HttpStatus.NOT_FOUND.value(), Utilitaria.MSG_CLIENTE_NO_ENCONTRADO);
        }
        return new InformacionClienteModelo(infoClienteOptional.get());
    }
}
