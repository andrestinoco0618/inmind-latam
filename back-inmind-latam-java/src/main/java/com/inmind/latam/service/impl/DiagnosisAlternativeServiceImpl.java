package com.inmind.latam.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.inmind.latam.model.DiagnosisAlternative;
import com.inmind.latam.repository.IDiagnosisAlternativeRepository;
import com.inmind.latam.service.IDiagnosisAlternativeService;

/**
 * Implementation of the IDiagnosisAlternativeService interface for managing diagnosis alternatives.
 * <p>
 * This class provides functionality for handling the relationship between diagnoses and alternatives,
 * including operations to retrieve and filter alternatives based on diagnoses.
 *
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.service.IDiagnosisAlternativeService
 * @see com.inmind.latam.model.DiagnosisAlternative
 */
@Service
public class DiagnosisAlternativeServiceImpl implements IDiagnosisAlternativeService{
	
	private final IDiagnosisAlternativeRepository diagnosisAlternativeRepository;

	/**
	 * Constructs a new DiagnosisAlternativeServiceImpl with the required repository.
	 *
	 * @param diagnosisAlternativeRepository the repository for diagnosis alternatives
	 */
	public DiagnosisAlternativeServiceImpl(IDiagnosisAlternativeRepository diagnosisAlternativeRepository) {
		this.diagnosisAlternativeRepository = diagnosisAlternativeRepository;
	}

	/**
	 * Gets the list of alternative IDs that should be removed based on the given diagnoses.
	 * <p>
	 * This method filters the diagnosis alternatives based on the provided diagnosis IDs
	 * and returns a list of alternative IDs that should be removed.
	 *
	 * @param diagnosis list of diagnosis IDs to check
	 * @return list of alternative IDs that should be removed
	 */
	public List<String> getAlternativeRemoveByDiagnosis(List<String> diagnosis) {
	    List<DiagnosisAlternative> listAlternativeByDiagnosis = diagnosisAlternativeRepository.findAll();

	    return listAlternativeByDiagnosis.stream()
	        .filter(da -> diagnosis != null && diagnosis.contains(da.getIdDiagnosis()))
	        .map(DiagnosisAlternative::getIdAlternative)
	        .collect(Collectors.toList());
	}

}
