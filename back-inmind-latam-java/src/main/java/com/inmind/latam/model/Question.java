package com.inmind.latam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a question in the system.
 * <p>
 * This class maps to the 'tpregunta' table in the database and contains
 * information about questions including:
 * - Unique identifier
 * - Question text
 * - Number of alternatives
 * - Print status
 * - Associated question type
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "t_question")
@Data
@NoArgsConstructor
public class Question {
	
	@Id
	@Column(name = "id_question")
	private String idQuestion;
	
	@Column(name = "question_text")
	private String textQuestion;

	@Column(name = "number_alternatives")
	private int numberAlternatives;
	
	@Column(name = "printed")
	private String printed;
	
	@ManyToOne
    @JoinColumn(name = "id_question_type", referencedColumnName = "id_question_type", insertable = false, updatable = false)
    private QuestionType questionType;
}
