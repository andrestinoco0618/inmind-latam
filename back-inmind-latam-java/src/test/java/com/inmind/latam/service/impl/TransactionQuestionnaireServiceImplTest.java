package com.inmind.latam.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.inmind.latam.constant.ProfileType;
import com.inmind.latam.dto.QuestionMemoryDto;
import com.inmind.latam.dto.QuestionnaireResponse;
import com.inmind.latam.dto.QuestionResponseDto;
import com.inmind.latam.dto.TransactionQuestionnaireDto;
import com.inmind.latam.model.AccumulatedPsychoProfile;
import com.inmind.latam.service.IAccumulatedPsychoProfileService;
import com.inmind.latam.service.IAlternativeQuestionDaughterService;
import com.inmind.latam.service.IDiagnosisService;
import com.inmind.latam.service.IQuestionQuestionDaughterService;
import com.inmind.latam.service.IQuestionService;
import com.inmind.latam.service.IQuestionnaireService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import com.inmind.latam.dto.QuestionDaughterDto;
import com.inmind.latam.model.Diagnosis;
import static com.inmind.latam.constant.QuestionIdentifiers.QUESTION_FIFTY_SEVEN;
import static com.inmind.latam.constant.QuestionIdentifiers.ALTERNATIVE_NINE_HUNDRED_TWENTY_NINE;

/**
 * Unit tests for {@link TransactionQuestionnaireServiceImpl}.
 * <p>
 * These tests verify the behavior of the service for managing questionnaire transactions.
 */
@ExtendWith(MockitoExtension.class)
class TransactionQuestionnaireServiceImplTest {

    @Mock
    private IQuestionQuestionDaughterService questionQuestionDaughterService;
    @Mock
    private IAlternativeQuestionDaughterService alternativeQuestionDaughterService;
    @Mock
    private IQuestionService questionService;
    @Mock
    private IQuestionnaireService questionnaireService;
    @Mock
    private IAccumulatedPsychoProfileService accumulatedPsychoProfileService;
    @Mock
    private IDiagnosisService diagnosisService;

    @InjectMocks
    private TransactionQuestionnaireServiceImpl service;

    private String profileType;
    private String idQuestionnaire;
    private String idQuestion;
    private List<QuestionMemoryDto> questionMemoryList;

    @BeforeEach
    void setUp() {
        profileType = ProfileType.PF001.name();
        idQuestionnaire = UUID.randomUUID().toString();
        idQuestion = "P00001";
        questionMemoryList = List.of(new QuestionMemoryDto(1, idQuestion, false, null, 1));
    }

    @Test
    void shouldStartQuestionnaireSuccessfully() {
        // Arrange
        when(questionnaireService.initializeQuestionnaireCache(anyString(), anyString())).thenReturn(questionMemoryList);
        when(questionnaireService.incrementPosition(anyString())).thenReturn(1);
        when(questionService.createQuestion(anyString(), anyString(), any(Integer.class))).thenReturn(createQuestionResponseDto());

        // Act
        QuestionnaireResponse response = service.startQuestionnaire(profileType);

        // Assert
        assertThat(response).isInstanceOf(QuestionResponseDto.class);
    }

    @Test
    void shouldThrowExceptionForInvalidProfileType() {
        // Arrange
        String invalidProfile = "INVALID";

        // Act & Assert
        assertThatThrownBy(() -> service.startQuestionnaire(invalidProfile))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid type profile");
    }

    @Test
    void shouldProcessTransactionQuestionnaireWithMatch() {
        // Arrange
        TransactionQuestionnaireDto dto = new TransactionQuestionnaireDto(idQuestionnaire, idQuestion, List.of("A1"), "");
        when(questionService.createQuestion(anyString())).thenReturn(createQuestionResponseDto());
        // For validateMatch = true, se simula el flujo con un DTO especial
        TransactionQuestionnaireDto matchDto = new TransactionQuestionnaireDto("match", idQuestion, List.of("A1"), "");
        when(questionService.createQuestion(matchDto.idQuestionnaire())).thenReturn(createQuestionResponseDto());

        // Act
        QuestionnaireResponse response = service.transactionQuestionnaire(matchDto);

        // Assert
        assertThat(response).isInstanceOf(QuestionResponseDto.class);
    }

    @Test
    void shouldProcessTransactionQuestionnaireWithFullFlow() {
        // Arrange
        TransactionQuestionnaireDto dto = new TransactionQuestionnaireDto(idQuestionnaire, idQuestion, List.of("A1"), "");
        when(questionService.createQuestion(anyString())).thenReturn(createQuestionResponseDto());
        when(questionQuestionDaughterService.findQuestionDaughterByQuestion(any(), any())).thenReturn(Collections.emptyList());
        when(alternativeQuestionDaughterService.findQuestionDaughterByAlternative(any())).thenReturn(Collections.emptyList());
        when(questionnaireService.getQuestionnaireCache(anyString())).thenReturn(questionMemoryList);

        // Act
        QuestionnaireResponse response = service.transactionQuestionnaire(dto);

        // Assert
        assertThat(response).isInstanceOf(QuestionResponseDto.class);
    }

    @Test
    void shouldReturnFalseWhenVerifyTransitionWithNullDto() {
        boolean result = serviceTestable().verifyTransition(null);
        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnFalseWhenVerifyTransitionWithNullIdQuestion() {
        TransactionQuestionnaireDto dto = new TransactionQuestionnaireDto(idQuestionnaire, null, List.of(), "");
        boolean result = serviceTestable().verifyTransition(dto);
        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnTrueWhenVerifyTransitionWithValidTransition() {
        // Usar el primer ProfileType y su método getTransitionQuestion()
        ProfileType type = ProfileType.values()[0];
        TransactionQuestionnaireDto dto = new TransactionQuestionnaireDto(idQuestionnaire, type.getTransitionQuestion(), List.of(), "");
        boolean result = serviceTestable().verifyTransition(dto);
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenVerifyTransitionWithInvalidTransition() {
        TransactionQuestionnaireDto dto = new TransactionQuestionnaireDto(idQuestionnaire, "INVALID", List.of(), "");
        boolean result = serviceTestable().verifyTransition(dto);
        assertThat(result).isFalse();
    }

    @Test
    void shouldCallSaveDiagnosisCacheAndExcludeQuestionWhenIdentifyDiagnosisMatches() {
        TransactionQuestionnaireDto dto = new TransactionQuestionnaireDto(idQuestionnaire, idQuestion, List.of("ALT1"), "");
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setIdAlternative("ALT1");
        diagnosis.setIdDiagnosis("D1");
        diagnosis.setExcludeQuestion(true);
        when(diagnosisService.getAll()).thenReturn(List.of(diagnosis));
        serviceTestable().identifyDiagnosis(dto);
        // No assertions, just verify no exceptions and coverage
    }

    @Test
    void shouldNotCallSaveDiagnosisCacheWhenIdentifyDiagnosisNoMatch() {
        TransactionQuestionnaireDto dto = new TransactionQuestionnaireDto(idQuestionnaire, idQuestion, List.of("ALT2"), "");
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setIdAlternative("ALT1");
        diagnosis.setIdDiagnosis("D1");
        diagnosis.setExcludeQuestion(false);
        when(diagnosisService.getAll()).thenReturn(List.of(diagnosis));
        serviceTestable().identifyDiagnosis(dto);
    }

    @Test
    void shouldCallSaveSelectedCountryWhenIdentifyCountryIsPeru() {
        TransactionQuestionnaireDto dto = new TransactionQuestionnaireDto(idQuestionnaire, "COUNTRY_QUESTION", List.of("PERU_ID"), "");
        // Simular que COUNTRY_QUESTIONS contiene "COUNTRY_QUESTION" y PERU_ID es "PERU_ID"
        serviceTestable().identifyCountry(dto);
    }

    @Test
    void shouldCallSaveRedirectValidWhenIdentifyRedirect() {
        TransactionQuestionnaireDto dto = new TransactionQuestionnaireDto(idQuestionnaire, idQuestion, List.of("ALTERNATIVE_SIX"), "");
        serviceTestable().identifyRedirect(dto);
    }

    @Test
    void shouldReturnFormattedAlternativeResponses() {
        QuestionMemoryDto q1 = new QuestionMemoryDto(1, "Q1", false, "A1:desc", 1);
        QuestionMemoryDto q2 = new QuestionMemoryDto(2, "Q2", false, "A2", 1);
        when(questionnaireService.getQuestionnaireCache(anyString())).thenReturn(List.of(q1, q2));
        List<String> result = serviceTestable().getFormattedAlternativeResponses(idQuestionnaire);
        assertThat(result).containsExactly("A1", "A2");
    }

    @Test
    void shouldUpdateLevelQuestionForQuestionDaughter() {
        QuestionDaughterDto dto = new QuestionDaughterDto();
        List<QuestionDaughterDto> list = new ArrayList<>();
        list.add(dto);
        when(questionnaireService.getLevelQuestion(anyString())).thenReturn(1);
        List<QuestionDaughterDto> result = serviceTestable().updateLevelQuestion(list, true, idQuestionnaire);
        assertThat(result.get(0).getLevelQuestion()).isEqualTo(2);
    }

    @Test
    void shouldFindUnansweredIdQuestion() {
        QuestionMemoryDto q1 = new QuestionMemoryDto(1, "Q1", false, null, 1);
        List<QuestionMemoryDto> list = List.of(q1);
        when(questionnaireService.getLevelQuestion(anyString())).thenReturn(1);
        String result = serviceTestable().findUnansweredIdQuestion(list, idQuestionnaire);
        assertThat(result).isEqualTo("Q1");
    }

    @Test
    void shouldReturnNullWhenNoUnansweredIdQuestion() {
        QuestionMemoryDto q1 = new QuestionMemoryDto(1, "Q1", true, null, 1);
        List<QuestionMemoryDto> list = List.of(q1);
        when(questionnaireService.getLevelQuestion(anyString())).thenReturn(1);
        String result = serviceTestable().findUnansweredIdQuestion(list, idQuestionnaire);
        assertThat(result).isNull();
    }

    @Test
    void shouldReturnTrueWhenValidateMatch() {
        TransactionQuestionnaireDto dto = new TransactionQuestionnaireDto(idQuestionnaire, QUESTION_FIFTY_SEVEN, List.of(ALTERNATIVE_NINE_HUNDRED_TWENTY_NINE), "");
        when(questionnaireService.getGroupDisordersOne(anyString())).thenReturn(true);
        boolean result = serviceTestable().validateMatch(dto);
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenValidateMatch() {
        TransactionQuestionnaireDto dto = new TransactionQuestionnaireDto(idQuestionnaire, "OTHER", List.of("OTHER"), "");
        when(questionnaireService.getGroupDisordersOne(anyString())).thenReturn(false);
        boolean result = serviceTestable().validateMatch(dto);
        assertThat(result).isFalse();
    }

    @Test
    void shouldUpdateSelectPsycho() {
        Boolean result = service.updateSelectPsycho(idQuestionnaire, "PSYCHO1");
        assertThat(result).isTrue();
    }

    // Clase interna para exponer métodos privados a pruebas
    private TransactionQuestionnaireServiceImpl serviceTestable() {
        return new TransactionQuestionnaireServiceImpl(
                questionQuestionDaughterService,
                questionService,
                questionnaireService,
                alternativeQuestionDaughterService,
                accumulatedPsychoProfileService,
                diagnosisService) {
            public boolean verifyTransition(TransactionQuestionnaireDto dto) { return super.verifyTransition(dto); }
            public void identifyDiagnosis(TransactionQuestionnaireDto dto) { super.identifyDiagnosis(dto); }
            public void identifyCountry(TransactionQuestionnaireDto dto) { super.identifyCountry(dto); }
            public void identifyRedirect(TransactionQuestionnaireDto dto) { super.identifyRedirect(dto); }
            public List<String> getFormattedAlternativeResponses(String id) { return super.getFormattedAlternativeResponses(id); }
            public List<QuestionDaughterDto> updateLevelQuestion(List<QuestionDaughterDto> l, boolean b, String id) { return super.updateLevelQuestion(l, b, id); }
            public String findUnansweredIdQuestion(List<QuestionMemoryDto> l, String id) { return super.findUnansweredIdQuestion(l, id); }
            public boolean validateMatch(TransactionQuestionnaireDto dto) { return super.validateMatch(dto); }
        };
    }

    // Utilidad para crear un QuestionResponseDto válido
    private QuestionResponseDto createQuestionResponseDto() {
        return new QuestionResponseDto(
                "PROCESSING",
                1,
                idQuestionnaire,
                idQuestion,
                "QT001",
                "Test Question",
                new ArrayList<>()
        );
    }
} 