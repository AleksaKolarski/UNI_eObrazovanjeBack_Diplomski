package com.projekat.eObrazovanje.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.eObrazovanje.dto.QuestionDTO;
import com.projekat.eObrazovanje.service.QuestionService;

@RestController
@RequestMapping("/questions")
public class QuestionController {

	
	@Autowired
	private QuestionService qService;
	
	
	@GetMapping("/all")
	public ResponseEntity<List<QuestionDTO>> getQuestions(){
		return new ResponseEntity<>(QuestionDTO.convertEntityList(qService.findAll()), HttpStatus.OK);
	}
	
}
