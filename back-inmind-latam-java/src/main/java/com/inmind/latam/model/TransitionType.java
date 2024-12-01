package com.inmind.latam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "ttipotransicion")
public class TransitionType {
	
	public TransitionType() {
		super();
	}
	
	@Id
	@Column(name = "id_tipo_transicion")
	private String idTransitionType;		
	
	@Column(name = "nombre_transicion")
	private String transitionName;
	
	@Column(name = "id_perfil")
	private String idProfile;
	
	@Column(name = "id_alternativa")
	private String idAlternative;

	public String getIdTransitionType() {
		return idTransitionType;
	}

	public void setIdTransitionType(String idTransitionType) {
		this.idTransitionType = idTransitionType;
	}

	public String getTransitionName() {
		return transitionName;
	}

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}

	public String getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(String idProfile) {
		this.idProfile = idProfile;
	}

	public String getIdAlternative() {
		return idAlternative;
	}

	public void setIdAlternative(String idAlternative) {
		this.idAlternative = idAlternative;
	}

}
