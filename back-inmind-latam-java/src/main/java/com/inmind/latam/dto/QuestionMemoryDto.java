package com.inmind.latam.dto;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for question memory information.
 * <p>
 * This class represents the state of a question in the questionnaire flow,
 * containing:
 * - Question position in the sequence
 * - Question identifier
 * - Answer status
 * - Selected alternative
 * - Question level
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see java.io.Serializable
 */
@Data
@NoArgsConstructor
public class QuestionMemoryDto implements Serializable {
	
	@Serial
	private static final long serialVersionUID = 1L;
	/** The position of the question in the sequence */
	private int positionQuestion;
	/** The unique identifier of the question */
	private String idQuestion;
	/** Flag indicating if the question has been answered */
	private boolean isAnswered;
	/** The selected alternative response */
	private String alternativeResponse;
	/** The level of the question in the hierarchy */
	private int levelQuestion;
		
    /**
     * Constructs a new QuestionMemoryDto with the specified parameters.
     * 
     * @param positionQuestion the position of the question in the sequence
     * @param idQuestion the identifier of the question
     * @param status whether the question has been answered
     * @param alternativeQuestion the selected alternative
     * @param levelQuestion the level of the question
     */
	public QuestionMemoryDto(int positionQuestion, String idQuestion, boolean status, String alternativeQuestion, int levelQuestion) {
		this.positionQuestion = positionQuestion;
		this.idQuestion = idQuestion;
		this.isAnswered = status;
		this.alternativeResponse = alternativeQuestion;
		this.levelQuestion = levelQuestion;
	}
}
