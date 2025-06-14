package com.inmind.latam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for saving question responses.
 * <p>
 * This class is used to store and transfer question response data, containing:
 * - The question identifier
 * - The selected alternative response
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionSaveDto {
	
	/** The unique identifier of the question */
	private String idQuestion;
	
	/** The selected alternative response identifier */
	private String alternativeResponse;
}
