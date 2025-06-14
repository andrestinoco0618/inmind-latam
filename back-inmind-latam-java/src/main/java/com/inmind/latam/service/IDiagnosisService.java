package com.inmind.latam.service;

import java.util.List;

import com.inmind.latam.model.Diagnosis;

/**
 * Service interface for managing Diagnosis entities.
 * <p>
 * This interface provides operations for handling diagnosis data and
 * retrieving diagnosis information.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.model.Diagnosis
 */
public interface IDiagnosisService {
	
	/**
	 * Gets all available diagnoses.
	 * 
	 * @return list of all diagnoses
	 */
	List<Diagnosis> getAll();

}