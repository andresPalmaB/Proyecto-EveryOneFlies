package com.betek.everyOneFlies.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de EveryoneFlies")
                        .description("Documentación del API para el sistema de reserva de vuelos")
                        .version("1.0"));
    }
}
