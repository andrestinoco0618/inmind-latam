package com.inmind.latam.strategy;

import java.util.List;
import com.inmind.latam.dto.AlternativeDto;

/**
 * Interface that defines the strategy for obtaining location alternatives.
 * 
 * This interface provides methods for:
 * - Getting location alternatives based on parent entity ID
 * - Handling different types of location selections
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.dto.AlternativeDto
 */
public interface LocationStrategy {
    /**
     * Gets the location alternatives.
     * 
     * @param parentId ID of the parent entity (can be null for countries)
     * @return List of alternatives
     */
    List<AlternativeDto> getAlternatives(Integer parentId);
} 