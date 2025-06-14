package com.inmind.latam.dto;

import java.io.Serializable;

/**
 * Data Transfer Object for redirect information.
 * <p>
 * This record contains:
 * - Current status
 * - Redirect URL or destination
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.dto.QuestionnaireResponse
 */
public record RedirectDto (
	/** The current status of the questionnaire */
	String status,
	/** The URL or destination to redirect to */
	String redirect)
implements QuestionnaireResponse, Serializable {}