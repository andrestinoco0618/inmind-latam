package com.inmind.latam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inmind.latam.model.TransitionType;

/**
 * Repository interface for managing TransitionType entities.
 * <p>
 * This interface extends JpaRepository to provide basic CRUD operations and
 * additional custom queries for TransitionType entities.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.inmind.latam.model.TransitionType
 */
@Repository
public interface ITransitionTypeRepository extends JpaRepository<TransitionType, String>{
	
	/**
	 * Finds a transition type by profile ID and alternative ID.
	 * 
	 * @param idProfile the ID of the profile
	 * @param idAlternative the ID of the alternative
	 * @return Optional containing the transition type if found
	 */
	Optional<TransitionType> findByIdProfileAndIdAlternative(String idProfile, String idAlternative);

}
