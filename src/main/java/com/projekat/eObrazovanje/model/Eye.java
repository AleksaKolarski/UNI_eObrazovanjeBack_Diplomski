package com.projekat.eObrazovanje.model;

public class Eye {
	
	private Double fpogx;
	private Double fpogy;
	
	
	public Eye() {}
	
	public Eye(Double fpogx, Double fpogy) {
		this.fpogx = fpogx;
		this.fpogy = fpogy;
	}


	public Double getFpogx() {
		return fpogx;
	}

	public void setFpogx(Double fpogx) {
		this.fpogx = fpogx;
	}

	public Double getFpogy() {
		return fpogy;
	}

	public void setFpogy(Double fpogy) {
		this.fpogy = fpogy;
	}
}
