package com.inmind.latam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inmind.latam.model.City;

/**
 * Repository interface for managing City entities.
 * <p>
 * This interface extends JpaRepository to provide basic CRUD operations and
 * additional custom queries for City entities.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.inmind.latam.model.City
 */
@Repository
public interface ICityRepository extends JpaRepository<City, Integer>{

	/**
	 * Finds all cities for a given state, ordered by name in ascending order.
	 * 
	 * @param stateId the ID of the state to find cities for
	 * @return list of cities ordered by name
	 */
	List<City> findByStateIdOrderByNameAsc(Integer stateId);

}
