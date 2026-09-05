package com.inmind.latam.service;

import java.util.List;

import com.inmind.latam.model.QuestionType;

/**
 * Service interface for managing QuestionType entities.
 * <p>
 * This interface provides operations for handling question type data
 * and retrieving question type information.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.model.QuestionType
 */
public interface IQuestionTypeService {
	
	/**
	 * Gets a question type by its unique identifier.
	 * 
	 * @param idQuestionType the unique identifier of the question type
	 * @return the question type entity
	 */
	public QuestionType getQuestionTypeById(String idQuestionType);

	/**
	 * Gets all available question types.
	 * 
	 * @return list of all question types
	 */
	public List<QuestionType> getAll();
	
}
