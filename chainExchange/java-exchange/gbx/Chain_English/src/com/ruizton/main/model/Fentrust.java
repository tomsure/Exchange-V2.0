package com.ruizton.main.model;

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
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.ruizton.main.Enum.EntrustStatusEnum;
import com.ruizton.main.Enum.EntrustTypeEnum;

/**
 * Fentrust entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fentrust", catalog = "yunshu333_new_vcoin")
public class Fentrust implements java.io.Serializable {

	// Fields

	private int fid;
	private Ftrademapping ftrademapping;
	private Fuser fuser;
	private Timestamp fcreateTime;
	private Timestamp flastUpdatTime;
	private String flastUpdatTime_s;
	private int fentrustType;// EntrustTypeEnum
	private String fentrustType_s;
	private double fprize;
	private double famount;
	private double ffees ;
	private double fleftfees ;
	private double fsuccessAmount;
	private double fcount;
	private double fleftCount;// 未成交数量
	private int fstatus;// EntrustStatusEnum
	private String fstatus_s;
	private boolean fisLimit;// 按照市价完全成交的订单
	private int version;
	private boolean fhasSubscription;
    private boolean fissend;
	// Constructors




	/** default constructor */
	public Fentrust() {
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "native")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "fId", unique = true, nullable = false)
	public Integer getFid() {
		return this.fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ftrademapping")
	public Ftrademapping getFtrademapping() {
		return ftrademapping;
	}

	public void setFtrademapping(Ftrademapping ftrademapping) {
		this.ftrademapping = ftrademapping;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FUs_fId")
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

	@Column(name = "fCreateTime", length = 0)
	public Timestamp getFcreateTime() {
		return this.fcreateTime;
	}

	public void setFcreateTime(Timestamp fcreateTime) {
		this.fcreateTime = fcreateTime;
	}

	@Column(name = "fEntrustType")
	public int getFentrustType() {
		return this.fentrustType;
	}

	public void setFentrustType(int fentrustType) {
		this.fentrustType = fentrustType;
	}

	@Column(name = "fPrize", precision = 12, scale = 0)
	public Double getFprize() {
		return this.fprize;
	}

	public void setFprize(double fprize) {
		this.fprize = fprize;
	}

	@Column(name = "fAmount", precision = 12, scale = 0)
	public double getFamount() {
		return this.famount;
	}

	public void setFamount(double famount) {
		this.famount = famount;
	}

	@Column(name = "fCount", precision = 12, scale = 0)
	public double getFcount() {
		return this.fcount;
	}

	public void setFcount(double fcount) {
		this.fcount = fcount;
	}

	@Column(name = "fStatus")
	public int getFstatus() {
		return this.fstatus;
	}

	public void setFstatus(int fstatus) {
		this.fstatus = fstatus;
	}

	@Column(name = "flastUpdatTime")
	public Timestamp getFlastUpdatTime() {
		return flastUpdatTime;
	}

	public void setFlastUpdatTime(Timestamp flastUpdatTime) {
		this.flastUpdatTime = flastUpdatTime;
	}

	@Column(name = "fleftCount")
	public double getFleftCount() {
		return fleftCount;
	}

	public void setFleftCount(double fleftCount) {
		this.fleftCount = fleftCount;
	}

	@Column(name = "fsuccessAmount")
	public double getFsuccessAmount() {
		return fsuccessAmount;
	}

	public void setFsuccessAmount(double fsuccessAmount) {
		this.fsuccessAmount = fsuccessAmount;
	}

	@Column(name = "fisLimit")
	public boolean isFisLimit() {
		return fisLimit;
	}

	public void setFisLimit(boolean fisLimit) {
		this.fisLimit = fisLimit;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Transient
	public String getFentrustType_s() {
		int type = this.getFentrustType();
		return EntrustTypeEnum.getEnumString(type);
	}

	public void setFentrustType_s(String fentrustType_s) {
		this.fentrustType_s = fentrustType_s;
	}

	@Transient
	public String getFstatus_s() {
		int status = this.getFstatus();
		return EntrustStatusEnum.getEnumString(status);
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}

	@Column(name="ffees")
	public double getFfees() {
		return ffees;
	}

	public void setFfees(double ffees) {
		this.ffees = ffees;
	}

	@Column(name="fleftfees")
	public double getFleftfees() {
		return fleftfees;
	}

	public void setFleftfees(double fleftfees) {
		this.fleftfees = fleftfees;
	}
	
	@Column(name="fhasSubscription")
	public boolean isFhasSubscription() {
		return fhasSubscription;
	}

	public void setFhasSubscription(boolean fhasSubscription) {
		this.fhasSubscription = fhasSubscription;
	}

	@Column(name="fissend")
	public boolean isFissend() {
		return fissend;
	}

	public void setFissend(boolean fissend) {
		this.fissend = fissend;
	}
	
	@Transient
	public String getFlastUpdatTime_s() {
		return this.getFlastUpdatTime().toString();
	}

	public void setFlastUpdatTime_s(String flastUpdatTime_s) {
		this.flastUpdatTime_s = flastUpdatTime_s;
	}

}