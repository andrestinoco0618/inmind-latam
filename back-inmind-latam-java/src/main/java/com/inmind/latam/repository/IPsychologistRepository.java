package com.inmind.latam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inmind.latam.model.Psychologist;

/**
 * Repository interface for managing Psychologist entities.
 * <p>
 * This interface extends JpaRepository to provide basic CRUD operations for
 * Psychologist entities.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.inmind.latam.model.Psychologist
 */
@Repository
public interface IPsychologistRepository extends JpaRepository<Psychologist, String>{
}
