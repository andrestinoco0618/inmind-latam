package com.inmind.latam.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Data Transfer Object for question response information.
 * <p>
 * This record contains:
 * - Current status
 * - Question position
 * - Questionnaire identifier
 * - Question identifier
 * - Question type identifier
 * - Question title
 * - List of answer options
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.dto.QuestionnaireResponse
 * @see com.inmind.latam.dto.AlternativeDto
 */
public record QuestionResponseDto(
		/** The current status of the questionnaire */
		String status,
		/** The position of the question in the sequence */
		Integer positionQuestion,
		/** The unique identifier of the questionnaire */
		String idQuestionnaire,
		/** The unique identifier of the question */
		String idQuestion,
		/** The type identifier of the question */
		String idQuestionType,
		/** The title or text of the question */
		String title,
		/** List of possible answer options */
		List<AlternativeDto> optionsAnswer)
implements QuestionnaireResponse, Serializable {}