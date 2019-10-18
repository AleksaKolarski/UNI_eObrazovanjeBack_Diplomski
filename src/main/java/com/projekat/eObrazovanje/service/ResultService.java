package com.projekat.eObrazovanje.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.eObrazovanje.model.Result;
import com.projekat.eObrazovanje.repository.ResultRepository;

@Service
public class ResultService implements ResultServiceInterface {

	@Autowired
	ResultRepository resultRepository;
	
	@Override
	public List<Result> findAll() {
		return resultRepository.findAll();
	}

	@Override
	public Result findById(Integer id) {
		return resultRepository.findById(id).orElse(null);
	}

	@Override
	public Result save(Result result) {
		return resultRepository.save(result);
	}

	@Override
	public void remove(Result result) {
		resultRepository.delete(result);
	}	
}
