package com.inmind.latam.service;

import java.util.List;

/**
 * Service interface for managing diagnosis alternatives.
 * <p>
 * This interface provides operations for handling diagnosis alternatives
 * and their relationships with diagnoses.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
public interface IDiagnosisAlternativeService {

	/**
	 * Gets the list of alternative IDs to remove based on the given diagnoses.
	 * 
	 * @param diagnosis list of diagnosis IDs
	 * @return list of alternative IDs to remove
	 */
	List<String> getAlternativeRemoveByDiagnosis(List<String> diagnosis);
	
}
