package com.inmind.latam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "talternativaxtpreguntahija")
public class AlternativeQuestionDaughter {
	
	public AlternativeQuestionDaughter() {
		super();
	}
	
	@Id
	@Column(name = "ID_AlternativaXPreguntaHija")
	private String idAlternativeQuestionDaughter;
	
	@Column(name = "ID_Alternativa")
	private String idAlternative;
	
	@Column(name = "ID_PreguntaHija")
	private String idQuestionDaughter;
	
	@Column(name = "ID_TipoTransicion")
	private String idTransitionType;

}
