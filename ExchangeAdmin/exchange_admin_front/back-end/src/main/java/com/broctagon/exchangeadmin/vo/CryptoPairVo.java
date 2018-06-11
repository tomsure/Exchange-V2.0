package com.broctagon.exchangeadmin.vo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;

public class CryptoPairVo {

	@Id
	@Column(name="ID")
	private int id;
	
	@Column(name="baseCoin")
	private String baseCoin;
	
	@Column(name="tradeCoin")
	private String tradeCoin;
	
	@Column(name="BuyFee")
	private BigDecimal buyFee;
	
	@Column(name="SellFee")
	private BigDecimal sellFee;
	
	@Column(name="TradeBeginTime")
	private String tradeBeginTime;
	
	@Column(name="TradeEndTime")
	private String tradeEndTime;
	
	@Column(name="MinBuyQty")
	private BigDecimal minBuyQty;
	
	@Column(name="MinSellQty")
	private BigDecimal minSellQty;
	
	@Column(name="MaxDepositQty")
	private BigDecimal maxDepositQty;

	@Column(name="Status")
	private int status;
	
	public CryptoPairVo(int id, String baseCoin, String tradeCoin, BigDecimal buyFee, BigDecimal sellFee,
			String tradeBeginTime, String tradeEndTime, BigDecimal minBuyQty, BigDecimal minSellQty,
			BigDecimal maxDepositQty) {
		super();
		this.id = id;
		this.baseCoin = baseCoin;
		this.tradeCoin = tradeCoin;
		this.buyFee = buyFee;
		this.sellFee = sellFee;
		this.tradeBeginTime = tradeBeginTime;
		this.tradeEndTime = tradeEndTime;
		this.minBuyQty = minBuyQty;
		this.minSellQty = minSellQty;
		this.maxDepositQty = maxDepositQty;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBaseCoin() {
		return baseCoin;
	}

	public void setBaseCoin(String baseCoin) {
		this.baseCoin = baseCoin;
	}

	public String getTradeCoin() {
		return tradeCoin;
	}

	public void setTradeCoin(String tradeCoin) {
		this.tradeCoin = tradeCoin;
	}

	public BigDecimal getBuyFee() {
		return buyFee;
	}

	public void setBuyFee(BigDecimal buyFee) {
		this.buyFee = buyFee;
	}

	public BigDecimal getSellFee() {
		return sellFee;
	}

	public void setSellFee(BigDecimal sellFee) {
		this.sellFee = sellFee;
	}

	public String getTradeBeginTime() {
		return tradeBeginTime;
	}

	public void setTradeBeginTime(String tradeBeginTime) {
		this.tradeBeginTime = tradeBeginTime;
	}

	public String getTradeEndTime() {
		return tradeEndTime;
	}

	public void setTradeEndTime(String tradeEndTime) {
		this.tradeEndTime = tradeEndTime;
	}

	public BigDecimal getMinBuyQty() {
		return minBuyQty;
	}

	public void setMinBuyQty(BigDecimal minBuyQty) {
		this.minBuyQty = minBuyQty;
	}

	public BigDecimal getMinSellQty() {
		return minSellQty;
	}

	public void setMinSellQty(BigDecimal minSellQty) {
		this.minSellQty = minSellQty;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public BigDecimal getMaxDepositQty() {
		return maxDepositQty;
	}

	public void setMaxDepositQty(BigDecimal maxDepositQty) {
		this.maxDepositQty = maxDepositQty;
	}
	
}
