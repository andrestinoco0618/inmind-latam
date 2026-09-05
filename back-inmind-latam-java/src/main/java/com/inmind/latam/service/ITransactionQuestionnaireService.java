package com.inmind.latam.service;

import com.inmind.latam.dto.QuestionnaireResponse;
import com.inmind.latam.dto.TransactionQuestionnaireDto;

/**
 * Service interface for managing questionnaire transactions.
 * <p>
 * This interface provides operations for handling questionnaire flow,
 * including starting questionnaires, processing transactions, and
 * updating psychologist selections.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.dto.QuestionnaireResponse
 * @see com.inmind.latam.dto.TransactionQuestionnaireDto
 */
public interface ITransactionQuestionnaireService {

	/**
	 * Starts a new questionnaire with the specified profile type.
	 * 
	 * @param profileType the type of profile for the questionnaire
	 * @return the questionnaire response
	 */
	public QuestionnaireResponse startQuestionnaire(String profileType);

	/**
	 * Processes a questionnaire transaction.
	 * 
	 * @param transactionQuestionnaire the transaction data
	 * @return the questionnaire response
	 */
	public QuestionnaireResponse transactionQuestionnaire(TransactionQuestionnaireDto transactionQuestionnaire);

	/**
	 * Updates the selected psychologist for a questionnaire.
	 * 
	 * @param idQuestionnaire the ID of the questionnaire
	 * @param idPsycho the ID of the selected psychologist
	 * @return true if the update was successful
	 */
	public Boolean updateSelectPsycho(String idQuestionnaire, String idPsycho);
	
}
