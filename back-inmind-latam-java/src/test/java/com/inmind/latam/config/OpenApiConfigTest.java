package com.inmind.latam.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for OpenApiConfig class.
 * Tests the configuration of OpenAPI (Swagger) documentation.
 */
@ExtendWith(MockitoExtension.class)
class OpenApiConfigTest {

    @InjectMocks
    private OpenApiConfig openApiConfig;

    @Test
    void shouldCreateOpenAPIWithCorrectInfo() {
        // Act
        OpenAPI openAPI = openApiConfig.customOpenAPI();

        // Assert
        assertThat(openAPI).isNotNull();
        assertThat(openAPI.getInfo()).isNotNull();
        
        Info info = openAPI.getInfo();
        assertThat(info.getTitle()).isEqualTo("InMind Latam API");
        assertThat(info.getDescription()).isEqualTo("REST API for the InMind Latam system");
        assertThat(info.getVersion()).isEqualTo("1.0");
        assertThat(info.getContact()).isNotNull();
        assertThat(info.getContact().getName()).isEqualTo("InMind Latam");
        assertThat(info.getContact().getEmail()).isEqualTo("contacto@inmindlatam.com");
        assertThat(info.getContact().getUrl()).isEqualTo("https://inmindlatam.com");
        assertThat(info.getLicense()).isNotNull();
        assertThat(info.getLicense().getName()).isEqualTo("Private License");
        assertThat(info.getLicense().getUrl()).isEqualTo("https://inmindlatam.com/licencia");
    }

    @Test
    void shouldCreateOpenAPIWithCorrectServers() {
        // Act
        OpenAPI openAPI = openApiConfig.customOpenAPI();

        // Assert
        assertThat(openAPI.getServers()).hasSize(2);
        
        Server devServer = openAPI.getServers().get(0);
        assertThat(devServer.getUrl()).isEqualTo("http://localhost:8080");
        assertThat(devServer.getDescription()).isEqualTo("Development Server");

        Server prodServer = openAPI.getServers().get(1);
        assertThat(prodServer.getUrl()).isEqualTo("https://api.inmindlatam.com");
        assertThat(prodServer.getDescription()).isEqualTo("Production Server");
    }
} 