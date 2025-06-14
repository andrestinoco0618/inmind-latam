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
import com.inmind.latam.factory.DistrictFactory;
import com.inmind.latam.model.District;
import com.inmind.latam.repository.IDistrictRepository;

/**
 * Unit tests for {@link DistrictServiceImpl}.
 *
 * These tests verify the behavior of the service for retrieving district alternatives.
 */
@ExtendWith(MockitoExtension.class)
class DistrictServiceImplTest {

    @Mock
    private IDistrictRepository districtRepository;

    @Mock
    private DistrictFactory districtFactory;

    @InjectMocks
    private DistrictServiceImpl service;

    @Test
    void shouldReturnDistrictAlternatives() {
        // Arrange
        District district1 = new District();
        district1.setId(1);
        district1.setName("District 1");
        District district2 = new District();
        district2.setId(2);
        district2.setName("District 2");
        when(districtRepository.findByCityIdOrderByNameAsc(1))
            .thenReturn(Arrays.asList(district1, district2));

        // Act
        List<AlternativeDto> result = service.getDistrictFormatAlternative(1);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).idAlternative()).isEqualTo("1");
        assertThat(result.get(0).text()).isEqualTo("District 1");
        assertThat(result.get(1).idAlternative()).isEqualTo("2");
        assertThat(result.get(1).text()).isEqualTo("District 2");
    }

    @Test
    void shouldReturnOtherWhenNoDistrictsFound() {
        // Arrange
        District otherDistrict = new District();
        otherDistrict.setId(999);
        otherDistrict.setName("Other");
        when(districtRepository.findByCityIdOrderByNameAsc(1))
            .thenReturn(Collections.emptyList());
        when(districtFactory.createOtherEntity())
            .thenReturn(otherDistrict);

        // Act
        List<AlternativeDto> result = service.getDistrictFormatAlternative(1);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).idAlternative()).isEqualTo("999");
        assertThat(result.get(0).text()).isEqualTo("Other");
    }
} 