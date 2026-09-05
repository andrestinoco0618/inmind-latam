package com.inmind.latam.service.imp;

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

	@Override
	public List<QuestionDaughterDto> findQuestionDaughterByQuestion(TransactionQuestionnaireDto transactionQuestionnaire) {
		List<Object[]> listQuestion;
		String idQuestion = transactionQuestionnaire.idQuestion();
		String profileType = questionnaireService.getProfileTypeCache(transactionQuestionnaire.idQuestionnaire());
		String alternativeTransition = questionnaireService.getAlternativeTransitionCache(transactionQuestionnaire.idQuestionnaire());
		String transitionTypeDefault = questionnaireService.getTransitionCache(transactionQuestionnaire.idQuestionnaire());
				
		if (alternativeTransition != null ) {
			TransitionType transitionType = transitionTypeService.identifyTranstionType(profileType, alternativeTransition);
			transitionTypeDefault = questionnaireService.saveTransitionCache(transactionQuestionnaire.idQuestionnaire(), transitionType.getIdTransitionType());
			questionnaireService.removeAlternativeTransitionCache(transactionQuestionnaire.idQuestionnaire());
			
		}
		
        listQuestion = questionQuestionDaughterRepository.findQuestionDaughterByQuestion(idQuestion, transitionTypeDefault);
	    
	    
	    return listQuestion.stream()
	                       .map(objArray -> new QuestionDaughterDto((String) objArray[0]))
	                       .collect(Collectors.toList());
	}
	 
}
