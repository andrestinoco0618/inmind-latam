package com.inmind.latam.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.inmind.latam.dto.QuestionDaughterDto;
import com.inmind.latam.dto.TransactionQuestionnaireDto;
import com.inmind.latam.model.TransitionType;
import com.inmind.latam.repository.IQuestionQuestionDaughterRepository;
import com.inmind.latam.service.IQuestionQuestionDaughterService;
import com.inmind.latam.service.IQuestionnaireService;
import com.inmind.latam.service.ITransitionTypeService;

import static com.inmind.latam.constant.LocationConstants.DISTRICT_QUESTIONS;
import static com.inmind.latam.constant.QuestionIdentifiers.*;

/**
 * Implementation of the IQuestionQuestionDaughterService interface for managing QuestionQuestionDaughter entities.
 * <p>
 * This class provides functionality for handling question daughter data and retrieving daughter questions by question.
 *
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.service.IQuestionQuestionDaughterService
 */
@Service
public class QuestionQuestionDaughterServiceImpl implements IQuestionQuestionDaughterService{
	
	private final IQuestionQuestionDaughterRepository questionQuestionDaughterRepository;
	private final ITransitionTypeService transitionTypeService;
	private final IQuestionnaireService questionnaireService;

    public QuestionQuestionDaughterServiceImpl(IQuestionQuestionDaughterRepository questionQuestionDaughterRepository, ITransitionTypeService transitionTypeService,
    											IQuestionnaireService questionnaireService){
        this.questionQuestionDaughterRepository = questionQuestionDaughterRepository;
        this.transitionTypeService = transitionTypeService;
		this.questionnaireService = questionnaireService;
    }

	/**
	 * Finds daughter questions by question for a given transaction questionnaire and answers cache.
	 *
	 * @param transactionQuestionnaire the transaction questionnaire DTO
	 * @param answersCache the list of answers from the cache
	 * @return the list of daughter question DTOs
	 */
	@Override
	public List<QuestionDaughterDto> findQuestionDaughterByQuestion(TransactionQuestionnaireDto transactionQuestionnaire, List<String> answersCache) {
		String idQuestion = transactionQuestionnaire.idQuestion();
		String profileType = questionnaireService.getProfileTypeCache(transactionQuestionnaire.idQuestionnaire());
		String alternativeTransition = questionnaireService.getAlternativeTransitionCache(transactionQuestionnaire.idQuestionnaire());
		String transitionTypeDefault = questionnaireService.getTransitionCache(transactionQuestionnaire.idQuestionnaire());
				
		if (alternativeTransition != null ) {
			TransitionType transitionType = transitionTypeService.identifyTranstionType(profileType, alternativeTransition);
			transitionTypeDefault = questionnaireService.saveTransitionCache(transactionQuestionnaire.idQuestionnaire(), transitionType.getIdTransitionType());
			questionnaireService.removeAlternativeTransitionCache(transactionQuestionnaire.idQuestionnaire());
		}
		
		List<Object[]> listQuestion = questionQuestionDaughterRepository.findQuestionDaughterByQuestion(idQuestion, transitionTypeDefault);
        
        // Eliminar pregunta 9 cuando no hay alternativas del grupo 1
		if (isTargetQuestion(idQuestion)) {
		    boolean hasGroupDisordersOne = answersCache.stream()
		        .anyMatch(GROUP_DISORDERS_ONE::contains);

		    if (!hasGroupDisordersOne) {
		        listQuestion.removeIf(arr -> QUESTION_NINE.equals(arr[0]));
		    }
		}
				
		if (isTargetDistricts(idQuestion)) {
			boolean isCountryPeru = questionnaireService.
					getSelectedCountry(transactionQuestionnaire.idQuestionnaire());
			
			if (isCountryPeru) {
				Collections.reverse(listQuestion);
			} else {
				listQuestion.removeIf(arr -> QUESTION_TWO_HUNDRED_SEVENTEEN.equals(arr[0]));
			}
		}

	    return listQuestion.stream()
	                       .map(objArray -> new QuestionDaughterDto((String) objArray[0], 1))
	                       .collect(Collectors.toList());
	}

	/**
	 * Checks if the given question ID is the target question for group disorders.
	 *
	 * @param idQuestion the question ID
	 * @return true if it is the target question, false otherwise
	 */
	private boolean isTargetQuestion(String idQuestion) {
	    return QUESTION_EIGHT.equals(idQuestion);
	}

	/**
	 * Checks if the given question ID is a target for district questions.
	 *
	 * @param idQuestion the question ID
	 * @return true if it is a district question, false otherwise
	 */
	private boolean isTargetDistricts(String idQuestion) {
	    return DISTRICT_QUESTIONS.contains(idQuestion);
	}
	
}
