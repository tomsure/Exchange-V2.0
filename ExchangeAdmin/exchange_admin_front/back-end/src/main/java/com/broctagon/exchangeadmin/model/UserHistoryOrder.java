package com.broctagon.exchangeadmin.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.broctagon.exchangeadmin.model.User;

@Entity
@Table(name="token_order")
public class UserHistoryOrder {

	@Id
	@Column(name="ID")
	private BigDecimal orderId;
	
	@ManyToOne
	@JoinColumn(name="UserID")
	private User user;
	
	@Column(name="Symbol")
	private String cryptoPair;
	
	@Column(name="Type")
	private int type;
	
	@Column(name="Status")
	private String status;
	
	@Column(name="Price")
	private BigDecimal price;
	
	@Column(name="Amount")
	private BigDecimal qty;
	
	@Column(name="TradeAmount")
	private BigDecimal amount;
	
	@Column(name="LastTraderTime")
	private BigDecimal time;

	public BigDecimal getOrderId() {
		return orderId;
	}

	public void setOrderId(BigDecimal orderId) {
		this.orderId = orderId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
