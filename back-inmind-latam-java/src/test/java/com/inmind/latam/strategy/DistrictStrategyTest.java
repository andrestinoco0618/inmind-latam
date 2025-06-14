package com.inmind.latam.strategy;

import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.service.IDistrictService;
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
 * Unit tests for DistrictStrategy.
 * Tests the district-specific location strategy implementation.
 */
@ExtendWith(MockitoExtension.class)
class DistrictStrategyTest {

    @Mock
    private IDistrictService districtService;

    private DistrictStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new DistrictStrategy(districtService);
    }

    @Test
    void shouldGetDistrictAlternatives() {
        // Arrange
        Integer cityId = 1;
        List<AlternativeDto> expectedAlternatives = Arrays.asList(
            new AlternativeDto("1", "Chapinero"),
            new AlternativeDto("2", "Usaquén")
        );
        when(districtService.getDistrictFormatAlternative(cityId)).thenReturn(expectedAlternatives);

        // Act
        List<AlternativeDto> result = strategy.getAlternatives(cityId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedAlternatives);
        verify(districtService).getDistrictFormatAlternative(cityId);
    }

    @Test
    void shouldHandleNullParentId() {
        // Arrange
        List<AlternativeDto> expectedAlternatives = Arrays.asList(
            new AlternativeDto("1", "Chapinero"),
            new AlternativeDto("2", "Usaquén")
        );
        when(districtService.getDistrictFormatAlternative(0)).thenReturn(expectedAlternatives);

        // Act
        List<AlternativeDto> result = strategy.getAlternatives(0);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedAlternatives);
        verify(districtService).getDistrictFormatAlternative(0);
    }
} 