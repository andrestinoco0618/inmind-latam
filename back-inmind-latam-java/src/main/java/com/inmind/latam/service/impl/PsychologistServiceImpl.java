package com.inmind.latam.service.impl;

import org.springframework.stereotype.Service;

import com.inmind.latam.exception.ResourceNotFoundException;
import com.inmind.latam.model.Psychologist;
import com.inmind.latam.repository.IPsychologistRepository;
import com.inmind.latam.service.IPsychologistService;

/**
 * Implementation of the IPsychologistService interface for managing Psychologist entities.
 *
 * This class provides functionality for handling psychologist data and retrieving psychologist information.
 *
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.service.IPsychologistService
 * @see com.inmind.latam.model.Psychologist
 */
@Service
public class PsychologistServiceImpl implements IPsychologistService{
	
	private final IPsychologistRepository psychologistRepository;
	
	public PsychologistServiceImpl(IPsychologistRepository psychologistRepository) {
		this.psychologistRepository = psychologistRepository;
	}

	/**
	 * Retrieves a psychologist by their unique identifier.
	 *
	 * @param idPsychologist the unique identifier of the psychologist
	 * @return the psychologist entity
	 * @throws ResourceNotFoundException if no psychologist is found with the given ID
	 */
	@Override
	public Psychologist getPsychologistById(String idPsychologist) {
		Psychologist psychologist = psychologistRepository.findById(idPsychologist).orElseThrow(
				() -> new ResourceNotFoundException("Psychologist not found with: " + idPsychologist));
		
		return psychologist;
	}

}
