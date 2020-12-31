package com.stacksnow.flow.services;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "ML / AI OPS - Web Services",
        description = "Exposes RESTFul services to automate ML/AI operations through DAG pipeline"),
        externalDocs = @ExternalDocumentation(description = "Flow Designer IDE", url = "http://localhost:3000/"))
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
