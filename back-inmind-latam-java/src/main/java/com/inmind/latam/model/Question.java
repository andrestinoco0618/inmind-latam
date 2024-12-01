package com.inmind.latam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tpregunta")
public class Question {
	
	public Question() {
		super();
	}
	
	@Id
	@Column(name = "ID_Pregunta")
	private String idQuestion;
	
	@Column(name = "TextoPregunta")
	private String textQuestion;

	@Column(name = "NumeroAlternativas")
	private int numberAlternatives;
	
	@Column(name = "impresa")
	private String impresa;
	
	@ManyToOne
    @JoinColumn(name = "ID_TipoPregunta", referencedColumnName = "id_tipo_pregunta", insertable = false, updatable = false)
    private QuestionType questionType;

	public String getIdQuestion() {
		return idQuestion;
	}

	public void setIdQuestion(String idQuestion) {
		this.idQuestion = idQuestion;
	}

	public String getTextQuestion() {
		return textQuestion;
	}

	public void setTextQuestion(String textQuestion) {
		this.textQuestion = textQuestion;
	}

	public int getNumberAlternatives() {
		return numberAlternatives;
	}

	public void setNumberAlternatives(int numberAlternatives) {
		this.numberAlternatives = numberAlternatives;
	}

	public String getImpresa() {
		return impresa;
	}

	public void setImpresa(String impresa) {
		this.impresa = impresa;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}
}
