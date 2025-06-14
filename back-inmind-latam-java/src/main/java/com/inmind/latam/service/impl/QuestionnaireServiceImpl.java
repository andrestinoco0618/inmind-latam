package com.inmind.latam.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.inmind.latam.constant.ProfileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.inmind.latam.dto.QuestionDaughterDto;
import com.inmind.latam.dto.QuestionMemoryDto;
import com.inmind.latam.service.IQuestionnaireService;
import static com.inmind.latam.constant.CacheKeys.*;
import static com.inmind.latam.constant.QuestionIdentifiers.*;

/**
 * Implementation of the IQuestionnaireService interface for managing questionnaire operations and cache.
 * <p>
 * This class provides comprehensive functionality for handling questionnaire data, including:
 * - Initialization and management of questionnaire cache
 * - Question status and position updates
 * - Profile type and transition management
 * - Diagnosis and alternative handling
 * - Cache operations using Redis
 * <p>
 * The service uses Spring's caching annotations (@Cacheable, @CachePut, @CacheEvict) for efficient
 * cache management and Redis for persistent storage.
 *
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.service.IQuestionnaireService
 * @see com.inmind.latam.dto.QuestionMemoryDto
 * @see com.inmind.latam.dto.QuestionDaughterDto
 */
@Service
public class QuestionnaireServiceImpl implements IQuestionnaireService {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * Initializes the questionnaire cache for a given questionnaire and profile type.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @param profileType the profile type
	 * @return the list of question memory DTOs
	 */
	@Override
	@CachePut(value = QUESTIONNAIRE, key = "#idQuestionnarie")
	public List<QuestionMemoryDto> initializeQuestionnaireCache(String idQuestionnarie, String profileType) {
		registerCacheKey(idQuestionnarie, QUESTIONNAIRE + "::" + idQuestionnarie);

		String idQuestion;
		try {
			ProfileType type = ProfileType.valueOf(profileType);
			idQuestion = type.getStartQuestion();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid type profile: " + profileType, e);
		}

		QuestionMemoryDto questionMemory = new QuestionMemoryDto(0, idQuestion, false, EMPTY_STRING, 1);
		List<QuestionMemoryDto> questionMemoryList = new ArrayList<>();
		questionMemoryList.add(questionMemory);

		applicationContext.getBean(QuestionnaireServiceImpl.class).saveLevelQuestion(idQuestionnarie, 1);

		return questionMemoryList;
	}

	/**
	 * Retrieves the questionnaire cache for a given questionnaire ID.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @return the list of question memory DTOs
	 */
	@Override
	@Cacheable(value = QUESTIONNAIRE, key = "#idQuestionnarie")
	public List<QuestionMemoryDto> getQuestionnaireCache(String idQuestionnarie) {
	    return null;
	}

	/**
	 * Updates the question status in the questionnaire cache.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @param idQuestion the question ID
	 * @param questionMemoryCacheList the list of question memory DTOs
	 * @param newQuestionDaughterList the list of new daughter questions
	 * @param alternativeResponse the alternative response
	 * @param diagnosis the list of diagnoses
	 * @return the updated list of question memory DTOs
	 */
	@Override
	@CachePut(value = QUESTIONNAIRE, key = "#idQuestionnarie")
	public List<QuestionMemoryDto> updateQuestionStatus(
	        String idQuestionnarie, 
	        String idQuestion, 
	        List<QuestionMemoryDto> questionMemoryCacheList, 
	        List<QuestionDaughterDto> newQuestionDaughterList, 
	        String alternativeResponse, 
	        List<String> diagnosis) {
	    
	    registerCacheKey(idQuestionnarie, QUESTIONNAIRE + "::" + idQuestionnarie);

	    QuestionnaireServiceImpl questionnaireService = applicationContext.getBean(QuestionnaireServiceImpl.class);

	    List<QuestionMemoryDto> cacheList = questionMemoryCacheList != null 
	        ? new ArrayList<>(questionMemoryCacheList) 
	        : new ArrayList<>();

	    // Generar nuevas preguntas hijas, aplicando reglas de exclusión
	    List<QuestionMemoryDto> newQuestionList = newQuestionDaughterList.stream()
	        .map(question -> {
	            boolean isQuestionThirtyNine = QUESTION_THIRTY_NINE.equals(question.getIdQuestionDaughter());
	            boolean shouldExcludeQuestion = questionnaireService.getExludeQuestion(idQuestionnarie);

	            String resolvedId = (isQuestionThirtyNine && shouldExcludeQuestion)
	                ? QUESTION_FORTY 
	                : question.getIdQuestionDaughter();

	            return new QuestionMemoryDto(0, resolvedId, false, EMPTY_STRING, question.getLevelQuestion());
	        })
	        .collect(Collectors.toList());

	    // Marcar pregunta actual como respondida
	    cacheList.stream()
	        .filter(q -> q.getIdQuestion().equals(idQuestion))
	        .findFirst()
	        .ifPresent(q -> {
	            q.setAnswered(true);
	            q.setAlternativeResponse(alternativeResponse);
	        });

	    // Buscar índice de la pregunta padre
	    OptionalInt parentIndexOpt = IntStream.range(0, cacheList.size())
	        .filter(i -> cacheList.get(i).getIdQuestion().equals(idQuestion))
	        .findFirst();

	    List<QuestionMemoryDto> filteredList = new ArrayList<>(cacheList);

	    if (parentIndexOpt.isPresent()) {
	        int parentIndex = parentIndexOpt.getAsInt();

	        // Eliminar preguntas hijas antiguas de esta pregunta
	        Set<String> daughterIds = newQuestionList.stream()
        	    .map(QuestionMemoryDto::getIdQuestion)
        	    .collect(Collectors.toSet());

        	filteredList = removeChildQuestions(cacheList, parentIndex, daughterIds);

	        // Ajustar posición de navegación
	        int newPosition = cacheList.get(parentIndex).getPositionQuestion();
	        questionnaireService.setPosition(idQuestionnarie, newPosition);
	    }

	    // Añadir solo preguntas nuevas que no existan en la lista filtrada
	    Set<String> existingIds = filteredList.stream()
	        .map(QuestionMemoryDto::getIdQuestion)
	        .collect(Collectors.toSet());

	    List<QuestionMemoryDto> newUniqueQuestions = newQuestionList.stream()
	        .filter(q -> !existingIds.contains(q.getIdQuestion()))
	        .collect(Collectors.toList());

	    filteredList.addAll(newUniqueQuestions);

	    return filteredList;
	}

	/**
	 * Updates the position of a question in the questionnaire cache.
	 *
	 * @param idQuestionnarie         the questionnaire ID
	 * @param idQuestion              the question ID
	 * @param positionQuestion        the new position of the question
	 * @param questionMemoryCacheList the list of question memory DTOs
	 */
	@Override
	@CachePut(value = QUESTIONNAIRE, key = "#idQuestionnarie")
    public List<QuestionMemoryDto> updateQuestionPosition(String idQuestionnarie, String idQuestion, int positionQuestion, List<QuestionMemoryDto> questionMemoryCacheList) {
		registerCacheKey(idQuestionnarie, QUESTIONNAIRE + "::" + idQuestionnarie);
		
		if (questionMemoryCacheList == null) {
	        questionMemoryCacheList = new ArrayList<>();
	    }
        
        questionMemoryCacheList.stream()
	        .filter(item -> item.getIdQuestion().equals(idQuestion))
	        .findFirst()
	        .ifPresent(question -> question.setPositionQuestion(positionQuestion));

        return questionMemoryCacheList;
    }

	/**
	 * Clears the user cache for a given questionnaire.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 */
	@Override
	@CacheEvict(value = QUESTIONNAIRE, key = "#idQuestionnarie")
	public void clearUserCache(String idQuestionnarie) {
	    String masterKey = PREFIX + idQuestionnarie;
	    Set<Object> keys = redisTemplate.opsForSet().members(masterKey);

	    if (keys != null && !keys.isEmpty()) {
	        List<String> keyList = keys.stream()
	            .filter(Objects::nonNull)
	            .map(Object::toString)
	            .collect(Collectors.toList());

	        redisTemplate.delete(keyList);
	    }

	    redisTemplate.delete(masterKey);
	}

	/**
	 * Increments the position value for a given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @return the incremented position value
	 */
	@Override
	public int incrementPosition(String idQuestionnaire) {
		return redisTemplate.opsForValue().increment(idQuestionnaire, 1).intValue();
	}

	/**
	 * Sets the position value for a given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @param newPosition the new position value
	 */
	@Override
	public void setPosition(String idQuestionnaire, int newPosition) {
	    redisTemplate.opsForValue().set(idQuestionnaire, newPosition);
	}

	/**
	 * Saves the profile type in the cache for a given questionnaire.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @param profileType     the profile type
	 */
	@Override
    @CachePut(value = PROFILE_TYPE, key = "#idQuestionnarie")
    public String saveProfileTypeCache(String idQuestionnarie, String profileType) {
	    registerCacheKey(idQuestionnarie, PROFILE_TYPE + "::" + idQuestionnarie);
        return profileType;
    }

	/**
	 * Retrieves the profile type from the cache for a given questionnaire.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @return the profile type
	 */
	@Override
	@Cacheable(value = PROFILE_TYPE, key = "#idQuestionnarie")
	public String getProfileTypeCache(String idQuestionnarie) {
	    return null;
	}

	/**
	 * Saves the alternative transition in the cache for a given questionnaire.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @param profileType     the profile type
	 */
	@Override
    @CachePut(value = ALTERNATIVE_TRANSITION, key = "#idQuestionnarie")
    public String saveAlternativeTransitionCache(String idQuestionnarie, String profileType) {
	    registerCacheKey(idQuestionnarie, PROFILE_TYPE + "::" + idQuestionnarie);
		return profileType;
    }

	/**
	 * Retrieves the alternative transition from the cache for a given questionnaire.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @return the alternative transition
	 */
	@Override
	@Cacheable(value = ALTERNATIVE_TRANSITION, key = "#idQuestionnarie")
	public String getAlternativeTransitionCache(String idQuestionnarie) {
	    return null;
	}

	/**
	 * Removes the alternative transition from the cache for a given questionnaire.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 */
	@Override
    @CacheEvict(value = ALTERNATIVE_TRANSITION, key = "#idQuestionnarie")
    public void removeAlternativeTransitionCache(String idQuestionnarie) {
    }

	/**
	 * Saves the transition in the cache for a given questionnaire.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @param profileType the profile type
	 * @return the saved profile type
	 */
	@Override
    @CachePut(value = TRANSITION, key = "#idQuestionnarie")
    public String saveTransitionCache(String idQuestionnarie, String profileType) {
	    registerCacheKey(idQuestionnarie, TRANSITION + "::" + idQuestionnarie);
	    
		return profileType;
    }

	/**
	 * Retrieves the transition from the cache for a given questionnaire.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @return the transition
	 */
	@Override
	@Cacheable(value = TRANSITION, key = "#idQuestionnarie")
	public String getTransitionCache(String idQuestionnarie) {
	    return null;
	}

	/**
	 * Saves the diagnosis list in the cache for a given questionnaire.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @param diagnosis       the list of diagnoses
	 */
	@Override
	@CachePut(value = DIAGNOSIS, key = "#idQuestionnarie")
	public List<String> saveDiagnosisCache(String idQuestionnarie, List<String> diagnosis) {
	    registerCacheKey(idQuestionnarie, DIAGNOSIS + "::" + idQuestionnarie);
		
		return diagnosis;
	}

	/**
	 * Retrieves the diagnosis list from the cache for a given questionnaire.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @return the list of diagnoses
	 */
	@Override
	@Cacheable(value = DIAGNOSIS, key = "#idQuestionnarie")
	public List<String> getDiagnosisCache(String idQuestionnarie) {
		return null;
	}

	/**
	 * Saves the level of the questionnaire in the cache.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @param levelQuestion   the level to save
	 */
	@Override
	@CachePut(value = LEVEL, key = "#idQuestionnarie")
	public int saveLevelQuestion(String idQuestionnarie, int levelQuestion) {
	    registerCacheKey(idQuestionnarie, LEVEL + "::" + idQuestionnarie);
	    
		return levelQuestion;
	}

	/**
	 * Retrieves the level of the questionnaire from the cache.
	 *
	 * @param idQuestionnarie the questionnaire ID
	 * @return the level of the questionnaire
	 */
	@Override
	@Cacheable(value = LEVEL, key = "#idQuestionnarie")
	public int getLevelQuestion(String idQuestionnarie) {
		return 0;
	}

	/**
	 * Saves the group disorders one flag for a given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 */
	@Override
    public void saveGroupDisordersOne(String idQuestionnaire) {
		String key = DISORDERS + idQuestionnaire;
	    registerCacheKey(idQuestionnaire, key);
		
        redisTemplate.opsForValue().set(DISORDERS + idQuestionnaire, "true");
    }

	/**
	 * Retrieves the group disorders one flag for a given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @return true if group disorders one is enabled, false otherwise
	 */
	@Override
    public Boolean getGroupDisordersOne(String idQuestionnaire) {
		String key = DISORDERS + idQuestionnaire;
	    registerCacheKey(idQuestionnaire, key);
		
        String value = (String) redisTemplate.opsForValue().get(DISORDERS + idQuestionnaire);
        return value != null ? Boolean.parseBoolean(value) : false;
    }

	/**
	 * Saves the exclude question flag for a given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 */
	@Override
	public void saveExludeQuestion(String idQuestionnaire) {
		String key = EXCLUDE + idQuestionnaire;
	    registerCacheKey(idQuestionnaire, key);
		
        redisTemplate.opsForValue().set(EXCLUDE + idQuestionnaire, "true");
	}

	/**
	 * Retrieves the exclude question flag for a given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @return true if exclude question is enabled, false otherwise
	 */
	@Override
	public Boolean getExludeQuestion(String idQuestionnaire) {
		String key = EXCLUDE + idQuestionnaire;
	    registerCacheKey(idQuestionnaire, key);
		
        String value = (String) redisTemplate.opsForValue().get(EXCLUDE + idQuestionnaire);
        return value != null ? Boolean.parseBoolean(value) : false;
	}

	/**
	 * Saves the selected country flag for a given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @param isValid true if the country is selected, false otherwise
	 */
	@Override
	public void saveSelectedCountry(String idQuestionnaire, boolean isValid) {
		String key = COUNTRY + idQuestionnaire;
	    registerCacheKey(idQuestionnaire, key);
		
		redisTemplate.opsForValue().set(COUNTRY + idQuestionnaire, String.valueOf(isValid));		
	}

	/**
	 * Retrieves the selected country flag for a given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @return true if the country is selected, false otherwise
	 */
	@Override
	public Boolean getSelectedCountry(String idQuestionnaire) {
		String key = COUNTRY + idQuestionnaire;
	    registerCacheKey(idQuestionnaire, key);
		
		String value = (String) redisTemplate.opsForValue().get(COUNTRY + idQuestionnaire);
        return value != null ? Boolean.parseBoolean(value) : false;
	}

	/**
	 * Saves the redirect valid flag for a given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 */
	@Override
	public void saveRedirectValid(String idQuestionnaire) {
		String key = REDIRECT + idQuestionnaire;
	    registerCacheKey(idQuestionnaire, key);
		
		redisTemplate.opsForValue().set(REDIRECT + idQuestionnaire, "true");
	}

	/**
	 * Retrieves the redirect valid flag for a given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @return true if redirect is valid, false otherwise
	 */
	@Override
	public Boolean getRedirectValid(String idQuestionnaire) {
		String key = REDIRECT + idQuestionnaire;
	    registerCacheKey(idQuestionnaire, key);
		
		String value = (String) redisTemplate.opsForValue().get(REDIRECT + idQuestionnaire);
        return value != null ? Boolean.parseBoolean(value) : false;
	}

	/**
	 * Registers a cache key for a given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @param redisKey the Redis key to register
	 */
	private void registerCacheKey(String idQuestionnaire, String redisKey) {
	    redisTemplate.opsForSet().add(PREFIX + idQuestionnaire, redisKey);
	}

	/**
	 * Removes child questions from the cache list after the parent index.
	 *
	 * @param cacheList the list of question memory DTOs
	 * @param parentIndex the index of the parent question
	 * @param childIdsToRemove the set of child question IDs to remove
	 * @return the filtered list of question memory DTOs
	 */
	private List<QuestionMemoryDto> removeChildQuestions(
	        List<QuestionMemoryDto> cacheList, 
	        int parentIndex, 
	        Set<String> childIdsToRemove
	) {
	    List<QuestionMemoryDto> result = new ArrayList<>();

	    // Mantener solo las preguntas hasta el índice del padre
	    for (int i = 0; i <= parentIndex; i++) {
	        result.add(cacheList.get(i));
	    }

	    return result;
	}


}
