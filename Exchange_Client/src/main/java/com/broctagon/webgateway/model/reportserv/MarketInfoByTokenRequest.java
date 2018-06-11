package com.broctagon.webgateway.model.reportserv;

public class MarketInfoByTokenRequest {

	private int WSID;
	
	private String SessionID;
	
	private String RequestID;
	
	private int Tag = com.broctagon.webgateway.constant.Tag.COIN_MARKET_REQUEST;
	
	private String Symbol;

	public MarketInfoByTokenRequest() {
		
	}
	
	public MarketInfoByTokenRequest(int WSID, String SessionID, String RequestID, String Symbol) {
		this.WSID = WSID;
		this.SessionID = SessionID;
		this.RequestID = RequestID;
		this.Symbol = Symbol;
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

	public void setTag(int Tag) {
		this.Tag = Tag;
	}

	public String getSymbol() {
		return Symbol;
	}

	public void setSymbol(String symbol) {
		Symbol = symbol;
	}
	
}
