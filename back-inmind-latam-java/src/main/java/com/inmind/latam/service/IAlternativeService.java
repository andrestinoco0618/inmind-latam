package com.inmind.latam.service;

import java.util.List;

import com.inmind.latam.dto.AlternativeDto;

/**
 * Service interface for managing Alternative entities.
 * <p>
 * This interface provides operations for handling alternative data
 * and retrieving alternatives for questions.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.dto.AlternativeDto
 */
public interface IAlternativeService {
	
	/**
	 * Gets the list of alternatives for a given question.
	 * 
	 * @param questionId the ID of the question to get alternatives for
	 * @return list of alternatives
	 */
	public List<AlternativeDto> getAlternativesByQuestionId(String questionId);

}
