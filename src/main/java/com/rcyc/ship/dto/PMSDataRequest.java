package com.rcyc.ship.dto;

public class PMSDataRequest {

	private String data;
	private String dataType;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public PMSDataRequest(String data, String dataType) {
		super();
		this.data = data;
		this.dataType = dataType;
	}

}
