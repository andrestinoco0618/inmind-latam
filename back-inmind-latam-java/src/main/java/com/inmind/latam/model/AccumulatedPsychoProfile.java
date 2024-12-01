package com.inmind.latam.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "t_acumulado_psico_perfil")
public class AccumulatedPsychoProfile {
	
	public AccumulatedPsychoProfile() {
		super();
	}
	
	public AccumulatedPsychoProfile(String idProfile, String idQuestionnaireAnswered) {
		super();
		this.idProfile = idProfile;
		this.idQuestionnaireAnswered = idQuestionnaireAnswered;
	 	this.createdAt = LocalDateTime.now(); 
	    this.isReview = true;
	}

	@Id
	@Column(name = "id_acumulado_psico_perfil")
	private int idAccumulatedPsychoProfile;
	
	@Column(name = "id_perfil")
	private String idProfile;
	
	@Column(name = "id_psicologo")
	private String idPsychologist;
	
	@Column(name = "id_cuestionario_respondido")
	private String idQuestionnaireAnswered;
	
	@Column(name = "puntaje_final")
	private int finalPoint;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	@Column(name = "is_review")
	private boolean isReview;

	public int getIdAccumulatedPsychoProfile() {
		return idAccumulatedPsychoProfile;
	}

	public void setIdAccumulatedPsychoProfile(int idAccumulatedPsychoProfile) {
		this.idAccumulatedPsychoProfile = idAccumulatedPsychoProfile;
	}

	public String getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(String idProfile) {
		this.idProfile = idProfile;
	}

	public String getIdPsychologist() {
		return idPsychologist;
	}

	public void setIdPsychologist(String idPsychologist) {
		this.idPsychologist = idPsychologist;
	}

	public String getIdQuestionnaireAnswered() {
		return idQuestionnaireAnswered;
	}

	public void setIdQuestionnaireAnswered(String idQuestionnaireAnswered) {
		this.idQuestionnaireAnswered = idQuestionnaireAnswered;
	}

	public int getFinalPoint() {
		return finalPoint;
	}

	public void setFinalPoint(int finalPoint) {
		this.finalPoint = finalPoint;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean isReview() {
		return isReview;
	}

	public void setReview(boolean isReview) {
		this.isReview = isReview;
	}

}
