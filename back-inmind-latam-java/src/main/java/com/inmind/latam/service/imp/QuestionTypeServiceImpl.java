package com.inmind.latam.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inmind.latam.exception.ResourceNotFoundException;
import com.inmind.latam.model.QuestionType;
import com.inmind.latam.repository.IQuestionTypeRepository;
import com.inmind.latam.service.IQuestionTypeService;

@Service
public class QuestionTypeServiceImpl implements IQuestionTypeService{
	
	private final IQuestionTypeRepository questionTypeRepository;

    public QuestionTypeServiceImpl(IQuestionTypeRepository questionTypeRepository) {
        this.questionTypeRepository = questionTypeRepository;
    }

	@Override
	public QuestionType getQuestionTypeById(String idQuestionType){
		QuestionType questionType = questionTypeRepository.findByIdQuestionType(idQuestionType).orElseThrow(
				() -> new ResourceNotFoundException("Question type not found with: " + idQuestionType));
		
		return questionType;
	}

	@Override
	public List<QuestionType> getAll(){
		List<QuestionType> listQuestionType = questionTypeRepository.findAll();
		
		if(!listQuestionType.isEmpty()) {
			return listQuestionType;
		}
		else {
			throw new ResourceNotFoundException("No question types are registered");
		}
	}

}
