package com.projekat.eObrazovanje.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.eObrazovanje.model.Log;
import com.projekat.eObrazovanje.repository.LogRepository;

@Service
public class LogService implements LogServiceInterface {
	
	@Autowired
	private LogRepository logRepository;

	@Override
	public List<Log> findAll() {
		return logRepository.findAll();
	}

	@Override
	public Log findById(Integer id) {
		return logRepository.findById(id).orElse(null);
	}

	@Override
	public Log save(Log log) {
		return logRepository.save(log);
	}

	@Override
	public void remove(Log log) {
		logRepository.delete(log);
	}
	
}
