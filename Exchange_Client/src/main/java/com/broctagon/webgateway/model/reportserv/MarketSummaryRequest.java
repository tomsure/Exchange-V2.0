package com.broctagon.webgateway.model.reportserv;

public class MarketSummaryRequest {

	private int Tag = com.broctagon.webgateway.constant.Tag.COIN_MARKET_SUMMARY_REQUEST;
	
	private String RequestID;
	
	private String SessionID;
	
	private String Symbol;
	
	private int WSID;
	
	public MarketSummaryRequest() {
		
	}
	
	public MarketSummaryRequest(String RequestID, String SessionID, String Symbol, int WSID) {
		this.RequestID = RequestID;
		this.SessionID = SessionID;
		this.Symbol = Symbol;
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

	public String getSymbol() {
		return Symbol;
	}

	public void setSymbol(String symbol) {
		Symbol = symbol;
	}

	public int getWSID() {
		return WSID;
	}

	public void setWSID(int wSID) {
		WSID = wSID;
	}
	
}
