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

import com.inmind.latam.model.Diagnosis;
import com.inmind.latam.repository.IDiagnosisRepository;

/**
 * Unit tests for {@link DiagnosisServiceImpl}.
 *
 * These tests verify the behavior of the service for retrieving diagnoses.
 */
@ExtendWith(MockitoExtension.class)
class DiagnosisServiceImplTest {

    @Mock
    private IDiagnosisRepository diagnosisRepository;

    @InjectMocks
    private DiagnosisServiceImpl service;

    @Test
    void shouldReturnAllDiagnoses() {
        // Arrange
        Diagnosis diagnosis1 = new Diagnosis();
        diagnosis1.setIdDiagnosis("D001");
        diagnosis1.setDiagnosisName("Diagnosis 1");
        Diagnosis diagnosis2 = new Diagnosis();
        diagnosis2.setIdDiagnosis("D002");
        diagnosis2.setDiagnosisName("Diagnosis 2");
        when(diagnosisRepository.findAll())
            .thenReturn(Arrays.asList(diagnosis1, diagnosis2));

        // Act
        List<Diagnosis> result = service.getAll();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getIdDiagnosis()).isEqualTo("D001");
        assertThat(result.get(0).getDiagnosisName()).isEqualTo("Diagnosis 1");
        assertThat(result.get(1).getIdDiagnosis()).isEqualTo("D002");
        assertThat(result.get(1).getDiagnosisName()).isEqualTo("Diagnosis 2");
    }

    @Test
    void shouldReturnEmptyListWhenNoDiagnosesFound() {
        // Arrange
        when(diagnosisRepository.findAll())
            .thenReturn(Collections.emptyList());

        // Act
        List<Diagnosis> result = service.getAll();

        // Assert
        assertThat(result).isEmpty();
    }
} 