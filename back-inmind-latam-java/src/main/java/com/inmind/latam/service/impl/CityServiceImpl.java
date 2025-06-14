package com.inmind.latam.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.factory.CityFactory;
import com.inmind.latam.model.City;
import com.inmind.latam.repository.ICityRepository;
import com.inmind.latam.service.ICityService;

import static com.inmind.latam.constant.QuestionIdentifiers.OTHER;

/**
 * Implementation of the ICityService interface for managing City entities.
 * <p>
 * This class extends AbstractLocationServiceImpl to provide city-specific
 * functionality for handling city data and alternatives.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.service.ICityService
 * @see com.inmind.latam.service.impl.AbstractLocationServiceImpl
 */
@Service
public class CityServiceImpl extends AbstractLocationServiceImpl<City, Integer, ICityRepository> implements ICityService {
	
	/**
	 * Constructs a new CityServiceImpl with the provided dependencies.
	 * 
	 * @param cityRepository the repository for city operations
	 * @param cityFactory the factory for creating city entities
	 */
	public CityServiceImpl(ICityRepository cityRepository, CityFactory cityFactory) {
		super(cityRepository, cityFactory);
	}
	
	/**
	 * Gets the ordered list of cities for a given state.
	 * 
	 * @param parentId the ID of the parent state
	 * @return list of cities ordered by name
	 */
	@Override
	protected List<City> getOrderedEntities(Integer parentId) {
		return repository.findByStateIdOrderByNameAsc(parentId);
	}
	
	/**
	 * Gets the list of city alternatives in the required format for a given state.
	 * 
	 * @param stateId the ID of the state to get cities for
	 * @return list of city alternatives
	 */
	@Override
	public List<AlternativeDto> getCityFormatAlternative(int stateId) {
		return getFormatAlternative(stateId);
	}
}