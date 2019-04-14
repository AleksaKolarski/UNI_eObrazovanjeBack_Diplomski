package com.projekat.eObrazovanje.service;

import java.util.List;

import com.projekat.eObrazovanje.model.QuestionAnswerPair;
import com.projekat.eObrazovanje.model.QuestionAnswerPairKey;

public interface QuestionAnswerPairServiceInterface {

	List<QuestionAnswerPair> findAll();
	
	QuestionAnswerPair findById(QuestionAnswerPairKey id);
	
	QuestionAnswerPair save(QuestionAnswerPair questionAnswerPair);
	
	void remove(QuestionAnswerPair questionAnswerPair);
	
}
