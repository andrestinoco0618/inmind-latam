package com.inmind.latam.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.factory.StateFactory;
import com.inmind.latam.model.State;
import com.inmind.latam.repository.IStateRepository;
import com.inmind.latam.service.IStateService;

/**
 * Implementation of the IStateService interface for managing State entities.
 * <p>
 * This class extends AbstractLocationServiceImpl to provide state-specific
 * functionality for handling state data and alternatives.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.service.IStateService
 * @see com.inmind.latam.service.impl.AbstractLocationServiceImpl
 */
@Service
public class StateServiceImpl extends AbstractLocationServiceImpl<State, Integer, IStateRepository> implements IStateService {
    
    /**
     * Constructs a new StateServiceImpl with the provided dependencies.
     * 
     * @param stateRepository the repository for state operations
     * @param stateFactory the factory for creating state entities
     */
    public StateServiceImpl(IStateRepository stateRepository, StateFactory stateFactory) {
        super(stateRepository, stateFactory);
    }
    
    /**
     * Gets the ordered list of states for a given country.
     * 
     * @param parentId the ID of the parent country
     * @return list of states ordered by name
     */
    @Override
    protected List<State> getOrderedEntities(Integer parentId) {
        return repository.findByCountryIdOrderByNameAsc(parentId);
    }
    
    /**
     * Gets the list of state alternatives in the required format for a given country.
     * 
     * @param countryId the ID of the country to get states for
     * @return list of state alternatives
     */
    @Override
    public List<AlternativeDto> getStateFormatAlternative(int countryId) {
        return getFormatAlternative(countryId);
    }
} 