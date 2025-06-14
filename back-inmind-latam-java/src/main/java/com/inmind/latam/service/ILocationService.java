package com.inmind.latam.service;

import java.util.List;
import com.inmind.latam.dto.AlternativeDto;

/**
 * Generic base interface for location services.
 * <p>
 * Provides operations for retrieving location entities filtered by parent ID and formatting them as alternatives.
 *
 * @param <T> The type of the location entity (Country, State, City, District)
 * @param <ID> The type of the parent entity ID
 *
 * @author InMind Latam
 * @version 1.0
 */
public interface ILocationService<T, ID> {

    /**
     * Retrieves the list of location entities filtered by the parent entity ID.
     *
     * @param parentId the ID of the parent entity
     * @return the list of location entities
     */
    List<T> getByParentId(ID parentId);

    /**
     * Retrieves the list of location entities formatted as alternatives.
     *
     * @param parentId the ID of the parent entity
     * @return the list of alternatives
     */
    List<AlternativeDto> getFormatAlternative(ID parentId);

    /**
     * Retrieves all location entities (only for the country service).
     *
     * @return the list of all entities
     * @throws UnsupportedOperationException if the method is not supported for the service
     */
    default List<T> getAll() {
        throw new UnsupportedOperationException("This method is only available for country service");
    }
} 