package com.inmind.latam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "tpreguntaxtpreguntahija")
public class QuestionQuestionDaughter {
	
	public QuestionQuestionDaughter() {
		super();
	}
	
	@Id
	@Column(name = "ID_PreguntaXPreguntaHija")
	private String idQuestionQuestionDaughter;
	
	@Column(name = "ID_Pregunta")
	private String idQuestion;
	
	@Column(name = "ID_PreguntaHija")
	private String idQuestionDaughter;
	
	@Column(name = "ID_TipoTransicion")
	private String idTransitionType;

}
