package com.libreria;

import com.libreria.errores.ErrorServicio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class main {

    public static void main(String[] args) throws ErrorServicio {
        SpringApplication.run(main.class, args);

    }

}
