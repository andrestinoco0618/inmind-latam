package com.inmind.latam.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for standardized error responses in the API.
 * <p>
 * This class encapsulates error information returned to the client when an exception occurs,
 * including:
 * - Timestamp of the error
 * - HTTP status code
 * - Error type
 * - Descriptive message
 * - Request path
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see java.io.Serializable
 */
public record ExceptionResponseDto(
    String timestamp,
    String status,
    String error,
    String message,
    String path
) implements Serializable {
	
    /**
     * Constructs a new ExceptionResponseDto with the current timestamp.
     * 
     * @param status the HTTP status code
     * @param error the type of error
     * @param message the error message
     * @param path the request path
     */
    public ExceptionResponseDto(String status, String error, String message, String path) {
        this(LocalDateTime.now().toString(), status, error, message, path);
    }
    
}
