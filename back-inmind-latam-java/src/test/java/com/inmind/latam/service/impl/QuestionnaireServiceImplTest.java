package com.inmind.latam.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

import com.inmind.latam.constant.CacheKeys;
import com.inmind.latam.constant.ProfileType;
import com.inmind.latam.constant.QuestionIdentifiers;
import com.inmind.latam.dto.QuestionDaughterDto;
import com.inmind.latam.dto.QuestionMemoryDto;

/**
 * Unit tests for {@link QuestionnaireServiceImpl}.
 *
 * These tests verify the behavior of the service for managing questionnaire cache and state.
 */
@ExtendWith(MockitoExtension.class)
class QuestionnaireServiceImplTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private SetOperations<String, Object> setOperations;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private QuestionnaireServiceImpl service;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForSet()).thenReturn(setOperations);
    }

    @Test
    void shouldThrowExceptionWhenInvalidProfileType() {
        // Arrange
        String idQuestionnarie = "Q001";
        String invalidProfileType = "INVALID";

        // Act & Assert
        assertThatThrownBy(() -> service.initializeQuestionnaireCache(idQuestionnarie, invalidProfileType))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Invalid type profile");
    }

    @Test
    void shouldUpdateQuestionStatus() {
        // Arrange
        String idQuestionnarie = "Q001";
        String idQuestion = QuestionIdentifiers.QUESTION_ONE;
        List<QuestionMemoryDto> cacheList = new ArrayList<>();
        QuestionMemoryDto question = new QuestionMemoryDto(0, idQuestion, false, QuestionIdentifiers.EMPTY_STRING, 1);
        cacheList.add(question);

        List<QuestionDaughterDto> newQuestions = Arrays.asList(
            new QuestionDaughterDto(QuestionIdentifiers.QUESTION_FIVE, 2),
            new QuestionDaughterDto(QuestionIdentifiers.QUESTION_EIGHT, 2)
        );

        when(applicationContext.getBean(QuestionnaireServiceImpl.class)).thenReturn(service);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(CacheKeys.EXCLUDE + idQuestionnarie)).thenReturn("false");

        // Act
        List<QuestionMemoryDto> result = service.updateQuestionStatus(
            idQuestionnarie,
            idQuestion,
            cacheList,
            newQuestions,
            QuestionIdentifiers.ALTERNATIVE_SIX,
            new ArrayList<>()
        );

        // Assert
        assertThat(result).hasSize(3);
        assertThat(result.get(0).isAnswered()).isTrue();
        assertThat(result.get(0).getAlternativeResponse()).isEqualTo(QuestionIdentifiers.ALTERNATIVE_SIX);
        assertThat(result.get(1).getIdQuestion()).isEqualTo(QuestionIdentifiers.QUESTION_FIVE);
        assertThat(result.get(2).getIdQuestion()).isEqualTo(QuestionIdentifiers.QUESTION_EIGHT);
        verify(setOperations).add(eq(CacheKeys.PREFIX + idQuestionnarie), eq(CacheKeys.QUESTIONNAIRE + "::" + idQuestionnarie));
    }

    @Test
    void shouldUpdateQuestionPosition() {
        // Arrange
        String idQuestionnarie = "Q001";
        String idQuestion = QuestionIdentifiers.QUESTION_ONE;
        List<QuestionMemoryDto> cacheList = new ArrayList<>();
        QuestionMemoryDto question = new QuestionMemoryDto(0, idQuestion, false, QuestionIdentifiers.EMPTY_STRING, 1);
        cacheList.add(question);

        // Act
        service.updateQuestionPosition(idQuestionnarie, idQuestion, 2, cacheList);

        // Assert
        assertThat(cacheList.get(0).getPositionQuestion()).isEqualTo(2);
        verify(setOperations).add(eq(CacheKeys.PREFIX + idQuestionnarie), eq(CacheKeys.QUESTIONNAIRE + "::" + idQuestionnarie));
    }

    @Test
    void shouldHandleGroupDisordersOne() {
        // Arrange
        String idQuestionnarie = "Q001";
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(CacheKeys.DISORDERS + idQuestionnarie)).thenReturn("true");

        // Act
        service.saveGroupDisordersOne(idQuestionnarie);
        Boolean result = service.getGroupDisordersOne(idQuestionnarie);

        // Assert
        assertThat(result).isTrue();
        verify(setOperations, times(2)).add(eq(CacheKeys.PREFIX + idQuestionnarie), eq(CacheKeys.DISORDERS + idQuestionnarie));
        verify(valueOperations).set(eq(CacheKeys.DISORDERS + idQuestionnarie), eq("true"));
    }

    @Test
    void shouldHandleExcludeQuestion() {
        // Arrange
        String idQuestionnarie = "Q001";
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(CacheKeys.EXCLUDE + idQuestionnarie)).thenReturn("true");

        // Act
        service.saveExludeQuestion(idQuestionnarie);
        Boolean result = service.getExludeQuestion(idQuestionnarie);

        // Assert
        assertThat(result).isTrue();
        verify(setOperations, times(2)).add(eq(CacheKeys.PREFIX + idQuestionnarie), eq(CacheKeys.EXCLUDE + idQuestionnarie));
        verify(valueOperations).set(eq(CacheKeys.EXCLUDE + idQuestionnarie), eq("true"));
    }

    @Test
    void shouldHandleSelectedCountry() {
        // Arrange
        String idQuestionnarie = "Q001";
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(CacheKeys.COUNTRY + idQuestionnarie)).thenReturn("true");

        // Act
        service.saveSelectedCountry(idQuestionnarie, true);
        Boolean result = service.getSelectedCountry(idQuestionnarie);

        // Assert
        assertThat(result).isTrue();
        verify(setOperations, times(2)).add(eq(CacheKeys.PREFIX + idQuestionnarie), eq(CacheKeys.COUNTRY + idQuestionnarie));
        verify(valueOperations).set(eq(CacheKeys.COUNTRY + idQuestionnarie), eq("true"));
    }

    @Test
    void shouldHandleRedirectValid() {
        // Arrange
        String idQuestionnarie = "Q001";
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(CacheKeys.REDIRECT + idQuestionnarie)).thenReturn("true");

        // Act
        service.saveRedirectValid(idQuestionnarie);
        Boolean result = service.getRedirectValid(idQuestionnarie);

        // Assert
        assertThat(result).isTrue();
        verify(setOperations, times(2)).add(eq(CacheKeys.PREFIX + idQuestionnarie), eq(CacheKeys.REDIRECT + idQuestionnarie));
        verify(valueOperations).set(eq(CacheKeys.REDIRECT + idQuestionnarie), eq("true"));
    }
} 