package com.projekat.eObrazovanje.service;

import java.util.List;

import com.projekat.eObrazovanje.model.Question;

public interface QuestionServiceInterface {
	
	List<Question> findAll();
		
	Question findById(Integer id);
	
	Question save(Question question);
	
	void remove(Question question);
	
}
