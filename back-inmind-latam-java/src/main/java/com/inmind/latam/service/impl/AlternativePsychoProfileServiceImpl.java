package com.inmind.latam.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.inmind.latam.dto.PsychologistDto;
import com.inmind.latam.dto.PsychologistProfileDto;
import com.inmind.latam.model.Psychologist;
import com.inmind.latam.repository.IAlternativePsychoProfileRepository;
import com.inmind.latam.service.IAlternativePsychoProfileService;
import com.inmind.latam.service.IPsychologistService;

import static com.inmind.latam.constant.StatusValues.MATCHING;;

/**
 * Implementation of the IAlternativePsychoProfileService interface for managing AlternativePsychoProfile entities.
 * <p>
 * This class provides functionality for determining psychologist profiles based on alternatives and questionnaire answers.
 *
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.service.IAlternativePsychoProfileService
 */
@Service
public class AlternativePsychoProfileServiceImpl implements IAlternativePsychoProfileService {

	private final IAlternativePsychoProfileRepository alternativePsychoProfileRepository;
	private final IPsychologistService psychologistService;


	public AlternativePsychoProfileServiceImpl(IAlternativePsychoProfileRepository alternativePsychoProfileRepository,
			IPsychologistService psychologistService) {
		
		this.alternativePsychoProfileRepository = alternativePsychoProfileRepository;
		this.psychologistService = psychologistService;
	}

	/**
	 * Determines the psychologist profile based on the provided alternatives and questionnaire answers.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @param profileType the profile type
	 * @param answersCache the list of answers from the questionnaire
	 * @return the psychologist profile DTO
	 * @throws IllegalArgumentException if the answersCache is null
	 * @throws NoSuchElementException if no psychologist is found with the provided alternatives
	 */
	@Override
	public PsychologistProfileDto determinePsychologistWithAlternatives(String idQuestionnaire, String profileType, List<String> answersCache) {
		if (answersCache == null || answersCache == null) {
			throw new IllegalArgumentException(
					"ResponseQuestionMemoryDto cannot be null and must contain responses and a profile.");
		}

	    List<Object[]> results = alternativePsychoProfileRepository.findPsychoWithMostAlternatives(profileType, answersCache);

	    if (results == null || results.isEmpty()) {
	        throw new NoSuchElementException("No psychologist found with the provided alternatives.");
	    }

	    List<PsychologistDto> psychologistList = results.stream()
	        .map(data -> {
	            String idPsychologist = data[0] != null ? data[0].toString() : null;
	            
	            Psychologist psychologist = psychologistService.getPsychologistById(idPsychologist);
	            String name = capitalize(psychologist.getName()) + " " + capitalize(psychologist.getLastname());
	            
	            return new PsychologistDto(idPsychologist, name, psychologist.getLinkProfile(), psychologist.getImage());
	        })
	        .collect(Collectors.toList());

	    Long countAlternatives = ((Number) results.get(0)[1]).longValue();
	    
	    return new PsychologistProfileDto(idQuestionnaire, MATCHING, psychologistList, countAlternatives);
	}

	/**
	 * Capitalizes each word in the given string value.
	 *
	 * @param value the string to capitalize
	 * @return the capitalized string
	 */
	private String capitalize(String value) {
	    if (value == null || value.isBlank()) return "";
	    return Arrays.stream(value.trim().split("\\s+"))
	        .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
	        .collect(Collectors.joining(" "));
	}
}
