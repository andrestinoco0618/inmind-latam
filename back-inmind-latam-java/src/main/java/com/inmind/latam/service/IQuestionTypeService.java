package com.inmind.latam.service;

import java.util.List;

import com.inmind.latam.model.QuestionType;

public interface IQuestionTypeService {
	
	public QuestionType getQuestionTypeById(String idQuestionType);
	public List<QuestionType> getAll();
	
}
