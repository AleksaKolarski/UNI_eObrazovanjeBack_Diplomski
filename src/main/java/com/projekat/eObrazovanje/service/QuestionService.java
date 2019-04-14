package com.projekat.eObrazovanje.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
	public List<Question> findRandom(Integer amount) {
		
		List<Question> questions = new ArrayList<Question>();
		
		int count = count();
		
		if(amount > count) {
			amount = count;
		}
		
		Random rand = new Random();
		int[] targets = new int[amount];
		
		for(int i = 0; i < amount; i++) {
			
			boolean has = false;
			int potential;
			do {
				potential = rand.nextInt(count) + 1;
				has = false;
				for(int j = 0; j < i; j++) {
					if(targets[j] == potential) {
						has = true;
						break;
					}
				}
			}while(has == true);
			targets[i] = potential;
			
			questions.add(findById(potential));
		}
		
		return questions;
	}

	@Override
	public Question findById(Integer id) {
		Optional<Question> optional =  qRepository.findById(id);
		return optional.orElse(null);
	}
	
	@Override
	public Integer count() {
		return (int) qRepository.count();
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
