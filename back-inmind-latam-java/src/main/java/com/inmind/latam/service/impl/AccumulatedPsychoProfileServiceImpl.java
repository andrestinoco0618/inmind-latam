package com.inmind.latam.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inmind.latam.dto.PsychologistProfileDto;
import com.inmind.latam.exception.ResourceNotFoundException;
import com.inmind.latam.model.AccumulatedPsychoProfile;
import com.inmind.latam.repository.IAccumulatedPsychoProfileRepository;
import com.inmind.latam.service.IAccumulatedPsychoProfileService;

import static com.inmind.latam.constant.QuestionIdentifiers.EMPTY_STRING;

/**
 * Implementation of the accumulated psycho profile service.
 * <p>
 * This service handles operations related to accumulated psycho profiles including:
 * - Saving new profiles
 * - Retrieving profiles by questionnaire ID
 * - Updating profile information
 * - Managing psychologist selection
 * 
 * @author InMind Latam
 * @version 1.0
 */
@Service
@Transactional
public class AccumulatedPsychoProfileServiceImpl implements IAccumulatedPsychoProfileService {
	
	private final IAccumulatedPsychoProfileRepository accumulatedPsychoProfileRepository;
	
	public AccumulatedPsychoProfileServiceImpl(IAccumulatedPsychoProfileRepository accumulatedPsychoProfileRepository) {
		this.accumulatedPsychoProfileRepository = accumulatedPsychoProfileRepository;
	}

	/**
	 * Saves a new accumulated psycho profile.
	 *
	 * @param accumulatedPsychoProfile the accumulated psycho profile to save
	 * @throws IllegalArgumentException if the profile is null
	 */
	@Override
	@Transactional
	public void saveAccumulatedPsychoProfile(AccumulatedPsychoProfile accumulatedPsychoProfile) {
		if (accumulatedPsychoProfile == null) {
			throw new IllegalArgumentException("Accumulated psycho profile cannot be null");
		}
		accumulatedPsychoProfileRepository.save(accumulatedPsychoProfile);
	}

	/**
	 * Retrieves an accumulated psycho profile by questionnaire ID.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @return the accumulated psycho profile
	 * @throws IllegalArgumentException if the questionnaire ID is null or empty
	 * @throws ResourceNotFoundException if no profile is found for the given ID
	 */
	@Override
	@Transactional(readOnly = true)
	public AccumulatedPsychoProfile getAccumulatedPsychoProfileByIdQuestionnaire(String idQuestionnaire) {
		if (idQuestionnaire == null || idQuestionnaire.trim().isEmpty()) {
			throw new IllegalArgumentException("Questionnaire ID cannot be null or empty");
		}
		return accumulatedPsychoProfileRepository.findByIdQuestionnaireAnswered(idQuestionnaire)
				.orElseThrow(() -> new ResourceNotFoundException("Accumulated psycho profile not found with questionnaire ID: " + idQuestionnaire));
	}

	/**
	 * Updates an accumulated psycho profile with psychologist profile data.
	 *
	 * @param psychoProfile the psychologist profile data
	 * @param idQuestionnaire the questionnaire ID
	 * @param questionnaire the questionnaire data
	 * @throws IllegalArgumentException if any argument is null or empty
	 */
	@Override
	@Transactional
	public void updateAccumulatedPsychoProfile(PsychologistProfileDto psychoProfile, String idQuestionnaire, String questionnaire) {
		if (psychoProfile == null) {
			throw new IllegalArgumentException("Psychologist profile cannot be null");
		}
		if (idQuestionnaire == null || idQuestionnaire.trim().isEmpty()) {
			throw new IllegalArgumentException("Questionnaire ID cannot be null or empty");
		}
		if (questionnaire == null || questionnaire.trim().isEmpty()) {
			throw new IllegalArgumentException("Questionnaire cannot be null or empty");
		}

		AccumulatedPsychoProfile accumulatedPsychoProfile = getAccumulatedPsychoProfileByIdQuestionnaire(idQuestionnaire);

		accumulatedPsychoProfile.setFinalPoint(psychoProfile.countAlternatives().intValue());
		accumulatedPsychoProfile.setIdPsychologist(EMPTY_STRING);
		accumulatedPsychoProfile.setUpdatedAt(LocalDateTime.now());
		accumulatedPsychoProfile.setQuestionnaireAnswered(questionnaire);
		accumulatedPsychoProfile.setFinish(true);

		accumulatedPsychoProfileRepository.save(accumulatedPsychoProfile);
	}

	/**
	 * Updates the selected psychologist for an accumulated psycho profile.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @param idPsychologist the psychologist ID
	 * @throws IllegalArgumentException if any argument is null or empty
	 */
	@Override
	@Transactional
	public void updateSelectPsycho(String idQuestionnaire, String idPsychologist) {
		if (idQuestionnaire == null || idQuestionnaire.trim().isEmpty()) {
			throw new IllegalArgumentException("Questionnaire ID cannot be null or empty");
		}
		if (idPsychologist == null || idPsychologist.trim().isEmpty()) {
			throw new IllegalArgumentException("Psychologist ID cannot be null or empty");
		}

		AccumulatedPsychoProfile accumulatedPsychoProfile = getAccumulatedPsychoProfileByIdQuestionnaire(idQuestionnaire);
		
		accumulatedPsychoProfile.setIdPsychologist(idPsychologist);
		accumulatedPsychoProfile.setSelectPsychology(true);
		accumulatedPsychoProfile.setUpdatedAt(LocalDateTime.now());
		
		accumulatedPsychoProfileRepository.save(accumulatedPsychoProfile);
	}
}
