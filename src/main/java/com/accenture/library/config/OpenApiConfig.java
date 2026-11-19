package com.accenture.library.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

/**
 * Configuración de la documentación OpenAPI (Swagger UI), con autenticación Bearer JWT.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Define la información de la API, el servidor y el esquema de seguridad Bearer JWT.
     *
     * @return la definición OpenAPI
     */
    @Bean
    public OpenAPI libraryOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST de Gestión de Biblioteca")
                        .description("API para gestionar libros, autores, categorías y préstamos de una biblioteca")
                        .version("1.0.0")
                        .contact(new Contact().name("Jorge Martín Pérez Moreno").email("jorge.martin@example.com")))
                .servers(List.of(new Server().url("http://localhost:8080").description("Servidor local")))
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Token obtenido en POST /auth/login")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}