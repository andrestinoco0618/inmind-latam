package com.inmind.latam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a questionnaire in the system.
 * <p>
 * This class maps to the 'tcuestionario' table in the database and contains
 * information about questionnaires including:
 * - Unique identifier
 * - Questionnaire name
 * - Description
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "t_questionnaire")
@Data
@NoArgsConstructor
public class Questionnaire {
	
	@Id
	@Column(name = "id_questionnaire")
	private String idQuestionnaire;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
}		