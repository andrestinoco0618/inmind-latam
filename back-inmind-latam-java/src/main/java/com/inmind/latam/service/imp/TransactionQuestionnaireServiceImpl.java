package com.inmind.latam.service.imp;

import static com.inmind.latam.constant.QuestionConstant.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import com.inmind.latam.dto.PsychoProfileDto;
import com.inmind.latam.dto.QuestionDaughterDto;
import com.inmind.latam.dto.QuestionMemoryDto;
import com.inmind.latam.dto.QuestionResponseDto;
import com.inmind.latam.dto.ResponseQuestionMemoryDto;
import com.inmind.latam.dto.TransactionQuestionnaireDto;
import com.inmind.latam.exception.QuestionNotReadyException;
import com.inmind.latam.model.AccumulatedPsychoProfile;
import com.inmind.latam.service.IAccumulatedPsychoProfileService;
import com.inmind.latam.service.IAlternativePsychoProfileService;
import com.inmind.latam.service.IAlternativeQuestionDaughterService;
import com.inmind.latam.service.IQuestionQuestionDaughterService;
import com.inmind.latam.service.IQuestionService;
import com.inmind.latam.service.IQuestionnaireService;
import com.inmind.latam.service.ITransactionQuestionnaireService;

@Service
public class TransactionQuestionnaireServiceImpl implements ITransactionQuestionnaireService{

	private final IQuestionQuestionDaughterService questionQuestionDaughterService;
	private final IAlternativeQuestionDaughterService alternativeQuestionDaughterService;
	private final IQuestionService questionService;
	private final IQuestionnaireService questionnaireService;
	private final IAccumulatedPsychoProfileService accumulatedPsychoProfileService;
	private final IAlternativePsychoProfileService alternativePsychoProfileService;
	
	public TransactionQuestionnaireServiceImpl(	IQuestionQuestionDaughterService questionQuestionDaughterService, IQuestionService questionService, 
												IQuestionnaireService questionnaireService, IAlternativeQuestionDaughterService alternativeQuestionDaughterService,
												IAccumulatedPsychoProfileService accumulatedPsychoProfileService, IAlternativePsychoProfileService alternativePsychoProfileService) {
		this.questionQuestionDaughterService = questionQuestionDaughterService;
		this.alternativeQuestionDaughterService = alternativeQuestionDaughterService;
        this.questionService = questionService;
        this.questionnaireService = questionnaireService;
    	this.accumulatedPsychoProfileService = accumulatedPsychoProfileService;
    	this.alternativePsychoProfileService = alternativePsychoProfileService;
	}
	
	@Override
	public QuestionResponseDto startQuestionnaire(String profileType){
		
		String idQuestionnaire = UUID.randomUUID().toString();
		String transitionDefault = PROFILE_TRANSITION_DEFAULT.get(profileType);
		
        List<QuestionMemoryDto> questionnaireList = questionnaireService.initializeQuestionnaireCache(idQuestionnaire, profileType);
        String idQuestion = questionnaireList.stream()
        	    .map(QuestionMemoryDto::getIdQuestion)
        	    .findFirst()                            
        	    .orElse(QUESTION_ONE);      
        
        int positionQuestion = questionnaireService.incrementPosition(idQuestionnaire);
        questionnaireService.updateQuestionPosition(idQuestionnaire, idQuestion, positionQuestion, questionnaireList);
        
        questionnaireService.saveProfileTypeCache(idQuestionnaire, profileType);
        questionnaireService.saveTransitionCache(idQuestionnaire, transitionDefault);
	    accumulatedPsychoProfileService.saveAccumulatedPsychoProfile(new AccumulatedPsychoProfile(profileType, idQuestionnaire));
        
		return questionService.createQuestion(idQuestionnaire, idQuestion, positionQuestion);
	}

	@Override
	public QuestionResponseDto transactionQuestionnaire(TransactionQuestionnaireDto transactionQuestionnaire) 
	        throws QuestionNotReadyException {
		
		// Guardar respuestas
		String profileType = questionnaireService.getProfileTypeCache(transactionQuestionnaire.idQuestionnaire());
		ResponseQuestionMemoryDto responseQuestionMemory = questionnaireService.getResponseQuestionnaireCache(transactionQuestionnaire.idQuestionnaire());
		questionnaireService.updateResponseQuestionnaireCache(transactionQuestionnaire.idQuestionnaire(), profileType, transactionQuestionnaire.responseAnswer(), responseQuestionMemory);
		
		// Pregunta para obtener alternativa
		boolean isvalidQuestion = verifyTransition(transactionQuestionnaire);
		if (isvalidQuestion) {
			String alternative = transactionQuestionnaire.responseAnswer().stream().findFirst().orElse(null);
			questionnaireService.saveAlternativeTransitionCache(transactionQuestionnaire.idQuestionnaire(), alternative);
		}

	    // Ejecutar tareas en paralelo
	    CompletableFuture<List<QuestionDaughterDto>> questionDaughterFuture = executeAsync(() -> 
	        questionQuestionDaughterService.findQuestionDaughterByQuestion(transactionQuestionnaire));

	    CompletableFuture<List<QuestionDaughterDto>> alternativeQuestionFuture = executeAsync(() -> 
	        alternativeQuestionDaughterService.findQuestionDaughterByAlternative(transactionQuestionnaire));

	    CompletableFuture<List<QuestionMemoryDto>> questionMemoryCacheFuture = executeAsync(() -> 
	        questionnaireService.getQuestionnaireCache(transactionQuestionnaire.idQuestionnaire()));

	    // Combinar resultados de las tareas en paralelo
	    List<QuestionDaughterDto> combinedQuestions = fetchCombinedQuestions(
	        questionDaughterFuture, alternativeQuestionFuture);

	    List<QuestionMemoryDto> questionMemoryList = updateCachedQuestions(
	        transactionQuestionnaire, combinedQuestions, questionMemoryCacheFuture);

	    // Crear la respuesta para la siguiente pregunta
	    return generateQuestionResponse(transactionQuestionnaire, questionMemoryList);
	}

	// Método auxiliar para manejar excepciones en CompletableFuture
	private <T> CompletableFuture<T> executeAsync(Supplier<T> task) {
	    return CompletableFuture.supplyAsync(() -> {
	        try {
	            return task.get();
	        } catch (Exception e) {
	            throw new RuntimeException("Error ejecutando tarea en paralelo", e);
	        }
	    });
	}

	// Combina preguntas de las dos fuentes
	private List<QuestionDaughterDto> fetchCombinedQuestions(
	        CompletableFuture<List<QuestionDaughterDto>> questionDaughterFuture,
	        CompletableFuture<List<QuestionDaughterDto>> alternativeQuestionFuture) {

	    try {
	        List<QuestionDaughterDto> questionDaughterList = questionDaughterFuture.get();
	        List<QuestionDaughterDto> alternativeQuestionList = alternativeQuestionFuture.get();
	        questionDaughterList.addAll(alternativeQuestionList);
	        return questionDaughterList;
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	        throw new RuntimeException("La ejecución fue interrumpida", e);
	    } catch (ExecutionException e) {
	        throw new RuntimeException("Error al obtener preguntas hijas", e);
	    }
	}

	// Actualiza el estado de las preguntas en la caché
	private List<QuestionMemoryDto> updateCachedQuestions(
	        TransactionQuestionnaireDto transactionQuestionnaire,
	        List<QuestionDaughterDto> combinedQuestions,
	        CompletableFuture<List<QuestionMemoryDto>> questionMemoryCacheFuture) {

	    try {
	        List<QuestionMemoryDto> questionMemoryCacheList = questionMemoryCacheFuture.get();
	        return questionnaireService.updateQuestionStatus(
	                transactionQuestionnaire.idQuestionnaire(),
	                transactionQuestionnaire.idQuestion(),
	                questionMemoryCacheList,
	                combinedQuestions
	        );
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	        throw new RuntimeException("La ejecución fue interrumpida", e);
	    } catch (ExecutionException e) {
	        throw new RuntimeException("Error al actualizar preguntas en la caché", e);
	    }
	}

	// Genera la respuesta basada en la siguiente pregunta
	private QuestionResponseDto generateQuestionResponse(
	        TransactionQuestionnaireDto transactionQuestionnaire,
	        List<QuestionMemoryDto> questionMemoryList) throws QuestionNotReadyException {

	    String idQuestion = questionMemoryList.stream()
	            .filter(question -> !question.getStatus())
	            .map(QuestionMemoryDto::getIdQuestion)
	            .reduce((first, second) -> second)
	            .orElse(null);

        int positionQuestion = questionnaireService.incrementPosition(transactionQuestionnaire.idQuestionnaire());
        questionnaireService.updateQuestionPosition(transactionQuestionnaire.idQuestionnaire(), idQuestion, positionQuestion, questionMemoryList);

	    if (idQuestion == null) {
	    	ResponseQuestionMemoryDto responseQuestionMemory = questionnaireService.getResponseQuestionnaireCache(transactionQuestionnaire.idQuestionnaire());
	    	PsychoProfileDto psychoProfile = alternativePsychoProfileService.findPsychologistWithAlternatives(responseQuestionMemory);
	    	accumulatedPsychoProfileService.updateAccumulatedPsychoProfile(psychoProfile, transactionQuestionnaire.idQuestionnaire());
	    	
	        throw new QuestionNotReadyException("No hay más preguntas disponibles.");
	    }

	    return questionService.createQuestion(
	            transactionQuestionnaire.idQuestionnaire(),
	            idQuestion,
	            positionQuestion
	    );
	}
	
	private  boolean verifyTransition(TransactionQuestionnaireDto dto) {
        if (dto == null || dto.idQuestion() == null) {
            return false; 
        }
        return PROFILE_TRANSITION_QUESTION.containsValue(dto.idQuestion());
    }
}
