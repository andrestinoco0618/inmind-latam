package com.inmind.latam.service.impl;

import static com.inmind.latam.constant.QuestionIdentifiers.GROUP_DISORDERS_ONE;
import static com.inmind.latam.constant.QuestionIdentifiers.QUESTION_FIVE;
import static com.inmind.latam.constant.QuestionIdentifiers.TYPE_TRANSITION_TWO;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.inmind.latam.dto.QuestionDaughterDto;
import com.inmind.latam.dto.TransactionQuestionnaireDto;
import com.inmind.latam.model.TransitionType;
import com.inmind.latam.repository.IAlternativeQuestionDaughterRepository;
import com.inmind.latam.service.IAlternativeQuestionDaughterService;
import com.inmind.latam.service.IQuestionnaireService;
import com.inmind.latam.service.ITransitionTypeService;

/**
 * Implementation of the IAlternativeQuestionDaughterService interface for managing AlternativeQuestionDaughter entities.
 * <p>
 * This class provides functionality for handling alternative question daughter data and retrieving daughter questions by alternative.
 *
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.service.IAlternativeQuestionDaughterService
 */
@Service
public class AlternativeQuestionDaughterServiceImpl implements IAlternativeQuestionDaughterService{
	
	private final IAlternativeQuestionDaughterRepository alternativeQuestionDaughterRepository;
	private final ITransitionTypeService transitionTypeService;
	private final IQuestionnaireService questionnaireService;

	
	public AlternativeQuestionDaughterServiceImpl(IAlternativeQuestionDaughterRepository alternativeQuestionDaughterRepository, ITransitionTypeService transitionTypeService,
													IQuestionnaireService questionnaireService) {
		this.alternativeQuestionDaughterRepository = alternativeQuestionDaughterRepository;
		this.transitionTypeService = transitionTypeService;
		this.questionnaireService = questionnaireService;
	}

	/**
	 * Finds daughter questions by alternative for a given transaction questionnaire.
	 *
	 * @param transactionQuestionnaire the transaction questionnaire DTO
	 * @return the list of daughter question DTOs
	 */
	@Override
	public List<QuestionDaughterDto> findQuestionDaughterByAlternative(TransactionQuestionnaireDto transactionQuestionnaire) {
		List<String> listQuestion;

		String idQuestion = transactionQuestionnaire.idQuestion();
		String profileType = questionnaireService.getProfileTypeCache(transactionQuestionnaire.idQuestionnaire());
		String alternativeTransition = questionnaireService.getAlternativeTransitionCache(transactionQuestionnaire.idQuestionnaire());
		String transitionTypeDefault = questionnaireService.getTransitionCache(transactionQuestionnaire.idQuestionnaire());
		
		if (alternativeTransition != null ) {
			TransitionType transitionType = transitionTypeService.identifyTranstionType(profileType, alternativeTransition);
			transitionTypeDefault = questionnaireService.saveTransitionCache(transactionQuestionnaire.idQuestionnaire(), transitionType.getIdTransitionType());
			questionnaireService.removeAlternativeTransitionCache(transactionQuestionnaire.idQuestionnaire());
		}
		
        listQuestion = alternativeQuestionDaughterRepository.findQuestionDaughterByAlternativeAndTransaction(transitionTypeDefault, transactionQuestionnaire.responseAnswer());
        
        if (isTargetQuestion(idQuestion)) {
		    boolean hasGroupDisordersOne = transactionQuestionnaire.responseAnswer().stream()
			        .anyMatch(GROUP_DISORDERS_ONE::contains);
		    
		    if (hasGroupDisordersOne && TYPE_TRANSITION_TWO.equals(transitionTypeDefault)) {
		            questionnaireService.saveGroupDisordersOne(transactionQuestionnaire.idQuestionnaire());
		    }
		    
        }
	    
	    return listQuestion.stream()
	                       .map(objArray -> new QuestionDaughterDto(objArray, 1))
	                       .collect(Collectors.toList());
	}

	/**
	 * Checks if the given question ID is the target question for group disorders.
	 *
	 * @param idQuestion the question ID
	 * @return true if it is the target question, false otherwise
	 */
	private boolean isTargetQuestion(String idQuestion) {
	    return QUESTION_FIVE.equals(idQuestion);
	}

}
