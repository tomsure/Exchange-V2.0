package com.broctagon.webgateway.model.reportserv;

public class AssetsByTokenRequest {

	private int Tag = com.broctagon.webgateway.constant.Tag.USER_ASSETS_BY_ONE_TOKEN_REQUEST;
	
	private int WSID;
	
	private String RequestID;
	
	private String SessionID;
	
	private int UserID;
	
	private String BaseCoin;
	
	private String TradeCoin;

	public int getTag() {
		return Tag;
	}

	public void setTag(int tag) {
		Tag = tag;
	}

	public int getWSID() {
		return WSID;
	}

	public void setWSID(int wSID) {
		WSID = wSID;
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

	public String getBaseCoin() {
		return BaseCoin;
	}

	public void setBaseCoin(String baseCoin) {
		BaseCoin = baseCoin;
	}

	public String getTradeCoin() {
		return TradeCoin;
	}

	public void setTradeCoin(String tradeCoin) {
		TradeCoin = tradeCoin;
	}

}
