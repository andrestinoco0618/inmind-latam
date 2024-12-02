package com.inmind.latam.dto;

import java.io.Serializable;
import java.util.List;

public class ResponseQuestionMemoryDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String profileType;
	private List<String> responseQuestion;
	
	public ResponseQuestionMemoryDto() {
		super();
	}

	public ResponseQuestionMemoryDto(String profileType, List<String> responseQuestion) {
		super();
		this.profileType = profileType;
		this.responseQuestion = responseQuestion;
	}

	public String getProfileType() {
		return profileType;
	}

	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}

	public List<String> getResponseQuestion() {
		return responseQuestion;
	}

	public void setResponseQuestion(List<String> responseQuestion) {
		this.responseQuestion = responseQuestion;
	}
	
	
}
