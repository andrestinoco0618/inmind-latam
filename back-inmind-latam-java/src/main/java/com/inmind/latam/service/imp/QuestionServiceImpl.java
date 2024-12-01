package com.inmind.latam.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.dto.QuestionResponseDto;
import com.inmind.latam.exception.ResourceNotFoundException;
import com.inmind.latam.model.Question;
import com.inmind.latam.model.QuestionType;
import com.inmind.latam.repository.IQuestionRepository;
import com.inmind.latam.service.IAlternativeService;
import com.inmind.latam.service.IQuestionService;
import com.inmind.latam.service.IQuestionTypeService;

@Service
public class QuestionServiceImpl implements IQuestionService{
	
	private final IQuestionRepository questionRepository;
	private final IQuestionTypeService questionTypeService;
	private final IAlternativeService alternativeService;

    public QuestionServiceImpl(IQuestionRepository questionRepository, IQuestionTypeService questionTypeService, IAlternativeService alternativeService) {
        this.questionRepository = questionRepository;
        this.questionTypeService = questionTypeService;
        this.alternativeService = alternativeService;
    }

	@Override
	public Question getQuestionById(String idQuestion){
		Question question = questionRepository.findByIdQuestion(idQuestion).orElseThrow(
				() -> new ResourceNotFoundException("Question not found with: " + idQuestion));
		
		return question;
	}
	
	public QuestionResponseDto createQuestion(String idQuestionnaire, String idQuestion, int positionQuestion){
		Question question = getQuestionById(idQuestion);
		QuestionType questionType = questionTypeService.getQuestionTypeById(question.getQuestionType().getIdQuestionType());
		List<AlternativeDto> alternatives = alternativeService.getAlternativesByQuestionId(question.getIdQuestion());
		
		
		QuestionResponseDto questionResponse = new QuestionResponseDto(
				positionQuestion,
				idQuestionnaire,
				question.getIdQuestion(),
				questionType.getIdQuestionType(),
				question.getTextQuestion(),
				alternatives
				);
		
		return questionResponse;
	}
}
