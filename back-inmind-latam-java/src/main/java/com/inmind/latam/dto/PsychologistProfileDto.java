package com.inmind.latam.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Data Transfer Object for psychologist profile information.
 * <p>
 * This record contains:
 * - Questionnaire identifier
 * - Current status
 * - List of available psychologists
 * - Count of alternatives
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.dto.QuestionnaireResponse
 * @see com.inmind.latam.dto.PsychologistDto
 */
public record PsychologistProfileDto (
		/** The unique identifier of the questionnaire */
		String idQuestionnaire,
		/** The current status of the questionnaire */
		String status,
		/** List of available psychologists */
		List<PsychologistDto> listPsychologist, 
		/** Total count of alternatives */
		Long countAlternatives)
implements QuestionnaireResponse, Serializable {}