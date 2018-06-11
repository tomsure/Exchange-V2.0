package com.broctagon.exchangeadmin.message.listrequest;

public class CryptoListRequest {

	private String coinName;
	
	private int chainId;
	
	private int status;
	

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public int getChainId() {
		return chainId;
	}

	public void setChainId(int chainId) {
		this.chainId = chainId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
