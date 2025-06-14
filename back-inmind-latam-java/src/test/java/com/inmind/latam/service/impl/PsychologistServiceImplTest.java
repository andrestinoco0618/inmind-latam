package com.inmind.latam.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.inmind.latam.exception.ResourceNotFoundException;
import com.inmind.latam.model.Psychologist;
import com.inmind.latam.repository.IPsychologistRepository;

/**
 * Unit tests for {@link PsychologistServiceImpl}.
 *
 * These tests verify the behavior of the service for retrieving psychologist information.
 */
@ExtendWith(MockitoExtension.class)
class PsychologistServiceImplTest {

    @Mock
    private IPsychologistRepository psychologistRepository;

    @InjectMocks
    private PsychologistServiceImpl service;

    @Test
    void shouldReturnPsychologistWhenFound() {
        // Arrange
        String idPsychologist = "PS0001";
        Psychologist psychologist = new Psychologist();
        psychologist.setIdPsychologist(idPsychologist);
        psychologist.setName("John Doe");
        when(psychologistRepository.findById(idPsychologist))
            .thenReturn(Optional.of(psychologist));

        // Act
        Psychologist result = service.getPsychologistById(idPsychologist);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdPsychologist()).isEqualTo(idPsychologist);
        assertThat(result.getName()).isEqualTo("John Doe");
    }

    @Test
    void shouldThrowExceptionWhenPsychologistNotFound() {
        // Arrange
        String idPsychologist = "PS0001";
        when(psychologistRepository.findById(idPsychologist))
            .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> service.getPsychologistById(idPsychologist))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Psychologist not found with: " + idPsychologist);
    }
} 