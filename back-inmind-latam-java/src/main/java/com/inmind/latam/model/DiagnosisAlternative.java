package com.inmind.latam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a diagnosis alternative in the system.
 * <p>
 * This class maps to the 't_diagnostico_alternativa' table in the database and contains
 * information about diagnosis alternatives including:
 * - Unique identifier
 * - Associated diagnosis identifier
 * - Associated alternative identifier
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "t_alternative_diagnosis")
@Data
@NoArgsConstructor
public class DiagnosisAlternative {
	
	@Id
	@Column(name = "id_alternative_diagnosis")
	private String idDiagnosisAlternative;
	
	@Column(name = "id_diagnosis")
	private String idDiagnosis;
	
	@Column(name = "id_alternative")
	private String idAlternative;
}
