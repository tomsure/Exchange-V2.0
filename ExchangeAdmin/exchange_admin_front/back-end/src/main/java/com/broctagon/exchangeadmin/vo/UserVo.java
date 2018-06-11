package com.broctagon.exchangeadmin.vo;

public class UserVo {

	private int userId;
	
	private String userName;
	
	private String mobile;
	
	private String idType;
	
	private String idNo;
	
	private int isKyc;
	
	private int isKycLocked;

	private String lastLoginTime;
	
	private int status;
	
	public UserVo() {
		
	}

	public UserVo(int userId, String userName, String mobile, int isKyc, int isKycLocked, String lastLoginTime, int status) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.mobile = mobile;
		this.isKyc = isKyc;
		this.isKycLocked = isKycLocked;
		this.lastLoginTime = lastLoginTime;
		this.status = status;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public int getIsKyc() {
		return isKyc;
	}

	public void setIsKyc(int isKyc) {
		this.isKyc = isKyc;
	}

	public int getIsKycLocked() {
		return isKycLocked;
	}

	public void setIsKycLocked(int isKycLocked) {
		this.isKycLocked = isKycLocked;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
