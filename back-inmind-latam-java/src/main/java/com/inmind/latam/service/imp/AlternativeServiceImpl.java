package com.inmind.latam.service.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.repository.IAlternativeRepository;
import com.inmind.latam.service.IAlternativeService;

@Service
public class AlternativeServiceImpl implements IAlternativeService{
	
	private final IAlternativeRepository alternativeRepository;

    public AlternativeServiceImpl(IAlternativeRepository alternativeRepository) {
        this.alternativeRepository = alternativeRepository;
    }

	@Override
	public List<AlternativeDto> getAlternativesByQuestionId(String questionId) {
        return alternativeRepository.findByQuestion_IdQuestion(questionId)
                .stream()
                .map(alternative -> new AlternativeDto(alternative.getIdAlternative(), alternative.getTextAlternative()))
                .collect(Collectors.toList());
	}

}