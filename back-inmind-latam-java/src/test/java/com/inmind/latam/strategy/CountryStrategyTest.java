package com.inmind.latam.strategy;

import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.service.ICountryService;
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
 * Unit tests for CountryStrategy.
 * Tests the country-specific location strategy implementation.
 */
@ExtendWith(MockitoExtension.class)
class CountryStrategyTest {

    @Mock
    private ICountryService countryService;

    private CountryStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new CountryStrategy(countryService);
    }

    @Test
    void shouldGetCountryAlternatives() {
        // Arrange
        List<AlternativeDto> expectedAlternatives = Arrays.asList(
            new AlternativeDto("1", "Colombia"),
            new AlternativeDto("2", "Perú")
        );
        when(countryService.getCountryFormatAlternative()).thenReturn(expectedAlternatives);

        // Act
        List<AlternativeDto> result = strategy.getAlternatives(null);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedAlternatives);
        verify(countryService).getCountryFormatAlternative();
    }

    @Test
    void shouldIgnoreParentId() {
        // Arrange
        List<AlternativeDto> expectedAlternatives = Arrays.asList(
            new AlternativeDto("1", "Colombia"),
            new AlternativeDto("2", "Perú")
        );
        when(countryService.getCountryFormatAlternative()).thenReturn(expectedAlternatives);

        // Act
        List<AlternativeDto> result = strategy.getAlternatives(123);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedAlternatives);
        verify(countryService).getCountryFormatAlternative();
    }
} 