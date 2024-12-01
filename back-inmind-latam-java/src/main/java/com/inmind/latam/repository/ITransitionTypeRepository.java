package com.inmind.latam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inmind.latam.model.TransitionType;

@Repository
public interface ITransitionTypeRepository extends JpaRepository<TransitionType, String>{
	
	Optional<TransitionType> findByIdProfileAndIdAlternative(String idProfile, String idAlternative);

}
