package com.inmind.latam.service;

import com.inmind.latam.dto.QuestionResponseDto;
import com.inmind.latam.model.Question;

public interface IQuestionService {
	
	public Question getQuestionById(String idQuestion);
	public QuestionResponseDto createQuestion(String idQuestionnaire, String idQuestion, int positionQuestion);

}
