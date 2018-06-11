package com.broctagon.exchangeadmin.vo;

public class KycVo {

	private int userId;
	
	private String userName;
	
	private String idType;
	
	private String idNo;

	public KycVo() {
		
	}
	
	public KycVo(int userId, String userName, String idType, String idNo) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.idType = idType;
		this.idNo = idNo;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
}
