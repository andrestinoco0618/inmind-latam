package com.inmind.latam.factory;

import com.inmind.latam.model.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.inmind.latam.constant.QuestionIdentifiers.OTHER;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for CityFactory class.
 * Tests the creation of City entities and the special "Other" city.
 */
@ExtendWith(MockitoExtension.class)
class CityFactoryTest {

    @InjectMocks
    private CityFactory cityFactory;

    @Test
    void shouldCreateEmptyCity() {
        // Act
        City city = cityFactory.createEntity();

        // Assert
        assertThat(city).isNotNull();
        assertThat(city.getId()).isEqualTo(0);
        assertThat(city.getName()).isNull();
    }

    @Test
    void shouldCreateOtherCity() {
        // Act
        City otherCity = cityFactory.createOtherEntity();

        // Assert
        assertThat(otherCity).isNotNull();
        assertThat(otherCity.getId()).isEqualTo(0);
        assertThat(otherCity.getName()).isEqualTo(OTHER);
    }
} 