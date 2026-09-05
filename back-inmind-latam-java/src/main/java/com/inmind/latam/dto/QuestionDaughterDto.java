package com.inmind.latam.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for daughter question information.
 * <p>
 * This class represents a question that is related to or dependent on another question,
 * containing:
 * - Daughter question identifier
 * - Question level in the hierarchy
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
@Setter
@Getter
@Data
@NoArgsConstructor
public class QuestionDaughterDto {

	/** The unique identifier of the daughter question
     * -- GETTER --
     *  Gets the daughter question identifier.
     * <p>
     *
     * -- SETTER --
     *  Sets the daughter question identifier.
     *
     */
	private String idQuestionDaughter;
	
	/** The level of the question in the hierarchy
     * -- GETTER --
     *  Gets the question level.
     * <p>
     *
     * -- SETTER --
     *  Sets the question level.
     *
     */
	private int levelQuestion;

	/**
	 * Constructs a new QuestionDaughterDto with the specified parameters.
	 * 
	 * @param idQuestionDaughter the identifier of the daughter question
	 * @param levelQuestion the level of the question in the hierarchy
	 */
	public QuestionDaughterDto(String idQuestionDaughter, int levelQuestion) {
		this.idQuestionDaughter = idQuestionDaughter;
		this.levelQuestion = levelQuestion;
	}

}