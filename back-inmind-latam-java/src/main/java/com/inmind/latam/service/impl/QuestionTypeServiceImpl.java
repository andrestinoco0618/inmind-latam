package com.inmind.latam.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inmind.latam.exception.ResourceNotFoundException;
import com.inmind.latam.model.QuestionType;
import com.inmind.latam.repository.IQuestionTypeRepository;
import com.inmind.latam.service.IQuestionTypeService;

/**
 * Implementation of the IQuestionTypeService interface for managing QuestionType entities.
 * <p>
 * This class provides functionality for handling question type data and
 * retrieving question type information.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.service.IQuestionTypeService
 * @see com.inmind.latam.model.QuestionType
 */
@Service
public class QuestionTypeServiceImpl implements IQuestionTypeService{
	
	private final IQuestionTypeRepository questionTypeRepository;

    /**
     * Constructs a new QuestionTypeServiceImpl with the provided repository.
     * 
     * @param questionTypeRepository the repository for question type operations
     */
    public QuestionTypeServiceImpl(IQuestionTypeRepository questionTypeRepository) {
        this.questionTypeRepository = questionTypeRepository;
    }

	/**
	 * Gets a question type by its unique identifier.
	 * 
	 * @param idQuestionType the unique identifier of the question type
	 * @return the question type entity
	 */
	@Override
	public QuestionType getQuestionTypeById(String idQuestionType){
		QuestionType questionType = questionTypeRepository.findByIdQuestionType(idQuestionType).orElseThrow(
				() -> new ResourceNotFoundException("Question type not found with: " + idQuestionType));
		
		return questionType;
	}

	/**
	 * Gets all available question types.
	 * 
	 * @return list of all question types
	 */
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
