package com.toures.balon.orden.modelo;

import lombok.Getter;

@Getter
public class ProductoVendido {
    public String idProducto;
    public Integer cantidad;
    public String tipoProveedor;

    public ProductoVendido(String idProducto, Integer cantidad, String tipoProveedor) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.tipoProveedor = tipoProveedor;
    }

    public ProductoVendido() {
    }
}
