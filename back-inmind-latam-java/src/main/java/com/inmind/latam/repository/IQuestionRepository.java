package com.inmind.latam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inmind.latam.model.Question;

/**
 * Repository interface for managing Question entities.
 * <p>
 * This interface extends JpaRepository to provide basic CRUD operations and
 * additional custom queries for Question entities.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.inmind.latam.model.Question
 */
@Repository
public interface IQuestionRepository extends JpaRepository<Question, String>{
	
	/**
	 * Finds a question by its unique identifier.
	 * 
	 * @param idQuestion The unique identifier of the question to find
	 * @return An Optional containing the question if found, empty otherwise
	 */
	Optional<Question> findByIdQuestion(String idQuestion);

}
