package com.inmind.latam.service;

import java.util.List;

import com.inmind.latam.dto.QuestionDaughterDto;
import com.inmind.latam.dto.QuestionMemoryDto;

/**
 * Service interface for managing questionnaire operations and cache.
 * <p>
 * Provides operations for initializing, retrieving, updating, and managing questionnaire data and related cache.
 *
 * @author InMind Latam
 * @version 1.0
 */
public interface IQuestionnaireService {

	/**
	 * Initializes the questionnaire cache for a given questionnaire and profile type.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @param profileType the profile type
	 * @return the list of question memory DTOs
	 */
	public List<QuestionMemoryDto> initializeQuestionnaireCache(String idQuestionnarie, String profileType);

	/**
	 * Retrieves the questionnaire cache for a given questionnaire ID.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @return the list of question memory DTOs
	 */
	public List<QuestionMemoryDto> getQuestionnaireCache(String idQuestionnarie);

	/**
	 * Updates the question status in the questionnaire cache.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @param idQuestion the question ID
	 * @param questionMemoryCacheList the list of question memory DTOs
	 * @param newQuestionDaughterList the list of new daughter questions
	 * @param alternativeQuestion the alternative question
	 * @param diagnosis the list of diagnoses
	 * @return the updated list of question memory DTOs
	 */
	public List<QuestionMemoryDto> updateQuestionStatus(String idQuestionnarie, String idQuestion, List<QuestionMemoryDto> questionMemoryCacheList,
			List<QuestionDaughterDto> newQuestionDaughterList, String alternativeQuestion, List<String> diagnosis);

	/**
	 * Updates the position of a question in the questionnaire cache.
	 *
	 * @param idQuestionnarie         the questionnaire ID
	 * @param idQuestion              the question ID
	 * @param positionQuestion        the new position of the question
	 * @param questionMemoryCacheList the list of question memory DTOs
	 */
	public List<QuestionMemoryDto> updateQuestionPosition(String idQuestionnarie, String idQuestion, int positionQuestion,
			List<QuestionMemoryDto> questionMemoryCacheList);

	/**
	 * Clears the user cache for a given questionnaire.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 */
	public void clearUserCache(String idQuestionnarie);

	/**
	 * Increments the position value for a given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @return the incremented position value
	 */
	public int incrementPosition(String idQuestionnaire);

	/**
	 * Sets the position value for a given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @param newPosition the new position value
	 */
	public void setPosition(String idQuestionnaire, int newPosition);

	/**
	 * Saves the profile type in the cache for a given questionnaire.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @param profileType     the profile type
	 */
	public String saveProfileTypeCache(String idQuestionnarie, String profileType);

	/**
	 * Retrieves the profile type from the cache for a given questionnaire.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @return the profile type
	 */
	public String getProfileTypeCache(String idQuestionnarie);

	/**
	 * Saves the alternative transition in the cache for a given questionnaire.
	 *
	 * @param idQuestionnarie       the questionnaire ID
	 * @param alternativeTransition the alternative transition
	 */
	public String saveAlternativeTransitionCache(String idQuestionnarie, String alternativeTransition);

	/**
	 * Retrieves the alternative transition from the cache for a given questionnaire.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @return the alternative transition
	 */
	public String getAlternativeTransitionCache(String idQuestionnarie);

	/**
	 * Removes the alternative transition from the cache for a given questionnaire.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 */
	public void removeAlternativeTransitionCache(String idQuestionnarie);

	/**
	 * Saves the transition in the cache for a given questionnaire.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @param transition the transition
	 * @return the saved transition
	 */
	public String saveTransitionCache(String idQuestionnarie, String transition);

	/**
	 * Retrieves the transition from the cache for a given questionnaire.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @return the transition
	 */
	public String getTransitionCache(String idQuestionnarie);

	/**
	 * Saves the diagnosis list in the cache for a given questionnaire.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @param diagnosis       the list of diagnoses
	 */
	public List<String> saveDiagnosisCache(String idQuestionnarie, List<String> diagnosis);

	/**
	 * Retrieves the diagnosis list from the cache for a given questionnaire.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @return the list of diagnoses
	 */
	public List<String> getDiagnosisCache(String idQuestionnarie);

	/**
	 * Saves the level of the questionnaire in the cache.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @param levelQuestion   the level to save
	 */
	public int saveLevelQuestion(String idQuestionnaire, int levelQuestion);

	/**
	 * Retrieves the level of the questionnaire from the cache.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @return the level of the questionnaire
	 */
	public int getLevelQuestion(String idQuestionnaire);

	/**
	 * Saves the group disorders one flag for a given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 */
    public void saveGroupDisordersOne(String idQuestionnaire);

	/**
	 * Retrieves the group disorders one flag for a given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @return true if group disorders one is enabled, false otherwise
	 */
	public Boolean getGroupDisordersOne(String idQuestionnaire);

	/**
	 * Saves the exclude question flag for a given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 */
    public void saveExludeQuestion(String idQuestionnaire);

	/**
	 * Retrieves the exclude question flag for a given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @return true if exclude question is enabled, false otherwise
	 */
	public Boolean getExludeQuestion(String idQuestionnaire);

	/**
	 * Saves the selected country flag for a given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @param isValid true if the country is selected, false otherwise
	 */
    public void saveSelectedCountry(String idQuestionnaire, boolean isValid);

	/**
	 * Retrieves the selected country flag for a given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @return true if the country is selected, false otherwise
	 */
	public Boolean getSelectedCountry(String idQuestionnaire);

	/**
	 * Saves the redirect valid flag for a given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 */
    public void saveRedirectValid(String idQuestionnaire);

	/**
	 * Retrieves the redirect valid flag for a given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @return true if redirect is valid, false otherwise
	 */
	public Boolean getRedirectValid(String idQuestionnaire);
}