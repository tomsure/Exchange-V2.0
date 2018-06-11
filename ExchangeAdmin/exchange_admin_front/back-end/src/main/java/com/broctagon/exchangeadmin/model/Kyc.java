package com.broctagon.exchangeadmin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_kyc")
public class Kyc implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="userid")
	private int userId;
	
	@Column(name="country")
	private String country;
	
	@Column(name="firstname")
	private String firstName;
	
	@Column(name="secondname")
	private String secondName;
	
	@Column(name="idtype")
	private String idType;
	
	@Column(name="idno")
	private String idNo;
	
	@Column(name="image1")
	private String image1;
	
	@Column(name="image2")
	private String image2;
	
	@Column(name="image3")
	private String image3;
	
	public Kyc() {
		
	}

	public Kyc(int id, int userId, String country, String firstName, String secondName, String idType, String idNo,
			String image1, String image2, String image3) {
		super();
		this.id = id;
		this.userId = userId;
		this.country = country;
		this.firstName = firstName;
		this.secondName = secondName;
		this.idType = idType;
		this.idNo = idNo;
		this.image1 = image1;
		this.image2 = image2;
		this.image3 = image3;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
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

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}
	
}
