package com.ruizton.main.model;

// default package

import java.math.BigDecimal;
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

import com.ruizton.main.Enum.SharePlanStatusEnum;
import com.ruizton.main.Enum.SharePlanTypeEnum;

/**
 * 分红计划表，用于增加分币
 */
@Entity
@Table(name = "fshareplan", catalog = "yunshu333_new_vcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fshareplan implements java.io.Serializable {

	private Integer fid;
	private Fadmin fcreator;// 创建人
	private Fvirtualcointype fvirtualcointype;// 分红虚拟币种
	private Integer fstatus;// 状态//SharePlanStatusEnum
	private String fstatus_s;
	private String ftitle;// 主题
	private BigDecimal famount;// 此次分红总金额
	private Timestamp fcreateTime;// 创建日期

	private Integer ftype;// SharePlanTypeEnum
	private String ftype_s;
	private BigDecimal fpercent;// 分红比例
	private Timestamp fstartDate;// 开始时间
	private Timestamp fendDate;// 结束时间
	private BigDecimal ftotalCoinQty;// 总币数
	private int version;

	private int noSend;
	private int hasSend;
	private String ftypes;
	private Integer fcoinid;


	private Fvirtualcointype fsendcointype;// 分红虚拟币种

	// Constructors

	/** default constructor */
	public Fshareplan() {
	}

	/** full constructor */
	public Fshareplan(Fadmin fcreator, Fvirtualcointype fvirtualcointype,
			Integer fstatus, String ftitle, BigDecimal famount,
			Timestamp fcreateTime) {
		this.fcreator = fcreator;
		this.fvirtualcointype = fvirtualcointype;
		this.fstatus = fstatus;
		this.ftitle = ftitle;
		this.famount = famount;
		this.fcreateTime = fcreateTime;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "native")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "FId", unique = true, nullable = false)
	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FCreatorId")
	public Fadmin getFcreator() {
		return this.fcreator;
	}

	public void setFcreator(Fadmin fcreator) {
		this.fcreator = fcreator;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FVirtualCoinTypeId")
	public Fvirtualcointype getFvirtualcointype() {
		return this.fvirtualcointype;
	}

	public void setFvirtualcointype(Fvirtualcointype fvirtualcointype) {
		this.fvirtualcointype = fvirtualcointype;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FsendCoinTypeId")
	public Fvirtualcointype getFsendcointype() {
		return fsendcointype;
	}

	public void setFsendcointype(Fvirtualcointype fsendcointype) {
		this.fsendcointype = fsendcointype;
	}

	@Column(name = "FStatus")
	public Integer getFstatus() {
		return this.fstatus;
	}

	public void setFstatus(Integer fstatus) {
		this.fstatus = fstatus;
	}

	@Column(name = "FTitle", length = 100)
	public String getFtitle() {
		return this.ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}

	@Column(name = "FAmount", precision = 10, scale = 0)
	public BigDecimal getFamount() {
		return this.famount;
	}

	public void setFamount(BigDecimal famount) {
		this.famount = famount;
	}

	@Column(name = "FCreateTime", length = 19)
	public Timestamp getFcreateTime() {
		return this.fcreateTime;
	}

	public void setFcreateTime(Timestamp fcreateTime) {
		this.fcreateTime = fcreateTime;
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
	public String getFstatus_s() {
		return SharePlanStatusEnum.getEnumString(this.getFstatus());
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}

	@Column(name = "FType")
	public Integer getFtype() {
		return ftype;
	}

	public void setFtype(Integer ftype) {
		this.ftype = ftype;
	}
	
	@Column(name = "ftypes")
	public String getFtypes() {
		return ftypes;
	}

	public void setFtypes(String ftypes) {
		this.ftypes = ftypes;
	}

	@Column(name = "FPercent", precision = 11, scale = 6)
	public BigDecimal getFpercent() {
		return fpercent;
	}

	public void setFpercent(BigDecimal fpercent) {
		this.fpercent = fpercent;
	}


	@Column(name = "FStartDate", length = 19)
	public Date getFstartDate() {
		return fstartDate;
	}

	public void setFstartDate(Timestamp fstartDate) {
		this.fstartDate = fstartDate;
	}


	@Column(name = "FEndDate", length = 19)
	public Date getFendDate() {
		return fendDate;
	}

	public void setFendDate(Timestamp fendDate) {
		this.fendDate = fendDate;
	}

	@Column(name = "FTotalCoinQty", precision = 11, scale = 6)
	public BigDecimal getFtotalCoinQty() {
		return ftotalCoinQty;
	}

	public void setFtotalCoinQty(BigDecimal ftotalCoinQty) {
		this.ftotalCoinQty = ftotalCoinQty;
	}

	@Transient
	public int getNoSend() {
		return noSend;
	}

	public void setNoSend(int noSend) {
		this.noSend = noSend;
	}

	@Transient
	public int getHasSend() {
		return hasSend;
	}

	public void setHasSend(int hasSend) {
		this.hasSend = hasSend;
	}
	
	@Column(name = "fcoinid")
	public Integer getFcoinid() {
		return fcoinid;
	}

	public void setFcoinid(Integer fcoinid) {
		this.fcoinid = fcoinid;
	}

	@Transient
	public String getFtype_s() {
		return SharePlanTypeEnum.getEnumString(this.getFtype());
	}

	public void setFtype_s(String ftype_s) {
		this.ftype_s = ftype_s;
	}

}