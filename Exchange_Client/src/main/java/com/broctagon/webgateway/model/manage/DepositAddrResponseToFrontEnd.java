package com.broctagon.webgateway.model.manage;

public class DepositAddrResponseToFrontEnd extends DepositAddrResponse{

	private String imgAddr;

	public DepositAddrResponseToFrontEnd(DepositAddrResponse depositAddrResponse) {
		this.Tag = depositAddrResponse.getTag();
		this.UserID = depositAddrResponse.getUserID();
		this.SessionID = depositAddrResponse.getSessionID();
		this.DepsositAddr = depositAddrResponse.getDepsositAddr();
	}
	
	public String getImgAddr() {
		return imgAddr;
	}

	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}
	
	
}
