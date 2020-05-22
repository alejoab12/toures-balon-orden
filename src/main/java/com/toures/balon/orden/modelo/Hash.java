package com.toures.balon.orden.modelo;

import lombok.Data;

@Data
public class Hash {
    private String code;

    public Hash() {
    }

    public Hash(String code) {
        this.code = code;
    }
}
