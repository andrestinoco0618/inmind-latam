package com.inmind.latam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inmind.latam.model.Question;

@Repository
public interface IQuestionRepository extends JpaRepository<Question, String>{
	
	Optional<Question> findByIdQuestion(String idQuestion);

}
