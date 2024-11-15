package com.betek.everyOneFlies.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
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

    @Bean
    public OpenApiCustomizer orderTagsCustomiser() {
        return openApi -> {
            if (openApi.getTags() != null) {
                openApi.setTags(openApi.getTags().stream()
                        .sorted((tag1, tag2) -> {
                            // Define el orden deseado aquí
                            String[] desiredOrder = {"Airline", "Location", "Airport", "Flight", "Reserve"};
                            int index1 = java.util.Arrays.asList(desiredOrder).indexOf(tag1.getName());
                            int index2 = java.util.Arrays.asList(desiredOrder).indexOf(tag2.getName());
                            return Integer.compare(index1, index2);
                        })
                        .toList());
            }
        };
    }

}
