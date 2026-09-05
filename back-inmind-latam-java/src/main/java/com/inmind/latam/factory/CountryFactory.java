package com.inmind.latam.factory;

import org.springframework.stereotype.Component;
import com.inmind.latam.model.Country;

import static com.inmind.latam.constant.QuestionIdentifiers.OTHER;

/**
 * Factory class for creating Country entities.
 * <p>
 * This class implements the LocationEntityFactory interface to provide
 * factory methods for creating Country entities, including a special "Other" country.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.model.Country
 * @see com.inmind.latam.factory.LocationEntityFactory
 */
@Component
public class CountryFactory implements LocationEntityFactory<Country> {
    
    /**
     * Creates a new empty Country entity.
     * 
     * @return a new Country instance
     */
    @Override
    public Country createEntity() {
        return new Country();
    }
    
    /**
     * Creates a special "Other" Country entity with predefined values.
     * This entity is used when a user selects "Other" as their country.
     * 
     * @return a Country instance representing the "Other" option
     */
    @Override
    public Country createOtherEntity() {
        Country other = new Country();
        other.setId(0);
        other.setName(OTHER);
        return other;
    }
} 