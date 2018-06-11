package com.broctagon.exchangeadmin.message;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class EnableRequest {

	private List<Integer> ids;
	
	@ApiModelProperty(notes = "1 : enabled, 0 : disabled")
	private int status;

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
