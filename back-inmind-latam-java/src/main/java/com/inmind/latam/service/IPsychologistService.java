package com.inmind.latam.service;

import com.inmind.latam.model.Psychologist;

/**
 * Service interface for managing Psychologist entities.
 * <p>
 * This interface provides operations for handling psychologist data
 * and retrieving psychologist information.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.model.Psychologist
 */
public interface IPsychologistService {
	
	/**
	 * Gets a psychologist by their unique identifier.
	 * 
	 * @param idPsychologist the unique identifier of the psychologist
	 * @return the psychologist entity
	 */
	Psychologist getPsychologistById(String idPsychologist);
}
