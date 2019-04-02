package com.projekat.eObrazovanje.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.eObrazovanje.model.Question;
import com.projekat.eObrazovanje.repository.QuestionRepository;

@Service
public class QuestionService implements QuestionServiceInterface {
	
	@Autowired
	private QuestionRepository qRepository;

	@Override
	public List<Question> findAll() {
		return qRepository.findAll();
	}

	@Override
	public Question findById(Integer id) {
		Optional<Question> optional =  qRepository.findById(id);
		return optional.orElse(null);
	}

	@Override
	public Question save(Question question) {
		return qRepository.save(question);
	}

	@Override
	public void remove(Question question) {
		qRepository.delete(question);
	}

}
