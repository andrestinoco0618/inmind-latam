package com.inmind.latam.service;

import java.util.List;

import com.inmind.latam.dto.QuestionnaireResponse;
import com.inmind.latam.model.Question;

/**
 * Service interface for managing Question entities.
 * <p>
 * This interface provides operations for handling question data,
 * creating questions, and managing questionnaire flow.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.model.Question
 * @see com.inmind.latam.dto.QuestionnaireResponse
 */
public interface IQuestionService {
	
	/**
	 * Gets a question by its unique identifier.
	 * 
	 * @param idQuestion the unique identifier of the question
	 * @return the question entity
	 */
	public Question getQuestionById(String idQuestion);

	/**
	 * Creates a new question in a questionnaire.
	 * 
	 * @param idQuestionnaire the ID of the questionnaire
	 * @return the questionnaire response
	 */
	public QuestionnaireResponse createQuestion(String idQuestionnaire);

	/**
	 * Creates a new question in a questionnaire at a specific position.
	 * 
	 * @param idQuestionnaire the ID of the questionnaire
	 * @param idQuestion the ID of the question to create
	 * @param positionQuestion the position in the questionnaire
	 * @return the questionnaire response
	 */
	public QuestionnaireResponse createQuestion(String idQuestionnaire, String idQuestion, int positionQuestion);

	/**
	 * Creates a new question in a questionnaire at a specific position with response answers.
	 * 
	 * @param idQuestionnaire the ID of the questionnaire
	 * @param idQuestion the ID of the question to create
	 * @param positionQuestion the position in the questionnaire
	 * @param responseAnswer the list of response answers
	 * @return the questionnaire response
	 */
	public QuestionnaireResponse createQuestion(String idQuestionnaire, String idQuestion, int positionQuestion, List<String> responseAnswer);

}
