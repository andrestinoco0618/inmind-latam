package com.inmind.latam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inmind.latam.model.Country;
import java.util.List;

/**
 * Repository interface for managing Country entities.
 * <p>
 * This interface extends JpaRepository to provide basic CRUD operations and
 * additional custom queries for Country entities.
 * 
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.inmind.latam.model.Country
 */
@Repository
public interface ICountryRepository extends JpaRepository<Country, Integer>{
    /**
     * Finds all countries ordered by name in ascending order.
     * 
     * @return list of countries ordered by name
     */
    List<Country> findAllByOrderByNameAsc();
}
