package com.inmind.latam.service.impl;

import static com.inmind.latam.constant.LocationConstants.COUNTRY_QUESTIONS;
import static com.inmind.latam.constant.LocationConstants.PERU_ID;
import static com.inmind.latam.constant.QuestionIdentifiers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.inmind.latam.constant.ProfileType;
import org.springframework.stereotype.Service;

import com.inmind.latam.dto.QuestionDaughterDto;
import com.inmind.latam.dto.QuestionMemoryDto;
import com.inmind.latam.dto.QuestionnaireResponse;
import com.inmind.latam.dto.TransactionQuestionnaireDto;
import com.inmind.latam.model.AccumulatedPsychoProfile;
import com.inmind.latam.model.Diagnosis;
import com.inmind.latam.service.IAccumulatedPsychoProfileService;
import com.inmind.latam.service.IAlternativeQuestionDaughterService;
import com.inmind.latam.service.IDiagnosisService;
import com.inmind.latam.service.IQuestionQuestionDaughterService;
import com.inmind.latam.service.IQuestionService;
import com.inmind.latam.service.IQuestionnaireService;
import com.inmind.latam.service.ITransactionQuestionnaireService;

/**
 * Implementation of the ITransactionQuestionnaireService interface for managing questionnaire transactions.
 *
 * This class provides functionality for handling the flow of questionnaire transactions, including starting questionnaires, processing responses, and updating psychologist selections.
 *
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.service.ITransactionQuestionnaireService
 */
@Service
public class TransactionQuestionnaireServiceImpl implements ITransactionQuestionnaireService{

	private final IQuestionQuestionDaughterService questionQuestionDaughterService;
	private final IAlternativeQuestionDaughterService alternativeQuestionDaughterService;
	private final IQuestionService questionService;
	private final IQuestionnaireService questionnaireService;
	private final IAccumulatedPsychoProfileService accumulatedPsychoProfileService;
	private final IDiagnosisService diagnosisService;
	
	public TransactionQuestionnaireServiceImpl(	IQuestionQuestionDaughterService questionQuestionDaughterService, IQuestionService questionService, 
												IQuestionnaireService questionnaireService, IAlternativeQuestionDaughterService alternativeQuestionDaughterService,
												IAccumulatedPsychoProfileService accumulatedPsychoProfileService,
												IDiagnosisService diagnosisService) {
		
		this.questionQuestionDaughterService = questionQuestionDaughterService;
		this.alternativeQuestionDaughterService = alternativeQuestionDaughterService;
        this.questionService = questionService;
        this.questionnaireService = questionnaireService;
    	this.accumulatedPsychoProfileService = accumulatedPsychoProfileService;
    	this.diagnosisService = diagnosisService;
	}

	/**
	 * Starts a new questionnaire for the given profile type.
	 *
	 * @param profileType the profile type
	 * @return the questionnaire response
	 */
	@Override
	public QuestionnaireResponse startQuestionnaire(String profileType) {
		String idQuestionnaire = UUID.randomUUID().toString();

		ProfileType type;
		try {
			type = ProfileType.valueOf(profileType);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid type profile: " + profileType, e);
		}

		String transitionDefault = type.getDefaultTransition();
		int positionQuestion = questionnaireService.incrementPosition(idQuestionnaire);

		List<QuestionMemoryDto> questionnaireList = questionnaireService.initializeQuestionnaireCache(idQuestionnaire, profileType);
		String idQuestion = questionnaireList.stream()
				.map(QuestionMemoryDto::getIdQuestion)
				.findFirst()
				.orElse(QUESTION_ONE);

		questionnaireService.updateQuestionPosition(idQuestionnaire, idQuestion, positionQuestion, questionnaireList);

		CompletableFuture<Void> saveProfileFuture = CompletableFuture.runAsync(() ->
				questionnaireService.saveProfileTypeCache(idQuestionnaire, profileType)
		);
		CompletableFuture<Void> saveTransitionFuture = CompletableFuture.runAsync(() ->
				questionnaireService.saveTransitionCache(idQuestionnaire, transitionDefault)
		);
		CompletableFuture<Void> savePsychoProfileFuture = CompletableFuture.runAsync(() ->
				accumulatedPsychoProfileService.saveAccumulatedPsychoProfile(
						new AccumulatedPsychoProfile(profileType, idQuestionnaire)
				)
		);

		CompletableFuture.allOf(saveProfileFuture, saveTransitionFuture, savePsychoProfileFuture).join();

		return questionService.createQuestion(idQuestionnaire, idQuestion, positionQuestion);
	}

	/**
	 * Processes a questionnaire transaction and returns the next question or result.
	 *
	 * @param transactionQuestionnaire the transaction questionnaire DTO
	 * @return the questionnaire response
	 */
	@Override
	public QuestionnaireResponse transactionQuestionnaire(TransactionQuestionnaireDto transactionQuestionnaire) {
		
		if (validateMatch(transactionQuestionnaire)) {
	    	return questionService.createQuestion(transactionQuestionnaire.idQuestionnaire());
		}
		
		// Identificar diagnostico
		identifyDiagnosis(transactionQuestionnaire);
		
		// Identificar pais
		identifyCountry(transactionQuestionnaire);
		
		// Identificar si hay redireccion al final del cuestionario
		identifyRedirect(transactionQuestionnaire);
		
		// Pregunta para obtener alternativa
		boolean isvalidQuestion = verifyTransition(transactionQuestionnaire);
		if (isvalidQuestion) {
			String alternative = transactionQuestionnaire.responseAnswer().stream().findFirst().orElse(null);
			questionnaireService.saveAlternativeTransitionCache(transactionQuestionnaire.idQuestionnaire(), alternative);
		}
		
		List<String> answersCache = getFormattedAlternativeResponses(transactionQuestionnaire.idQuestionnaire());

	    // Ejecutar tareas en paralelo
	    CompletableFuture<List<QuestionDaughterDto>> questionDaughterFuture = executeAsync(() -> 
	        questionQuestionDaughterService.findQuestionDaughterByQuestion(transactionQuestionnaire, answersCache));

	    CompletableFuture<List<QuestionDaughterDto>> alternativeDaughterFuture = executeAsync(() -> 
	        alternativeQuestionDaughterService.findQuestionDaughterByAlternative(transactionQuestionnaire));

	    CompletableFuture<List<QuestionMemoryDto>> questionMemoryCacheFuture = executeAsync(() -> 
	        questionnaireService.getQuestionnaireCache(transactionQuestionnaire.idQuestionnaire()));

	    // Combinar resultados de las tareas en paralelo
	    List<QuestionDaughterDto> combinedQuestions = fetchCombinedQuestions(
	        questionDaughterFuture, alternativeDaughterFuture, transactionQuestionnaire.idQuestionnaire());
	    
	    List<QuestionMemoryDto> questionMemoryList = updateCachedQuestions(
	        transactionQuestionnaire, combinedQuestions, questionMemoryCacheFuture);

	    // Crear la respuesta para la siguiente pregunta
	    return generateQuestionResponse(transactionQuestionnaire, questionMemoryList);
	}

	/**
	 * Executes a task asynchronously and handles exceptions.
	 *
	 * @param task the supplier task to execute
	 * @param <T> the type of the result
	 * @return a CompletableFuture with the result
	 */
	private <T> CompletableFuture<T> executeAsync(Supplier<T> task) {
	    return CompletableFuture.supplyAsync(() -> {
	        try {
	            return task.get();
	        } catch (Exception e) {
	            throw new RuntimeException("Error executing task in parallel", e);
	        }
	    });
	}

	/**
	 * Combines questions from two sources and updates their levels.
	 *
	 * @param questionDaughterFuture the future for question daughter list
	 * @param alternativeQuestionFuture the future for alternative question list
	 * @param idQuestionnaire the questionnaire ID
	 * @return the combined list of question daughter DTOs
	 */	private List<QuestionDaughterDto> fetchCombinedQuestions(
	        CompletableFuture<List<QuestionDaughterDto>> questionDaughterFuture,
	        CompletableFuture<List<QuestionDaughterDto>> alternativeQuestionFuture,
	        String idQuestionnaire) {

	    try {
	        List<QuestionDaughterDto> questionDaughterList = questionDaughterFuture.get();
	        List<QuestionDaughterDto> alternativeQuestionList = alternativeQuestionFuture.get();
	        
	        // Aplica el nivel correspondiente
	        updateLevelQuestion(questionDaughterList, false, idQuestionnaire);
	        updateLevelQuestion(alternativeQuestionList, true, idQuestionnaire);

	        questionDaughterList.addAll(alternativeQuestionList);
	        return questionDaughterList;
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	        throw new RuntimeException("The execution was interrupted", e);
	    } catch (ExecutionException e) {
	        throw new RuntimeException("Error getting child questions", e);
	    }
	}

	/**
	 * Updates the cached questions with the combined questions and responses.
	 *
	 * @param transactionQuestionnaire the transaction questionnaire DTO
	 * @param combinedQuestions the combined list of question daughter DTOs
	 * @param questionMemoryCacheFuture the future for question memory cache
	 * @return the updated list of question memory DTOs
	 */	private List<QuestionMemoryDto> updateCachedQuestions(
	        TransactionQuestionnaireDto transactionQuestionnaire,
	        List<QuestionDaughterDto> combinedQuestions,
	        CompletableFuture<List<QuestionMemoryDto>> questionMemoryCacheFuture) {

	    try {
	    	String alternativeResponse = EMPTY_STRING;
	    	String openQuestion = transactionQuestionnaire.openQuestion();
	    	String responseAnswer = String.join(", ", transactionQuestionnaire.responseAnswer());
	    	List<String> diagnosis = questionnaireService.getDiagnosisCache(transactionQuestionnaire.idQuestionnaire());

	    	alternativeResponse = (openQuestion != null && !openQuestion.trim().isEmpty()) 
	    	    ? responseAnswer + openQuestion 
	    	    : responseAnswer;
	        
	        List<QuestionMemoryDto> questionMemoryCacheList = questionMemoryCacheFuture.get();
	        
	        return questionnaireService.updateQuestionStatus(
	                transactionQuestionnaire.idQuestionnaire(),
	                transactionQuestionnaire.idQuestion(),
	                questionMemoryCacheList,
	                combinedQuestions, 
	                alternativeResponse,
	                diagnosis
	        );
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	        throw new RuntimeException("The execution was interrupted", e);
	    } catch (ExecutionException e) {
	        throw new RuntimeException("Error updating questions in the cache", e);
	    }
	}

	/**
	 * Generates the response for the next question in the questionnaire.
	 *
	 * @param transactionQuestionnaire the transaction questionnaire DTO
	 * @param questionMemoryList the list of question memory DTOs
	 * @return the questionnaire response
	 */
	private QuestionnaireResponse generateQuestionResponse(
	        TransactionQuestionnaireDto transactionQuestionnaire,
	        List<QuestionMemoryDto> questionMemoryList) {
        
        String idQuestion = findUnansweredIdQuestion(questionMemoryList, transactionQuestionnaire.idQuestionnaire());
	    
        int positionQuestion = questionnaireService.incrementPosition(transactionQuestionnaire.idQuestionnaire());
        questionnaireService.updateQuestionPosition(transactionQuestionnaire.idQuestionnaire(), idQuestion, positionQuestion, questionMemoryList);
        
	    if (idQuestion == null) {  
	    	return questionService.createQuestion(transactionQuestionnaire.idQuestionnaire());
	    }
	    
	    return questionService.createQuestion(
	            transactionQuestionnaire.idQuestionnaire(),
	            idQuestion,
	            positionQuestion,
	            transactionQuestionnaire.responseAnswer()
	    );
	}

	/**
	 * Verifies if the transition is valid for the given DTO.
	 *
	 * @param dto the transaction questionnaire DTO
	 * @return true if the transition is valid, false otherwise
	 */
    boolean verifyTransition(TransactionQuestionnaireDto dto) {
		if (dto == null || dto.idQuestion() == null) {
			return false;
		}

		return Arrays.stream(ProfileType.values())
				.anyMatch(profile -> profile.getTransitionQuestion().equals(dto.idQuestion()));
	}

	/**
	 * Identifies the diagnosis for the given transaction questionnaire.
	 *
	 * @param transactionQuestionnaire the transaction questionnaire DTO
	 */
    void identifyDiagnosis(TransactionQuestionnaireDto transactionQuestionnaire) {
	    List<Diagnosis> listDiagnosis = diagnosisService.getAll();

	    List<Diagnosis> matchedDiagnosis = listDiagnosis.stream()
	        .filter(diagnosis -> transactionQuestionnaire.responseAnswer().contains(diagnosis.getIdAlternative()))
	        .toList();

	    if (!matchedDiagnosis.isEmpty()) {
	        // Guarda diagnósticos encontrados
	        List<String> diagnosisIds = matchedDiagnosis.stream()
	            .map(Diagnosis::getIdDiagnosis)
	            .toList();

	        questionnaireService.saveDiagnosisCache(transactionQuestionnaire.idQuestionnaire(), diagnosisIds);

	        // Excluir pregunta 39
	        boolean shouldExcludeQuestion = matchedDiagnosis.stream()
	            .anyMatch(diagnosis -> diagnosis.getExcludeQuestion() == true);
	        
	        if (shouldExcludeQuestion) {
	        	questionnaireService.saveExludeQuestion(transactionQuestionnaire.idQuestionnaire());
	        }
	    }
	}

	/**
	 * Retrieves the formatted alternative responses from the questionnaire cache.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @return the list of formatted alternative responses
	 */
    List<String> getFormattedAlternativeResponses(String idQuestionnaire) {
	    List<QuestionMemoryDto> questionMemoryCache = questionnaireService.getQuestionnaireCache(idQuestionnaire);

	    return questionMemoryCache.stream()
	        .map(QuestionMemoryDto::getAlternativeResponse) // Extraer las respuestas
	        .filter(response -> response != null && !response.isEmpty()) // Filtrar respuestas vacías
	        .flatMap(response -> Arrays.stream(response.split(",\\s*"))) // Manejar selección múltiple
	        .map(answer -> answer.contains(":") ? answer.split(":")[0].trim() : answer.trim()) // Eliminar lo que está después de ":"
	        .collect(Collectors.toList()); // Convertir en lista
	}

	/**
	 * Updates the level of the question daughter list.
	 *
	 * @param listQuestionDaughter
	 * @param questionDaughter
	 * @param idQuestionnaire
	 * @return
	 */
    List<QuestionDaughterDto> updateLevelQuestion(List<QuestionDaughterDto> listQuestionDaughter, boolean questionDaughter, String idQuestionnaire) {
	    int baseLevel = questionnaireService.getLevelQuestion(idQuestionnaire);

	    int levelToSet = questionDaughter ? baseLevel + 1 : baseLevel;
	    
	    if (questionDaughter) {
	    	questionnaireService.saveLevelQuestion(idQuestionnaire, levelToSet);
	    }

	    for (QuestionDaughterDto dto : listQuestionDaughter) {
	        dto.setLevelQuestion(levelToSet);
	    }

	    return listQuestionDaughter;
	}

	/**
	 * Determine the level with that save the next questions
	 *
	 * @param questionMemoryList
	 * @param idQuestionnaire
	 * @return
	 */
    String findUnansweredIdQuestion(List<QuestionMemoryDto> questionMemoryList, String idQuestionnaire) {
		// Paso 1: obtener el nivel actual desde cache (o desde el máximo si no hay valor aún)
	    int currentLevel = questionnaireService.getLevelQuestion(idQuestionnaire);

	    // Paso 2: si no hay nada guardado en cache, usar el máximo nivel
	    if (currentLevel == 0) {
	        currentLevel = questionMemoryList.stream()
	            .mapToInt(QuestionMemoryDto::getLevelQuestion)
	            .max()
	            .orElse(0);
	    }

	    // Paso 3: buscar hacia abajo el próximo nivel con preguntas sin responder
	    for (int level = currentLevel; level >= 0; level--) {
	        final int levelFinal = level; // 👈 esto es lo importante

	        Optional<String> idQuestion = questionMemoryList.stream()
	            .filter(q -> !q.isAnswered() && q.getLevelQuestion() == levelFinal)
	            .map(QuestionMemoryDto::getIdQuestion)
	            .findFirst();

	        if (idQuestion.isPresent()) {
	        	questionnaireService.saveLevelQuestion(idQuestionnaire, levelFinal);
	            return idQuestion.get();
	        }
	    }

	    return null;
	}

	/**
	 * Validate match of alternatives to validations
	 *
	 * @param transactionQuestionnaire
	 * @return
	 */
    boolean validateMatch(TransactionQuestionnaireDto transactionQuestionnaire) {
	    boolean hasGroupDisorders = questionnaireService.getGroupDisordersOne(transactionQuestionnaire.idQuestionnaire());

	    boolean isTargetQuestion = QUESTION_FIFTY_SEVEN.equals(transactionQuestionnaire.idQuestion());
	    boolean containsTargetAlternative = transactionQuestionnaire.responseAnswer().contains(ALTERNATIVE_NINE_HUNDRED_TWENTY_NINE);

	    return hasGroupDisorders && isTargetQuestion && containsTargetAlternative;
	}

	/**
	 * Identify country is Peru
	 *
	 * @param transactionQuestionnaire
	 */
    void identifyCountry(TransactionQuestionnaireDto transactionQuestionnaire) {
		if (COUNTRY_QUESTIONS.contains(transactionQuestionnaire.idQuestion()) && transactionQuestionnaire.responseAnswer().contains(PERU_ID)) {
			questionnaireService.saveSelectedCountry(transactionQuestionnaire.idQuestionnaire(), true);
	    } 
	}

	/**
	 * Identify redirect to WhatsApp
	 *
	 * @param transactionQuestionnaire
	 */
    void identifyRedirect(TransactionQuestionnaireDto transactionQuestionnaire) {
		if (transactionQuestionnaire.responseAnswer().contains(ALTERNATIVE_SIX)) {
			questionnaireService.saveRedirectValid(transactionQuestionnaire.idQuestionnaire());
	    }
	}

	/**
	 * Update select psycho profile
	 *
	 * @param idQuestionnaire the ID of the questionnaire
	 * @param idPsycho the ID of the selected psychologist
	 * @return
	 */
	@Override
	public Boolean updateSelectPsycho(String idQuestionnaire, String idPsycho) {
		accumulatedPsychoProfileService.updateSelectPsycho(idQuestionnaire, idPsycho);
		return true;
	}

}
