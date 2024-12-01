package com.inmind.latam.dto;

import java.io.Serializable;
import java.util.List;

public record TransactionQuestionnaireDto (String idQuestionnaire, String idQuestion, List<String> responseAnswer, String openQuestion) implements Serializable {

}