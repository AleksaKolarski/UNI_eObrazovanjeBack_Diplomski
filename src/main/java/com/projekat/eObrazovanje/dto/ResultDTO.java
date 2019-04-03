package com.projekat.eObrazovanje.dto;

import java.util.List;

public class ResultDTO {
	
	private List<QuestionAnswerPairDTO> answers;
	private List<LogDTO> log;
	
	public ResultDTO() {}
	
	
	public List<QuestionAnswerPairDTO> getAnswers() {
		return answers;
	}

	public void setAnswers(List<QuestionAnswerPairDTO> answers) {
		this.answers = answers;
	}

	public List<LogDTO> getLog() {
		return log;
	}

	public void setLog(List<LogDTO> log) {
		this.log = log;
	}
}
