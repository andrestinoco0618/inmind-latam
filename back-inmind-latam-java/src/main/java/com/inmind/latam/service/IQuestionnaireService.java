package com.inmind.latam.service;

import java.util.List;

import com.inmind.latam.dto.QuestionDaughterDto;
import com.inmind.latam.dto.QuestionMemoryDto;
import com.inmind.latam.dto.ResponseQuestionMemoryDto;
import com.inmind.latam.dto.TransactionQuestionnaireDto;
import com.inmind.latam.exception.QuestionNotReadyException;

public interface IQuestionnaireService {
	
	public List<QuestionMemoryDto> initializeQuestionnaireCache(String idQuestionnarie, String profileType);
	public List<QuestionMemoryDto> getQuestionnaireCache(String idQuestionnarie);
	public List<QuestionMemoryDto> updateQuestionStatus(String idQuestionnarie, String idQuestion, List<QuestionMemoryDto> questionMemoryCacheList, 
			List<QuestionDaughterDto> newQuestionDaughterList);
	List<QuestionMemoryDto> updateQuestionPosition(String idQuestionnarie, String idQuestion, int positionQuestion,
			List<QuestionMemoryDto> questionMemoryCacheList);
	public void clearUserCache(String idQuestionnarie);
	
	public int incrementPosition(String idQuestionnaire);
	
	public ResponseQuestionMemoryDto getResponseQuestionnaireCache(String idQuestionnarie);
	public ResponseQuestionMemoryDto updateResponseQuestionnaireCache(String idQuestionnarie, String profileType,
			List<String> responseQuestion, ResponseQuestionMemoryDto responseQuestionMemory);
	
	public String saveProfileTypeCache(String idQuestionnarie, String profileType);
	public String getProfileTypeCache(String idQuestionnarie);
	public void removeProfileTypeCache(String idQuestionnarie);
	
	public String saveAlternativeTransitionCache(String idQuestionnarie, String alternativeTransition);
	public String getAlternativeTransitionCache(String idQuestionnarie);
	public void removeAlternativeTransitionCache(String idQuestionnarie);
	
	public String saveTransitionCache(String idQuestionnarie, String transition); 
	public String getTransitionCache(String idQuestionnarie);
	public void removeTransitionCache(String idQuestionnarie); 
	
	
	public void validationResponseQuestion(TransactionQuestionnaireDto transactionQuestionnaire)  throws QuestionNotReadyException;		
}
