package com.broctagon.webgateway.model.manage;

public class DepositAddrResponse {

	protected int Tag;
	
	protected int UserID;
	
	protected String SessionID;
	
	protected String DepsositAddr;

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

	public String getSessionID() {
		return SessionID;
	}

	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}

	public String getDepsositAddr() {
		return DepsositAddr;
	}

	public void setDepsositAddr(String depsositAddr) {
		DepsositAddr = depsositAddr;
	}
}
