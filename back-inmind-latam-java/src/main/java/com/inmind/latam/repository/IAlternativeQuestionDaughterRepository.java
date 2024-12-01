package com.inmind.latam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inmind.latam.model.AlternativeQuestionDaughter;

@Repository
public interface IAlternativeQuestionDaughterRepository extends JpaRepository<AlternativeQuestionDaughter, String>{
	
	@Query(value = "SELECT DISTINCT tah.ID_PreguntaHija "
            + "FROM talternativaxtpreguntahija tah "
            + "WHERE tah.ID_TipoTransicion = :transactionType "
            + "AND tah.ID_Alternativa IN :alternatives", 
    nativeQuery = true)
	List<String> findQuestionDaughterByAlternativeAndTransaction(
    @Param("transactionType") String transactionType,
    @Param("alternatives") List<String> alternatives);

}
