package com.inmind.latam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inmind.latam.model.Questionnaire;

@Repository
public interface IQuestionnaireRepository extends JpaRepository<Questionnaire, String>{
	
    Optional<Questionnaire> findByName(String name);
    
}
