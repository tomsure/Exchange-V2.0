package com.broctagon.webgateway.model.c2c;

import com.broctagon.webgateway.constant.Tag;

public class C2cPendingOrderRequest {

	private String wsId;
	
	private String sessionId;
	
	private String requestId;
	
	private int tag = Tag.C2C_GET_OPEN_ORDER_REQUEST;
	
	private String coinName;
	
	
	public String getWsId() {
		return wsId;
	}

	public void setWsId(String wsId) {
		this.wsId = wsId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

}
