package com.inmind.latam.service;

import java.util.List;

import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.model.City;

/**
 * Service interface for managing City entities.
 * <p>
 * This interface extends ILocationService to provide city-specific operations
 * and additional functionality for handling city data.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.service.ILocationService
 * @see com.inmind.latam.model.City
 */
public interface ICityService extends ILocationService<City, Integer> {

	/**
	 * Gets the list of city alternatives in the required format for a given state.
	 * 
	 * @param idState the ID of the state to get cities for
	 * @return list of city alternatives
	 */
	public List<AlternativeDto> getCityFormatAlternative(int idState);

}
