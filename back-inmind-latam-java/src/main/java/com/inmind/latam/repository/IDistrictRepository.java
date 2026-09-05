package com.inmind.latam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inmind.latam.model.District;

/**
 * Repository interface for managing District entities.
 * <p>
 * This interface extends JpaRepository to provide basic CRUD operations and
 * additional custom queries for District entities.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.inmind.latam.model.District
 */
@Repository
public interface IDistrictRepository extends JpaRepository<District, Integer>{
	
	/**
	 * Finds all districts for a given city, ordered by name in ascending order.
	 * 
	 * @param cityId the ID of the city to find districts for
	 * @return list of districts ordered by name
	 */
	List<District> findByCityIdOrderByNameAsc(Integer cityId);

}
