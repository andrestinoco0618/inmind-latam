package com.inmind.latam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a question-question daughter relationship in the system.
 * <p>
 * This class maps to the 'tpreguntaxtpreguntahija' table in the database and contains
 * information about question relationships including:
 * - Unique identifier
 * - Parent question identifier
 * - Daughter question identifier
 * - Transition type identifier
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "t_question_question_daughter")
@Data
@NoArgsConstructor
public class QuestionQuestionDaughter {
	
	@Id
	@Column(name = "id_question_question_daughter")
	private String idQuestionQuestionDaughter;
	
	@Column(name = "id_question")
	private String idQuestion;
	
	@Column(name = "id_question_daughter")
	private String idQuestionDaughter;
	
	@Column(name = "id_transition_type")
	private String idTransitionType;
}
