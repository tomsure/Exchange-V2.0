package com.broctagon.exchangeadmin.vo;

import java.math.BigDecimal;
import java.util.Map;

public class StatsVo {
	
	private String time;
	
	private Map<String, BigDecimal> stats;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Map<String, BigDecimal> getStats() {
		return stats;
	}

	public void setStats(Map<String, BigDecimal> stats) {
		this.stats = stats;
	}
	
}
