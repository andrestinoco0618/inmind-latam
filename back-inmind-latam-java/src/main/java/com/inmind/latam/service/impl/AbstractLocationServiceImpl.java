package com.inmind.latam.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.factory.LocationEntityFactory;
import com.inmind.latam.service.ILocationService;

import java.lang.reflect.Method;

import static com.inmind.latam.constant.QuestionIdentifiers.OTHER;

/**
 * Abstract base implementation for location services.
 *
 * This abstract class provides common functionality for location-related services,
 * including retrieving entities by parent ID, formatting alternatives, and utility methods
 * for setting entity properties using reflection.
 *
 * @param <T> The type of the location entity
 * @param <ID> The type of the parent entity ID
 * @param <R> The type of the repository
 *
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractLocationServiceImpl<T, ID, R extends JpaRepository<T, ID>> implements ILocationService<T, ID> {
    
    protected final R repository;
    protected final LocationEntityFactory<T> factory;
    
    protected AbstractLocationServiceImpl(R repository, LocationEntityFactory<T> factory) {
        this.repository = repository;
        this.factory = factory;
    }
    
    /**
     * Gets the list of entities ordered by name.
     *
     * @param parentId the ID of the parent entity
     * @return the list of ordered entities
     */
    protected abstract List<T> getOrderedEntities(ID parentId);

    /**
     * Retrieves entities by parent ID. If no entities are found, returns a list with the 'other' entity.
     *
     * @param parentId the ID of the parent entity
     * @return the list of entities
     */
    @Override
    public List<T> getByParentId(ID parentId) {
        List<T> entities = getOrderedEntities(parentId);
        
        if (!entities.isEmpty()) {
            return entities;
        } else {
            return List.of(factory.createOtherEntity());
        }
    }

    /**Add commentMore actions
     * Retrieves a list of alternatives in the required format for the given parent ID.
     *
     * @param parentId the ID of the parent entity
     * @return the list of alternatives
     */
    @Override
    public List<AlternativeDto> getFormatAlternative(ID parentId) {
        List<T> entities = getByParentId(parentId);
        
        return entities.stream()
                .map(entity -> {
                    try {
                        Method getId = entity.getClass().getMethod("getId");
                        Method getName = entity.getClass().getMethod("getName");
                        return new AlternativeDto(String.valueOf(getId.invoke(entity)), (String) getName.invoke(entity));
                    } catch (Exception e) {
                        throw new RuntimeException("Error accessing getId or getName methods", e);
                    }
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Utility method to set the ID of an entity using reflection.
     *
     * @param entity the entity to set the ID for
     * @param id the ID value to set
     */
    protected void setId(T entity, int id) {
        try {
            Method setId = entity.getClass().getMethod("setId", int.class);
            setId.invoke(entity, id);
        } catch (Exception e) {
            throw new RuntimeException("Error setting entity ID", e);
        }
    }
    
    /**
     * Utility method to set the name of an entity using reflection.
     *
     * @param entity the entity to set the name for
     * @param name the name value to set     */
    protected void setName(T entity, String name) {
        try {
            Method setName = entity.getClass().getMethod("setName", String.class);
            setName.invoke(entity, name);
        } catch (Exception e) {
            throw new RuntimeException("Error setting entity name", e);
        }
    }
} 