package com.projekat.eObrazovanje.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class QuestionAnswerPair {
	
	@EmbeddedId
	QuestionAnswerPairKey id;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @MapsId("id")
    @JoinColumn(name = "result_id")
    Result result;
 
	@ManyToOne(fetch = FetchType.EAGER)
    @MapsId("id")
    @JoinColumn(name = "answer_id")
    Answer answer;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @MapsId("id")
    @JoinColumn(name = "question_id")
    Question question;
	
	
	public QuestionAnswerPair() {}
	
	
	public QuestionAnswerPairKey getId() {
		return id;
	}

	public void setId(QuestionAnswerPairKey id) {
		this.id = id;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}
