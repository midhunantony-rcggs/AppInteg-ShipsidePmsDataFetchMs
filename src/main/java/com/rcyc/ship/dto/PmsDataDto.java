package com.rcyc.ship.dto;

import java.util.Date;

public class PmsDataDto implements java.io.Serializable{
	
	private int id;

	private String data;

	private Date updateddate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Date getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(Date updateddate) {
		this.updateddate = updateddate;
	}

}
