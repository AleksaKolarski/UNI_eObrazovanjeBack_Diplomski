package com.projekat.eObrazovanje.dto;

import java.util.ArrayList;
import java.util.List;

import com.projekat.eObrazovanje.model.Result;

public class ResultNameDTO {
	
	private Integer id;
	private String name;
	
	public ResultNameDTO() {}
	
	public ResultNameDTO(Result result) {
		this.id = result.getId();
		this.name = result.getName();
	}
	
	
	public static List<ResultNameDTO> listToListDTO(List<Result> results){
		List<ResultNameDTO> resultNamesDTO = new ArrayList<ResultNameDTO>();
		for(Result result: results) {
			resultNamesDTO.add(new ResultNameDTO(result));
		}
		return resultNamesDTO;
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
}
