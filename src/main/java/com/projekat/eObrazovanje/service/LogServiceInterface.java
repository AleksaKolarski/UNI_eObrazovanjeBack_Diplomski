package com.projekat.eObrazovanje.service;

import java.util.List;

import com.projekat.eObrazovanje.model.Log;

public interface LogServiceInterface {
	
	List<Log> findAll();
	
	Log findById(Integer id);
	
	Log save(Log log);
	
	void remove(Log log);
	
}
