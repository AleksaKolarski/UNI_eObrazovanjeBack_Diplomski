package com.projekat.eObrazovanje.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.projekat.eObrazovanje.model.Log;

public class LogDTO {
	
	private Integer id;
	private String type;
	private Integer before;
	private Integer after;
	private Date time;
	
	
	public LogDTO() {}
	
	public LogDTO(Log log) {
		this.id = log.getId();
		this.type = log.getType();
		this.before = log.getBefore();
		this.after = log.getAfter();
		this.time = log.getTime();
	}
	
	
	public static List<LogDTO> listToListDTO(List<Log> logs){
		List<LogDTO> logsDTO = new ArrayList<LogDTO>();
		for(Log log: logs) {
			logsDTO.add(new LogDTO(log));
		}
		return logsDTO;
	}

	
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
}
