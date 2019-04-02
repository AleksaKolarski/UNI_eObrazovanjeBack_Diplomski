package com.projekat.eObrazovanje.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projekat.eObrazovanje.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

	
	
}
