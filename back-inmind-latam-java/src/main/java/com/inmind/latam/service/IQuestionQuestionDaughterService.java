package com.inmind.latam.service;

import java.util.List;

import com.inmind.latam.dto.QuestionDaughterDto;
import com.inmind.latam.dto.TransactionQuestionnaireDto;

public interface IQuestionQuestionDaughterService {
	
	public List<QuestionDaughterDto> findQuestionDaughterByQuestion(TransactionQuestionnaireDto transactionQuestionnaire);

}
