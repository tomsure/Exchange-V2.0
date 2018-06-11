package com.broctagon.webgateway.model.market;

public class MarketInfoBo {

	private String BaseCoin;
	
	private String Symbol;
	
	private int SymbolID;
	
	private double UPDown;

	public MarketInfoBo(String symbol, double uPDown) {
		this.Symbol = symbol;
		this.UPDown = uPDown;
	}
	
	public String getBaseCoin() {
		return BaseCoin;
	}

	public void setBaseCoin(String baseCoin) {
		BaseCoin = baseCoin;
	}

	public String getSymbol() {
		return Symbol;
	}

	public void setSymbol(String symbol) {
		Symbol = symbol;
	}

	public int getSymbolID() {
		return SymbolID;
	}

	public void setSymbolID(int symbolID) {
		SymbolID = symbolID;
	}

	public double getUPDown() {
		return UPDown;
	}

	public void setUPDown(double uPDown) {
		UPDown = uPDown;
	}
	
	
}
