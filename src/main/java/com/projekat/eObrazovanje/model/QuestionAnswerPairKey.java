package com.projekat.eObrazovanje.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class QuestionAnswerPairKey implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "result_id")
    Integer resultId;
 
    @Column(name = "answer_id")
    Integer answerId;
    
    @Column(name = "question_id")
    Integer questionId;

    
	public QuestionAnswerPairKey() {}

	
	public Integer getResultId() {
		return resultId;
	}

	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}

	public Integer getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answerId == null) ? 0 : answerId.hashCode());
		result = prime * result + ((questionId == null) ? 0 : questionId.hashCode());
		result = prime * result + ((resultId == null) ? 0 : resultId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestionAnswerPairKey other = (QuestionAnswerPairKey) obj;
		if (answerId == null) {
			if (other.answerId != null)
				return false;
		} else if (!answerId.equals(other.answerId))
			return false;
		if (questionId == null) {
			if (other.questionId != null)
				return false;
		} else if (!questionId.equals(other.questionId))
			return false;
		if (resultId == null) {
			if (other.resultId != null)
				return false;
		} else if (!resultId.equals(other.resultId))
			return false;
		return true;
	}
}
