package com.inmind.latam.strategy;

import com.inmind.latam.constant.QuestionGroupType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for LocationStrategyFactory.
 * Tests the strategy creation and retrieval functionality.
 */
@ExtendWith(MockitoExtension.class)
class LocationStrategyFactoryTest {

    @Mock
    private CountryStrategy countryStrategy;

    @Mock
    private StateStrategy stateStrategy;

    @Mock
    private CityStrategy cityStrategy;

    @Mock
    private DistrictStrategy districtStrategy;

    private LocationStrategyFactory factory;

    @BeforeEach
    void setUp() {
        factory = new LocationStrategyFactory(
            countryStrategy,
            stateStrategy,
            cityStrategy,
            districtStrategy
        );
    }

    @Test
    void shouldGetCountryStrategy() {
        // Act
        LocationStrategy strategy = factory.getStrategy(QuestionGroupType.COUNTRY);

        // Assert
        assertThat(strategy).isNotNull();
        assertThat(strategy).isInstanceOf(CountryStrategy.class);
    }

    @Test
    void shouldGetStateStrategy() {
        // Act
        LocationStrategy strategy = factory.getStrategy(QuestionGroupType.STATE);

        // Assert
        assertThat(strategy).isNotNull();
        assertThat(strategy).isInstanceOf(StateStrategy.class);
    }

    @Test
    void shouldGetCityStrategy() {
        // Act
        LocationStrategy strategy = factory.getStrategy(QuestionGroupType.CITY);

        // Assert
        assertThat(strategy).isNotNull();
        assertThat(strategy).isInstanceOf(CityStrategy.class);
    }

    @Test
    void shouldGetDistrictStrategy() {
        // Act
        LocationStrategy strategy = factory.getStrategy(QuestionGroupType.DISTRICT);

        // Assert
        assertThat(strategy).isNotNull();
        assertThat(strategy).isInstanceOf(DistrictStrategy.class);
    }

    @Test
    void shouldReturnNullForDefaultType() {
        // Act
        LocationStrategy strategy = factory.getStrategy(QuestionGroupType.DEFAULT);

        // Assert
        assertThat(strategy).isNull();
    }
} 