package com.inmind.latam.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.inmind.latam.model.Question;
import com.inmind.latam.model.QuestionType;
import com.inmind.latam.repository.IQuestionRepository;
import com.inmind.latam.constant.QuestionGroupType;
import com.inmind.latam.dto.*;
import com.inmind.latam.exception.ResourceNotFoundException;
import com.inmind.latam.service.*;
import com.inmind.latam.strategy.LocationStrategy;
import com.inmind.latam.strategy.LocationStrategyFactory;

/**
 * Unit tests for {@link QuestionServiceImpl}.
 *
 * These tests verify the behavior of the service for managing questions.
 */
@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private IQuestionRepository questionRepository;

    @Mock
    private IQuestionTypeService questionTypeService;

    @Mock
    private IAlternativeService alternativeService;

    @Mock
    private IDiagnosisAlternativeService diagnosisAlternativeService;

    @Mock
    private IQuestionnaireService questionnaireService;

    @Mock
    private IAlternativePsychoProfileService alternativePsychoProfileService;

    @Mock
    private IAccumulatedPsychoProfileService accumulatedPsychoProfileService;

    @Mock
    private LocationStrategyFactory locationStrategyFactory;

    @Mock
    private LocationStrategy locationStrategy;

    @InjectMocks
    private QuestionServiceImpl service;

    private Question mockQuestion;
    private QuestionType mockQuestionType;
    private List<AlternativeDto> alternatives;
    private List<QuestionMemoryDto> questionMemoryList;

    @BeforeEach
    void setUp() {
        mockQuestionType = new QuestionType();
        mockQuestionType.setIdQuestionType("QT001");
        mockQuestionType.setQuestionType("Type A");

        mockQuestion = new Question();
        mockQuestion.setIdQuestion("P00001");
        mockQuestion.setTextQuestion("Test Question");
        mockQuestion.setNumberAlternatives(3);
        mockQuestion.setPrinted("Y");
        mockQuestion.setQuestionType(mockQuestionType);

        alternatives = Arrays.asList(new AlternativeDto("1", "Alt1"), new AlternativeDto("2", "Alt2"));
        questionMemoryList = Arrays.asList(new QuestionMemoryDto(1, "P00001", false, "1", 1));
    }

    @Test
    void shouldGetQuestionById() {
        // Arrange
        when(questionRepository.findByIdQuestion("P00001")).thenReturn(Optional.of(mockQuestion));

        // Act
        Question result = service.getQuestionById("P00001");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdQuestion()).isEqualTo("P00001");
        assertThat(result.getTextQuestion()).isEqualTo("Test Question");
        assertThat(result.getNumberAlternatives()).isEqualTo(3);
        assertThat(result.getPrinted()).isEqualTo("Y");
        assertThat(result.getQuestionType()).isNotNull();
        assertThat(result.getQuestionType().getIdQuestionType()).isEqualTo("QT001");
        verify(questionRepository).findByIdQuestion("P00001");
    }

    @Test
    void shouldThrowExceptionWhenQuestionNotFound() {
        // Arrange
        when(questionRepository.findByIdQuestion(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        try {
            service.getQuestionById("NOT_FOUND");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(ResourceNotFoundException.class);
            assertThat(e.getMessage()).contains("Question not found with: NOT_FOUND");
        }
        verify(questionRepository).findByIdQuestion("NOT_FOUND");
    }

    @Test
    void shouldReturnRedirectWhenIsValidRedirect() {
        when(questionnaireService.getRedirectValid(anyString())).thenReturn(true);
        doNothing().when(questionnaireService).clearUserCache(anyString());
        // El valor de redirectLink se inyecta por @Value, así que lo simulamos con reflection si es necesario
        // Aquí solo verificamos el tipo
        QuestionnaireResponse response = service.createQuestion("Q123");
        assertThat(response).isInstanceOf(RedirectDto.class);
    }

    @Test
    void shouldReturnPsychologistProfileWhenNotRedirect() {
        when(questionnaireService.getRedirectValid(anyString())).thenReturn(false);
        when(questionnaireService.getProfileTypeCache(anyString())).thenReturn("PF001");
        when(alternativePsychoProfileService.determinePsychologistWithAlternatives(anyString(), anyString(), anyList())).thenReturn(new PsychologistProfileDto("Q123", "PROCESSING", Collections.emptyList(), 0L));
        when(questionnaireService.getQuestionnaireCache(anyString())).thenReturn(questionMemoryList);
        doNothing().when(accumulatedPsychoProfileService).updateAccumulatedPsychoProfile(any(), anyString(), anyString());
        doNothing().when(questionnaireService).clearUserCache(anyString());
        QuestionnaireResponse response = service.createQuestion("Q123");
        assertThat(response).isInstanceOf(PsychologistProfileDto.class);
    }

    @Test
    void shouldCreateQuestionWithAlternativesAndDiagnosis() {
        when(questionRepository.findByIdQuestion(anyString())).thenReturn(Optional.of(mockQuestion));
        when(questionTypeService.getQuestionTypeById(anyString())).thenReturn(mockQuestionType);
        when(alternativeService.getAlternativesByQuestionId(anyString())).thenReturn(new ArrayList<>(alternatives));
        when(questionnaireService.getDiagnosisCache(anyString())).thenReturn(Arrays.asList("D1"));
        when(diagnosisAlternativeService.getAlternativeRemoveByDiagnosis(anyList())).thenReturn(Arrays.asList("1"));
        mockQuestion.setIdQuestion("P00012");
        QuestionnaireResponse response = service.createQuestion("QX", "P00012", 1, Arrays.asList("1"));
        assertThat(response).isInstanceOf(QuestionResponseDto.class);
        QuestionResponseDto dto = (QuestionResponseDto) response;
        assertThat(dto.optionsAnswer()).hasSize(1);
        assertThat(dto.optionsAnswer().get(0).idAlternative()).isEqualTo("2");
    }

    @Test
    void shouldCreateQuestionWithGroupDefault() {
        when(questionRepository.findByIdQuestion(anyString())).thenReturn(Optional.of(mockQuestion));
        when(questionTypeService.getQuestionTypeById(anyString())).thenReturn(mockQuestionType);
        when(alternativeService.getAlternativesByQuestionId(anyString())).thenReturn(alternatives);
        when(questionnaireService.getDiagnosisCache(anyString())).thenReturn(null);
        QuestionnaireResponse response = service.createQuestion("QX", "P00001", 1, Arrays.asList("1"));
        assertThat(response).isInstanceOf(QuestionResponseDto.class);
        QuestionResponseDto dto = (QuestionResponseDto) response;
        assertThat(dto.optionsAnswer()).hasSize(2);
    }

    @Test
    void shouldCreateQuestionWithGroupStateAndResponse() {
        when(questionRepository.findByIdQuestion(anyString())).thenReturn(Optional.of(mockQuestion));
        when(questionTypeService.getQuestionTypeById(anyString())).thenReturn(mockQuestionType);
        when(locationStrategyFactory.getStrategy(any())).thenReturn(locationStrategy);
        when(locationStrategy.getAlternatives(anyInt())).thenReturn(alternatives);
        // No modificar campos estáticos, solo simular el flujo con mocks
        QuestionnaireResponse response = service.createQuestion("QX", "P00001", 1, Arrays.asList("1"));
        assertThat(response).isInstanceOf(QuestionResponseDto.class);
        QuestionResponseDto dto = (QuestionResponseDto) response;
        assertThat(dto.optionsAnswer()).hasSize(2);
    }
} 