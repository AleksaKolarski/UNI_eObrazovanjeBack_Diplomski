package com.projekat.eObrazovanje.dto;

import java.util.ArrayList;
import java.util.List;

import com.projekat.eObrazovanje.model.Result;

public class ResultDTO {
	
	private Integer id;
	private String name;
	private List<QuestionAnswerPairDTO> answers;
	private List<LogDTO> log;
	
	
	public ResultDTO() {}
	
	public ResultDTO(Result result) {
		this.id = result.getId();
		this.name = result.getName();
		this.answers = QuestionAnswerPairDTO.listToListDTO(result.getAnswers());
		this.log = LogDTO.listToListDTO(result.getLog());
	}
	
	
	public static List<ResultDTO> listToListDTO(List<Result> results) {
		List<ResultDTO> resultsDTO = new ArrayList<ResultDTO>();
		for(Result result: results) {
			resultsDTO.add(new ResultDTO(result));
		}
		return resultsDTO;
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
