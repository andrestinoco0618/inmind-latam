package com.inmind.latam.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for WebConfig class.
 * Tests the CORS configuration and other web-related settings.
 */
@ExtendWith(MockitoExtension.class)
class WebConfigTest {

    @InjectMocks
    private WebConfig webConfig;

    @Mock
    private CorsRegistry corsRegistry;

    @Mock
    private CorsRegistration corsRegistration;

    @Test
    void shouldConfigureCorsCorrectly() {
        // Arrange
        when(corsRegistry.addMapping(anyString())).thenReturn(corsRegistration);
        when(corsRegistration.allowedOriginPatterns(anyString())).thenReturn(corsRegistration);
        when(corsRegistration.allowedMethods(anyString(), anyString(), anyString())).thenReturn(corsRegistration);
        when(corsRegistration.exposedHeaders(anyString())).thenReturn(corsRegistration);
        when(corsRegistration.maxAge(3600L)).thenReturn(corsRegistration);

        WebMvcConfigurer configurer = webConfig.corsConfigurer();

        // Act
        configurer.addCorsMappings(corsRegistry);

        // Assert
        verify(corsRegistry).addMapping("/**");
        verify(corsRegistration).allowedOriginPatterns("*");
        verify(corsRegistration).allowedMethods("GET", "POST", "PATCH");
        verify(corsRegistration).exposedHeaders("*");
        verify(corsRegistration).maxAge(3600L);
    }
} 