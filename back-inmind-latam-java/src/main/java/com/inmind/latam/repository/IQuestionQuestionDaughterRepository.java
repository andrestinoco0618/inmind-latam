package com.inmind.latam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inmind.latam.model.QuestionQuestionDaughter;

/**
 * Repository interface for managing QuestionQuestionDaughter entities.
 * <p>
 * This interface extends JpaRepository to provide basic CRUD operations and
 * custom queries for QuestionQuestionDaughter entities.
 *
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.inmind.latam.model.QuestionQuestionDaughter
 */
@Repository
public interface IQuestionQuestionDaughterRepository extends JpaRepository<QuestionQuestionDaughter, String>{
	
	/**
	 * Finds daughter question IDs by question ID and transaction type.
	 *
	 * @param idQuestion the ID of the question
	 * @param idTransactionType the ID of the transaction type
	 * @return list of Object arrays containing daughter question IDs
	 */
	@Query(value = "SELECT tph.id_question_daughter "
			+ "FROM t_question_question_daughter tph "
			+ "JOIN t_question tp ON tph.id_question_daughter = tp.id_question  "
			+ "WHERE tph.id_question = :idQuestion "
			+ "AND tph.id_transition_type = :idTransactionType",
	nativeQuery = true)
    List<Object[]> findQuestionDaughterByQuestion(@Param("idQuestion") String idQuestion, 
                                               @Param("idTransactionType") String idTransactionType);
    
	/**
	 * Finds daughter question IDs by question ID without filtering by transaction type.
	 *
	 * @param idQuestion the ID of the question
	 * @return list of Object arrays containing daughter question IDs
	 */
    @Query(value = "SELECT tph.id_question_daughter "
			+ "FROM t_question_question_daughter tph "
			+ "JOIN t_question tp ON tph.id_question_daughter = tp.id_question  "
			+ "WHERE tph.id_question = :idQuestion",
	nativeQuery = true)
	List<Object[]> findQuestionDaughterByQuestionWithoutTransactionType(@Param("idQuestion") String idQuestion);
    
}
