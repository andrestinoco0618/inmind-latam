package com.inmind.latam.service.impl;

import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.factory.LocationEntityFactory;
import com.inmind.latam.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

import static com.inmind.latam.constant.QuestionIdentifiers.OTHER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AbstractLocationServiceImpl.
 * Tests the common functionality provided by the abstract location service.
 */
@ExtendWith(MockitoExtension.class)
class AbstractLocationServiceImplTest {

    @Mock
    private JpaRepository<Country, Integer> repository;

    @Mock
    private LocationEntityFactory<Country> factory;

    private TestLocationServiceImpl service;
    private Country mockCountry;
    private Country otherCountry;

    @BeforeEach
    void setUp() {
        service = new TestLocationServiceImpl(repository, factory);
        
        mockCountry = new Country();
        mockCountry.setId(1);
        mockCountry.setName("Colombia");
        
        otherCountry = new Country();
        otherCountry.setId(0);
        otherCountry.setName(OTHER);
    }

    @Test
    void shouldGetByParentIdWhenEntitiesExist() {
        // Arrange
        List<Country> countries = Arrays.asList(mockCountry);
        when(repository.findAll()).thenReturn(countries);

        // Act
        List<Country> result = service.getByParentId(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Colombia");
    }

    @Test
    void shouldReturnOtherEntityWhenNoEntitiesFound() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of());
        when(factory.createOtherEntity()).thenReturn(otherCountry);

        // Act
        List<Country> result = service.getByParentId(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo(OTHER);
    }

    @Test
    void shouldGetFormatAlternative() {
        // Arrange
        List<Country> countries = Arrays.asList(mockCountry);
        when(repository.findAll()).thenReturn(countries);

        // Act
        List<AlternativeDto> result = service.getFormatAlternative(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).idAlternative()).isEqualTo("1");
        assertThat(result.get(0).text()).isEqualTo("Colombia");
    }

    @Test
    void shouldSetIdAndName() {
        // Arrange
        Country country = new Country();

        // Act
        service.setId(country, 1);
        service.setName(country, "Test");

        // Assert
        assertThat(country.getId()).isEqualTo(1);
        assertThat(country.getName()).isEqualTo("Test");
    }

    // Test implementation of AbstractLocationServiceImpl
    private static class TestLocationServiceImpl extends AbstractLocationServiceImpl<Country, Integer, JpaRepository<Country, Integer>> {
        public TestLocationServiceImpl(JpaRepository<Country, Integer> repository, LocationEntityFactory<Country> factory) {
            super(repository, factory);
        }

        @Override
        protected List<Country> getOrderedEntities(Integer parentId) {
            return repository.findAll();
        }
    }
} 