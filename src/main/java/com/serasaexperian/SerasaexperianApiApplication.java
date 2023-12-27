package com.serasaexperian;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info=@Info(
                title="Desafio Serasa Experian",
                description="API de cadastro de pessoas e endereços"
        )
)
public class SerasaexperianApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SerasaexperianApiApplication.class, args);
    }

}
