package com.toures.balon.orden.servicios.Imp;

import com.toures.balon.orden.excepcion.ExcepcionPersonalizada;
import com.toures.balon.orden.modelo.*;
import com.toures.balon.orden.persistencia.entidades.Detalle;
import com.toures.balon.orden.persistencia.entidades.InformacionCliente;
import com.toures.balon.orden.persistencia.entidades.Orden;
import com.toures.balon.orden.persistencia.repositorio.DetalleRepositorio;
import com.toures.balon.orden.persistencia.repositorio.InformacionClienteRepositorio;
import com.toures.balon.orden.persistencia.repositorio.OrdenRepositorio;
import com.toures.balon.orden.servicios.OrdenServicio;
import com.toures.balon.orden.util.EstadosOrden;
import com.toures.balon.orden.util.Utilitaria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service

public class OrdenServicioImp implements OrdenServicio {
    @Autowired
    private OrdenRepositorio ordenRepositorio;
    @Autowired
    private InformacionClienteRepositorio informacionClienteRepositorio;
    @Autowired
    private DetalleRepositorio detalleRepositorio;

    @Override
    public OrdenModelo consultar(String id) {
        Optional<Orden> optionalOrden = ordenRepositorio.findById(id);
        if (!optionalOrden.isPresent()) {
            throw new ExcepcionPersonalizada(HttpStatus.NOT_FOUND.value(), Utilitaria.MSG_ORDEN_NO_ENCONTRADA.concat(id));
        }
        return constuirModelo(optionalOrden.get());
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
    public List<String> consultarOrdenesPorIdProducto(String idProducto) {
        Optional<List<String>> optionalListOrdenes = detalleRepositorio.buscarOrdenesPorIdProducto(idProducto);
        if (!optionalListOrdenes.isPresent()) {
            throw new ExcepcionPersonalizada(HttpStatus.NOT_FOUND.value(), Utilitaria.MSG_ORDENES_NO_ENCONTRADAS);
        }
        return optionalListOrdenes.get();
    }

    @Override
    public InformacionClienteModelo consultarCliente(Integer tipoDocumento, String documento) {
        Optional<InformacionCliente> infoClienteOptional = this.informacionClienteRepositorio.findByDocumentoAndTipoDocumento(documento, tipoDocumento);
        if (!infoClienteOptional.isPresent()) {
            throw new ExcepcionPersonalizada(HttpStatus.NOT_FOUND.value(), Utilitaria.MSG_CLIENTE_NO_ENCONTRADO);
        }
        return new InformacionClienteModelo(infoClienteOptional.get());
    }

    @Override
    public List<RankingOrdenEstadoModelo> rankingOrdenesPorEstado() {
        List<RankingOrdenEstadoModelo> listaRankingPorEstado = new ArrayList<>();
        Map<Integer, List<Orden>> mapOrdenesPorEstado = StreamSupport
                .stream(ordenRepositorio.findAll().spliterator(), true)
                .collect(Collectors.groupingBy(Orden::getEstado));
        for (Integer key : mapOrdenesPorEstado.keySet()) {
            listaRankingPorEstado.add(new RankingOrdenEstadoModelo(key, consultarNombreEstado(key), mapOrdenesPorEstado.get(key).size()));
        }
        listaRankingPorEstado.sort(Comparator.comparingInt(RankingOrdenEstadoModelo::getCantidad).reversed());
        return listaRankingPorEstado;
    }

    @Override
    public List<RankingClienteFacturadoModelo> rankingClientesOrdenesFacturadas(Timestamp fechaInicio, Timestamp fechaFin) {
        Optional<List<Orden>> listaOrdenesRangoFecha = this.ordenRepositorio.findByFechaCompraBetween(fechaInicio, fechaFin);
        List<RankingClienteFacturadoModelo> listaClienteOrdenFacturado = new ArrayList<>();
        if (!listaOrdenesRangoFecha.isPresent()) {
            throw new ExcepcionPersonalizada(HttpStatus.NOT_FOUND.value(), Utilitaria.MSG_NO_HAY_ORDENES_X_FECHA);
        }
        Map<String, List<Orden>> mapRankingOrdenPorCliente =
                listaOrdenesRangoFecha.get().stream().collect(Collectors.groupingBy(Orden::getIdUsuario));
        for (String key : mapRankingOrdenPorCliente.keySet()) {
            listaClienteOrdenFacturado.add(new RankingClienteFacturadoModelo(key, mapRankingOrdenPorCliente.get(key).size()));
        }
        listaClienteOrdenFacturado.sort(Comparator.comparingInt(RankingClienteFacturadoModelo::getOrdenesFacturadas).reversed());
        return listaClienteOrdenFacturado;
    }

    private OrdenModelo constuirModelo(Orden orden) {
        List<DetalleModelo> listaDetalleModelo = new ArrayList<>();
        Map<String, InformacionClienteModelo> informacionClienteModeloMap = new HashMap<>();
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

    private String consultarNombreEstado(Integer estado) {
        String nombreEstado = null;
        switch (estado) {
            case 1: {
                nombreEstado = EstadosOrden.VALIDACION.getValor();
                break;
            }
            case 2: {
                nombreEstado = EstadosOrden.RESERVACION.getValor();
                break;
            }
            case 3: {
                nombreEstado = EstadosOrden.CERRADA.getValor();
                break;
            }
            case 4: {
                nombreEstado = EstadosOrden.RECHAZADA.getValor();
                break;
            }
            default: {
                nombreEstado = EstadosOrden.VALIDACION.getValor();
            }
        }
        return nombreEstado;
    }
}
