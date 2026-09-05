package com.inmind.latam.service.imp;

import org.springframework.stereotype.Service;

import com.inmind.latam.exception.ResourceNotFoundException;
import com.inmind.latam.model.TransitionType;
import com.inmind.latam.repository.ITransitionTypeRepository;
import com.inmind.latam.service.ITransitionTypeService;

@Service
public class TransitionTypeServiceImpl implements ITransitionTypeService{
	
	private final ITransitionTypeRepository transitionTypeRepository;
	
	public TransitionTypeServiceImpl(ITransitionTypeRepository transitionTypeRepository) {
		this.transitionTypeRepository = transitionTypeRepository;
	}

	@Override
	public TransitionType identifyTranstionType(String idProfile, String idAlternative) {
		TransitionType transitionType = transitionTypeRepository.findByIdProfileAndIdAlternative(idProfile, idAlternative).orElseThrow(
				() -> new ResourceNotFoundException("Transition type not found with: " + idProfile + " - " + idAlternative));
		
		return transitionType;
	}

}
