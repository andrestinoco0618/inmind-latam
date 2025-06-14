package com.inmind.latam.strategy;

import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.service.ICityService;
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
 * Unit tests for CityStrategy.
 * Tests the city-specific location strategy implementation.
 */
@ExtendWith(MockitoExtension.class)
class CityStrategyTest {

    @Mock
    private ICityService cityService;

    private CityStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new CityStrategy(cityService);
    }

    @Test
    void shouldGetCityAlternatives() {
        // Arrange
        Integer stateId = 1;
        List<AlternativeDto> expectedAlternatives = Arrays.asList(
            new AlternativeDto("1", "Bogotá"),
            new AlternativeDto("2", "Medellín")
        );
        when(cityService.getCityFormatAlternative(stateId)).thenReturn(expectedAlternatives);

        // Act
        List<AlternativeDto> result = strategy.getAlternatives(stateId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedAlternatives);
        verify(cityService).getCityFormatAlternative(stateId);
    }

    @Test
    void shouldHandleNullParentId() {
        // Arrange
        List<AlternativeDto> expectedAlternatives = Arrays.asList(
            new AlternativeDto("1", "Bogotá"),
            new AlternativeDto("2", "Medellín")
        );
        when(cityService.getCityFormatAlternative(0)).thenReturn(expectedAlternatives);

        // Act
        List<AlternativeDto> result = strategy.getAlternatives(0);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedAlternatives);
        verify(cityService).getCityFormatAlternative(0);
    }
} 