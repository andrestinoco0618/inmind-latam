package com.inmind.latam.service;

import com.inmind.latam.dto.AlternativeDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit test for the default getAll() method in ILocationService.
 *
 * Verifies that UnsupportedOperationException is thrown if not overridden.
 */
@ExtendWith(MockitoExtension.class)
class ILocationServiceDefaultTest {

    static class DummyLocationService implements ILocationService<Object, Object> {
        @Override
        public List<Object> getByParentId(Object parentId) {
            return Collections.emptyList();
        }
        @Override
        public List<AlternativeDto> getFormatAlternative(Object parentId) {
            return Collections.emptyList();
        }
        // No override for getAll()
    }

    @Test
    void shouldThrowUnsupportedOperationExceptionWhenGetAllIsCalled() {
        // Arrange
        ILocationService<Object, Object> service = new DummyLocationService();

        // Act & Assert
        assertThatThrownBy(service::getAll)
            .isInstanceOf(UnsupportedOperationException.class)
            .hasMessage("This method is only available for country service");
    }
} 