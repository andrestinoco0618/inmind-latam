package com.inmind.latam.service;

import java.util.List;

import com.inmind.latam.dto.AlternativeDto;

public interface IAlternativeService {
	
	public List<AlternativeDto> getAlternativesByQuestionId(String questionId);

}
