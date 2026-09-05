package com.inmind.latam.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.inmind.latam.dto.QuestionDaughterDto;
import com.inmind.latam.dto.QuestionMemoryDto;
import com.inmind.latam.dto.ResponseQuestionMemoryDto;
import com.inmind.latam.dto.TransactionQuestionnaireDto;
import com.inmind.latam.exception.QuestionNotReadyException;
import com.inmind.latam.service.IQuestionnaireService;
import static com.inmind.latam.constant.QuestionConstant.*;

@Service
public class QuestionnaireServiceImpl implements IQuestionnaireService {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	@CachePut(value = QUESTIONNAIRE_CACHE, key = "#idQuestionnarie")
	public List<QuestionMemoryDto> initializeQuestionnaireCache(String idQuestionnarie, String profileType) {
		String idQuestion = PROFILE_START_QUESTIONNAIRE.get(profileType);
		
		QuestionMemoryDto questionMemory = new QuestionMemoryDto(0, idQuestion, false);	    
	    List<QuestionMemoryDto> questionMemoryList = new ArrayList<>();
	    questionMemoryList.add(questionMemory);
	    	    
	    return questionMemoryList;
	}
	
	@Override
	@Cacheable(value = QUESTIONNAIRE_CACHE, key = "#idQuestionnarie")
	public List<QuestionMemoryDto> getQuestionnaireCache(String idQuestionnarie) {
	    return null;
	}

	@Override
	@CachePut(value = QUESTIONNAIRE_CACHE, key = "#idQuestionnarie")
    public List<QuestionMemoryDto> updateQuestionStatus(String idQuestionnarie, String idQuestion, List<QuestionMemoryDto> questionMemoryCacheList, List<QuestionDaughterDto> newQuestionDaughterList) {
		
		if (questionMemoryCacheList == null) {
	        questionMemoryCacheList = new ArrayList<>();
	    }

		List<QuestionMemoryDto> newQuestionList = newQuestionDaughterList.stream()
                .map(question -> new QuestionMemoryDto(0, question.getIdQuestionDaughter(), false))
                .collect(Collectors.toList());
        
        Set<String> existingQuestionIds = questionMemoryCacheList.stream()
                .map(QuestionMemoryDto::getIdQuestion)
                .collect(Collectors.toSet());
        
        questionMemoryCacheList.stream()
	        .filter(item -> item.getIdQuestion().equals(idQuestion))
	        .findFirst()
	        .ifPresent(question -> question.setStatus(true));
        
        List<QuestionMemoryDto> uniqueNewQuestions = newQuestionList.stream()
                .filter(newQuestion -> !existingQuestionIds.contains(newQuestion.getIdQuestion()))
                .collect(Collectors.toList());
        
        questionMemoryCacheList.addAll(uniqueNewQuestions);

        return questionMemoryCacheList;
    }
	
	@Override
	@CachePut(value = QUESTIONNAIRE_CACHE, key = "#idQuestionnarie")
    public List<QuestionMemoryDto> updateQuestionPosition(String idQuestionnarie, String idQuestion, int positionQuestion, List<QuestionMemoryDto> questionMemoryCacheList) {
		
		if (questionMemoryCacheList == null) {
	        questionMemoryCacheList = new ArrayList<>();
	    }
        
        questionMemoryCacheList.stream()
	        .filter(item -> item.getIdQuestion().equals(idQuestion))
	        .findFirst()
	        .ifPresent(question -> question.setPositionQuestion(positionQuestion));

        return questionMemoryCacheList;
    }
	
	@Override
	@CacheEvict(value = QUESTIONNAIRE_CACHE, key = "#idQuestionnarie")
    public void clearUserCache(String idQuestionnarie) {
    }
	
	@Override
	public int incrementPosition(String idQuestionnaire) {
	    return redisTemplate.opsForValue().increment(idQuestionnaire, 1).intValue();
	}
	
	@Override
	@Cacheable(value = RESPONSE_CACHE, key = "#idQuestionnarie")
	public ResponseQuestionMemoryDto getResponseQuestionnaireCache(String idQuestionnarie) {
	    return null;
	}
	
	@Override
	@CachePut(value = RESPONSE_CACHE, key = "#idQuestionnarie")
    public ResponseQuestionMemoryDto updateResponseQuestionnaireCache(String idQuestionnarie, String profileType, List<String> responseQuestion, ResponseQuestionMemoryDto responseQuestionMemory) {
		
		if (responseQuestionMemory == null) {
	        responseQuestionMemory = new ResponseQuestionMemoryDto(profileType, new ArrayList<>());
	    }

	    responseQuestionMemory.getResponseQuestion().addAll(responseQuestion);
	    return responseQuestionMemory;
    }	
	
	@Override
    @CachePut(value = PROFILE_TYPE, key = "#idQuestionnarie")
    public String saveProfileTypeCache(String idQuestionnarie, String profileType) {
        return profileType;
    }
	
	@Override
	@Cacheable(value = PROFILE_TYPE, key = "#idQuestionnarie")
	public String getProfileTypeCache(String idQuestionnarie) {
	    return null;
	}

	@Override
    @CacheEvict(value = PROFILE_TYPE, key = "#idQuestionnarie")
    public void removeProfileTypeCache(String idQuestionnarie) {
    }
	
	@Override
    @CachePut(value = ALTERNATIVE_TRANSITION, key = "#idQuestionnarie")
    public String saveAlternativeTransitionCache(String idQuestionnarie, String profileType) {
        return profileType;
    }
	
	@Override
	@Cacheable(value = ALTERNATIVE_TRANSITION, key = "#idQuestionnarie")
	public String getAlternativeTransitionCache(String idQuestionnarie) {
	    return null;
	}

	@Override
    @CacheEvict(value = ALTERNATIVE_TRANSITION, key = "#idQuestionnarie")
    public void removeAlternativeTransitionCache(String idQuestionnarie) {
    }
	

	@Override
    @CachePut(value = TRANSITION_QUESTIONNAIRE, key = "#idQuestionnarie")
    public String saveTransitionCache(String idQuestionnarie, String profileType) {
        return profileType;
    }
	
	@Override
	@Cacheable(value = TRANSITION_QUESTIONNAIRE, key = "#idQuestionnarie")
	public String getTransitionCache(String idQuestionnarie) {
	    return null;
	}

	@Override
    @CacheEvict(value = TRANSITION_QUESTIONNAIRE, key = "#idQuestionnarie")
    public void removeTransitionCache(String idQuestionnarie) {
    }
	

	@Override
	public void validationResponseQuestion(TransactionQuestionnaireDto transactionQuestionnaire) throws QuestionNotReadyException {

	    // Validar que la lista no sea nula y esté cargada con preguntas
	    List<QuestionMemoryDto> questionsInMemory = getQuestionnaireCache(transactionQuestionnaire.idQuestionnaire());

	    if (questionsInMemory == null || questionsInMemory.isEmpty() || 
	        !validateQuestionInList(questionsInMemory, transactionQuestionnaire)) {
	        throw new QuestionNotReadyException("La pregunta no está lista para ser contestada o no ha sido cargada.");
	    }

	    // Resto de la lógica para validar la pregunta en la lista
	}
	
	private Boolean validateQuestionInList(List<QuestionMemoryDto> questionsInMemory, TransactionQuestionnaireDto transactionQuestionnaire) {
	    return questionsInMemory != null && questionsInMemory.stream()
	            .anyMatch(question -> question.getIdQuestion().equals(transactionQuestionnaire.idQuestion()));
	}
	
}
