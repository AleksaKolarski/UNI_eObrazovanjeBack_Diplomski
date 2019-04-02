package com.projekat.eObrazovanje.dto;

import java.util.ArrayList;
import java.util.List;

import com.projekat.eObrazovanje.model.Answer;
import com.projekat.eObrazovanje.model.Question;

public class QuestionDTO {
	
	private Integer id;
	private String body;
	private List<AnswerDTO> answers;
	
	
	public QuestionDTO() {}
	
	public QuestionDTO(Question q) {
		this.id = q.getId();
		this.body = q.getBody();
		this.answers = new ArrayList<>();
		for(Answer a: q.getAnswers()) {
			this.answers.add(new AnswerDTO(a));
		}
	}
	
	
	public static List<QuestionDTO> convertEntityList(List<Question> qList){
		List<QuestionDTO> qListDTO = new ArrayList<>();
		for(Question q: qList) {
			qListDTO.add(new QuestionDTO(q));
		}
		return qListDTO;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<AnswerDTO> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerDTO> answers) {
		this.answers = answers;
	}
}
