package com.dealership.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI/Swagger configuration for E-Dealership API
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-Dealership API")
                        .description("A comprehensive REST API for managing car dealerships, car models, and pricing information. " +
                                "This API provides endpoints for CRUD operations on dealerships, car models, and pricing data, " +
                                "along with advanced search and filtering capabilities.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("E-Dealership API Team")
                                .email("support@e-dealership.com")
                                .url("https://github.com/dealership/e-dealership-api"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:9091")
                                .description("Development Server"),
                        new Server()
                                .url("https://api.e-dealership.com")
                                .description("Production Server")));
    }
}