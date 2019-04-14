package com.projekat.eObrazovanje.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.eObrazovanje.model.Answer;
import com.projekat.eObrazovanje.repository.AnswerRepository;

@Service
public class AnswerService implements AnswerServiceInterface {

	@Autowired
	AnswerRepository answerRepository;
	
	@Override
	public List<Answer> findAll() {
		return answerRepository.findAll();
	}

	@Override
	public Answer findById(Integer id) {
		return answerRepository.findById(id).orElse(null);
	}

	@Override
	public Answer save(Answer answer) {
		return answerRepository.save(answer);
	}

	@Override
	public void remove(Answer answer) {
		answerRepository.delete(answer);
	}

}
