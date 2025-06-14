package com.inmind.latam.exception;

import java.io.Serial;

/**
 * Custom exception for resources not found in the system.
 * <p>
 * This exception is thrown when:
 * - Attempting to access a non-existent resource
 * - Performing operations on a non-existent resource
 * - Requesting information about an unavailable resource
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see java.lang.RuntimeException
 */
public class ResourceNotFoundException extends RuntimeException{

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new ResourceNotFoundException with the specified detail message.
	 * 
	 * @param message the detail message explaining the reason for the exception
	 */
	public ResourceNotFoundException(String message) {
		super(message);
	}

}