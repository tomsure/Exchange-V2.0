package com.broctagon.exchangeadmin.message.listrequest;

public class CryptoPairListRequest {

	private int baseCoinId;
	
	private int tradeCoinId;
	
	private int status;

	public int getBaseCoinId() {
		return baseCoinId;
	}

	public void setBaseCoinId(int baseCoinId) {
		this.baseCoinId = baseCoinId;
	}

	public int getTradeCoinId() {
		return tradeCoinId;
	}

	public void setTradeCoinId(int tradeCoinId) {
		this.tradeCoinId = tradeCoinId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
