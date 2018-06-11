package com.broctagon.exchangeadmin.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AdminVo {

	@Id
	private int id;
	
	@Column(name="username")
	private String userName;
	
	private String mobile;
	
	private String roleName;
	
	private int status;
	
	private String lastLoginTime;
	
	
	public AdminVo() {
		
	}
	
	
	public AdminVo(int id, String userName, String mobile, String roleName, int status, String lastLoginTime) {
		super();
		this.id = id;
		this.userName = userName;
		this.mobile = mobile;
		this.roleName = roleName;
		this.status = status;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
}
