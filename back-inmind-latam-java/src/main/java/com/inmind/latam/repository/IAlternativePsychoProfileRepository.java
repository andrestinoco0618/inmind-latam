package com.inmind.latam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inmind.latam.model.AlternativePsychoProfile;

public interface IAlternativePsychoProfileRepository extends JpaRepository<AlternativePsychoProfile, String>{
	
	@Query(value = "SELECT ta.id_psicologa, COUNT(*) AS total "
			+ "FROM t_alternativa_psico_perfil ta "
			+ "WHERE ta.id_alternativa IN :response "
			+ "AND ta.id_perfil = :profileType "
			+ "GROUP BY ta.id_psicologa "
			+ "ORDER BY total DESC "
			+ "LIMIT 1",
			nativeQuery = true)
	Optional<Object[]> findPsychoWithMostAlternatives(
	        @Param("profileType") String profileType,
	        @Param("response") List<String> response);

}
