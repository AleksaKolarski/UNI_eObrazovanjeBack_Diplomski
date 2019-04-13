package com.projekat.eObrazovanje.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class QuestionAnswerPair {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "question_id", referencedColumnName = "id", unique = false, nullable = false)
	private Question question;
	
	@ManyToOne
	@JoinColumn(name = "answer_id", referencedColumnName = "id", unique = false, nullable = false)
	private Answer answer;
	
	@ManyToOne
	@JoinColumn(name = "result_id", referencedColumnName = "id", unique = false, nullable = false)
	private Result result;
	
	
	public QuestionAnswerPair() {}


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
