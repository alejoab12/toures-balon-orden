package com.toures.balon.orden.servicios.Imp;

import com.toures.balon.orden.excepcion.ExcepcionPersonalizada;
import com.toures.balon.orden.modelo.ProductoVendido;
import com.toures.balon.orden.persistencia.entidades.Detalle;
import com.toures.balon.orden.persistencia.repositorio.DetalleRepositorio;
import com.toures.balon.orden.servicios.DetalleServicio;
import com.toures.balon.orden.util.Utilitaria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class DetalleServicioImp implements DetalleServicio {
    @Autowired
    private DetalleRepositorio detalleRepositorio;

    @Override
    public List<ProductoVendido> rankingProductoVendido(Timestamp fechaInicio, Timestamp fechaFin) {
        Optional<List<Detalle>> optionalDetalles= detalleRepositorio.findByFechaInicioBetween(fechaInicio,fechaFin);
        if(!optionalDetalles.isPresent()){
            throw new ExcepcionPersonalizada(HttpStatus.NOT_FOUND.value(), Utilitaria.MSG_PRODUCTOS_NO_ENCONTRADOS);
        }
        List<Detalle> detalles=optionalDetalles.get();
        List<ProductoVendido> productosVendidos =new ArrayList<>();
        Map<String,List<Detalle>> mapDetalles = detalles.stream().collect(Collectors.groupingBy(Detalle::getIdProducto));
        for (String key : mapDetalles.keySet()) {
            productosVendidos.add(new ProductoVendido(key,mapDetalles.get(key).size(),mapDetalles.get(key).get(0).getTipoProveedor()));
        }
        productosVendidos.sort(Comparator.comparingInt(ProductoVendido::getCantidad).reversed());
        return productosVendidos;
    }
}
