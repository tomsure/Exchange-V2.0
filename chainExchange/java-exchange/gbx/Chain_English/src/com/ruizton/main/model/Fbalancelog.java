package com.ruizton.main.model;

// default package

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.ruizton.main.Enum.BalancelogStatusEnum;

/**
 * Fbalancelog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fbalancelog", catalog = "yunshu333_new_vcoin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fbalancelog implements java.io.Serializable {

	// Fields

	private Integer fid;
	private Integer version;
	private Fuser fuser;
	private Double famount;
	private Double fincomeAmount1;
	private Double fincomeAmount2;
	private Integer fstatus;
	private String fstatus_s;
	private String ftype;
	private String ftype_en;
	private Double frate;
	private Integer fday;
	private Timestamp fcreatetime;
	private Date feffecttime;
	private Date fendtime;
	private Fvirtualcointype fvirtualcointype;
	private boolean fisReturnAmt;
//	private Integer frecType;
//	private String frecType_s;

	// Constructors


	/** default constructor */
	public Fbalancelog() {
	}

	/** full constructor */
	public Fbalancelog(Fuser fuser, Double famount, Double fincomeAmount1,
			Double fincomeAmount2, Integer fstatus, String ftype, Double frate,
			Timestamp fcreatetime) {
		this.fuser = fuser;
		this.famount = famount;
		this.fincomeAmount1 = fincomeAmount1;
		this.fincomeAmount2 = fincomeAmount2;
		this.fstatus = fstatus;
		this.ftype = ftype;
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

	@Version
	@Column(name = "version")
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fuserid")
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fvid")
	public Fvirtualcointype getFvirtualcointype() {
		return fvirtualcointype;
	}

	public void setFvirtualcointype(Fvirtualcointype fvirtualcointype) {
		this.fvirtualcointype = fvirtualcointype;
	}

	@Column(name = "famount", precision = 16, scale = 6)
	public Double getFamount() {
		return this.famount;
	}

	public void setFamount(Double famount) {
		this.famount = famount;
	}

	@Column(name = "fincomeAmount1", precision = 16, scale = 6)
	public Double getFincomeAmount1() {
		return this.fincomeAmount1;
	}

	public void setFincomeAmount1(Double fincomeAmount1) {
		this.fincomeAmount1 = fincomeAmount1;
	}

	@Column(name = "fincomeAmount2", precision = 16, scale = 6)
	public Double getFincomeAmount2() {
		return this.fincomeAmount2;
	}

	public void setFincomeAmount2(Double fincomeAmount2) {
		this.fincomeAmount2 = fincomeAmount2;
	}

	@Column(name = "fstatus")
	public Integer getFstatus() {
		return this.fstatus;
	}

	public void setFstatus(Integer fstatus) {
		this.fstatus = fstatus;
	}

	@Column(name = "ftype")
	public String getFtype() {
		return this.ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
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

	@Column(name = "feffecttime")
	public Date getFeffecttime() {
		return feffecttime;
	}

	public void setFeffecttime(Date feffecttime) {
		this.feffecttime = feffecttime;
	}

	@Transient
	public String getFstatus_s() {

		return BalancelogStatusEnum.getEnumString(this.getFstatus());
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}

	@Column(name = "fday")
	public Integer getFday() {
		return fday;
	}

	public void setFday(Integer fday) {
		this.fday = fday;
	}

	@Column(name = "fendtime")
	public Date getFendtime() {
		return fendtime;
	}

	public void setFendtime(Date fendtime) {
		this.fendtime = fendtime;
	}

	@Column(name = "fisReturnAmt")
	public boolean isFisReturnAmt() {
		return fisReturnAmt;
	}

	public void setFisReturnAmt(boolean fisReturnAmt) {
		this.fisReturnAmt = fisReturnAmt;
	}
	
	@Column(name = "ftype_en")
	public String getFtype_en() {
		return ftype_en;
	}

	public void setFtype_en(String ftype_en) {
		this.ftype_en = ftype_en;
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
}