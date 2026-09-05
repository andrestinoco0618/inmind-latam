package com.inmind.latam.factory;

import com.inmind.latam.model.Country;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.inmind.latam.constant.QuestionIdentifiers.OTHER;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for CountryFactory class.
 * Tests the creation of Country entities and the special "Other" country.
 */
@ExtendWith(MockitoExtension.class)
class CountryFactoryTest {

    @InjectMocks
    private CountryFactory countryFactory;

    @Test
    void shouldCreateEmptyCountry() {
        // Act
        Country country = countryFactory.createEntity();

        // Assert
        assertThat(country).isNotNull();
        assertThat(country.getId()).isEqualTo(0);
        assertThat(country.getName()).isNull();
    }

    @Test
    void shouldCreateOtherCountry() {
        // Act
        Country otherCountry = countryFactory.createOtherEntity();

        // Assert
        assertThat(otherCountry).isNotNull();
        assertThat(otherCountry.getId()).isEqualTo(0);
        assertThat(otherCountry.getName()).isEqualTo(OTHER);
    }
} 