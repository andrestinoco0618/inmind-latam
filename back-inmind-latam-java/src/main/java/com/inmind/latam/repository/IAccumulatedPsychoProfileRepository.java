package com.inmind.latam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inmind.latam.model.AccumulatedPsychoProfile;

/**
 * Repository interface for managing AccumulatedPsychoProfile entities.
 * <p>
 * This interface extends JpaRepository to provide basic CRUD operations and
 * additional custom queries for AccumulatedPsychoProfile entities.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.inmind.latam.model.AccumulatedPsychoProfile
 */
@Repository
public interface IAccumulatedPsychoProfileRepository extends JpaRepository<AccumulatedPsychoProfile, Integer>{

	/**
	 * Finds an accumulated psycho profile by questionnaire ID.
	 * 
	 * @param idQuestionnaire the ID of the questionnaire to find the profile for
	 * @return Optional containing the accumulated psycho profile if found
	 */
	public Optional<AccumulatedPsychoProfile> findByIdQuestionnaireAnswered(String idQuestionnaire);
	
}
