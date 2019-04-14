package com.projekat.eObrazovanje.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Result {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "name", unique = false, nullable = false, length = 30)
	private String name;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "result")
	private List<QuestionAnswerPair> answers;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "result")
	private List<Log> log;
	
	
	public Result() {}

	
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

	public List<QuestionAnswerPair> getAnswers() {
		return answers;
	}

	public void setAnswers(List<QuestionAnswerPair> answers) {
		this.answers = answers;
	}

	public List<Log> getLog() {
		return log;
	}

	public void setLog(List<Log> log) {
		this.log = log;
	}
}
