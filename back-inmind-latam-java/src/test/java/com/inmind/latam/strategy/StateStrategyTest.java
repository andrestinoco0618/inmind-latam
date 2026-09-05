package com.inmind.latam.strategy;

import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.service.IStateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for StateStrategy.
 * Tests the state-specific location strategy implementation.
 */
@ExtendWith(MockitoExtension.class)
class StateStrategyTest {

    @Mock
    private IStateService stateService;

    private StateStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new StateStrategy(stateService);
    }

    @Test
    void shouldGetStateAlternatives() {
        // Arrange
        Integer countryId = 1;
        List<AlternativeDto> expectedAlternatives = Arrays.asList(
            new AlternativeDto("1", "Cundinamarca"),
            new AlternativeDto("2", "Antioquia")
        );
        when(stateService.getStateFormatAlternative(countryId)).thenReturn(expectedAlternatives);

        // Act
        List<AlternativeDto> result = strategy.getAlternatives(countryId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedAlternatives);
        verify(stateService).getStateFormatAlternative(countryId);
    }

    @Test
    void shouldHandleNullParentId() {
        // Arrange
        List<AlternativeDto> expectedAlternatives = Arrays.asList(
            new AlternativeDto("1", "Cundinamarca"),
            new AlternativeDto("2", "Antioquia")
        );
        when(stateService.getStateFormatAlternative(0)).thenReturn(expectedAlternatives);

        // Act
        List<AlternativeDto> result = strategy.getAlternatives(0);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedAlternatives);
        verify(stateService).getStateFormatAlternative(0);
    }
} 