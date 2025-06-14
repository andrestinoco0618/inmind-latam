package com.inmind.latam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a question type in the system.
 * <p>
 * This class maps to the 'ttipopregunta' table in the database and contains
 * information about question types including:
 * - Unique identifier
 * - Question type name
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "t_question_type")
@Data
@NoArgsConstructor
public class QuestionType {
	
	@Id
	@Column(name = "id_question_type")
	private String idQuestionType;		
	
	@Column(name = "question_type")
	private String questionType;
}
