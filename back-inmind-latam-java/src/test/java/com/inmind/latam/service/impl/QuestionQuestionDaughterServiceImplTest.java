package com.inmind.latam.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.lenient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.inmind.latam.constant.ProfileType;
import com.inmind.latam.constant.QuestionIdentifiers;
import com.inmind.latam.dto.QuestionDaughterDto;
import com.inmind.latam.dto.TransactionQuestionnaireDto;
import com.inmind.latam.model.TransitionType;
import com.inmind.latam.repository.IQuestionQuestionDaughterRepository;
import com.inmind.latam.service.IQuestionnaireService;
import com.inmind.latam.service.ITransitionTypeService;

/**
 * Unit tests for {@link QuestionQuestionDaughterServiceImpl}.
 *
 * These tests verify the behavior of the service for managing question daughter data.
 */
@ExtendWith(MockitoExtension.class)
class QuestionQuestionDaughterServiceImplTest {

    @Mock
    private IQuestionQuestionDaughterRepository questionQuestionDaughterRepository;

    @Mock
    private ITransitionTypeService transitionTypeService;

    @Mock
    private IQuestionnaireService questionnaireService;

    @InjectMocks
    private QuestionQuestionDaughterServiceImpl service;

    private TransactionQuestionnaireDto transactionQuestionnaire;
    private List<String> answersCache;
    private List<Object[]> mockQuestionList;
    private TransitionType transitionType;

    @BeforeEach
    void setUp() {
        transactionQuestionnaire = new TransactionQuestionnaireDto("questionnaire123-abc", "P00001", Arrays.asList("A1", "A2"), "");
        answersCache = Arrays.asList("A1", "A2");
        mockQuestionList = new java.util.ArrayList<>(Arrays.asList(
            new Object[]{QuestionIdentifiers.QUESTION_FIVE, 1},
            new Object[]{QuestionIdentifiers.QUESTION_EIGHT, 1}
        ));
        transitionType = new TransitionType();
        transitionType.setIdTransitionType("T001");
    }

    @Test
    void shouldFindQuestionDaughterByQuestion() {
        // Arrange
        when(questionnaireService.getProfileTypeCache(anyString())).thenReturn(ProfileType.PF001.name());
        when(questionnaireService.getAlternativeTransitionCache(anyString())).thenReturn(null);
        when(questionnaireService.getTransitionCache(anyString())).thenReturn("T001");
        when(questionQuestionDaughterRepository.findQuestionDaughterByQuestion(anyString(), any()))
            .thenReturn(mockQuestionList);

        // Act
        List<QuestionDaughterDto> result = service.findQuestionDaughterByQuestion(transactionQuestionnaire, answersCache);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).extracting(QuestionDaughterDto::getIdQuestionDaughter)
            .containsExactlyInAnyOrder(QuestionIdentifiers.QUESTION_FIVE, QuestionIdentifiers.QUESTION_EIGHT);
        verify(questionQuestionDaughterRepository).findQuestionDaughterByQuestion(
            eq(QuestionIdentifiers.QUESTION_ONE), eq("T001"));
    }

    @Test
    void shouldHandleAlternativeTransition() {
        // Arrange
        when(questionnaireService.getProfileTypeCache(anyString())).thenReturn(ProfileType.PF001.name());
        when(questionnaireService.getAlternativeTransitionCache(anyString())).thenReturn("ALT001");
        when(transitionTypeService.identifyTranstionType(anyString(), anyString())).thenReturn(transitionType);
        when(questionnaireService.getTransitionCache(anyString())).thenReturn("T001");
        when(questionQuestionDaughterRepository.findQuestionDaughterByQuestion(anyString(), any()))
            .thenReturn(mockQuestionList);

        // Act
        List<QuestionDaughterDto> result = service.findQuestionDaughterByQuestion(transactionQuestionnaire, answersCache);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).extracting(QuestionDaughterDto::getIdQuestionDaughter)
            .containsExactlyInAnyOrder(QuestionIdentifiers.QUESTION_FIVE, QuestionIdentifiers.QUESTION_EIGHT);
        verify(questionnaireService).saveTransitionCache(eq("questionnaire123-abc"), eq("T001"));
        verify(questionnaireService).removeAlternativeTransitionCache(eq("questionnaire123-abc"));
    }

    @Test
    void shouldRemoveQuestionNineWhenNoGroupDisordersOne() {
        // Arrange
        transactionQuestionnaire = new TransactionQuestionnaireDto("questionnaire123-abc", QuestionIdentifiers.QUESTION_EIGHT, Collections.emptyList(), "");
        mockQuestionList = new java.util.ArrayList<>(Arrays.asList(
            new Object[]{QuestionIdentifiers.QUESTION_NINE, 1},
            new Object[]{QuestionIdentifiers.QUESTION_TWELVE, 1}
        ));
        when(questionnaireService.getProfileTypeCache(anyString())).thenReturn(ProfileType.PF001.name());
        when(questionnaireService.getAlternativeTransitionCache(anyString())).thenReturn(null);
        when(questionnaireService.getTransitionCache(anyString())).thenReturn("T001");
        when(questionQuestionDaughterRepository.findQuestionDaughterByQuestion(anyString(), any()))
            .thenReturn(mockQuestionList);

        // Act
        List<QuestionDaughterDto> result = service.findQuestionDaughterByQuestion(transactionQuestionnaire, Collections.emptyList());

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getIdQuestionDaughter()).isEqualTo(QuestionIdentifiers.QUESTION_TWELVE);
    }

    @Test
    void shouldHandlePeruCountryForDistrictQuestions() {
        // Arrange
        transactionQuestionnaire = new TransactionQuestionnaireDto("questionnaire123-abc", QuestionIdentifiers.DISTRICT_QUESTION_ONE, answersCache, "");
        mockQuestionList = new java.util.ArrayList<>(Arrays.asList(
            new Object[]{QuestionIdentifiers.DISTRICT_QUESTION_ONE, 1},
            new Object[]{QuestionIdentifiers.DISTRICT_QUESTION_TWO, 1}
        ));
        when(questionnaireService.getProfileTypeCache(anyString())).thenReturn(ProfileType.PF001.name());
        when(questionnaireService.getAlternativeTransitionCache(anyString())).thenReturn(null);
        when(questionnaireService.getTransitionCache(anyString())).thenReturn("T001");
        lenient().when(questionnaireService.getSelectedCountry(anyString())).thenReturn(true);
        when(questionQuestionDaughterRepository.findQuestionDaughterByQuestion(anyString(), any()))
            .thenReturn(mockQuestionList);

        // Act
        List<QuestionDaughterDto> result = service.findQuestionDaughterByQuestion(transactionQuestionnaire, answersCache);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).extracting(QuestionDaughterDto::getIdQuestionDaughter)
            .containsExactlyInAnyOrder(QuestionIdentifiers.DISTRICT_QUESTION_TWO, QuestionIdentifiers.DISTRICT_QUESTION_ONE);
    }

    @Test
    void shouldReturnAllDistrictQuestionsForNonPeruCountry() {
        // Arrange
        transactionQuestionnaire = new TransactionQuestionnaireDto("questionnaire123-abc", QuestionIdentifiers.DISTRICT_QUESTION_ONE, answersCache, "");
        mockQuestionList = new java.util.ArrayList<>(Arrays.asList(
            new Object[]{QuestionIdentifiers.DISTRICT_QUESTION_ONE, 1},
            new Object[]{QuestionIdentifiers.DISTRICT_QUESTION_TWO, 1}
        ));
        when(questionnaireService.getProfileTypeCache(anyString())).thenReturn(ProfileType.PF001.name());
        when(questionnaireService.getAlternativeTransitionCache(anyString())).thenReturn(null);
        when(questionnaireService.getTransitionCache(anyString())).thenReturn("T001");
        lenient().when(questionnaireService.getSelectedCountry(anyString())).thenReturn(false);
        when(questionQuestionDaughterRepository.findQuestionDaughterByQuestion(anyString(), any()))
            .thenReturn(mockQuestionList);

        // Act
        List<QuestionDaughterDto> result = service.findQuestionDaughterByQuestion(transactionQuestionnaire, answersCache);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).extracting(QuestionDaughterDto::getIdQuestionDaughter)
            .containsExactlyInAnyOrder(QuestionIdentifiers.DISTRICT_QUESTION_ONE, QuestionIdentifiers.DISTRICT_QUESTION_TWO);
    }
} 