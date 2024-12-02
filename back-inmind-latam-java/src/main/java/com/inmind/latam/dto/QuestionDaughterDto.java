package com.inmind.latam.dto;

public class QuestionDaughterDto {

	public QuestionDaughterDto() {
		super();
	}
	
	public QuestionDaughterDto(String idQuestionDaughter) {
		super();
		this.idQuestionDaughter = idQuestionDaughter;
	}

	private String idQuestionDaughter;

	public String getIdQuestionDaughter() {
		return idQuestionDaughter;
	}

	public void setIdQuestionDaughter(String idQuestionDaughter) {
		this.idQuestionDaughter = idQuestionDaughter;
	}

}