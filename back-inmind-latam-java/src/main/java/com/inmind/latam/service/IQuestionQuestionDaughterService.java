package com.inmind.latam.service;

import java.util.List;

import com.inmind.latam.dto.QuestionDaughterDto;
import com.inmind.latam.dto.TransactionQuestionnaireDto;

/**
 * Service interface for managing question daughter relationships.
 * <p>
 * Provides operations for finding daughter questions by question for a given transaction questionnaire and answers cache.
 *
 * @author InMind Latam
 * @version 1.0
 */
public interface IQuestionQuestionDaughterService {

	/**
	 * Finds daughter questions by question for a given transaction questionnaire and answers cache.
	 *
	 * @param transactionQuestionnaire the transaction questionnaire DTO
	 * @param answersCache the list of answers from the cache
	 * @return the list of daughter question DTOs
	 */
	public List<QuestionDaughterDto> findQuestionDaughterByQuestion(TransactionQuestionnaireDto transactionQuestionnaire, List<String> answersCache);

}
