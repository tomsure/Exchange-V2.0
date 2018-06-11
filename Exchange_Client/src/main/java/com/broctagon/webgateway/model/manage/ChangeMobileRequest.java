package com.broctagon.webgateway.model.manage;

public class ChangeMobileRequest {
	
	private int Tag;
	
	private String RequestID;
	
	private int UserID;
	
	private String Email;
	
	private String MobilePhone;
	
	private String SmsCode;

	public ChangeMobileRequest(int tag, String requestId, int userId, String email, String mobilePhone, String smsCode) {
		this.Tag = tag;
		this.RequestID = requestId;
		this.UserID = userId;
		this.Email = email;
		this.MobilePhone = mobilePhone;
		this.SmsCode = smsCode;
	}
	
	public int getTag() {
		return Tag;
	}

	public void setTag(int tag) {
		Tag = tag;
	}

	public String getRequestID() {
		return RequestID;
	}

	public void setRequestID(String requestID) {
		RequestID = requestID;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getMobilePhone() {
		return MobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		MobilePhone = mobilePhone;
	}

	public String getSmsCode() {
		return SmsCode;
	}

	public void setSmsCode(String smsCode) {
		SmsCode = smsCode;
	}
	
	

}
