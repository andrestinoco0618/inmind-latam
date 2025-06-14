package com.inmind.latam.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inmind.latam.dto.AlternativeDto;
import com.inmind.latam.factory.CountryFactory;
import com.inmind.latam.model.Country;
import com.inmind.latam.repository.ICountryRepository;
import com.inmind.latam.service.ICountryService;

import static com.inmind.latam.constant.QuestionIdentifiers.OTHER;

/**
 * Implementation of the ICountryService interface for managing Country entities.
 * <p>
 * This class extends AbstractLocationServiceImpl to provide country-specific
 * functionality for handling country data and alternatives.
 *
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see com.inmind.latam.service.ICountryService
 * @see com.inmind.latam.service.impl.AbstractLocationServiceImpl
 */
@Service
public class CountryServiceImpl extends AbstractLocationServiceImpl<Country, Integer, ICountryRepository> implements ICountryService {
	
	public CountryServiceImpl(ICountryRepository countryRepository, CountryFactory countryFactory) {
		super(countryRepository, countryFactory);
	}

	/**
	 * Gets the list of countries ordered by name.
	 *
	 * @param parentId the parent ID (not used for countries)
	 * @return the list of countries ordered by name
	 */
	@Override
	protected List<Country> getOrderedEntities(Integer parentId) {
		return repository.findAllByOrderByNameAsc();
	}

	/**
	 * Retrieves all countries.
	 *
	 * @return the list of all countries
	 */
	@Override
	public List<Country> getAll() {
		return getByParentId(null);
	}

	/**
	 * Retrieves a list of country alternatives in the required format.
	 *
	 * @return the list of country alternatives
	 */
	@Override
	public List<AlternativeDto> getCountryFormatAlternative() {
		return getFormatAlternative(null);
	}
}