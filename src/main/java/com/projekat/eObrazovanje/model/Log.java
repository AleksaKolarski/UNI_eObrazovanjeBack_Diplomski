package com.projekat.eObrazovanje.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Log {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "type", unique = false, nullable = false, length = 10)
	private String type;
	
	@Column(name = "before1", unique = false, nullable = false)
	private Integer before;
	
	@Column(name = "after1", unique = false, nullable = false)
	private Integer after;
	
	@Column(name = "time", nullable = false, columnDefinition = "DATETIME(3)")
	private Date time;
	
	@ManyToOne
	@JoinColumn(name = "result_id", referencedColumnName = "id", unique = false, nullable = false)
	private Result result;
	
	
	public Log() {}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getBefore() {
		return before;
	}

	public void setBefore(Integer before) {
		this.before = before;
	}

	public Integer getAfter() {
		return after;
	}

	public void setAfter(Integer after) {
		this.after = after;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}
