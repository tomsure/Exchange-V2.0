package com.broctagon.webgateway.model.manage;

public class KycRequest {

	private int Tag =  com.broctagon.webgateway.constant.Tag.KYC_REQUEST;
	
	private String RequestID;
	
	private int UserID;
	
	private String country;
	
	private String firstName;
	
	private String lastName;
	
	private String idType;
	
	private String idNumber;
	
	private String img1;
	
	private String img2;
	
	private String img3;
	
	public KycRequest(Identity1Request identity1Request) {
		this.UserID = identity1Request.getUserID();
		this.country = identity1Request.getCountry();
		this.firstName = identity1Request.getFirstName();
		this.lastName = identity1Request.getLastName();
		this.idType = identity1Request.getIdType();
		this.idNumber = identity1Request.getIdNumber();
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public String getImg3() {
		return img3;
	}

	public void setImg3(String img3) {
		this.img3 = img3;
	}
	
}
