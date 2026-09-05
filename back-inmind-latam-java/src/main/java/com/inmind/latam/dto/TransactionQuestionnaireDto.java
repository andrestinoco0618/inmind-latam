package com.inmind.latam.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Data Transfer Object for questionnaire transaction information.
 * <p>
 * This record represents a transaction in the questionnaire flow, containing:
 * - The questionnaire identifier
 * - The question identifier
 * - List of selected answer identifiers
 * - Open question response text
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
public record TransactionQuestionnaireDto (
    /** The unique identifier of the questionnaire */
    String idQuestionnaire,
    /** The unique identifier of the question */
    String idQuestion,
    /** List of selected answer identifiers */
    List<String> responseAnswer,
    /** Text response for open-ended questions */
    String openQuestion
) implements Serializable {}