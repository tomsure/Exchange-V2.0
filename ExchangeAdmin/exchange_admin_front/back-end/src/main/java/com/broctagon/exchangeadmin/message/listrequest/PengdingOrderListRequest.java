package com.broctagon.exchangeadmin.message.listrequest;

import java.math.BigDecimal;

public class PengdingOrderListRequest {

	private BigDecimal orderId;
	
	private String userName;
	
	private String cryptoPair;
	
	private int type;

	public BigDecimal getOrderId() {
		return orderId;
	}

	public void setOrderId(BigDecimal orderId) {
		this.orderId = orderId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCryptoPair() {
		return cryptoPair;
	}

	public void setCryptoPair(String cryptoPair) {
		this.cryptoPair = cryptoPair;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
