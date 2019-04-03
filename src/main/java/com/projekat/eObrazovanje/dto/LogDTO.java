package com.projekat.eObrazovanje.dto;

import java.util.Date;

public class LogDTO {
	private String type;
	private Integer before;
	private Integer after;
	private Date time;
	
	
	public LogDTO() {}

	
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
}
