package com.projekat.eObrazovanje.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projekat.eObrazovanje.dto.LogDTO;
import com.projekat.eObrazovanje.dto.QuestionAnswerPairDTO;
import com.projekat.eObrazovanje.dto.QuestionDTO;
import com.projekat.eObrazovanje.dto.ResultDTO;
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
	
	
	@PostMapping("/submit/{name}")
	public ResponseEntity<String> submit(@PathVariable("name") String name, @RequestBody ResultDTO result){
		System.out.println(name);
		for(QuestionAnswerPairDTO qa: result.getAnswers()) {
			System.out.println(qa.getQuestionId() + " " + qa.getAnswerId());
		}
		for(LogDTO log: result.getLog()) {
			System.out.println(log.getType() + " " + log.getBefore() + " " + log.getAfter() + " " + log.getTime());
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
}
