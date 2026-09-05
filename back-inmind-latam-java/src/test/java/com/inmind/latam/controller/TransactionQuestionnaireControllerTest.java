package com.inmind.latam.controller;

import com.inmind.latam.dto.QuestionResponseDto;
import com.inmind.latam.dto.TransactionQuestionnaireDto;
import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.exception.ResourceNotFoundException;
import com.inmind.latam.service.ITransactionQuestionnaireService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static com.inmind.latam.constant.CacheKeys.PROFILE_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for TransactionQuestionnaireController.
 * Tests the endpoints for questionnaire management.
 */
@ExtendWith(MockitoExtension.class)
class TransactionQuestionnaireControllerTest {

    @Mock
    private ITransactionQuestionnaireService transactionQuestionnaireService;

    @InjectMocks
    private TransactionQuestionnaireController controller;

    private QuestionResponseDto mockQuestionResponse;
    private TransactionQuestionnaireDto mockTransactionDto;

    @BeforeEach
    void setUp() {
        // Inicializar QuestionResponseDto con datos de prueba
        mockQuestionResponse = new QuestionResponseDto(
            "processing",
            1,
            "Q123",
            "P00001",
            "TP001",
            "Test Question",
            new ArrayList<>()
        );

        // Inicializar TransactionQuestionnaireDto con datos de prueba
        mockTransactionDto = new TransactionQuestionnaireDto(
            "Q123",
            "P00001",
            new ArrayList<>(),
            "Test response"
        );
    }

    @Test
    void shouldStartQuestionnaireSuccessfully() throws ResourceNotFoundException {
        // Arrange
        String profileType = "PF001";
        when(transactionQuestionnaireService.startQuestionnaire(profileType))
            .thenReturn(mockQuestionResponse);

        // Act
        ResponseEntity<?> response = controller.getQuestionnaireName(profileType);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockQuestionResponse);
        verify(transactionQuestionnaireService).startQuestionnaire(profileType);
    }

    @Test
    void shouldProcessQuestionnaireResponseSuccessfully() throws ResourceNotFoundException {
        // Arrange
        when(transactionQuestionnaireService.transactionQuestionnaire(any(TransactionQuestionnaireDto.class)))
            .thenReturn(mockQuestionResponse);

        // Act
        ResponseEntity<?> response = controller.transactionQuestionnaire(mockTransactionDto);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockQuestionResponse);
        verify(transactionQuestionnaireService).transactionQuestionnaire(mockTransactionDto);
    }

    @Test
    void shouldSelectPsychologistSuccessfully() {
        // Arrange
        String idQuestionnaire = "Q123";
        String idPsychologist = "P456";
        when(transactionQuestionnaireService.updateSelectPsycho(idQuestionnaire, idPsychologist))
            .thenReturn(true);

        // Act
        ResponseEntity<?> response = controller.selectPsychologist(idQuestionnaire, idPsychologist);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(transactionQuestionnaireService).updateSelectPsycho(idQuestionnaire, idPsychologist);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenStartingQuestionnaire() throws ResourceNotFoundException {
        // Arrange
        String profileType = "INVALID";
        when(transactionQuestionnaireService.startQuestionnaire(profileType))
            .thenThrow(new ResourceNotFoundException("Profile type not found"));

        // Act & Assert
        try {
            controller.getQuestionnaireName(profileType);
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Profile type not found");
        }
        verify(transactionQuestionnaireService).startQuestionnaire(profileType);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenProcessingResponse() throws ResourceNotFoundException {
        // Arrange
        when(transactionQuestionnaireService.transactionQuestionnaire(any(TransactionQuestionnaireDto.class)))
            .thenThrow(new ResourceNotFoundException("Questionnaire not found"));

        // Act & Assert
        try {
            controller.transactionQuestionnaire(mockTransactionDto);
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Questionnaire not found");
        }
        verify(transactionQuestionnaireService).transactionQuestionnaire(mockTransactionDto);
    }
} 