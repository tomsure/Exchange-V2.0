package com.broctagon.webgateway.model.reportserv;

public class HistoricalBarRequest {

	private int WSID;
	
	private String SessionID;
	
	private String RequestID;
	
	private int Tag = com.broctagon.webgateway.constant.Tag.HISTORICAL_BARS_REQUEST;
	
	private String Symbol;
	
	private String Timeframe;

	public HistoricalBarRequest() {
		
	}
	
	public HistoricalBarRequest(int WSID, String SessionID, String RequestID, String Symbol, String Timeframe) {
		this.WSID = WSID;
		this.SessionID = SessionID;
		this.RequestID = RequestID;
		this.Symbol = Symbol;
		this.Timeframe = Timeframe;
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

	public String getSymbol() {
		return Symbol;
	}

	public void setSymbol(String symbol) {
		Symbol = symbol;
	}

	public String getTimeframe() {
		return Timeframe;
	}

	public void setTimeframe(String timeframe) {
		Timeframe = timeframe;
	}
	
}
