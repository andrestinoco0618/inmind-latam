package com.inmind.latam.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.factory.DistrictFactory;
import com.inmind.latam.model.District;
import com.inmind.latam.repository.IDistrictRepository;
import com.inmind.latam.service.IDistrictService;

import static com.inmind.latam.constant.QuestionIdentifiers.OTHER;

/**
 * Implementation of the IDistrictService interface for managing District entities.
 * <p>
 * This class extends AbstractLocationServiceImpl to provide district-specific
 * functionality for handling district data and alternatives.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.service.IDistrictService
 * @see com.inmind.latam.service.impl.AbstractLocationServiceImpl
 */
@Service
public class DistrictServiceImpl extends AbstractLocationServiceImpl<District, Integer, IDistrictRepository> implements IDistrictService {
	
	/**
	 * Constructs a new DistrictServiceImpl with the provided dependencies.
	 * 
	 * @param districtRepository the repository for district operations
	 * @param districtFactory the factory for creating district entities
	 */
	public DistrictServiceImpl(IDistrictRepository districtRepository, DistrictFactory districtFactory) {
		super(districtRepository, districtFactory);
	}
	
	/**
	 * Gets the ordered list of districts for a given city.
	 * 
	 * @param parentId the ID of the parent city
	 * @return list of districts ordered by name
	 */
	@Override
	protected List<District> getOrderedEntities(Integer parentId) {
		return repository.findByCityIdOrderByNameAsc(parentId);
	}
	
	/**
	 * Gets the list of district alternatives in the required format for a given city.
	 * 
	 * @param cityId the ID of the city to get districts for
	 * @return list of district alternatives
	 */
	@Override
	public List<AlternativeDto> getDistrictFormatAlternative(int cityId) {
		return getFormatAlternative(cityId);
	}
}