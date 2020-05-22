package com.toures.balon.orden.excepcion.handler;

import com.toures.balon.orden.excepcion.ExcepcionPersonalizada;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExcepcionPersonalizadaHandler {
    @ExceptionHandler(value = ExcepcionPersonalizada.class)
    public ResponseEntity<Object> exception(ExcepcionPersonalizada exception) {
        return ResponseEntity.status(exception.getCodeResponse().intValue()).body(exception.getMessage());
    }
}

