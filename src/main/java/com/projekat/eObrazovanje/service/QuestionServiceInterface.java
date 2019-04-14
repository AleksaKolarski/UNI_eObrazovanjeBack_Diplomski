package com.projekat.eObrazovanje.service;

import java.util.List;

import com.projekat.eObrazovanje.model.Question;

public interface QuestionServiceInterface {
	
	List<Question> findAll();
	
	List<Question> findRandom(Integer amount);
		
	Question findById(Integer id);
	
	Integer count();
	
	Question save(Question question);
	
	void remove(Question question);
	
}
