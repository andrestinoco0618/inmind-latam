package com.inmind.latam.service.impl;

import com.inmind.latam.dto.PsychologistDto;
import com.inmind.latam.dto.PsychologistProfileDto;
import com.inmind.latam.model.Psychologist;
import com.inmind.latam.repository.IAlternativePsychoProfileRepository;
import com.inmind.latam.service.IPsychologistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.inmind.latam.constant.StatusValues.MATCHING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AlternativePsychoProfileServiceImpl.
 * Tests the functionality for determining psychologist profiles based on alternatives.
 */
@ExtendWith(MockitoExtension.class)
class AlternativePsychoProfileServiceImplTest {

    @Mock
    private IAlternativePsychoProfileRepository repository;

    @Mock
    private IPsychologistService psychologistService;

    private AlternativePsychoProfileServiceImpl service;
    private Psychologist mockPsychologist;

    @BeforeEach
    void setUp() {
        service = new AlternativePsychoProfileServiceImpl(repository, psychologistService);
        
        mockPsychologist = new Psychologist();
        mockPsychologist.setIdPsychologist("1");
        mockPsychologist.setName("John");
        mockPsychologist.setLastname("Doe");
        mockPsychologist.setLinkProfile("http://profile.com");
        mockPsychologist.setImage("image.jpg");
    }

    @Test
    void shouldDeterminePsychologistWithAlternatives() {
        // Arrange
        String idQuestionnaire = "Q1";
        String profileType = "TYPE1";
        List<String> answersCache = List.of("A1", "A2");
        Object[] result = new Object[]{"1", 5L};
        List<Object[]> results = new ArrayList<>();
        results.add(result);
        
        when(repository.findPsychoWithMostAlternatives(profileType, answersCache))
            .thenReturn(results);
        when(psychologistService.getPsychologistById("1")).thenReturn(mockPsychologist);

        // Act
        PsychologistProfileDto profile = service.determinePsychologistWithAlternatives(
            idQuestionnaire, profileType, answersCache);

        // Assert
        assertThat(profile).isNotNull();
        assertThat(profile.idQuestionnaire()).isEqualTo(idQuestionnaire);
        assertThat(profile.status()).isEqualTo(MATCHING);
        assertThat(profile.listPsychologist()).hasSize(1);
        assertThat(profile.countAlternatives()).isEqualTo(5L);
        
        PsychologistDto psychologist = profile.listPsychologist().get(0);
        assertThat(psychologist.idPsychologist()).isEqualTo("1");
        assertThat(psychologist.name()).isEqualTo("John Doe");
        assertThat(psychologist.linkProfile()).isEqualTo("http://profile.com");
        assertThat(psychologist.image()).isEqualTo("image.jpg");
    }

    @Test
    void shouldThrowExceptionWhenAnswersCacheIsNull() {
        // Act & Assert
        assertThatThrownBy(() -> service.determinePsychologistWithAlternatives("Q1", "TYPE1", null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("ResponseQuestionMemoryDto cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenNoPsychologistFound() {
        // Arrange
        when(repository.findPsychoWithMostAlternatives(any(), any())).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThatThrownBy(() -> service.determinePsychologistWithAlternatives("Q1", "TYPE1", List.of("A1")))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessageContaining("No psychologist found with the provided alternatives");
    }

    @Test
    void shouldCapitalizePsychologistName() {
        // Arrange
        mockPsychologist.setName("john");
        mockPsychologist.setLastname("doe");
        
        Object[] result = new Object[]{"1", 5L};
        List<Object[]> results = new ArrayList<>();
        results.add(result);
        when(repository.findPsychoWithMostAlternatives(any(), any()))
            .thenReturn(results);
        when(psychologistService.getPsychologistById("1")).thenReturn(mockPsychologist);

        // Act
        PsychologistProfileDto profile = service.determinePsychologistWithAlternatives(
            "Q1", "TYPE1", List.of("A1"));

        // Assert
        assertThat(profile.listPsychologist().get(0).name()).isEqualTo("John Doe");
    }
} 