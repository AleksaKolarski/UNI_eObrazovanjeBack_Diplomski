package com.projekat.eObrazovanje.model;

public class Eye {
	
	private Boolean connectionActive;
	private Double fpogx;
	private Double fpogy;
	
	
	public Eye() {}
	
	public Eye(Boolean connectionActive, Double fpogx, Double fpogy) {
		this.connectionActive = connectionActive;
		this.fpogx = fpogx;
		this.fpogy = fpogy;
	}


	public Boolean getConnectionActive() {
		return connectionActive;
	}

	public void setConnectionActive(Boolean connectionActive) {
		this.connectionActive = connectionActive;
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
