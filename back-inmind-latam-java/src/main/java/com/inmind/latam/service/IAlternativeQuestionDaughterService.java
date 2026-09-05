package com.inmind.latam.service;

import java.util.List;

import com.inmind.latam.dto.QuestionDaughterDto;
import com.inmind.latam.dto.TransactionQuestionnaireDto;

/**
 * Service interface for managing alternative question daughter relationships.
 * <p>
 * Provides operations for finding daughter questions by alternative for a given transaction questionnaire.
 *
 * @author InMind Latam
 * @version 1.0
 */
public interface IAlternativeQuestionDaughterService {

	/**
	 * Finds daughter questions by alternative for a given transaction questionnaire.
	 *
	 * @param transactionQuestionnaire the transaction questionnaire DTO
	 * @return the list of daughter question DTOs
	 */
	public List<QuestionDaughterDto> findQuestionDaughterByAlternative(TransactionQuestionnaireDto transactionQuestionnaire);

}
