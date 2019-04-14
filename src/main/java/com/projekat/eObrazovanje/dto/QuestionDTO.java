package com.projekat.eObrazovanje.dto;

import java.util.ArrayList;
import java.util.List;

import com.projekat.eObrazovanje.model.Question;

public class QuestionDTO {
	
	private Integer id;
	private String body;
	private List<AnswerDTO> answers;
	
	
	public QuestionDTO() {}
	
	public QuestionDTO(Question q) {
		this.id = q.getId();
		this.body = q.getBody();		
		this.answers = AnswerDTO.listToListDTO(q.getAnswers());
	}
	
	
	public static List<QuestionDTO> listToListDTO(List<Question> questions){
		List<QuestionDTO> questionsDTO = new ArrayList<>();
		for(Question q: questions) {
			questionsDTO.add(new QuestionDTO(q));
		}
		return questionsDTO;
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
