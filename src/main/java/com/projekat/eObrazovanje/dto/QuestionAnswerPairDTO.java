package com.projekat.eObrazovanje.dto;

public class QuestionAnswerPairDTO {
	private Integer questionId;
	private Integer answerId;
	
	
	public QuestionAnswerPairDTO() {}
	

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}
}
