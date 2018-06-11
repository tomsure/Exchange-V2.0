package com.broctagon.webgateway.model.reportserv;

public class PendingOrdersByTokenRequest {

	private int WSID;
	
	private String SessionID;
	
	private String RequestID;
	
	private int Tag = com.broctagon.webgateway.constant.Tag.PENDING_ORDER_REQUEST;
	
	private String Symbol;

	public PendingOrdersByTokenRequest(int wsId, String sessionId, String requestId, String symbol) {
		this.WSID = wsId;
		this.SessionID = sessionId;
		this.RequestID = requestId;
		this.Symbol = symbol;
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
	
}
