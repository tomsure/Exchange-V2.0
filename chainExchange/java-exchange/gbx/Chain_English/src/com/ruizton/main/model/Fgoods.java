package com.ruizton.main.model;

// default package

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.ruizton.main.Enum.GoodsStatusEnum;

/**
 * Fgoods entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fgoods", catalog = "yunshu333_new_vcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fgoods implements java.io.Serializable {

	// Fields
	private Integer fid;
	private Integer version;
	private String fname;
	private Double fprice;
	private Double fscore;
	private Integer ftotalQty;
	private Integer flastQty;
	private Integer fsellQty;
	private String fdescription;
	private String fprovince;
	private String fcity;
	private Timestamp fcreatetime;
	private Integer fstatus;
	private String fstatus_s;
	private String fsupplierNo;
	private boolean fisVirtual;
	private String fpictureUrl;
	private String fpictureUrl1;
	private String fpictureUrl2;
	private Fgoodtype ftype;
	private Double fmarketPrice;
	private String fqq;
	private Double fpostageAmt;
	private Double fbaoyouAmt;
	private String fstyleName;
	private String fstyleType;
	private String fstyleName1;
	private String fstyleType1;
	
	private String fbuyType;
    private int fseq;


	private Set<Fshoppinglog> fshoppinglogs = new HashSet<Fshoppinglog>(0);

	// Constructors

	/** default constructor */
	public Fgoods() {
	}

	/** full constructor */
	public Fgoods(String fname, Double fprice, Double fscore,
			Integer ftotalQty, Integer flastQty, String fdescription,
			String fprovince, String fcity, Timestamp fcreatetime,
			Integer fstatus, String fsupplierNo, boolean fisVirtual,
			String fpictureUrl, Set<Fshoppinglog> fshoppinglogs) {
		this.fname = fname;
		this.fprice = fprice;
		this.fscore = fscore;
		this.ftotalQty = ftotalQty;
		this.flastQty = flastQty;
		this.fdescription = fdescription;
		this.fprovince = fprovince;
		this.fcity = fcity;
		this.fcreatetime = fcreatetime;
		this.fstatus = fstatus;
		this.fsupplierNo = fsupplierNo;
		this.fisVirtual = fisVirtual;
		this.fpictureUrl = fpictureUrl;
		this.fshoppinglogs = fshoppinglogs;
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

	@Column(name = "fname", length = 100)
	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@Column(name = "fprice", precision = 16, scale = 6)
	public Double getFprice() {
		return this.fprice;
	}

	public void setFprice(Double fprice) {
		this.fprice = fprice;
	}

	@Column(name = "fscore", precision = 16, scale = 6)
	public Double getFscore() {
		return this.fscore;
	}

	public void setFscore(Double fscore) {
		this.fscore = fscore;
	}

	@Column(name = "ftotalQty")
	public Integer getFtotalQty() {
		return this.ftotalQty;
	}

	public void setFtotalQty(Integer ftotalQty) {
		this.ftotalQty = ftotalQty;
	}

	@Column(name = "flastQty")
	public Integer getFlastQty() {
		return this.flastQty;
	}

	public void setFlastQty(Integer flastQty) {
		this.flastQty = flastQty;
	}

	@Column(name = "fdescription", length = 65535)
	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	@Column(name = "fprovince", length = 50)
	public String getFprovince() {
		return this.fprovince;
	}

	public void setFprovince(String fprovince) {
		this.fprovince = fprovince;
	}

	@Column(name = "fcity", length = 50)
	public String getFcity() {
		return this.fcity;
	}

	public void setFcity(String fcity) {
		this.fcity = fcity;
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

	@Column(name = "fsupplierNo", length = 50)
	public String getFsupplierNo() {
		return this.fsupplierNo;
	}

	public void setFsupplierNo(String fsupplierNo) {
		this.fsupplierNo = fsupplierNo;
	}

	@Column(name = "fisVirtual")
	public boolean getFisVirtual() {
		return this.fisVirtual;
	}

	public void setFisVirtual(boolean fisVirtual) {
		this.fisVirtual = fisVirtual;
	}
	
	@Column(name = "fsellQty")
	public Integer getFsellQty() {
		return fsellQty;
	}

	public void setFsellQty(Integer fsellQty) {
		this.fsellQty = fsellQty;
	}

	@Column(name = "fpictureUrl", length = 100)
	public String getFpictureUrl() {
		return this.fpictureUrl;
	}

	public void setFpictureUrl(String fpictureUrl) {
		this.fpictureUrl = fpictureUrl;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fgoods")
	public Set<Fshoppinglog> getFshoppinglogs() {
		return this.fshoppinglogs;
	}

	public void setFshoppinglogs(Set<Fshoppinglog> fshoppinglogs) {
		this.fshoppinglogs = fshoppinglogs;
	}

	@Transient
	public String getFstatus_s() {
		return GoodsStatusEnum.getEnumString(this.getFstatus());
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ftype")
	public Fgoodtype getFtype() {
		return ftype;
	}

	public void setFtype(Fgoodtype ftype) {
		this.ftype = ftype;
	}

	@Column(name = "fmarketPrice")
	public Double getFmarketPrice() {
		return fmarketPrice;
	}

	public void setFmarketPrice(Double fmarketPrice) {
		this.fmarketPrice = fmarketPrice;
	}
	
	@Column(name = "fqq")
	public String getFqq() {
		return fqq;
	}

	public void setFqq(String fqq) {
		this.fqq = fqq;
	}
	
	@Column(name = "fpostageAmt")
	public Double getFpostageAmt() {
		return fpostageAmt;
	}

	public void setFpostageAmt(Double fpostageAmt) {
		this.fpostageAmt = fpostageAmt;
	}

	@Column(name = "fbaoyouAmt")
	public Double getFbaoyouAmt() {
		return fbaoyouAmt;
	}

	public void setFbaoyouAmt(Double fbaoyouAmt) {
		this.fbaoyouAmt = fbaoyouAmt;
	}
	
	@Column(name = "fstyleName")
	public String getFstyleName() {
		return fstyleName;
	}

	public void setFstyleName(String fstyleName) {
		this.fstyleName = fstyleName;
	}

	@Column(name = "fstyleType")
	public String getFstyleType() {
		return fstyleType;
	}

	public void setFstyleType(String fstyleType) {
		this.fstyleType = fstyleType;
	}

	@Column(name = "fpictureUrl1")
	public String getFpictureUrl1() {
		return fpictureUrl1;
	}

	public void setFpictureUrl1(String fpictureUrl1) {
		this.fpictureUrl1 = fpictureUrl1;
	}

	@Column(name = "fpictureUrl2")
	public String getFpictureUrl2() {
		return fpictureUrl2;
	}

	public void setFpictureUrl2(String fpictureUrl2) {
		this.fpictureUrl2 = fpictureUrl2;
	}
	
	@Column(name = "fstyleName1")
	public String getFstyleName1() {
		return fstyleName1;
	}

	public void setFstyleName1(String fstyleName1) {
		this.fstyleName1 = fstyleName1;
	}

	@Column(name = "fstyleType1")
	public String getFstyleType1() {
		return fstyleType1;
	}

	public void setFstyleType1(String fstyleType1) {
		this.fstyleType1 = fstyleType1;
	}

	@Column(name = "fbuyType")
	public String getFbuyType() {
		return fbuyType;
	}

	public void setFbuyType(String fbuyType) {
		this.fbuyType = fbuyType;
	}
	
	@Column(name = "fseq")
	public int getFseq() {
		return fseq;
	}

	public void setFseq(int fseq) {
		this.fseq = fseq;
	}
}