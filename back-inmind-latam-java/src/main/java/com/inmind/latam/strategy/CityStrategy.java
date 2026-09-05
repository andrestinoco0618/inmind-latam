package com.inmind.latam.strategy;

import java.util.List;
import org.springframework.stereotype.Component;
import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.service.ICityService;

/**
 * Strategy implementation for handling city-related location operations.
 * 
 * This class implements the LocationStrategy interface to provide
 * city-specific functionality for retrieving city alternatives.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.strategy.LocationStrategy
 * @see com.inmind.latam.service.ICityService
 */
@Component
public class CityStrategy implements LocationStrategy {
    
    private final ICityService cityService;
    
    /**
     * Constructs a new CityStrategy with the provided city service.
     * 
     * @param cityService the service for handling city operations
     */
    public CityStrategy(ICityService cityService) {
        this.cityService = cityService;
    }
    
    /**
     * Gets the list of city alternatives for a given state.
     * 
     * @param parentId the ID of the parent state
     * @return list of city alternatives
     */
    @Override
    public List<AlternativeDto> getAlternatives(Integer parentId) {
        return cityService.getCityFormatAlternative(parentId);
    }
} 