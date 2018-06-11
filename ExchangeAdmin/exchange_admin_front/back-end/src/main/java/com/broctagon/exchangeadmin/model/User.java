package com.broctagon.exchangeadmin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {

	@Id
	@Column(name="ID")
	private int id;
	
	@Column(name="UserName")
	private String userName;
	
	@Column(name="loginPassword")
	private String loginPassword;
	
	@Column(name="MobilePhoneNum")
	private String mobile;
	
	@Column(name="Status")
	private int status;
	
	@Column(name="IsKYC")
	private int isKyc;
	
	@Column(name="IsKYCLock")
	private int isKycLocked;
	
	@Column(name="LastLoginTime")
	private String lastLoginTime;

	public User() {
		
	}
	
	public User(int id, String userName, String loginPassword, String mobile, int status, int isKyc, int isKycLocked,
			String lastLoginTime) {
		super();
		this.id = id;
		this.userName = userName;
		this.loginPassword = loginPassword;
		this.mobile = mobile;
		this.status = status;
		this.isKyc = isKyc;
		this.isKycLocked = isKycLocked;
		this.lastLoginTime = lastLoginTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
	
}
