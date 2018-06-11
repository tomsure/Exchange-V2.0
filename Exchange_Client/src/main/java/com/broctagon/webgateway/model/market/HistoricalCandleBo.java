package com.broctagon.webgateway.model.market;

import java.util.ArrayList;
import java.util.List;

public class HistoricalCandleBo {

	private int Tag = com.broctagon.webgateway.constant.Tag.HISTORICAL_BARS_REQUEST;
	
	private String RequestID;
	
	private String SessionID;
	
	private String WSID;
	
	private List<String> MarketHistory = new ArrayList<>();

	public HistoricalCandleBo(String candle) {
		MarketHistory.add(candle);
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

	public String getWSID() {
		return WSID;
	}

	public void setWSID(String wSID) {
		WSID = wSID;
	}

	public List<String> getMarketHistory() {
		return MarketHistory;
	}

	public void setMarketHistory(List<String> marketHistory) {
		MarketHistory = marketHistory;
	}

}
