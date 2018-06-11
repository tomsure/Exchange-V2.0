package com.broctagon.webgateway.model.market;

import java.util.ArrayList;
import java.util.List;

public class DayMarketBo {

	private int Tag = com.broctagon.webgateway.constant.Tag.COIN_MARKET_SUMMARY_REQUEST;
	
	private String RequestID;
	
	private String SessionID;
	
	private int WSID;
	
	private List<MarketInfoBo> SymbolList = new ArrayList<>();

	public DayMarketBo(MarketInfoBo marketInfo) {
		SymbolList.add(marketInfo);
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

	public int getWSID() {
		return WSID;
	}

	public void setWSID(int wSID) {
		WSID = wSID;
	}

	public List<MarketInfoBo> getSymbolList() {
		return SymbolList;
	}

	public void setSymbolList(List<MarketInfoBo> symbolList) {
		SymbolList = symbolList;
	}

}
