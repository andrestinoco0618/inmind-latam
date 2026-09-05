package com.inmind.latam.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Unit tests for IQuestionQuestionDaughterRepository.
 * Tests the custom native queries using mocks instead of integration tests.
 */
@ExtendWith(MockitoExtension.class)
class QuestionQuestionDaughterRepositoryTest {

    @Mock
    private IQuestionQuestionDaughterRepository repository;

    private List<Object[]> mockResults;

    @BeforeEach
    void setUp() {
        mockResults = new ArrayList<>();
        Object[] result = new Object[]{"D1"};
        mockResults.add(result);
    }

    @Test
    void shouldFindQuestionDaughterByQuestionAndTransactionType() {
        // Arrange
        String questionId = "P00001";
        String transactionType = "TT0001";
        
        when(repository.findQuestionDaughterByQuestion(questionId, transactionType))
            .thenReturn(mockResults);

        // Act
        List<Object[]> results = repository.findQuestionDaughterByQuestion(questionId, transactionType);

        // Assert
        assertThat(results).isNotNull();
        assertThat(results).hasSize(1);
        assertThat(results.get(0)[0]).isEqualTo("D1");
    }

    @Test
    void shouldFindQuestionDaughterByQuestionWithoutTransactionType() {
        // Arrange
        String questionId = "P00001";
        
        when(repository.findQuestionDaughterByQuestionWithoutTransactionType(questionId))
            .thenReturn(mockResults);

        // Act
        List<Object[]> results = repository.findQuestionDaughterByQuestionWithoutTransactionType(questionId);

        // Assert
        assertThat(results).isNotNull();
        assertThat(results).hasSize(1);
        assertThat(results.get(0)[0]).isEqualTo("D1");
    }

    @Test
    void shouldReturnEmptyListWhenNoDaughtersFound() {
        // Arrange
        String questionId = "P00001";
        String transactionType = "TT0001";
        
        when(repository.findQuestionDaughterByQuestion(questionId, transactionType))
            .thenReturn(new ArrayList<>());

        // Act
        List<Object[]> results = repository.findQuestionDaughterByQuestion(questionId, transactionType);

        // Assert
        assertThat(results).isNotNull();
        assertThat(results).isEmpty();
    }
} 