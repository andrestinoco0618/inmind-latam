package com.inmind.latam.strategy;

import java.util.List;
import org.springframework.stereotype.Component;
import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.service.ICountryService;

/**
 * Strategy implementation for handling country-related location operations.
 * 
 * This class implements the LocationStrategy interface to provide
 * country-specific functionality for retrieving country alternatives.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.strategy.LocationStrategy
 * @see com.inmind.latam.service.ICountryService
 */
@Component
public class CountryStrategy implements LocationStrategy {
    
    private final ICountryService countryService;
    
    /**
     * Constructs a new CountryStrategy with the provided country service.
     * 
     * @param countryService the service for handling country operations
     */
    public CountryStrategy(ICountryService countryService) {
        this.countryService = countryService;
    }
    
    /**
     * Gets the list of country alternatives.
     * 
     * @param parentId not used for countries as they are top-level entities
     * @return list of country alternatives
     */
    @Override
    public List<AlternativeDto> getAlternatives(Integer parentId) {
        return countryService.getCountryFormatAlternative();
    }
} 