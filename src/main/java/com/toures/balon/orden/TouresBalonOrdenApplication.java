package com.toures.balon.orden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TouresBalonOrdenApplication {

    public static void main(String[] args) {
        SpringApplication.run(TouresBalonOrdenApplication.class, args);
    }

}
