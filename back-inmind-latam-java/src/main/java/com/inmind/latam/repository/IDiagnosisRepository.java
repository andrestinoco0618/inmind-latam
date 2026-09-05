package com.inmind.latam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inmind.latam.model.Diagnosis;

/**
 * Repository interface for managing Diagnosis entities.
 * <p>
 * This interface extends JpaRepository to provide basic CRUD operations for
 * Diagnosis entities.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.inmind.latam.model.Diagnosis
 */
public interface IDiagnosisRepository extends JpaRepository<Diagnosis, String>{
	
}
