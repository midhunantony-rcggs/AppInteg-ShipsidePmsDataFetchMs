package com.rcyc.ship.dto;

import org.apache.poi.ss.formula.functions.T;

public class StatusResponse {

	private String status;

	private String message;
	
	private int code;
	
	private T data;
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public StatusResponse(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public StatusResponse(String status, String message, int code, T data) {
		super();
		this.status = status;
		this.message = message;
		this.code = code;
		this.data = data;
	}
	
	
	
	

}
