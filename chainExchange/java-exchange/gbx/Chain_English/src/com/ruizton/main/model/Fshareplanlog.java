package com.ruizton.main.model;
// default package

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.ruizton.main.Enum.SharePlanLogStatusEnum;

/**
 * Fshareplanlog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fshareplanlog", catalog = "yunshu333_new_vcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fshareplanlog implements java.io.Serializable {

	// Fields

	private Integer fid;
	private Fshareplan fshareplan;
	private Fuser fuser;
	private BigDecimal famount;
	private Integer fstatus;//SharePlanLogStatusEnum
	private String fstatus_s ;
	private Timestamp fcreatetime;
	private double fselfAmount ;
	private double fTotalAmount ;
	private double fTotalQty;
	private double foneAmount;
	private String ftype;
	// Constructors

	/** default constructor */
	public Fshareplanlog() {
	}

	/** full constructor */
	public Fshareplanlog(Fshareplan fshareplan, Fuser fuser, BigDecimal famount,
			Integer fstatus, Timestamp fcreatetime) {
		this.fshareplan = fshareplan;
		this.fuser = fuser;
		this.famount = famount;
		this.fstatus = fstatus;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fsharePlanId")
	public Fshareplan getFshareplan() {
		return this.fshareplan;
	}

	public void setFshareplan(Fshareplan fshareplan) {
		this.fshareplan = fshareplan;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fuserId")
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

	@Column(name = "famount", precision = 11, scale = 6)
	public BigDecimal getFamount() {
		return this.famount;
	}

	public void setFamount(BigDecimal famount) {
		this.famount = famount;
	}

	@Column(name = "fstatus")
	public Integer getFstatus() {
		return this.fstatus;
	}

	public void setFstatus(Integer fstatus) {
		this.fstatus = fstatus;
	}

	@Column(name = "fcreatetime", length = 19)
	public Timestamp getFcreatetime() {
		return this.fcreatetime;
	}

	public void setFcreatetime(Timestamp fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	@Column(name="fselfAmount")
	public double getFselfAmount() {
		return fselfAmount;
	}

	public void setFselfAmount(double fselfAmount) {
		this.fselfAmount = fselfAmount;
	}

	@Column(name="fTotalAmount")
	public double getfTotalAmount() {
		return fTotalAmount;
	}

	public void setfTotalAmount(double fTotalAmount) {
		this.fTotalAmount = fTotalAmount;
	}
	
	@Column(name="fTotalQty")
	public double getfTotalQty() {
		return fTotalQty;
	}

	public void setfTotalQty(double fTotalQty) {
		this.fTotalQty = fTotalQty;
	}
	
	@Column(name="foneAmount")
	public double getFoneAmount() {
		return foneAmount;
	}

	public void setFoneAmount(double foneAmount) {
		this.foneAmount = foneAmount;
	}

	@Transient
	public String getFstatus_s() {
		return SharePlanLogStatusEnum.getEnumString(this.getFstatus()) ;
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}
	
	@Column(name = "ftype")
	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

}