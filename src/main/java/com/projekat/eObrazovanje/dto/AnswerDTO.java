package com.projekat.eObrazovanje.dto;

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
