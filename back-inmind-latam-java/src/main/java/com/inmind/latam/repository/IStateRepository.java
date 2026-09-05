package com.inmind.latam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inmind.latam.model.State;

/**
 * Repository interface for managing State entities.
 * <p>
 * This interface extends JpaRepository to provide basic CRUD operations and
 * additional custom queries for State entities.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.inmind.latam.model.State
 */
@Repository
public interface IStateRepository extends JpaRepository<State, Integer>{
    
	/**
	 * Finds all states for a given country, ordered by name in ascending order.
	 * 
	 * @param countryId the ID of the country to find states for
	 * @return list of states ordered by name
	 */
	List<State> findByCountryIdOrderByNameAsc(Integer countryId);
	
}
