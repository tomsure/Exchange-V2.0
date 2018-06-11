package com.broctagon.webgateway.model.c2c;

import com.broctagon.webgateway.constant.Tag;

public class C2cPendingOrderByUserIdRequest {

	private String wsId;
	
	private String sessionId;
	
	private String requestId;
	
	private int tag = Tag.C2C_GET_USER_PENDING_ORDER_REQUEST;
	
	private int userId;
	
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	
}
