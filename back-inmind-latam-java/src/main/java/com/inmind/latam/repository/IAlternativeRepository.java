package com.inmind.latam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inmind.latam.model.Alternative;

@Repository
public interface IAlternativeRepository  extends JpaRepository<Alternative, String>{

    List<Alternative> findByQuestion_IdQuestion(String questionId);

}
