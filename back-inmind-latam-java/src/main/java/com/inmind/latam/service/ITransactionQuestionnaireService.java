package com.inmind.latam.service;

import com.inmind.latam.dto.QuestionResponseDto;
import com.inmind.latam.dto.TransactionQuestionnaireDto;
import com.inmind.latam.exception.QuestionNotReadyException;

public interface ITransactionQuestionnaireService {

	public QuestionResponseDto startQuestionnaire(String profileType);
	public QuestionResponseDto transactionQuestionnaire(TransactionQuestionnaireDto transactionQuestionnaire) throws QuestionNotReadyException;
	
}
