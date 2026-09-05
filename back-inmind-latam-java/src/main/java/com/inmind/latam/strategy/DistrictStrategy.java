package com.inmind.latam.strategy;

import java.util.List;
import org.springframework.stereotype.Component;
import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.service.IDistrictService;

/**
 * Strategy implementation for handling district-related location operations.
 * 
 * This class implements the LocationStrategy interface to provide
 * district-specific functionality for retrieving district alternatives.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.strategy.LocationStrategy
 * @see com.inmind.latam.service.IDistrictService
 */
@Component
public class DistrictStrategy implements LocationStrategy {
    
    private final IDistrictService districtService;
    
    /**
     * Constructs a new DistrictStrategy with the provided district service.
     * 
     * @param districtService the service for handling district operations
     */
    public DistrictStrategy(IDistrictService districtService) {
        this.districtService = districtService;
    }
    
    /**
     * Gets the list of district alternatives for a given city.
     * 
     * @param parentId the ID of the parent city
     * @return list of district alternatives
     */
    @Override
    public List<AlternativeDto> getAlternatives(Integer parentId) {
        return districtService.getDistrictFormatAlternative(parentId);
    }
} 