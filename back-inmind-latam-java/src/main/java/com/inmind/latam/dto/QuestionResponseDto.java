package com.inmind.latam.dto;

import java.io.Serializable;
import java.util.List;

public record QuestionResponseDto(int positionQuestion, String idQuestionnaire, String idQuestion, String idQuestionType, String title, List<AlternativeDto> optionsAnswer) implements Serializable {
}