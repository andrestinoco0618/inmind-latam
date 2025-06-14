package com.inmind.latam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a diagnosis in the system.
 * <p>
 * This class maps to the 't_diagnostico' table in the database and contains
 * information about diagnoses including:
 * - Unique identifier
 * - Diagnosis name
 * - Associated alternative
 * - Question exclusion flag
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "t_diagnosis")
@Data
@NoArgsConstructor
public class Diagnosis {
	
	@Id
	@Column(name = "id_diagnosis")
	private String idDiagnosis;
	
	@Column(name = "diagnosis_name")
	private String diagnosisName;
	
	@Column(name = "id_alternative")
	private String idAlternative;
	
	@Column(name = "exclude_question")
	private Boolean excludeQuestion;
}
