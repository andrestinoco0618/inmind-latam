package com.inmind.latam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inmind.latam.model.QuestionType;

@Repository
public interface IQuestionTypeRepository extends JpaRepository<QuestionType, String>{

	Optional<QuestionType> findByIdQuestionType(String idQuestionType);

}
