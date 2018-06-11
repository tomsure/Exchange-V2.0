package com.broctagon.exchangeadmin.vo;

import java.math.BigDecimal;

public class HistoryOrderVo {

	private BigDecimal orderId;
	
	private String userName;
	
	private String cryptoPair;
	
	private int type;
	
	private String status;
	
	private BigDecimal price;
	
	private BigDecimal qty;
	
	private BigDecimal amount;
	
	private BigDecimal time;

	public HistoryOrderVo(BigDecimal orderId, String userName, String cryptoPair, int type, String status,
			BigDecimal price, BigDecimal qty, BigDecimal amount, BigDecimal time) {
		super();
		this.orderId = orderId;
		this.userName = userName;
		this.cryptoPair = cryptoPair;
		this.type = type;
		this.status = status;
		this.price = price;
		this.qty = qty;
		this.amount = amount;
		this.time = time;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getTime() {
		return time;
	}

	public void setTime(BigDecimal time) {
		this.time = time;
	}
	
}
