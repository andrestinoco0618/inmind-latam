package com.inmind.latam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inmind.latam.model.QuestionQuestionDaughter;

@Repository
public interface IQuestionQuestionDaughterRepository extends JpaRepository<QuestionQuestionDaughter, String>{
	
	@Query(value = "SELECT tph.ID_PreguntaHija "
			+ "FROM tpreguntaxtpreguntahija tph "
			+ "JOIN tpregunta tp ON tph.ID_PreguntaHija = tp.ID_Pregunta  "
			+ "WHERE tph.ID_Pregunta = :idQuestion "
			+ "AND tph.ID_TipoTransicion = :idTransactionType", 
	nativeQuery = true)
    List<Object[]> findQuestionDaughterByQuestion(@Param("idQuestion") String idQuestion, 
                                               @Param("idTransactionType") String idTransactionType);
    
    @Query(value = "SELECT tph.ID_PreguntaHija "
			+ "FROM tpreguntaxtpreguntahija tph "
			+ "JOIN tpregunta tp ON tph.ID_PreguntaHija = tp.ID_Pregunta  "
			+ "WHERE tph.ID_Pregunta = :idQuestion", 
	nativeQuery = true)
	List<Object[]> findQuestionDaughterByQuestionWithoutTransactionType(@Param("idQuestion") String idQuestion);
    
}
