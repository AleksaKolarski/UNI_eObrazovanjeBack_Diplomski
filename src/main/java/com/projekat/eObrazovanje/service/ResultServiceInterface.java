package com.projekat.eObrazovanje.service;

import java.util.List;

import com.projekat.eObrazovanje.model.Result;

public interface ResultServiceInterface {

	List<Result> findAll();
	
	Result findById(Integer id);
	
	Result save(Result result);
	
	void remove(Result result);
	
}
