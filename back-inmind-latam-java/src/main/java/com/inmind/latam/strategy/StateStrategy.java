package com.inmind.latam.strategy;

import java.util.List;
import org.springframework.stereotype.Component;
import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.service.IStateService;

/**
 * Strategy implementation for handling state-related location operations.
 * 
 * This class implements the LocationStrategy interface to provide
 * state-specific functionality for retrieving state alternatives.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.strategy.LocationStrategy
 * @see com.inmind.latam.service.IStateService
 */
@Component
public class StateStrategy implements LocationStrategy {
    
    private final IStateService stateService;
    
    /**
     * Constructs a new StateStrategy with the provided state service.
     * 
     * @param stateService the service for handling state operations
     */
    public StateStrategy(IStateService stateService) {
        this.stateService = stateService;
    }
    
    /**
     * Gets the list of state alternatives for a given country.
     * 
     * @param parentId the ID of the parent country
     * @return list of state alternatives
     */
    @Override
    public List<AlternativeDto> getAlternatives(Integer parentId) {
        return stateService.getStateFormatAlternative(parentId);
    }
} 