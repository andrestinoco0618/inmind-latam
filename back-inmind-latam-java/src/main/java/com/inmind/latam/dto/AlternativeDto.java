package com.inmind.latam.dto;

import java.io.Serializable;

/**
 * Data Transfer Object for alternative answer information.
 * <p>
 * This record represents a possible answer option in a question, containing:
 * - The unique identifier of the alternative
 * - The text content of the alternative
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
public record AlternativeDto(
    /** The unique identifier of the alternative answer */
    String idAlternative,
    /** The text content of the alternative answer */
    String text
) implements Serializable {}