package com.broctagon.webgateway.model.manage;

public class RegisterRequest {

	private int Tag;
	
	private String Email;
	
	private String Password;
	
	private String RequestID;
	
	public RegisterRequest(int tag, String email, String password, String requestID) {
		this.Tag = tag;
		this.Email = email;
		this.Password = password;
		this.RequestID = requestID;
	}

	public int getTag() {
		return Tag;
	}

	public void setTag(int tag) {
		Tag = tag;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		this.Password = password;
	}

	public String getRequestID() {
		return RequestID;
	}

	public void setRequestID(String requestID) {
		RequestID = requestID;
	}

}
