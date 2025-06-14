package com.inmind.latam.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.inmind.latam.model.DiagnosisAlternative;
import com.inmind.latam.repository.IDiagnosisAlternativeRepository;

/**
 * Unit tests for {@link DiagnosisAlternativeServiceImpl}.
 *
 * These tests verify the behavior of the service for retrieving alternative IDs to remove based on diagnoses.
 */
@ExtendWith(MockitoExtension.class)
class DiagnosisAlternativeServiceImplTest {

    @Mock
    private IDiagnosisAlternativeRepository diagnosisAlternativeRepository;

    @InjectMocks
    private DiagnosisAlternativeServiceImpl service;

    @Test
    void shouldReturnAlternativesToRemoveWhenDiagnosesMatch() {
        // Arrange
        DiagnosisAlternative da1 = new DiagnosisAlternative();
        da1.setIdDiagnosis("D1");
        da1.setIdAlternative("A1");
        DiagnosisAlternative da2 = new DiagnosisAlternative();
        da2.setIdDiagnosis("D2");
        da2.setIdAlternative("A2");
        DiagnosisAlternative da3 = new DiagnosisAlternative();
        da3.setIdDiagnosis("D3");
        da3.setIdAlternative("A3");
        when(diagnosisAlternativeRepository.findAll())
            .thenReturn(Arrays.asList(da1, da2, da3));

        // Act
        List<String> result = service.getAlternativeRemoveByDiagnosis(Arrays.asList("D1", "D3"));

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder("A1", "A3");
    }

    @Test
    void shouldReturnEmptyListWhenNoDiagnosesMatch() {
        // Arrange
        DiagnosisAlternative da1 = new DiagnosisAlternative();
        da1.setIdDiagnosis("D1");
        da1.setIdAlternative("A1");
        DiagnosisAlternative da2 = new DiagnosisAlternative();
        da2.setIdDiagnosis("D2");
        da2.setIdAlternative("A2");
        when(diagnosisAlternativeRepository.findAll())
            .thenReturn(Arrays.asList(da1, da2));

        // Act
        List<String> result = service.getAlternativeRemoveByDiagnosis(Arrays.asList("D3", "D4"));

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenDiagnosesIsNull() {
        // Arrange
        DiagnosisAlternative da1 = new DiagnosisAlternative();
        da1.setIdDiagnosis("D1");
        da1.setIdAlternative("A1");
        when(diagnosisAlternativeRepository.findAll())
            .thenReturn(Collections.singletonList(da1));

        // Act
        List<String> result = service.getAlternativeRemoveByDiagnosis(null);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenNoAlternativesFound() {
        // Arrange
        when(diagnosisAlternativeRepository.findAll())
            .thenReturn(Collections.emptyList());

        // Act
        List<String> result = service.getAlternativeRemoveByDiagnosis(Arrays.asList("D1", "D2"));

        // Assert
        assertThat(result).isEmpty();
    }
} 