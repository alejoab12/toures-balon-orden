package com.toures.balon.orden.servicios;

import com.toures.balon.orden.modelo.Hash;
import com.toures.balon.orden.modelo.InformacionClienteModelo;
import com.toures.balon.orden.modelo.OrdenModelo;

public interface OrdenServicio {

    OrdenModelo consultar(String id);

    void Crear(OrdenModelo orden);

    Hash generarHash();

    InformacionClienteModelo consultarCliente(Integer tipoDocumento, String documento);

}
