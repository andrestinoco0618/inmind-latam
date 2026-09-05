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
import com.inmind.latam.factory.CityFactory;
import com.inmind.latam.model.City;
import com.inmind.latam.repository.ICityRepository;

import static com.inmind.latam.constant.QuestionIdentifiers.OTHER;

/**
 * Unit tests for {@link CityServiceImpl}.
 *
 * These tests verify the behavior of the service for retrieving city alternatives by state ID.
 */
@ExtendWith(MockitoExtension.class)
class CityServiceImplTest {

    @Mock
    private ICityRepository cityRepository;

    @Mock
    private CityFactory cityFactory;

    @InjectMocks
    private CityServiceImpl service;

    @Test
    void shouldReturnCityAlternativesByStateId() {
        // Arrange
        City city1 = new City();
        city1.setId(1);
        city1.setName("Bogotá");
        City city2 = new City();
        city2.setId(2);
        city2.setName("Medellín");
        when(cityRepository.findByStateIdOrderByNameAsc(10))
            .thenReturn(Arrays.asList(city1, city2));

        // Act
        List<AlternativeDto> result = service.getCityFormatAlternative(10);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).idAlternative()).isEqualTo("1");
        assertThat(result.get(0).text()).isEqualTo("Bogotá");
        assertThat(result.get(1).idAlternative()).isEqualTo("2");
        assertThat(result.get(1).text()).isEqualTo("Medellín");
    }

    @Test
    void shouldReturnEmptyListWhenNoCitiesFound() {
        // Arrange
        when(cityRepository.findByStateIdOrderByNameAsc(20))
            .thenReturn(Collections.emptyList());
        
        City otherCity = new City();
        otherCity.setId(0);
        otherCity.setName(OTHER);
        when(cityFactory.createOtherEntity()).thenReturn(otherCity);

        // Act
        List<AlternativeDto> result = service.getCityFormatAlternative(20);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).idAlternative()).isEqualTo("0");
        assertThat(result.get(0).text()).isEqualTo(OTHER);
    }
} 