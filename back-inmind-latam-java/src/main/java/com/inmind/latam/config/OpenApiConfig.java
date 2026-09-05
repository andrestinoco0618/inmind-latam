package com.inmind.latam.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI (Swagger) configuration for API documentation.
 * This class configures the general API information, including title,
 * description, version, contact information and license.
 *
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configures and returns the OpenAPI instance with customized API information.
     *
     * @return OpenAPI configured with API information
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("InMind Latam API")
                        .description("REST API for the InMind Latam system")
                        .version("1.0")
                        .contact(new Contact()
                                .name("InMind Latam")
                                .email("contacto@inmindlatam.com")
                                .url("https://inmindlatam.com"))
                        .license(new License()
                                .name("Private License")
                                .url("https://inmindlatam.com/licencia")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development Server"),
                        new Server()
                                .url("https://api.inmindlatam.com")
                                .description("Production Server")
                ));
    }
} 