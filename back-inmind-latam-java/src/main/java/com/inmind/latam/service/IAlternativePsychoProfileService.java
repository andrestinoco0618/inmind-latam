package com.inmind.latam.service;

import com.inmind.latam.dto.PsychoProfileDto;
import com.inmind.latam.dto.ResponseQuestionMemoryDto;

public interface IAlternativePsychoProfileService {

	public PsychoProfileDto findPsychologistWithAlternatives(ResponseQuestionMemoryDto responseQuestionMemory);
}
