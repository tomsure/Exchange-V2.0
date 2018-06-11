package com.broctagon.exchangeadmin.vo;

import java.math.BigDecimal;

public class CryptoPairStatsVo {
	
	private int cryptoPairId;
	
	private String cryptoPair;
	
	private BigDecimal high;
	
	private BigDecimal low;
	
	private BigDecimal qty;
	
	private BigDecimal amount;
	
	private BigDecimal profit;

	public int getCryptoPairId() {
		return cryptoPairId;
	}

	public void setCryptoPairId(int cryptoPairId) {
		this.cryptoPairId = cryptoPairId;
	}

	public String getCryptoPair() {
		return cryptoPair;
	}

	public void setCryptoPair(String cryptoPair) {
		this.cryptoPair = cryptoPair;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
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

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}
	
	
	
}
