package com.projekat.eObrazovanje.dto;

import java.util.ArrayList;
import java.util.List;

import com.projekat.eObrazovanje.model.Answer;

public class AnswerDTO {
	
	private Integer id;
	private String body;
	private Boolean correct;		
	
	
	public AnswerDTO() {}
	
	public AnswerDTO(Answer a) {
		this.id = a.getId();
		this.body = a.getBody();
		this.correct = a.getCorrect();
	}
	
	
	public static List<AnswerDTO> listToListDTO(List<Answer> answers){
		List<AnswerDTO> answersDTO = new ArrayList<>();
		for(Answer a: answers) {
			answersDTO.add(new AnswerDTO(a));
		}
		return answersDTO;
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

	public Boolean getCorrect() {
		return correct;
	}

	public void setCorrect(Boolean correct) {
		this.correct = correct;
	}
}
