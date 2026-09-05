package com.inmind.latam.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.model.Alternative;
import com.inmind.latam.repository.IAlternativeRepository;

/**
 * Unit tests for {@link AlternativeServiceImpl}.
 *
 * These tests verify the behavior of the service for retrieving alternatives by question ID.
 */
@ExtendWith(MockitoExtension.class)
class AlternativeServiceImplTest {

    @Mock
    private IAlternativeRepository alternativeRepository;

    @InjectMocks
    private AlternativeServiceImpl service;

    @Test
    void shouldReturnAlternativesByQuestionId() {
        // Arrange
        Alternative alt1 = new Alternative();
        alt1.setIdAlternative("A00001");
        alt1.setTextAlternative("Option 1");
        Alternative alt2 = new Alternative();
        alt2.setIdAlternative("A00002");
        alt2.setTextAlternative("Option 2");
        when(alternativeRepository.findByQuestion_IdQuestion("Q1"))
            .thenReturn(Arrays.asList(alt1, alt2));

        // Act
        List<AlternativeDto> result = service.getAlternativesByQuestionId("Q1");

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).idAlternative()).isEqualTo("A00001");
        assertThat(result.get(0).text()).isEqualTo("Option 1");
        assertThat(result.get(1).idAlternative()).isEqualTo("A00002");
        assertThat(result.get(1).text()).isEqualTo("Option 2");
    }

    @Test
    void shouldReturnEmptyListWhenNoAlternativesFound() {
        // Arrange
        when(alternativeRepository.findByQuestion_IdQuestion("Q2"))
            .thenReturn(Collections.emptyList());

        // Act
        List<AlternativeDto> result = service.getAlternativesByQuestionId("Q2");

        // Assert
        assertThat(result).isEmpty();
    }
} 