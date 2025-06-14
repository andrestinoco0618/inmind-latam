package com.inmind.latam.service;

import java.util.List;

import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.model.State;

/**
 * Service interface for managing State entities.
 * <p>
 * This interface extends ILocationService to provide state-specific operations
 * and additional functionality for handling state data.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.service.ILocationService
 * @see com.inmind.latam.model.State
 */
public interface IStateService extends ILocationService<State, Integer> {
	
	/**
	 * Gets the list of state alternatives in the required format for a given country.
	 * 
	 * @param idCountry the ID of the country to get states for
	 * @return list of state alternatives
	 */
	public List<AlternativeDto> getStateFormatAlternative(int idCountry);

}
