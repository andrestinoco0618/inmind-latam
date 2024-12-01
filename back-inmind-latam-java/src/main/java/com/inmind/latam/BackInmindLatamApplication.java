package com.inmind.latam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BackInmindLatamApplication {

	public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(BackInmindLatamApplication.class, args);
	}

}
