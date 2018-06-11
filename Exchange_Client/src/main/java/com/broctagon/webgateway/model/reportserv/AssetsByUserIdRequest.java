package com.broctagon.webgateway.model.reportserv;

public class AssetsByUserIdRequest {
	
	private int Tag = com.broctagon.webgateway.constant.Tag.USER_ASSERTS_REQUEST;
	
	private String RequestID;
	
	private String SessionID;
	
	private int UserID;
	
	private int WSID;

	public AssetsByUserIdRequest() {
		
	}
	
	public AssetsByUserIdRequest(String RequestID, String SessionID, int UserID, int WSID) {
		this.RequestID = RequestID;
		this.SessionID = SessionID;
		this.UserID = UserID;
		this.WSID = WSID;
	}
	
	public int getTag() {
		return Tag;
	}

	public void setTag(int tag) {
		Tag = tag;
	}

	public String getRequestID() {
		return RequestID;
	}

	public void setRequestID(String requestID) {
		RequestID = requestID;
	}

	public String getSessionID() {
		return SessionID;
	}

	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public int getWSID() {
		return WSID;
	}

	public void setWSID(int wSID) {
		WSID = wSID;
	}
	
}
