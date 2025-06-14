package com.inmind.latam.service;

import java.util.List;

import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.model.District;

/**
 * Service interface for managing District entities.
 * <p>
 * This interface extends ILocationService to provide district-specific operations
 * and additional functionality for handling district data.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.service.ILocationService
 * @see com.inmind.latam.model.District
 */
public interface IDistrictService extends ILocationService<District, Integer> {

	/**
	 * Gets the list of district alternatives in the required format for a given city.
	 * 
	 * @param idCity the ID of the city to get districts for
	 * @return list of district alternatives
	 */
	public List<AlternativeDto> getDistrictFormatAlternative(int idCity);

}
