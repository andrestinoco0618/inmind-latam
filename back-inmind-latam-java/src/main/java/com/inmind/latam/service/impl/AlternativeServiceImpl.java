package com.inmind.latam.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.repository.IAlternativeRepository;
import com.inmind.latam.service.IAlternativeService;

/**
 * Implementation of the IAlternativeService interface for managing Alternative entities.
 *
 * This class provides functionality for handling alternative data and retrieving alternatives by question ID.
 *
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.service.IAlternativeService
 */
@Service
public class AlternativeServiceImpl implements IAlternativeService{
	
	private final IAlternativeRepository alternativeRepository;

    public AlternativeServiceImpl(IAlternativeRepository alternativeRepository) {
        this.alternativeRepository = alternativeRepository;
    }

    /**
     * Retrieves a list of alternatives for a given question ID.
     *
     * @param questionId the ID of the question
     * @return the list of alternative DTOs
     */
	@Override
	public List<AlternativeDto> getAlternativesByQuestionId(String questionId) {
        return alternativeRepository.findByQuestion_IdQuestion(questionId)
                .stream()
                .map(alternative -> new AlternativeDto(alternative.getIdAlternative(), alternative.getTextAlternative()))
                .collect(Collectors.toList());
	}

}