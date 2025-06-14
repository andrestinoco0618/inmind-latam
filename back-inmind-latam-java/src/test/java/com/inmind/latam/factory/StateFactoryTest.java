package com.inmind.latam.factory;

import com.inmind.latam.model.State;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.inmind.latam.constant.QuestionIdentifiers.OTHER;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for StateFactory class.
 * Tests the creation of State entities and the special "Other" state.
 */
@ExtendWith(MockitoExtension.class)
class StateFactoryTest {

    @InjectMocks
    private StateFactory stateFactory;

    @Test
    void shouldCreateEmptyState() {
        // Act
        State state = stateFactory.createEntity();

        // Assert
        assertThat(state).isNotNull();
        assertThat(state.getId()).isEqualTo(0);
        assertThat(state.getName()).isNull();
    }

    @Test
    void shouldCreateOtherState() {
        // Act
        State otherState = stateFactory.createOtherEntity();

        // Assert
        assertThat(otherState).isNotNull();
        assertThat(otherState.getId()).isEqualTo(0);
        assertThat(otherState.getName()).isEqualTo(OTHER);
    }
} 