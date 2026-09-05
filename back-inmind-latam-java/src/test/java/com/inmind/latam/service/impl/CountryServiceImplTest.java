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

import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.factory.CountryFactory;
import com.inmind.latam.model.Country;
import com.inmind.latam.repository.ICountryRepository;

import static com.inmind.latam.constant.QuestionIdentifiers.OTHER;

/**
 * Unit tests for {@link CountryServiceImpl}.
 *
 * These tests verify the behavior of the service for retrieving country alternatives.
 */
@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {

    @Mock
    private ICountryRepository countryRepository;

    @Mock
    private CountryFactory countryFactory;

    @InjectMocks
    private CountryServiceImpl service;

    @Test
    void shouldReturnCountryAlternatives() {
        // Arrange
        Country country1 = new Country();
        country1.setId(1);
        country1.setName("Colombia");
        Country country2 = new Country();
        country2.setId(2);
        country2.setName("Perú");
        when(countryRepository.findAllByOrderByNameAsc())
            .thenReturn(Arrays.asList(country1, country2));

        // Act
        List<AlternativeDto> result = service.getCountryFormatAlternative();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).idAlternative()).isEqualTo("1");
        assertThat(result.get(0).text()).isEqualTo("Colombia");
        assertThat(result.get(1).idAlternative()).isEqualTo("2");
        assertThat(result.get(1).text()).isEqualTo("Perú");
    }

    @Test
    void shouldReturnOtherWhenNoCountriesFound() {
        // Arrange
        when(countryRepository.findAllByOrderByNameAsc())
            .thenReturn(Collections.emptyList());
        
        Country otherCountry = new Country();
        otherCountry.setId(0);
        otherCountry.setName(OTHER);
        when(countryFactory.createOtherEntity()).thenReturn(otherCountry);

        // Act
        List<AlternativeDto> result = service.getCountryFormatAlternative();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).idAlternative()).isEqualTo("0");
        assertThat(result.get(0).text()).isEqualTo(OTHER);
    }
} 