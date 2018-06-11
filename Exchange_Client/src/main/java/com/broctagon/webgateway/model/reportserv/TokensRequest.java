package com.broctagon.webgateway.model.reportserv;

public class TokensRequest {

	private int WSID;
	
	private String SessionID;
	
	private String RequestID;
	
	private int Tag = com.broctagon.webgateway.constant.Tag.COIN_LIST_REQUEST;

	public TokensRequest(int WSID, String SessionID, String RequestID) {
		this.WSID = WSID;
		this.SessionID = SessionID;
		this.RequestID = RequestID;
	}

	public int getWSID() {
		return WSID;
	}

	public void setWSID(int wSID) {
		WSID = wSID;
	}

	public String getSessionID() {
		return SessionID;
	}

	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}

	public String getRequestID() {
		return RequestID;
	}

	public void setRequestID(String requestID) {
		RequestID = requestID;
	}

	public int getTag() {
		return Tag;
	}

	public void setTag(int tag) {
		Tag = tag;
	}

}
