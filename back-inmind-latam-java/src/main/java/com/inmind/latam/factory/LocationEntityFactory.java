package com.inmind.latam.factory;

/**
 * Interface that defines the factory for creating location entities.
 * <p>
 * This interface provides methods for creating:
 * - New location entities
 * - Special "Other" location entities with predefined values
 * 
 * @param <T> The type of location entity
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
public interface LocationEntityFactory<T> {
    /**
     * Creates a new location entity.
     * 
     * @return A new instance of the location entity
     */
    T createEntity();
    
    /**
     * Creates an "OTHER" entity with ID 0 and name "OTHER".
     * This entity is used when a user selects "Other" as their location.
     * 
     * @return A new instance of the "OTHER" entity
     */
    T createOtherEntity();
} 