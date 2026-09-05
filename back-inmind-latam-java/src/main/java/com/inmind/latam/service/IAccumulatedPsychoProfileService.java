package com.inmind.latam.service;

import com.inmind.latam.dto.PsychologistProfileDto;
import com.inmind.latam.model.AccumulatedPsychoProfile;


/**
 * Service interface for managing accumulated psycho profiles.
 * <p>
 * Provides operations for saving, retrieving, and updating accumulated psycho profiles and psychologist selection.
 *
 * @author InMind Latam
 * @version 1.0
 */
public interface IAccumulatedPsychoProfileService {

	/**
	 * Saves a new accumulated psycho profile.
	 *
	 * @param accumulatedPsychoProfile the accumulated psycho profile to save
	 */
	public void saveAccumulatedPsychoProfile(AccumulatedPsychoProfile accumulatedPsychoProfile);

	/**
	 * Retrieves an accumulated psycho profile by questionnaire ID.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @return the accumulated psycho profile
	 */
	public AccumulatedPsychoProfile getAccumulatedPsychoProfileByIdQuestionnaire(String idQuestionnaire);

	/**
	 * Updates an accumulated psycho profile with psychologist profile data.
	 *
	 * @param psychoProfile the psychologist profile data
	 * @param idQuestionnaire the questionnaire ID
	 * @param questionnaire the questionnaire data
	 */
	public void updateAccumulatedPsychoProfile(PsychologistProfileDto psychoProfile, String idQuestionnaire, String questionnaire);

	/**
	 * Updates the selected psychologist for an accumulated psycho profile.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @param idPsycho the psychologist ID
	 */
	public void updateSelectPsycho(String idQuestionnaire, String idPsycho);

}
