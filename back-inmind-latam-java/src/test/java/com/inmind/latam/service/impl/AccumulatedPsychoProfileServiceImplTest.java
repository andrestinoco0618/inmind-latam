package com.inmind.latam.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.inmind.latam.dto.PsychologistProfileDto;
import com.inmind.latam.exception.ResourceNotFoundException;
import com.inmind.latam.model.AccumulatedPsychoProfile;
import com.inmind.latam.repository.IAccumulatedPsychoProfileRepository;

/**
 * Unit tests for {@link AccumulatedPsychoProfileServiceImpl}.
 * 
 * These tests verify the behavior of the accumulated psycho profile service implementation,
 * including saving, retrieving, and updating profiles.
 * 
 * @author InMind Latam
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class AccumulatedPsychoProfileServiceImplTest {

    @Mock
    private IAccumulatedPsychoProfileRepository accumulatedPsychoProfileRepository;

    private AccumulatedPsychoProfileServiceImpl accumulatedPsychoProfileService;

    @BeforeEach
    void setUp() {
        accumulatedPsychoProfileService = new AccumulatedPsychoProfileServiceImpl(accumulatedPsychoProfileRepository);
    }

    /**
     * Test saving a new accumulated psycho profile.
     * Verifies that the profile is saved correctly.
     */
    @Test
    void shouldSaveAccumulatedPsychoProfile() {
        // Arrange
        AccumulatedPsychoProfile profile = new AccumulatedPsychoProfile("profile1", "questionnaire1");

        // Act
        accumulatedPsychoProfileService.saveAccumulatedPsychoProfile(profile);

        // Assert
        verify(accumulatedPsychoProfileRepository).save(profile);
    }

    /**
     * Test saving a null accumulated psycho profile.
     * Verifies that an IllegalArgumentException is thrown.
     */
    @Test
    void shouldThrowExceptionWhenSavingNullProfile() {
        // Act & Assert
        assertThatThrownBy(() -> accumulatedPsychoProfileService.saveAccumulatedPsychoProfile(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Accumulated psycho profile cannot be null");
    }

    /**
     * Test retrieving an accumulated psycho profile by questionnaire ID.
     * Verifies that the correct profile is returned.
     */
    @Test
    void shouldGetAccumulatedPsychoProfileByIdQuestionnaire() {
        // Arrange
        String idQuestionnaire = "questionnaire1";
        AccumulatedPsychoProfile expectedProfile = new AccumulatedPsychoProfile("profile1", idQuestionnaire);
        when(accumulatedPsychoProfileRepository.findByIdQuestionnaireAnswered(idQuestionnaire))
            .thenReturn(Optional.of(expectedProfile));

        // Act
        AccumulatedPsychoProfile result = accumulatedPsychoProfileService.getAccumulatedPsychoProfileByIdQuestionnaire(idQuestionnaire);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdQuestionnaireAnswered()).isEqualTo(idQuestionnaire);
    }

    /**
     * Test retrieving a non-existent accumulated psycho profile.
     * Verifies that a ResourceNotFoundException is thrown.
     */
    @Test
    void shouldThrowExceptionWhenProfileNotFound() {
        // Arrange
        String idQuestionnaire = "nonExistent";
        when(accumulatedPsychoProfileRepository.findByIdQuestionnaireAnswered(idQuestionnaire))
            .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> accumulatedPsychoProfileService.getAccumulatedPsychoProfileByIdQuestionnaire(idQuestionnaire))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Accumulated psycho profile not found");
    }

    /**
     * Test updating an accumulated psycho profile with psychologist profile data.
     * Verifies that the profile is updated correctly.
     */
    @Test
    void shouldUpdateAccumulatedPsychoProfile() {
        // Arrange
        String idQuestionnaire = "questionnaire1";
        String questionnaire = "questionnaireData";
        PsychologistProfileDto psychoProfile = new PsychologistProfileDto(idQuestionnaire, "status", null, 5L);
        
        AccumulatedPsychoProfile existingProfile = new AccumulatedPsychoProfile("profile1", idQuestionnaire);
        when(accumulatedPsychoProfileRepository.findByIdQuestionnaireAnswered(idQuestionnaire))
            .thenReturn(Optional.of(existingProfile));
        when(accumulatedPsychoProfileRepository.save(any(AccumulatedPsychoProfile.class)))
            .thenReturn(existingProfile);

        // Act
        accumulatedPsychoProfileService.updateAccumulatedPsychoProfile(psychoProfile, idQuestionnaire, questionnaire);

        // Assert
        verify(accumulatedPsychoProfileRepository).save(existingProfile);
        assertThat(existingProfile.getFinalPoint()).isEqualTo(5);
        assertThat(existingProfile.getIdPsychologist()).isEmpty();
        assertThat(existingProfile.isFinish()).isTrue();
    }

    /**
     * Test updating the selected psychologist for an accumulated psycho profile.
     * Verifies that the psychologist is selected correctly.
     */
    @Test
    void shouldUpdateSelectPsycho() {
        // Arrange
        String idQuestionnaire = "questionnaire1";
        String idPsychologist = "psychologist1";
        
        AccumulatedPsychoProfile existingProfile = new AccumulatedPsychoProfile("profile1", idQuestionnaire);
        when(accumulatedPsychoProfileRepository.findByIdQuestionnaireAnswered(idQuestionnaire))
            .thenReturn(Optional.of(existingProfile));
        when(accumulatedPsychoProfileRepository.save(any(AccumulatedPsychoProfile.class)))
            .thenReturn(existingProfile);

        // Act
        accumulatedPsychoProfileService.updateSelectPsycho(idQuestionnaire, idPsychologist);

        // Assert
        verify(accumulatedPsychoProfileRepository).save(existingProfile);
        assertThat(existingProfile.getIdPsychologist()).isEqualTo(idPsychologist);
        assertThat(existingProfile.isSelectPsychology()).isTrue();
    }
} 