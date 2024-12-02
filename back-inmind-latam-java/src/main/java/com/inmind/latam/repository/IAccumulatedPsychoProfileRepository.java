package com.inmind.latam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inmind.latam.model.AccumulatedPsychoProfile;

@Repository
public interface IAccumulatedPsychoProfileRepository extends JpaRepository<AccumulatedPsychoProfile, Integer>{

	public Optional<AccumulatedPsychoProfile> findByIdQuestionnaireAnswered(String idQuestionnaire);
	
}
