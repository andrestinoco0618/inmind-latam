package com.inmind.latam.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ExceptionResponseDto(
    String timestamp,
    String status,
    String error,
    String message,
    String path
) implements Serializable {
	
    public ExceptionResponseDto(String status, String error, String message, String path) {
        this(LocalDateTime.now().toString(), status, error, message, path);
    }
    
}
