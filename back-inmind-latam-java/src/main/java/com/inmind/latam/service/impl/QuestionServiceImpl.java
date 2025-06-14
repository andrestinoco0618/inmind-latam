package com.inmind.latam.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import com.inmind.latam.constant.QuestionGroupType;
import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.dto.PsychologistProfileDto;
import com.inmind.latam.dto.QuestionMemoryDto;
import com.inmind.latam.dto.QuestionResponseDto;
import com.inmind.latam.dto.QuestionnaireResponse;
import com.inmind.latam.dto.RedirectDto;
import com.inmind.latam.exception.ResourceNotFoundException;
import com.inmind.latam.model.Question;
import com.inmind.latam.model.QuestionType;
import com.inmind.latam.repository.IQuestionRepository;
import com.inmind.latam.service.IAccumulatedPsychoProfileService;
import com.inmind.latam.service.IAlternativePsychoProfileService;
import com.inmind.latam.service.IAlternativeService;
import com.inmind.latam.service.ICityService;
import com.inmind.latam.service.ICountryService;
import com.inmind.latam.service.IDiagnosisAlternativeService;
import com.inmind.latam.service.IDistrictService;
import com.inmind.latam.service.IQuestionService;
import com.inmind.latam.service.IQuestionTypeService;
import com.inmind.latam.service.IQuestionnaireService;
import com.inmind.latam.service.IStateService;
import com.inmind.latam.strategy.LocationStrategy;
import com.inmind.latam.strategy.LocationStrategyFactory;

import static com.inmind.latam.constant.LocationConstants.QUESTION_GROUP_LOCATION;
import static com.inmind.latam.constant.QuestionIdentifiers.QUESTION_TWELVE;
import static com.inmind.latam.constant.StatusValues.PROCESSING;
import static com.inmind.latam.constant.StatusValues.REDIRECTING;

/**
 * Implementation of the IQuestionService interface for managing Question entities.
 * <p>
 * This class provides functionality for handling question data, creating questions, and managing questionnaire flow.
 *
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.service.IQuestionService
 */
@Service
public class QuestionServiceImpl implements IQuestionService{
	
	private final IQuestionRepository questionRepository;
	private final IQuestionTypeService questionTypeService;
	private final IAlternativeService alternativeService;
	private final IDiagnosisAlternativeService diagnosisAlternativeService;
	private final IQuestionnaireService questionnaireService;
	private final IAlternativePsychoProfileService alternativePsychoProfileService;
	private final IAccumulatedPsychoProfileService accumulatedPsychoProfileService;
	private final ICountryService countryService;
	private final IStateService stateService;
	private final ICityService cityService;
	private final IDistrictService districtService;
	private final LocationStrategyFactory locationStrategyFactory;

    @Value("${app.redirect-link}")
    private String redirectLink;

    public QuestionServiceImpl(IQuestionRepository questionRepository, IQuestionTypeService questionTypeService, IAlternativeService alternativeService,
    		IDiagnosisAlternativeService diagnosisAlternativeService, IQuestionnaireService questionnaireService, IAlternativePsychoProfileService alternativePsychoProfileService,
    		IAccumulatedPsychoProfileService accumulatedPsychoProfileService, ICountryService countryService, IStateService stateService, ICityService cityService,
    		IDistrictService districtService, LocationStrategyFactory locationStrategyFactory) {
        this.questionRepository = questionRepository;
        this.questionTypeService = questionTypeService;
        this.alternativeService = alternativeService;
        this.diagnosisAlternativeService = diagnosisAlternativeService;
        this.questionnaireService = questionnaireService;
    	this.alternativePsychoProfileService = alternativePsychoProfileService;
    	this.accumulatedPsychoProfileService = accumulatedPsychoProfileService;
    	this.countryService = countryService;
    	this.stateService = stateService;
    	this.cityService = cityService;
    	this.districtService = districtService;
    	this.locationStrategyFactory = locationStrategyFactory;
    }

	/**
	 * Retrieves a question by its unique identifier.
	 *
	 * @param idQuestion the unique identifier of the question
	 * @return the question entity
	 * @throws ResourceNotFoundException if no question is found with the given ID
	 */
	@Override
	public Question getQuestionById(String idQuestion){
		Question question = questionRepository.findByIdQuestion(idQuestion).orElseThrow(
				() -> new ResourceNotFoundException("Question not found with: " + idQuestion));
		
		return question;
	}

	/**
	 * Creates a new question or returns a redirect if necessary.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @return the questionnaire response
	 */
	public QuestionnaireResponse createQuestion(String idQuestionnaire){ 
		if (isValidRedirect(idQuestionnaire)) {
			questionnaireService.clearUserCache(idQuestionnaire);
			return new RedirectDto(REDIRECTING, redirectLink);
		}
		
		List<String> answersCache = getFormattedAlternativeResponses(idQuestionnaire);
		String profileType = questionnaireService.getProfileTypeCache(idQuestionnaire);
		PsychologistProfileDto psychoProfile = alternativePsychoProfileService.determinePsychologistWithAlternatives(idQuestionnaire, profileType, answersCache);
    	accumulatedPsychoProfileService.updateAccumulatedPsychoProfile(psychoProfile, idQuestionnaire, getQuestionAndAlternativeResponse(idQuestionnaire));
    	questionnaireService.clearUserCache(idQuestionnaire);
    	
		return psychoProfile;
	}

	/**
	 * Creates a new question for a given questionnaire, question, and position.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @param idQuestion the question ID
	 * @param positionQuestion the position of the question
	 * @return the questionnaire response
	 */
	@Override
	public QuestionnaireResponse createQuestion(String idQuestionnaire, String idQuestion, int positionQuestion) {
		Question question = getQuestionById(idQuestion);
		QuestionType questionType = questionTypeService.getQuestionTypeById(question.getQuestionType().getIdQuestionType());
		
		List<AlternativeDto> alternatives = alternativeService.getAlternativesByQuestionId(idQuestion);
				
		QuestionResponseDto questionResponse = new QuestionResponseDto(
				PROCESSING,
				positionQuestion,
				idQuestionnaire,
				question.getIdQuestion(),
				questionType.getIdQuestionType(),
				question.getTextQuestion(),
				alternatives
				);
		
		return questionResponse;
	}

	/**
	 * Creates a new question for a given questionnaire, question, position, and response answers.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @param idQuestion the question ID
	 * @param positionQuestion the position of the question
	 * @param responseAnswer the list of response answers
	 * @return the questionnaire response
	 */
	public QuestionnaireResponse createQuestion(String idQuestionnaire, String idQuestion, int positionQuestion, List<String> responseAnswer){
		List<String> diagnosis = questionnaireService.getDiagnosisCache(idQuestionnaire);
		Question question = getQuestionById(idQuestion);
		QuestionType questionType = questionTypeService.getQuestionTypeById(question.getQuestionType().getIdQuestionType());
		
		List<AlternativeDto> alternatives = getAlternatives(question.getIdQuestion(), responseAnswer);	
		List<String> alternativesToRemove = null; 

		if (diagnosis != null) {
		    alternativesToRemove = diagnosisAlternativeService.getAlternativeRemoveByDiagnosis(diagnosis);    ////////////////////////////////////// aca
		}

		if (QUESTION_TWELVE.equals(idQuestion) && alternativesToRemove != null && !alternativesToRemove.isEmpty()) {
		    Set<String> alternativesToRemoveSet = new HashSet<>(alternativesToRemove);
		    alternatives.removeIf(alternative -> alternativesToRemoveSet.contains(alternative.idAlternative()));
		}
				
		QuestionResponseDto questionResponse = new QuestionResponseDto(
				PROCESSING,
				positionQuestion,
				idQuestionnaire,
				question.getIdQuestion(),
				questionType.getIdQuestionType(),
				question.getTextQuestion(),
				alternatives
				);
		
		return questionResponse;
	}

	/**
	 * Retrieves the formatted alternative responses from the questionnaire cache.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @return the list of formatted alternative responses
	 */
	private List<String> getFormattedAlternativeResponses(String idQuestionnaire) {
	    List<QuestionMemoryDto> questionMemoryCache = questionnaireService.getQuestionnaireCache(idQuestionnaire);

	    return questionMemoryCache.stream()
	        .map(QuestionMemoryDto::getAlternativeResponse) // Extraer las respuestas
	        .filter(response -> response != null && !response.isEmpty()) // Filtrar respuestas vacías
	        .flatMap(response -> Arrays.stream(response.split(",\\s*"))) // Manejar selección múltiple
	        .map(answer -> answer.contains(":") ? answer.split(":")[0].trim() : answer.trim()) // Eliminar lo que está después de ":"
	        .collect(Collectors.toList()); // Convertir en lista
	}

	/**
	 * Retrieves the question and alternative responses as a formatted string.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @return the formatted string of questions and alternatives
	 */
	private String getQuestionAndAlternativeResponse(String idQuestionnaire) {
	    List<QuestionMemoryDto> questionMemoryCache = questionnaireService.getQuestionnaireCache(idQuestionnaire);
	    return questionMemoryCache.stream()
	            .map(memoryDto -> String.format("{\"pregunta\":\"%s\", \"alternativa\":\"%s\"}",
	                    memoryDto.getIdQuestion(), memoryDto.getAlternativeResponse()))
	            .collect(Collectors.joining(", ", "[", "]"));
	}

	/**
	 * Retrieves a list of alternatives for a given question and response answers.
	 *
	 * @param idQuestion the question ID
	 * @param responseAnswer the list of response answers
	 * @return the list of alternative DTOs
	 */
	private List<AlternativeDto> getAlternatives(String idQuestion, List<String> responseAnswer) {
		QuestionGroupType groupType = QUESTION_GROUP_LOCATION.getOrDefault(idQuestion, QuestionGroupType.DEFAULT);
		int idAlternative = 0;

		if ((groupType == QuestionGroupType.STATE || groupType == QuestionGroupType.CITY || groupType == QuestionGroupType.DISTRICT)
		        && responseAnswer != null && !responseAnswer.isEmpty()) {
	    	idAlternative = Integer.parseInt(responseAnswer.get(0));
	    }

		LocationStrategy strategy = locationStrategyFactory.getStrategy(groupType);
		if (strategy != null) {
		    return strategy.getAlternatives(idAlternative);
		}

		return alternativeService.getAlternativesByQuestionId(idQuestion);
	}

	/**
	 * Checks if a redirect is valid for the given questionnaire.
	 *
	 * @param idQuestionnaire the questionnaire ID
	 * @return true if the redirect is valid, false otherwise
	 */
	private boolean isValidRedirect(String idQuestionnaire) {
		return questionnaireService.getRedirectValid(idQuestionnaire);
	}
}
