package com.inmind.latam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tcuestionario")
public class Questionnaire {
	
	public Questionnaire() {
		super();
	}
	
	@Id
	@Column(name = "ID_Cuestionario")
	private String idQuestionnaire;
	
	@Column(name = "Nombre")
	private String name;
	
	@Column(name = "Descripcion")
	private String description;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}		