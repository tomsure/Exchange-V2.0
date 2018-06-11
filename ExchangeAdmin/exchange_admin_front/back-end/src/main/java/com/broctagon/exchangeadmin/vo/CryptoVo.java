package com.broctagon.exchangeadmin.vo;

import javax.persistence.Column;

public class CryptoVo {

	@Column(name="ID")
	private int id;
	
	@Column(name="CoinName")
	private String coinName;
	
	@Column(name="ChainName")
	private String chainType;
	
	@Column(name="WithdrawFee")
	private double withdrawalFee;
	
	@Column(name="Status")
	private int status;
	
	@Column(name="IsBaseCoin")
	private int baseCoin;
	
	@Column(name="Describe")
	private String descrip;
	
	public CryptoVo(int id, String coinName, String chainType, double withdrawalFee, int status, int baseCoin,
			String descrip) {
		super();
		this.id = id;
		this.coinName = coinName;
		this.chainType = chainType;
		this.withdrawalFee = withdrawalFee;
		this.status = status;
		this.baseCoin = baseCoin;
		this.descrip = descrip;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public String getChainType() {
		return chainType;
	}

	public void setChainType(String chainType) {
		this.chainType = chainType;
	}

	public double getWithdrawalFee() {
		return withdrawalFee;
	}

	public void setWithdrawalFee(double withdrawalFee) {
		this.withdrawalFee = withdrawalFee;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getBaseCoin() {
		return baseCoin;
	}

	public void setBaseCoin(int baseCoin) {
		this.baseCoin = baseCoin;
	}

	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	
}
