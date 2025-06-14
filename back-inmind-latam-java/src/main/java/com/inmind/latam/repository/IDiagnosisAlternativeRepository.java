package com.inmind.latam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inmind.latam.model.DiagnosisAlternative;

/**
 * Repository interface for managing DiagnosisAlternative entities.
 * <p>
 * This interface extends JpaRepository to provide basic CRUD operations and
 * additional custom queries for DiagnosisAlternative entities.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.inmind.latam.model.DiagnosisAlternative
 */
public interface IDiagnosisAlternativeRepository extends JpaRepository<DiagnosisAlternative, String> {
	/**
	 * Finds all diagnosis alternatives for a given diagnosis.
	 * 
	 * @param diagnosis the ID of the diagnosis to find alternatives for
	 * @return list of diagnosis alternatives
	 */
	List<DiagnosisAlternative> findByIdDiagnosis(String diagnosis);
}
