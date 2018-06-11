package com.broctagon.webgateway.model.util;

import com.broctagon.webgateway.constant.Tag;

public class EmailSendRequest {
	
	private int tag = Tag.SEND_EMAIL_REQUEST;
	
	private String dest;

	private String code;
	
	private String requestID;
	
	private String sessionID;
	
	private int wSID;

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public int getwSID() {
		return wSID;
	}

	public void setwSID(int wSID) {
		this.wSID = wSID;
	}
	
}
