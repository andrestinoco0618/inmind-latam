package com.inmind.latam.dto;

import java.io.Serializable;

/**
 * Data Transfer Object for psychologist information.
 * 
 * This record contains:
 * - Psychologist identifier
 * - Name
 * - Profile link
 * - Profile image
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
public record PsychologistDto (
		/** The unique identifier of the psychologist */
		String idPsychologist, 
		/** The name of the psychologist */
		String name, 
		/** The URL to the psychologist's profile */
		String linkProfile,
		/** The URL to the psychologist's profile image */
		String image) 
implements Serializable {}