package com.inmind.latam.service;

import java.util.List;

import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.model.Country;

/**
 * Service interface for managing Country entities.
 * <p>
 * This interface extends ILocationService to provide country-specific operations
 * and additional functionality for handling country data.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.service.ILocationService
 * @see com.inmind.latam.model.Country
 */
public interface ICountryService extends ILocationService<Country, Integer> {

	/**
	 * Gets the list of country alternatives in the required format.
	 * 
	 * @return list of country alternatives
	 */
	public List<AlternativeDto> getCountryFormatAlternative();

}
