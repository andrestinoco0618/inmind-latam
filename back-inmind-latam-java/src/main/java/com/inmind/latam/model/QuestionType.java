package com.inmind.latam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ttipopregunta")
public class QuestionType {
	
	public QuestionType() {
		super();
	}
	
	@Id
	@Column(name = "id_tipo_pregunta")
	private String idQuestionType;		
	
	@Column(name = "tipo_pregunta")
	private String questionType;

	public String getIdQuestionType() {
		return idQuestionType;
	}

	public void setIdQuestionType(String idQuestionType) {
		this.idQuestionType = idQuestionType;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	
}
