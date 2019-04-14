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

import com.projekat.eObrazovanje.dto.AnswerDTO;
import com.projekat.eObrazovanje.dto.LogDTO;
import com.projekat.eObrazovanje.dto.QuestionAnswerPairDTO;
import com.projekat.eObrazovanje.dto.QuestionDTO;
import com.projekat.eObrazovanje.dto.ResultDTO;
import com.projekat.eObrazovanje.model.Answer;
import com.projekat.eObrazovanje.model.Log;
import com.projekat.eObrazovanje.model.Question;
import com.projekat.eObrazovanje.model.QuestionAnswerPair;
import com.projekat.eObrazovanje.model.QuestionAnswerPairKey;
import com.projekat.eObrazovanje.model.Result;
import com.projekat.eObrazovanje.service.AnswerService;
import com.projekat.eObrazovanje.service.LogService;
import com.projekat.eObrazovanje.service.QuestionAnswerPairService;
import com.projekat.eObrazovanje.service.QuestionService;
import com.projekat.eObrazovanje.service.ResultService;

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private ResultService rService;
	
	@Autowired
	private QuestionService qService;
	
	@Autowired
	private AnswerService aService;
	
	@Autowired
	private QuestionAnswerPairService qapService;
	
	@Autowired
	private LogService lService;
	
	
	@GetMapping("/questions")
	public ResponseEntity<List<QuestionDTO>> getQuestions(){
		
		List<Question> questions = qService.findRandom(10);
		List<QuestionDTO> questionsDTO = QuestionDTO.listToListDTO(questions);
		
		return new ResponseEntity<>(questionsDTO, HttpStatus.OK);
	}
	
	@PostMapping("/questions")
	public QuestionDTO addQuestion(@RequestBody QuestionDTO questionDTO) {
		
		Question question = new Question();
		question.setBody(questionDTO.getBody());
		question = qService.save(question);
		
		for(AnswerDTO answerDTO: questionDTO.getAnswers()) {
			
			Answer answer = new Answer();
			answer.setBody(answerDTO.getBody());
			answer.setCorrect(answerDTO.getCorrect());
			answer.setQuestion(question);
			aService.save(answer);
		}
		
		question = qService.findById(question.getId());
		
		return new QuestionDTO(question);
	}
	
	@GetMapping("/results")
	public List<ResultDTO> getAllResults(){
		
		List<Result> results = rService.findAll();
		List<ResultDTO> resultsDTO = ResultDTO.listToListDTO(results);
		
		return resultsDTO;
	}
	
	@PostMapping("/results/{name}")
	public ResponseEntity<String> submit(@PathVariable("name") String name, @RequestBody ResultDTO resultDTO){	
		
		Result result = new Result();
		result.setName(name);
		result = rService.save(result);
		
		for(QuestionAnswerPairDTO qapDTO: resultDTO.getAnswers()) {	
			
			QuestionAnswerPair qap = new QuestionAnswerPair();
			
			QuestionAnswerPairKey qapKey = new QuestionAnswerPairKey();
			qapKey.setQuestionId(qapDTO.getQuestionId());
			qapKey.setAnswerId(qapDTO.getAnswerId());
			qapKey.setResultId(result.getId());
			
			qap.setId(qapKey);
			qapService.save(qap);
		}
		
		for(LogDTO logDTO: resultDTO.getLog()) {
			
			Log log = new Log();
			log.setType(logDTO.getType());
			log.setBefore(logDTO.getBefore());
			log.setAfter(logDTO.getAfter());
			log.setTime(logDTO.getTime());
			log.setResult(result);
			log = lService.save(log);
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
