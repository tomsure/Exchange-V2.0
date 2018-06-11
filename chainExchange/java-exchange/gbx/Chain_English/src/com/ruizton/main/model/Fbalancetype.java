package com.ruizton.main.model;

// default package

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * Fbalancetype entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fbalancetype", catalog = "yunshu333_new_vcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fbalancetype implements java.io.Serializable {

	// Fields

	private Integer fid;
	private String fname;
	private String fname_en;
	private Double frate;
	private Integer fday;
	private Timestamp fcreatetime;
	private Fvirtualcointype fvirtualcointype;
//	private Integer frecType;
//	private String frecType_s;

	// Constructors


	/** default constructor */
	public Fbalancetype() {
	}

	/** full constructor */
	public Fbalancetype(String fname, Double frate, Timestamp fcreatetime) {
		this.fname = fname;
		this.frate = frate;
		this.fcreatetime = fcreatetime;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "native")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "fid", unique = true, nullable = false)
	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	@Column(name = "fname", length = 50)
	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@Column(name = "frate", precision = 16, scale = 6)
	public Double getFrate() {
		return this.frate;
	}

	public void setFrate(Double frate) {
		this.frate = frate;
	}

	@Column(name = "fcreatetime", length = 0)
	public Timestamp getFcreatetime() {
		return this.fcreatetime;
	}

	public void setFcreatetime(Timestamp fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	@Column(name = "fday")
	public Integer getFday() {
		return fday;
	}

	public void setFday(Integer fday) {
		this.fday = fday;
	}
	
	@Column(name = "fname_en")
	public String getFname_en() {
		return fname_en;
	}

	public void setFname_en(String fname_en) {
		this.fname_en = fname_en;
	}
//	
//	@Column(name = "frecType")
//	public Integer getFrecType() {
//		return frecType;
//	}
//
//	public void setFrecType(Integer frecType) {
//		this.frecType = frecType;
//	}
//
//	@Transient
//	public String getFrecType_s() {
//		return BalanceRecTypeEnum.getEnumString(this.getFrecType());
//	}
//
//	public void setFrecType_s(String frecType_s) {
//		this.frecType_s = frecType_s;
//	}
//	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fvid")
	public Fvirtualcointype getFvirtualcointype() {
		return fvirtualcointype;
	}

	public void setFvirtualcointype(Fvirtualcointype fvirtualcointype) {
		this.fvirtualcointype = fvirtualcointype;
	}
}