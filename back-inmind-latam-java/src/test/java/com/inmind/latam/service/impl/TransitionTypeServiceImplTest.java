package com.inmind.latam.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.inmind.latam.exception.ResourceNotFoundException;
import com.inmind.latam.model.TransitionType;
import com.inmind.latam.repository.ITransitionTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link TransitionTypeServiceImpl}.
 *
 * These tests verify the behavior of the service for managing transition types.
 */
@ExtendWith(MockitoExtension.class)
class TransitionTypeServiceImplTest {

    @Mock
    private ITransitionTypeRepository transitionTypeRepository;

    @InjectMocks
    private TransitionTypeServiceImpl service;

    private TransitionType transitionType;

    @BeforeEach
    void setUp() {
        transitionType = new TransitionType();
        transitionType.setIdTransitionType("T001");
        transitionType.setTransitionName("TypeA");
        transitionType.setIdProfile("P001");
        transitionType.setIdAlternative("A001");
    }

    @Test
    void shouldReturnTransitionTypeWhenFound() {
        // Arrange
        when(transitionTypeRepository.findByIdProfileAndIdAlternative("P001", "A001"))
            .thenReturn(Optional.of(transitionType));

        // Act
        TransitionType result = service.identifyTranstionType("P001", "A001");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdTransitionType()).isEqualTo("T001");
        assertThat(result.getTransitionName()).isEqualTo("TypeA");
        assertThat(result.getIdProfile()).isEqualTo("P001");
        assertThat(result.getIdAlternative()).isEqualTo("A001");
    }

    @Test
    void shouldThrowExceptionWhenTransitionTypeNotFound() {
        // Arrange
        when(transitionTypeRepository.findByIdProfileAndIdAlternative(anyString(), anyString()))
            .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> service.identifyTranstionType("P002", "A002"))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Transition type not found with: P002 - A002");
    }
} 