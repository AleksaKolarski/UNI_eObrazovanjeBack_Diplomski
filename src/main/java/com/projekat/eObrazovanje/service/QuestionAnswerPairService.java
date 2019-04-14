package com.projekat.eObrazovanje.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.eObrazovanje.model.QuestionAnswerPair;
import com.projekat.eObrazovanje.model.QuestionAnswerPairKey;
import com.projekat.eObrazovanje.repository.QuestionAnswerPairRepository;

@Service
public class QuestionAnswerPairService implements QuestionAnswerPairServiceInterface {

	@Autowired
	QuestionAnswerPairRepository qapRepository;
	
	@Override
	public List<QuestionAnswerPair> findAll() {
		return qapRepository.findAll();
	}

	@Override
	public QuestionAnswerPair findById(QuestionAnswerPairKey id) {
		return qapRepository.findById(id).orElse(null);
	}

	@Override
	public QuestionAnswerPair save(QuestionAnswerPair questionAnswerPair) {
		return qapRepository.save(questionAnswerPair);
	}

	@Override
	public void remove(QuestionAnswerPair questionAnswerPair) {
		qapRepository.delete(questionAnswerPair);
	}

}
