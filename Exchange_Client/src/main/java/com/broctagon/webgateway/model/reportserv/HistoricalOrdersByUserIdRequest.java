package com.broctagon.webgateway.model.reportserv;

public class HistoricalOrdersByUserIdRequest {

	private int WSID;
	
	private String SessionID;
	
	private String RequestID;
	
	private int Tag = com.broctagon.webgateway.constant.Tag.USER_HISTORICAL_ORDERS_REQUEST;
	
	private int UserID;
	
	private String Symbol;
	
	private int OrderType;
	
	private long BeginTime;
	
	private long EndTime;

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

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public String getSymbol() {
		return Symbol;
	}

	public void setSymbol(String symbol) {
		Symbol = symbol;
	}

	public int getOrderType() {
		return OrderType;
	}

	public void setOrderType(int orderType) {
		OrderType = orderType;
	}

	public long getBeginTime() {
		return BeginTime;
	}

	public void setBeginTime(long beginTime) {
		BeginTime = beginTime;
	}

	public long getEndTime() {
		return EndTime;
	}

	public void setEndTime(long endTime) {
		EndTime = endTime;
	}

}
