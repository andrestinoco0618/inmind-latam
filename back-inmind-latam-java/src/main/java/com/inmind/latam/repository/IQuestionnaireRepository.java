package com.inmind.latam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inmind.latam.model.Questionnaire;

/**
 * Repository interface for managing Questionnaire entities.
 * <p>
 * This interface extends JpaRepository to provide basic CRUD operations and
 * additional custom queries for Questionnaire entities.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.inmind.latam.model.Questionnaire
 */
@Repository
public interface IQuestionnaireRepository extends JpaRepository<Questionnaire, String>{
	
    /**
     * Finds a questionnaire by its name.
     * 
     * @param name The name of the questionnaire to find
     * @return An Optional containing the questionnaire if found, empty otherwise
     */
    Optional<Questionnaire> findByName(String name);
    
}
