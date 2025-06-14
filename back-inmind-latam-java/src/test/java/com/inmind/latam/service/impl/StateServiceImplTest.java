package com.inmind.latam.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.factory.StateFactory;
import com.inmind.latam.model.State;
import com.inmind.latam.repository.IStateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link StateServiceImpl}.
 *
 * These tests verify the behavior of the service for managing states and alternatives.
 */
@ExtendWith(MockitoExtension.class)
class StateServiceImplTest {

    @Mock
    private IStateRepository stateRepository;
    @Mock
    private StateFactory stateFactory;

    @InjectMocks
    private StateServiceImpl stateService;

    private State state1;
    private State state2;
    private List<State> stateList;

    @BeforeEach
    void setUp() {
        state1 = new State();
        state1.setId(1);
        state1.setName("Amazonas");
        state1.setCountryId(100);

        state2 = new State();
        state2.setId(2);
        state2.setName("Lima");
        state2.setCountryId(100);

        stateList = new ArrayList<>();
        stateList.add(state1);
        stateList.add(state2);
    }

    @Test
    void shouldReturnOrderedStatesByCountry() {
        // Arrange
        when(stateRepository.findByCountryIdOrderByNameAsc(100)).thenReturn(stateList);

        // Act
        List<State> result = stateService.getByParentId(100);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Amazonas");
        assertThat(result.get(1).getName()).isEqualTo("Lima");
    }

    @Test
    void shouldReturnOtherStateWhenNoStatesFound() {
        // Arrange
        when(stateRepository.findByCountryIdOrderByNameAsc(anyInt())).thenReturn(Collections.emptyList());
        State other = new State();
        other.setId(0);
        other.setName("Other");
        when(stateFactory.createOtherEntity()).thenReturn(other);

        // Act
        List<State> result = stateService.getByParentId(999);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(0);
        assertThat(result.get(0).getName()).isEqualTo("Other");
    }

    @Test
    void shouldReturnStateFormatAlternatives() {
        // Arrange
        when(stateRepository.findByCountryIdOrderByNameAsc(100)).thenReturn(stateList);

        // Act
        List<AlternativeDto> result = stateService.getStateFormatAlternative(100);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).idAlternative()).isEqualTo("1");
        assertThat(result.get(0).text()).isEqualTo("Amazonas");
        assertThat(result.get(1).idAlternative()).isEqualTo("2");
        assertThat(result.get(1).text()).isEqualTo("Lima");
    }

    @Test
    void shouldReturnOtherAlternativeWhenNoStatesFound() {
        // Arrange
        when(stateRepository.findByCountryIdOrderByNameAsc(anyInt())).thenReturn(Collections.emptyList());
        State other = new State();
        other.setId(0);
        other.setName("Other");
        when(stateFactory.createOtherEntity()).thenReturn(other);

        // Act
        List<AlternativeDto> result = stateService.getStateFormatAlternative(999);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).idAlternative()).isEqualTo("0");
        assertThat(result.get(0).text()).isEqualTo("Other");
    }
} 