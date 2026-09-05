package com.inmind.latam.service.impl;

import org.springframework.stereotype.Service;

import com.inmind.latam.exception.ResourceNotFoundException;
import com.inmind.latam.model.TransitionType;
import com.inmind.latam.repository.ITransitionTypeRepository;
import com.inmind.latam.service.ITransitionTypeService;

/**
 * Implementation of the ITransitionTypeService interface for managing TransitionType entities.
 * <p>
 * This class provides functionality for handling transition type data and
 * identifying transition types between profiles and alternatives.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.service.ITransitionTypeService
 * @see com.inmind.latam.model.TransitionType
 */
@Service
public class TransitionTypeServiceImpl implements ITransitionTypeService{
	
	private final ITransitionTypeRepository transitionTypeRepository;

	public TransitionTypeServiceImpl(ITransitionTypeRepository transitionTypeRepository) {
		this.transitionTypeRepository = transitionTypeRepository;
	}

	/**
	 * Identifies the transition type for a given profile and alternative.
	 *
	 * @param idProfile the profile ID
	 * @param idAlternative the alternative ID
	 * @return the transition type entity
	 * @throws ResourceNotFoundException if no transition type is found for the given IDs
	 */
	@Override
	public TransitionType identifyTranstionType(String idProfile, String idAlternative) {
		TransitionType transitionType = transitionTypeRepository.findByIdProfileAndIdAlternative(idProfile, idAlternative).orElseThrow(
				() -> new ResourceNotFoundException("Transition type not found with: " + idProfile + " - " + idAlternative));
		
		return transitionType;
	}

}
