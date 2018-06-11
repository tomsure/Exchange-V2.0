package com.broctagon.exchangeadmin.vo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;

import com.broctagon.exchangeadmin.model.HistoryOrderChild;

public class HistoryOrderDetailsVo {

	@Column(name="orderId")
	private BigDecimal orderId;
	
	@Column(name="userName")
	private String userName;
	
	@Column(name="price")
	private BigDecimal price;
	
	@Column(name="qty")
	private BigDecimal qty;
	
	@Column(name="amount")
	private BigDecimal amount;
	
	@Column(name="tradeTime")
	private String time;

	public HistoryOrderDetailsVo(HistoryOrderChild historyOrderChild){
		orderId = historyOrderChild.getTradeOrderId();
		price = historyOrderChild.getPrice();
		qty = historyOrderChild.getAmount().divide(historyOrderChild.getPrice(), 8, RoundingMode.HALF_EVEN);
		amount = historyOrderChild.getAmount();
		// TODO
//		time = historyOrderChild.getTradeTime();
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
