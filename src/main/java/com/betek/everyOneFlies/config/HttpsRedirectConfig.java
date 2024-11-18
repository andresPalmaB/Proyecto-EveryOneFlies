package com.betek.everyOneFlies.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.net.HttpURLConnection;

@Configuration
public class HttpsRedirectConfig {

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.addErrorPages(
                new ErrorPage(HttpStatus.valueOf(HttpURLConnection.HTTP_MOVED_TEMP), "/"));
    }

}