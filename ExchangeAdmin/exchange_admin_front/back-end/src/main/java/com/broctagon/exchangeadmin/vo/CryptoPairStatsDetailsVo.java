package com.broctagon.exchangeadmin.vo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CryptoPairStatsDetailsVo {

	@Id
	@Column(name="id")
	private BigDecimal orderId;
	
	@Column(name="userName")
	private String user;
	
	@Column(name="type")
	private int type;
	
	@Column(name="price")
	private BigDecimal price;
	
	@Column(name="Amount")
	private BigDecimal qty;
	
	@Column(name="TradeAmount")
	private BigDecimal amount;
	
	@Column(name="LastTraderTime")
	private BigDecimal time;

	public CryptoPairStatsDetailsVo() {
		
	}
	
	public CryptoPairStatsDetailsVo(BigDecimal orderId, String user, int type, BigDecimal price, BigDecimal qty,
			BigDecimal amount, BigDecimal time) {
		super();
		this.orderId = orderId;
		this.user = user;
		this.type = type;
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
