package com.inmind.latam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "talternativa")
public class Alternative {
	
	public Alternative() {
		super();
	}

	@Id
	@Column(name = "ID_Alternativa")
	private String idAlternative;
	
	@Column(name = "texto_alternativa")
	private String textAlternative;
	
	@Column(name = "tipo_alternativa")
	private String alternativeType;
	
	@Column(name = "titulo")
	private String title;
	
	@ManyToOne
    @JoinColumn(name = "ID_Pregunta", referencedColumnName = "ID_Pregunta", insertable = false, updatable = false)
    private Question question;

	public String getIdAlternative() {
		return idAlternative;
	}

	public void setIdAlternative(String idAlternative) {
		this.idAlternative = idAlternative;
	}

	public String getTextAlternative() {
		return textAlternative;
	}

	public void setTextAlternative(String textAlternative) {
		this.textAlternative = textAlternative;
	}

	public String getAlternativeType() {
		return alternativeType;
	}

	public void setAlternativeType(String alternativeType) {
		this.alternativeType = alternativeType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
}
