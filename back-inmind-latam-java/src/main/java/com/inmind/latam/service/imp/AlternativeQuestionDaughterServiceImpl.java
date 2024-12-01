package com.inmind.latam.service.imp;

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

	@Override
	public List<QuestionDaughterDto> findQuestionDaughterByAlternative(TransactionQuestionnaireDto transactionQuestionnaire) {
		List<String> listQuestion;
		
		String profileType = questionnaireService.getProfileTypeCache(transactionQuestionnaire.idQuestionnaire());
		String alternativeTransition = questionnaireService.getAlternativeTransitionCache(transactionQuestionnaire.idQuestionnaire());
		String transitionTypeDefault = questionnaireService.getTransitionCache(transactionQuestionnaire.idQuestionnaire());
		
		if (alternativeTransition != null ) {
			TransitionType transitionType = transitionTypeService.identifyTranstionType(profileType, alternativeTransition);
			transitionTypeDefault = questionnaireService.saveTransitionCache(transactionQuestionnaire.idQuestionnaire(), transitionType.getIdTransitionType());
			questionnaireService.removeAlternativeTransitionCache(transactionQuestionnaire.idQuestionnaire());
			
		}
		
        listQuestion = alternativeQuestionDaughterRepository.findQuestionDaughterByAlternativeAndTransaction(transitionTypeDefault, transactionQuestionnaire.responseAnswer());
	    
	    return listQuestion.stream()
	                       .map(objArray -> new QuestionDaughterDto(objArray))
	                       .collect(Collectors.toList());
	}

}
