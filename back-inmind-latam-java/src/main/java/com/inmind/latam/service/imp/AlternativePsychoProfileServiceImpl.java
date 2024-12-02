package com.inmind.latam.service.imp;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.inmind.latam.dto.PsychoProfileDto;
import com.inmind.latam.dto.ResponseQuestionMemoryDto;
import com.inmind.latam.repository.IAlternativePsychoProfileRepository;
import com.inmind.latam.service.IAlternativePsychoProfileService;

@Service
public class AlternativePsychoProfileServiceImpl implements IAlternativePsychoProfileService {

	private final IAlternativePsychoProfileRepository alternativePsychoProfileRepository;

	public AlternativePsychoProfileServiceImpl(IAlternativePsychoProfileRepository alternativePsychoProfileRepository) {
		this.alternativePsychoProfileRepository = alternativePsychoProfileRepository;
	}

	@Override
	public PsychoProfileDto findPsychologistWithAlternatives(ResponseQuestionMemoryDto responseQuestionMemory) {
		if (responseQuestionMemory == null || responseQuestionMemory.getResponseQuestion() == null
				|| responseQuestionMemory.getProfileType() == null) {
			throw new IllegalArgumentException(
					"ResponseQuestionMemoryDto no puede ser nulo y debe contener respuestas y un perfil.");
		}

		List<String> response = responseQuestionMemory.getResponseQuestion();
		String profileType = responseQuestionMemory.getProfileType();

		Optional<Object[]> result = alternativePsychoProfileRepository.findPsychoWithMostAlternatives(profileType,
				response);

		if (result.isPresent()) {
			Object[] data = result.get();
			if (data[0] instanceof Object[]) {
				Object[] nestedData = (Object[]) data[0];

				String idPsychologist = nestedData[0] != null ? nestedData[0].toString() : null;
				Long countAlternatives = nestedData[1] != null ? ((Number) nestedData[1]).longValue() : 0L;

				return new PsychoProfileDto(idPsychologist, countAlternatives);

			} else {
				throw new NoSuchElementException(
						"No se encontró ningún psicólogo con las alternativas proporcionadas.");
			}
		}
	    throw new NoSuchElementException("No se encontró ningún psicólogo con las alternativas proporcionadas.");
	}
}
