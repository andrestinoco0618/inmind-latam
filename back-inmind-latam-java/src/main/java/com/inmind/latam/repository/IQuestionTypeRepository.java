package com.inmind.latam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inmind.latam.model.QuestionType;

/**
 * Repository interface for managing QuestionType entities.
 * <p>
 * This interface extends JpaRepository to provide basic CRUD operations and
 * additional custom queries for QuestionType entities.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.inmind.latam.model.QuestionType
 */
@Repository
public interface IQuestionTypeRepository extends JpaRepository<QuestionType, String>{

	/**
	 * Finds a question type by its unique identifier.
	 * 
	 * @param idQuestionType the unique identifier of the question type to find
	 * @return Optional containing the question type if found
	 */
	Optional<QuestionType> findByIdQuestionType(String idQuestionType);

}
