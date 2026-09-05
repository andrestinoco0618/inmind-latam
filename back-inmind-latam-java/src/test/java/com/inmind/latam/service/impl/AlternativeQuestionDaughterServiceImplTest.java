package com.inmind.latam.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.inmind.latam.dto.QuestionDaughterDto;
import com.inmind.latam.dto.TransactionQuestionnaireDto;
import com.inmind.latam.model.TransitionType;
import com.inmind.latam.repository.IAlternativeQuestionDaughterRepository;
import com.inmind.latam.service.IQuestionnaireService;
import com.inmind.latam.service.ITransitionTypeService;

import static com.inmind.latam.constant.QuestionIdentifiers.QUESTION_FIVE;
import static com.inmind.latam.constant.QuestionIdentifiers.GROUP_DISORDERS_ONE;
import static com.inmind.latam.constant.QuestionIdentifiers.TYPE_TRANSITION_TWO;

/**
 * Unit tests for {@link AlternativeQuestionDaughterServiceImpl}.
 *
 * These tests verify the behavior of the service for finding daughter questions by alternative,
 * including the main flow and special logic for group disorders.
 */
@ExtendWith(MockitoExtension.class)
class AlternativeQuestionDaughterServiceImplTest {

    @Mock
    private IAlternativeQuestionDaughterRepository alternativeQuestionDaughterRepository;
    @Mock
    private ITransitionTypeService transitionTypeService;
    @Mock
    private IQuestionnaireService questionnaireService;

    @InjectMocks
    private AlternativeQuestionDaughterServiceImpl service;

    private TransactionQuestionnaireDto transactionDto;

    @BeforeEach
    void setUp() {
        transactionDto = new TransactionQuestionnaireDto("questionnaire123-abc", "P00001", Arrays.asList("A1", "A2"), "");
    }

    @Test
    void shouldReturnDaughterQuestionsForNormalFlow() {
        // Arrange
        when(questionnaireService.getProfileTypeCache("questionnaire123-abc")).thenReturn("profileType");
        when(questionnaireService.getAlternativeTransitionCache("questionnaire123-abc")).thenReturn(null);
        when(questionnaireService.getTransitionCache("questionnaire123-abc")).thenReturn("transitionType");
        when(alternativeQuestionDaughterRepository.findQuestionDaughterByAlternativeAndTransaction(eq("transitionType"), any()))
            .thenReturn(Arrays.asList("QH1", "QH2"));

        // Act
        List<QuestionDaughterDto> result = service.findQuestionDaughterByAlternative(transactionDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getIdQuestionDaughter()).isEqualTo("QH1");
        assertThat(result.get(1).getIdQuestionDaughter()).isEqualTo("QH2");
    }

    @Test
    void shouldHandleAlternativeTransitionAndRemoveCache() {
        // Arrange
        when(questionnaireService.getProfileTypeCache("questionnaire123-abc")).thenReturn("profileType");
        when(questionnaireService.getAlternativeTransitionCache("questionnaire123-abc")).thenReturn("altTrans");
        when(questionnaireService.getTransitionCache("questionnaire123-abc")).thenReturn("transitionType");
        TransitionType transitionType = new TransitionType();
        transitionType.setIdTransitionType("newTransition");
        when(transitionTypeService.identifyTranstionType("profileType", "altTrans")).thenReturn(transitionType);
        when(questionnaireService.saveTransitionCache("questionnaire123-abc", "newTransition")).thenReturn("newTransition");
        when(alternativeQuestionDaughterRepository.findQuestionDaughterByAlternativeAndTransaction(eq("newTransition"), any()))
            .thenReturn(Collections.singletonList("QH1"));

        // Act
        List<QuestionDaughterDto> result = service.findQuestionDaughterByAlternative(transactionDto);

        // Assert
        assertThat(result).hasSize(1);
        verify(questionnaireService).removeAlternativeTransitionCache("questionnaire123-abc");
    }

    @Test
    void shouldSaveGroupDisordersOneWhenConditionsMet() {
        // Arrange
        when(questionnaireService.getProfileTypeCache("questionnaire123-abc")).thenReturn("profileType");
        when(questionnaireService.getAlternativeTransitionCache("questionnaire123-abc")).thenReturn(null);
        when(questionnaireService.getTransitionCache("questionnaire123-abc")).thenReturn(TYPE_TRANSITION_TWO);
        when(alternativeQuestionDaughterRepository.findQuestionDaughterByAlternativeAndTransaction(eq(TYPE_TRANSITION_TWO), any()))
            .thenReturn(Arrays.asList("QH1"));
        // El idQuestion es QUESTION_FIVE y la respuesta contiene un valor de GROUP_DISORDERS_ONE
        String groupDisorderValue = GROUP_DISORDERS_ONE.iterator().next(); // Tomamos el primer valor del conjunto
        TransactionQuestionnaireDto dto = new TransactionQuestionnaireDto("questionnaire123-abc", QUESTION_FIVE, Collections.singletonList(groupDisorderValue), "");

        // Act
        service.findQuestionDaughterByAlternative(dto);

        // Assert
        verify(questionnaireService).saveGroupDisordersOne("questionnaire123-abc");
    }

    @Test
    void shouldReturnEmptyListWhenNoDaughtersFound() {
        // Arrange
        when(questionnaireService.getProfileTypeCache("questionnaire123-abc")).thenReturn("profileType");
        when(questionnaireService.getAlternativeTransitionCache("questionnaire123-abc")).thenReturn(null);
        when(questionnaireService.getTransitionCache("questionnaire123-abc")).thenReturn("transitionType");
        when(alternativeQuestionDaughterRepository.findQuestionDaughterByAlternativeAndTransaction(eq("transitionType"), any()))
            .thenReturn(Collections.emptyList());

        // Act
        List<QuestionDaughterDto> result = service.findQuestionDaughterByAlternative(transactionDto);

        // Assert
        assertThat(result).isEmpty();
    }
} 