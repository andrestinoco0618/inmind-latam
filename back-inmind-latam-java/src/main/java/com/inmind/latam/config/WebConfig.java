package com.inmind.latam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web layer configuration class.
 * <p>
 * This class provides configurations for:
 * - CORS (Cross-Origin Resource Sharing)
 * - Web resource mapping
 * - Web security settings
 * - HTTP response customization
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer
 * @see org.springframework.context.annotation.Configuration
 */
@Configuration
public class WebConfig {

    /**
     * Configures CORS settings for the application.
     * Allows cross-origin requests from any origin with specific HTTP methods.
     *
     * @return WebMvcConfigurer with CORS configuration
     */
    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods("GET", "POST", "PATCH")
                        .exposedHeaders("*")
                        .maxAge(3600L);
            }
        };
    }
}
