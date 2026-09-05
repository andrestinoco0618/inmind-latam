package com.inmind.latam.factory;

import org.springframework.stereotype.Component;
import com.inmind.latam.model.City;

import static com.inmind.latam.constant.QuestionIdentifiers.OTHER;

/**
 * Factory class for creating City entities.
 * <p>
 * This class implements the LocationEntityFactory interface to provide
 * factory methods for creating City entities, including a special "Other" city.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.model.City
 * @see com.inmind.latam.factory.LocationEntityFactory
 */
@Component
public class CityFactory implements LocationEntityFactory<City> {
    
    /**
     * Creates a new empty City entity.
     * 
     * @return a new City instance
     */
    @Override
    public City createEntity() {
        return new City();
    }
    
    /**
     * Creates a special "Other" City entity with predefined values.
     * This entity is used when a user selects "Other" as their city.
     * 
     * @return a City instance representing the "Other" option
     */
    @Override
    public City createOtherEntity() {
        City other = new City();
        other.setId(0);
        other.setName(OTHER);
        return other;
    }
} 