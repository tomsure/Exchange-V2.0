package com.broctagon.webgateway.model.c2c;

import com.broctagon.webgateway.constant.Tag;

public class C2cCoinsRequest {

	private String wsId;
	
	private String sessionId;
	
	private String requestId;
	
	private int tag = Tag.C2C_COIN_LIST_REQUEST;

	public C2cCoinsRequest(String wsId, String sessionId, String requestId) {
		this.wsId = wsId;
		this.sessionId = sessionId;
		this.requestId = requestId;
	}
	
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
	
}
