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
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * Fbalanceearning entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fbalanceearning", catalog = "yunshu333_new_vcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fbalanceearning implements java.io.Serializable {

	// Fields

	private Integer fid;
	private Fuser fuser;
	private Double fqty;
	private Timestamp fcreatetime;
	private Integer fstatus;
	private int version;
	private int fvid;
//	private Integer frecType;
//	private String frecType_s;
	// Constructors

	/** default constructor */
	public Fbalanceearning() {
	}

	/** full constructor */
	public Fbalanceearning(Fuser fuser, Double fqty, Timestamp fcreatetime,
			Integer fstatus) {
		this.fuser = fuser;
		this.fqty = fqty;
		this.fcreatetime = fcreatetime;
		this.fstatus = fstatus;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fuserid")
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

	@Column(name = "fqty", precision = 16, scale = 6)
	public Double getFqty() {
		return this.fqty;
	}

	public void setFqty(Double fqty) {
		this.fqty = fqty;
	}

	@Column(name = "fcreatetime", length = 0)
	public Timestamp getFcreatetime() {
		return this.fcreatetime;
	}

	public void setFcreatetime(Timestamp fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	@Column(name = "fstatus")
	public Integer getFstatus() {
		return this.fstatus;
	}

	public void setFstatus(Integer fstatus) {
		this.fstatus = fstatus;
	}

	@Column(name = "fvid")
	public int getFvid() {
		return fvid;
	}

	public void setFvid(int fvid) {
		this.fvid = fvid;
	}

	@Version
	@Column(name = "version")
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

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
}