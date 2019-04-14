package com.projekat.eObrazovanje.dto;

import java.util.ArrayList;
import java.util.List;

import com.projekat.eObrazovanje.model.QuestionAnswerPair;

public class QuestionAnswerPairDTO {
	
	private Integer questionId;
	private Integer answerId;
	
	
	public QuestionAnswerPairDTO() {}
	
	public QuestionAnswerPairDTO(QuestionAnswerPair questionAnswerPair) {
		this.questionId = questionAnswerPair.getQuestion().getId();
		this.answerId = questionAnswerPair.getAnswer().getId();
	}

	
	public static List<QuestionAnswerPairDTO> listToListDTO(List<QuestionAnswerPair> questionAnswerPairs){
		List<QuestionAnswerPairDTO> questionAnswerPairsDTO = new ArrayList<QuestionAnswerPairDTO>();
		for(QuestionAnswerPair questionAnswerPair: questionAnswerPairs) {
			questionAnswerPairsDTO.add(new QuestionAnswerPairDTO(questionAnswerPair));
		}
		return questionAnswerPairsDTO;
	}
	

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
