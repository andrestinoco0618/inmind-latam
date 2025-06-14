package com.inmind.latam.service;

import java.util.List;

import com.inmind.latam.dto.PsychologistProfileDto;

/**
 * Service interface for managing alternative psychologist profiles.
 * <p>
 * This interface provides operations for determining psychologist profiles
 * based on questionnaire answers and alternatives.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.dto.PsychologistProfileDto
 */
public interface IAlternativePsychoProfileService {

	/**
	 * Determines the appropriate psychologist profile based on questionnaire answers.
	 * 
	 * @param idQuestionnarie the ID of the questionnaire
	 * @param profileType the type of profile
	 * @param answersCache the list of cached answers
	 * @return the determined psychologist profile
	 */
	public PsychologistProfileDto determinePsychologistWithAlternatives(String idQuestionnarie, String profileType, List<String> answersCache);
	
}
