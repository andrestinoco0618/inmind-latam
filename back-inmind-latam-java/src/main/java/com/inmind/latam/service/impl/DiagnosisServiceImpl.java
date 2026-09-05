package com.inmind.latam.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.inmind.latam.model.Diagnosis;
import com.inmind.latam.repository.IDiagnosisRepository;
import com.inmind.latam.service.IDiagnosisService;

/**
 * Implementation of the IDiagnosisService interface for managing diagnoses.
 * <p>
 * This class provides functionality for handling diagnosis data, including operations
 * to retrieve and manage diagnoses in the system.
 *
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.service.IDiagnosisService
 * @see com.inmind.latam.model.Diagnosis
 */
@Service
public class DiagnosisServiceImpl implements IDiagnosisService{
	
	private final IDiagnosisRepository diagnosisRepository;

	/**
	 * Constructs a new DiagnosisServiceImpl with the required repository.
	 *
	 * @param diagnosisRepository the repository for diagnoses
	 */
	public DiagnosisServiceImpl(IDiagnosisRepository diagnosisRepository) {
		this.diagnosisRepository = diagnosisRepository;
	}

	/**
	 * Retrieves all available diagnoses from the system.
	 * <p>
	 * This method returns a list of all diagnoses stored in the database.
	 * If no diagnoses are found, an empty list is returned.
	 *
	 * @return list of all diagnoses, or an empty list if none are found
	 */
	@Override
	public List<Diagnosis> getAll() {
	    List<Diagnosis> diagnoses = diagnosisRepository.findAll();
	    return diagnoses != null ? diagnoses : Collections.emptyList();
	}

}
