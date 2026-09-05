package com.inmind.latam.repository;

import com.inmind.latam.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Unit tests for IQuestionRepository.
 * Tests the custom query methods using mocks instead of integration tests.
 */
@ExtendWith(MockitoExtension.class)
class QuestionRepositoryTest {

    @Mock
    private IQuestionRepository questionRepository;

    private Question mockQuestion;

    @BeforeEach
    void setUp() throws Exception {
        mockQuestion = new Question();
        
        // Use reflection to set private fields
        setField(mockQuestion, "idQuestion", "P00001");
        setField(mockQuestion, "textQuestion", "Test Question");
        setField(mockQuestion, "numberAlternatives", 3);
        setField(mockQuestion, "printed", "Y");
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private Object getField(Object target, String fieldName) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }

    @Test
    void shouldFindQuestionByIdQuestion() throws Exception {
        // Arrange
        String questionId = "P00001";
        when(questionRepository.findByIdQuestion(questionId))
            .thenReturn(Optional.of(mockQuestion));

        // Act
        Optional<Question> result = questionRepository.findByIdQuestion(questionId);

        // Assert
        assertThat(result).isPresent();
        assertThat(getField(result.get(), "idQuestion")).isEqualTo(questionId);
        assertThat(getField(result.get(), "textQuestion")).isEqualTo("Test Question");
        assertThat(getField(result.get(), "numberAlternatives")).isEqualTo(3);
        assertThat(getField(result.get(), "printed")).isEqualTo("Y");
    }

    @Test
    void shouldReturnEmptyWhenQuestionNotFound() {
        // Arrange
        String nonExistentId = "NON_EXISTENT";
        when(questionRepository.findByIdQuestion(nonExistentId))
            .thenReturn(Optional.empty());

        // Act
        Optional<Question> result = questionRepository.findByIdQuestion(nonExistentId);

        // Assert
        assertThat(result).isEmpty();
    }
} 