package com.inmind.latam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inmind.latam.model.Alternative;

/**
 * Repository interface for managing Alternative entities.
 * <p>
 * This interface extends JpaRepository to provide basic CRUD operations and
 * additional custom queries for Alternative entities.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.inmind.latam.model.Alternative
 */
@Repository
public interface IAlternativeRepository extends JpaRepository<Alternative, String>{

    /**
     * Finds all alternatives for a given question.
     * 
     * @param questionId the ID of the question to find alternatives for
     * @return list of alternatives
     */
    List<Alternative> findByQuestion_IdQuestion(String questionId);

}
