package com.inmind.latam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing an alternative-question daughter relationship in the system.
 * <p>
 * This class maps to the 'talternativaxtpreguntahija' table in the database and contains
 * information about alternative-question relationships including:
 * - Unique identifier
 * - Alternative identifier
 * - Daughter question identifier
 * - Transition type identifier
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "t_alternative_question_daughter")
@Data
@NoArgsConstructor
public class AlternativeQuestionDaughter {
	
	@Id
	@Column(name = "id_alternative_question_daughter")
	private String idAlternativeQuestionDaughter;
	
	@Column(name = "id_alternative")
	private String idAlternative;
	
	@Column(name = "id_question_daughter")
	private String idQuestionDaughter;
	
	@Column(name = "id_type_transition")
	private String idTransitionType;
}
