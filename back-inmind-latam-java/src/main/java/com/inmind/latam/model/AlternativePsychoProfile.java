package com.inmind.latam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "t_alternativa_psico_perfil")
public class AlternativePsychoProfile {

	public AlternativePsychoProfile() {
		super();
	}

	@Id
	@Column(name = "id_alternativa_psico_perfil")
	private String idAlternativePsychoProfile;
	
	@Column(name = "id_psicologa")
	private String idPsychologist;
	
	@Column(name = "id_alternativa")
	private String idAlternative;
	
	@Column(name = "id_perfil")
	private String idProfile;

	public String getIdAlternativePsychoProfile() {
		return idAlternativePsychoProfile;
	}

	public void setIdAlternativePsychoProfile(String idAlternativePsychoProfile) {
		this.idAlternativePsychoProfile = idAlternativePsychoProfile;
	}

	public String getIdPsychologist() {
		return idPsychologist;
	}

	public void setIdPsychologist(String idPsychologist) {
		this.idPsychologist = idPsychologist;
	}

	public String getIdAlternative() {
		return idAlternative;
	}

	public void setIdAlternative(String idAlternative) {
		this.idAlternative = idAlternative;
	}

	public String getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(String idProfile) {
		this.idProfile = idProfile;
	}
	
}
