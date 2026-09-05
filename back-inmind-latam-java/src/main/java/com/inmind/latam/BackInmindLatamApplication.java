package com.inmind.latam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Main class of the InMind Latam application.
 * This class initializes the Spring Boot application and configures distributed caching.
 * 
 * The application implements a layered architecture that includes:
 * - Presentation Layer (Controllers)
 * - Service Layer (Services)
 * - Persistence Layer (Repositories)
 * - Model Layer (Models)
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see org.springframework.boot.SpringApplication
 * @see org.springframework.cache.annotation.EnableCaching
 */
@SpringBootApplication
@EnableCaching
public class BackInmindLatamApplication {

	/**
	 * Main method that starts the Spring Boot application.
	 * Disables devtools auto-restart to prevent issues in production.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(BackInmindLatamApplication.class, args);
	}

}
