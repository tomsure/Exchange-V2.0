package com.broctagon.exchangeadmin.message.listrequest;

public class CryptoPairStatsListRequest {

	private int cryptoPairId;
	
	private String high;
	
	private String low;
	
	private String qty;
	
	private String amount;
	
	private String peroid;

	public int getCryptoPairId() {
		return cryptoPairId;
	}

	public void setCryptoPairId(int cryptoPairId) {
		this.cryptoPairId = cryptoPairId;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPeroid() {
		return peroid;
	}

	public void setPeroid(String peroid) {
		this.peroid = peroid;
	}
	
	
}
