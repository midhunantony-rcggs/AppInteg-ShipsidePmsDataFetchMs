package com.rcyc.ship.dto;

public class PMSDataRequest {

	private String data;
	private String dataType;
	private String bookingTx;
	private String folioTx;

	
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

	public String getBookingTx() {
		return bookingTx;
	}

	public void setBookingTx(String bookingTx) {
		this.bookingTx = bookingTx;
	}

	public String getFolioTx() {
		return folioTx;
	}

	public void setFolioTx(String folioTx) {
		this.folioTx = folioTx;
	}

	public PMSDataRequest(String data, String dataType, String bookingTx, String folioTx) {
		super();
		this.data = data;
		this.dataType = dataType;
		this.bookingTx = bookingTx;
		this.folioTx = folioTx;
	}
	
	

}
