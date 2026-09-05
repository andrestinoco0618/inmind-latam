package com.inmind.latam.factory;

import com.inmind.latam.model.District;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.inmind.latam.constant.QuestionIdentifiers.OTHER;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for DistrictFactory class.
 * Tests the creation of District entities and the special "Other" district.
 */
@ExtendWith(MockitoExtension.class)
class DistrictFactoryTest {

    @InjectMocks
    private DistrictFactory districtFactory;

    @Test
    void shouldCreateEmptyDistrict() {
        // Act
        District district = districtFactory.createEntity();

        // Assert
        assertThat(district).isNotNull();
        assertThat(district.getId()).isEqualTo(0);
        assertThat(district.getName()).isNull();
    }

    @Test
    void shouldCreateOtherDistrict() {
        // Act
        District otherDistrict = districtFactory.createOtherEntity();

        // Assert
        assertThat(otherDistrict).isNotNull();
        assertThat(otherDistrict.getId()).isEqualTo(0);
        assertThat(otherDistrict.getName()).isEqualTo(OTHER);
    }
} 