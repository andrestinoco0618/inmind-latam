package com.inmind.latam.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.inmind.latam.exception.ResourceNotFoundException;
import com.inmind.latam.model.QuestionType;
import com.inmind.latam.repository.IQuestionTypeRepository;

/**
 * Unit tests for {@link QuestionTypeServiceImpl}.
 *
 * These tests verify the behavior of the service for retrieving question type information.
 */
@ExtendWith(MockitoExtension.class)
class QuestionTypeServiceImplTest {

    @Mock
    private IQuestionTypeRepository questionTypeRepository;

    @InjectMocks
    private QuestionTypeServiceImpl service;

    @Test
    void shouldReturnQuestionTypeWhenFoundById() {
        // Arrange
        String idQuestionType = "TP001";
        QuestionType questionType = new QuestionType();
        questionType.setIdQuestionType(idQuestionType);
        questionType.setQuestionType("Multiple Choice");
        when(questionTypeRepository.findByIdQuestionType(idQuestionType))
            .thenReturn(Optional.of(questionType));

        // Act
        QuestionType result = service.getQuestionTypeById(idQuestionType);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdQuestionType()).isEqualTo(idQuestionType);
        assertThat(result.getQuestionType()).isEqualTo("Multiple Choice");
    }

    @Test
    void shouldThrowExceptionWhenQuestionTypeNotFoundById() {
        // Arrange
        String idQuestionType = "TP001";
        when(questionTypeRepository.findByIdQuestionType(idQuestionType))
            .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> service.getQuestionTypeById(idQuestionType))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Question type not found with: " + idQuestionType);
    }

    @Test
    void shouldReturnAllQuestionTypes() {
        // Arrange
        QuestionType type1 = new QuestionType();
        type1.setIdQuestionType("TP001");
        type1.setQuestionType("Multiple Choice");
        QuestionType type2 = new QuestionType();
        type2.setIdQuestionType("TP002");
        type2.setQuestionType("Open Ended");
        when(questionTypeRepository.findAll())
            .thenReturn(Arrays.asList(type1, type2));

        // Act
        List<QuestionType> result = service.getAll();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getIdQuestionType()).isEqualTo("TP001");
        assertThat(result.get(0).getQuestionType()).isEqualTo("Multiple Choice");
        assertThat(result.get(1).getIdQuestionType()).isEqualTo("TP002");
        assertThat(result.get(1).getQuestionType()).isEqualTo("Open Ended");
    }

    @Test
    void shouldThrowExceptionWhenNoQuestionTypesFound() {
        // Arrange
        when(questionTypeRepository.findAll())
            .thenReturn(Collections.emptyList());

        // Act & Assert
        assertThatThrownBy(() -> service.getAll())
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("No question types are registered");
    }
} 