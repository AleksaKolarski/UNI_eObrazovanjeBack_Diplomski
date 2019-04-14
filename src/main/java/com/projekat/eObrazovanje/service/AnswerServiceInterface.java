package com.projekat.eObrazovanje.service;

import java.util.List;

import com.projekat.eObrazovanje.model.Answer;

public interface AnswerServiceInterface {

	List<Answer> findAll();
	
	Answer findById(Integer id);
	
	Answer save(Answer answer);
	
	void remove(Answer answer);
	
}
