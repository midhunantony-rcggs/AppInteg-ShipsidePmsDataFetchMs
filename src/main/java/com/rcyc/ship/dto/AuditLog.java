package com.rcyc.ship.dto;

import java.util.Date;


public class AuditLog {
	
	private Long ID;

    private String operationName;

    
    private String folioTx;
    
    private String bookingTx;

    private Integer userID;

    private String startTime;

    private String type;

    private String message;

    private String source;
    
    private String msgIdentifier;

	public AuditLog() {
		
	}

	public AuditLog(String operationName, String folioTx, String bookingTx, Integer userID, String date,
			String type, String message, String source, String msgIdentifier) {
		this.operationName = operationName;
		this.folioTx = folioTx;
		this.bookingTx = bookingTx;
		this.userID = userID;
		this.startTime = date;
		this.type = type;
		this.message = message;
		this.source = source;
		this.msgIdentifier = msgIdentifier;
	}



//	public AuditLog( String operationName, String businessTxNumber, Integer userID, String startTime, String type, String message, String source) {
//
//        this.operationName = operationName;
//        this.userID = userID;
//        this.startTime = startTime;
//        this.type = type;
//        this.message = message;
//        this.source = source;
//    }

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getFolioTx() {
		return folioTx;
	}

	public void setFolioTx(String folioTx) {
		this.folioTx = folioTx;
	}

	public String getBookingTx() {
		return bookingTx;
	}

	public void setBookingTx(String bookingTx) {
		this.bookingTx = bookingTx;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}


	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMsgIdentifier() {
		return msgIdentifier;
	}

	public void setMsgIdentifier(String msgIdentifier) {
		this.msgIdentifier = msgIdentifier;
	}

	
	

}
