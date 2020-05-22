package com.toures.balon.orden.excepcion;

import lombok.Getter;

@Getter
public class ExcepcionPersonalizada  extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private Integer codeResponse;

    public ExcepcionPersonalizada(Integer codeResponse, String message) {
        super(message);
        this.codeResponse = codeResponse;
    }
}
