package com.inmind.latam.service;

import com.inmind.latam.model.TransitionType;

/**
 * Service interface for managing transition types.
 * <p>
 * This interface provides operations for identifying and handling
 * transition types between profiles and alternatives.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.model.TransitionType
 */
public interface ITransitionTypeService {
	
	/**
	 * Identifies the transition type for a given profile and alternative.
	 * 
	 * @param idProfile the ID of the profile
	 * @param idAlternative the ID of the alternative
	 * @return the identified transition type
	 */
	public TransitionType identifyTranstionType(String idProfile, String idAlternative);

}
