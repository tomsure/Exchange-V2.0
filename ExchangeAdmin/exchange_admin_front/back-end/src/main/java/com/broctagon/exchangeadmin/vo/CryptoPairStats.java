package com.broctagon.exchangeadmin.vo;

public class CryptoPairStats {

	private CryptoPairStatsDetailsVo current;
	
	private CryptoPairStatsDetailsVo open;
	
	private CryptoPairStatsDetailsVo high;
	
	private CryptoPairStatsDetailsVo low;

	public CryptoPairStatsDetailsVo getCurrent() {
		return current;
	}

	public void setCurrent(CryptoPairStatsDetailsVo current) {
		this.current = current;
	}

	public CryptoPairStatsDetailsVo getOpen() {
		return open;
	}

	public void setOpen(CryptoPairStatsDetailsVo open) {
		this.open = open;
	}

	public CryptoPairStatsDetailsVo getHigh() {
		return high;
	}

	public void setHigh(CryptoPairStatsDetailsVo high) {
		this.high = high;
	}

	public CryptoPairStatsDetailsVo getLow() {
		return low;
	}

	public void setLow(CryptoPairStatsDetailsVo low) {
		this.low = low;
	}
	
}
