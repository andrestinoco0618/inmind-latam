package com.inmind.latam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inmind.latam.model.AlternativePsychoProfile;

/**
 * Repository interface for managing AlternativePsychoProfile entities.
 * <p>
 * This interface extends JpaRepository to provide basic CRUD operations and
 * custom queries for AlternativePsychoProfile entities.
 *
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.inmind.latam.model.AlternativePsychoProfile
 */
public interface IAlternativePsychoProfileRepository extends JpaRepository<AlternativePsychoProfile, String> {

	/**
	 * Finds the top 3 psychologists with the most alternatives for a given profile type and response list.
	 *
	 * @param profileType the profile type to filter psychologists
	 * @param response the list of alternative IDs
	 * @return a list of Object arrays containing psychologist IDs and their total count
	 */
	@Query(value = "SELECT ta.id_psychologist, COUNT(*) AS total " + "FROM t_alternative_psycho_profile ta "
			+ "JOIN t_psychologist p ON p.id_psychologist = ta.id_psychologist " + "WHERE ta.id_alternative IN :response "
			+ "AND ta.id_profile = :profileType " + "AND p.is_active = 1 " + "GROUP BY ta.id_psychologist "
			+ "ORDER BY total DESC " + "LIMIT 3", nativeQuery = true)
	List<Object[]> findPsychoWithMostAlternatives(@Param("profileType") String profileType,
			@Param("response") List<String> response);

}
