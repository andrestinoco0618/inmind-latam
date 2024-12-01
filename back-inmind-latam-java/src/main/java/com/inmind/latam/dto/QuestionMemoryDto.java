package com.inmind.latam.dto;

import java.io.Serializable;

public class QuestionMemoryDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int positionQuestion;
	private String idQuestion;
	private boolean status;
		
	public QuestionMemoryDto() {
		super();
	}
	
	public QuestionMemoryDto(int positionQuestion, String idQuestion, boolean status) {
		super();
		this.positionQuestion = positionQuestion;
		this.idQuestion = idQuestion;
		this.status = status;
	}

	public int getPositionQuestion() {
		return positionQuestion;
	}
	public void setPositionQuestion(int positionQuestion) {
		this.positionQuestion = positionQuestion;
	}
	public String getIdQuestion() {
		return idQuestion;
	}
	public void setIdQuestion(String idQuestion) {
		this.idQuestion = idQuestion;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
