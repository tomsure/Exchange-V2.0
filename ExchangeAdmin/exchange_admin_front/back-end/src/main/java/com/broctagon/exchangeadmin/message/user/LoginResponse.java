package com.broctagon.exchangeadmin.message.user;

import com.broctagon.exchangeadmin.message.BaseResponse;

public class LoginResponse extends BaseResponse{

	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
