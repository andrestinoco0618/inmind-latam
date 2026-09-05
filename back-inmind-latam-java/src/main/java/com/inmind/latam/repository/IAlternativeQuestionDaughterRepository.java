package com.inmind.latam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inmind.latam.model.AlternativeQuestionDaughter;

/**
 * Repository interface for managing AlternativeQuestionDaughter entities.
 * <p>
 * This interface extends JpaRepository to provide basic CRUD operations and
 * custom queries for AlternativeQuestionDaughter entities.
 *
 * @author InMind Latam
 * @version 1.0
 * @since 1.0
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.inmind.latam.model.AlternativeQuestionDaughter
 */
@Repository
public interface IAlternativeQuestionDaughterRepository extends JpaRepository<AlternativeQuestionDaughter, String>{
	
	/**
	 * Finds distinct daughter question IDs by transaction type and a list of alternatives.
	 *
	 * @param transactionType the transaction type to filter
	 * @param alternatives the list of alternative IDs
	 * @return list of daughter question IDs
	 */
	@Query(value = "SELECT DISTINCT tah.id_question_daughter "
            + "FROM t_alternative_question_daughter tah "
            + "WHERE tah.id_type_transition = :transactionType "
            + "AND tah.id_alternative IN :alternatives",
    nativeQuery = true)
	List<String> findQuestionDaughterByAlternativeAndTransaction(
    @Param("transactionType") String transactionType,
    @Param("alternatives") List<String> alternatives);

}
