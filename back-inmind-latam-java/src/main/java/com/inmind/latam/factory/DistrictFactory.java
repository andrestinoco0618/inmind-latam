package com.inmind.latam.factory;

import org.springframework.stereotype.Component;
import com.inmind.latam.model.District;

import static com.inmind.latam.constant.QuestionIdentifiers.OTHER;

/**
 * Factory class for creating District entities.
 * <p>
 * This class implements the LocationEntityFactory interface to provide
 * factory methods for creating District entities, including a special "Other" district.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.model.District
 * @see com.inmind.latam.factory.LocationEntityFactory
 */
@Component
public class DistrictFactory implements LocationEntityFactory<District> {
    
    /**
     * Creates a new empty District entity.
     * 
     * @return a new District instance
     */
    @Override
    public District createEntity() {
        return new District();
    }
    
    /**
     * Creates a special "Other" District entity with predefined values.
     * This entity is used when a user selects "Other" as their district.
     * 
     * @return a District instance representing the "Other" option
     */
    @Override
    public District createOtherEntity() {
        District other = new District();
        other.setId(0);
        other.setName(OTHER);
        return other;
    }
} 