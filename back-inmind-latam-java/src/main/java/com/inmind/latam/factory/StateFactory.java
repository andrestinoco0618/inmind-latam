package com.inmind.latam.factory;

import org.springframework.stereotype.Component;
import com.inmind.latam.model.State;

import static com.inmind.latam.constant.QuestionIdentifiers.OTHER;

/**
 * Factory class for creating State entities.
 * <p>
 * This class implements the LocationEntityFactory interface to provide
 * factory methods for creating State entities, including a special "Other" state.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.model.State
 * @see com.inmind.latam.factory.LocationEntityFactory
 */
@Component
public class StateFactory implements LocationEntityFactory<State> {
    
    /**
     * Creates a new empty State entity.
     * 
     * @return a new State instance
     */
    @Override
    public State createEntity() {
        return new State();
    }
    
    /**
     * Creates a special "Other" State entity with predefined values.
     * This entity is used when a user selects "Other" as their state.
     * 
     * @return a State instance representing the "Other" option
     */
    @Override
    public State createOtherEntity() {
        State other = new State();
        other.setId(0);
        other.setName(OTHER);
        return other;
    }
} 