package com.ruizton.main.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.VirtualCoinTypeStatusEnum;

/**
 * Fvirtualcointype entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fvirtualcointype", catalog = "yunshu333_new_vcoin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fvirtualcointype implements java.io.Serializable {

	// Fields

	private int fid;
	private int fid_s;
//	private boolean fisShare;// 是否可以交易
	private boolean FIsWithDraw;// 是否可以充值提现
	private String fname;
	private String fShortName;
	private String fdescription;
	private Timestamp faddTime;
	private int fstatus;// VirtualCoinTypeStatusEnum
	private String fstatus_s;
	private String fSymbol;
	private String faccess_key;
	private String fsecrt_key;
	private String fip;
	private String fport;
	private double lastDealPrize;// fake,最新成交价格
	private double higestBuyPrize;
	private double lowestSellPrize;
	private boolean canLend;// 是否可以借贷
	private Set<Fvirtualcaptualoperation> fvirtualcaptualoperations = new HashSet<Fvirtualcaptualoperation>(
			0);
	private Set<Fvirtualwallet> fvirtualwallets = new HashSet<Fvirtualwallet>(0);
	private int version;
	private String furl;

	private String fweburl;

//	private int fcount;
//	private String ftradetime;
//	private double fprice;
	private Double total;
	
	//虚拟币类型。是否法币
	private int ftype;
	private String ftype_s;
	
	private boolean fisauto;
	private boolean fisrecharge;
	private boolean fistransport;
    private double ftotalqty;
	private boolean fisautosend;
	private String fpassword;

	private String fregDesc;
	private String fwidDesc;


	private boolean fisEth ;
	private boolean fisToken;
	private String mainAddr ;
	private long startBlockId ;


	/** default constructor */
	public Fvirtualcointype() {
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

	@Column(name = "fName", length = 32)
	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@Column(name = "fDescription", length = 32)
	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	@Column(name = "fAddTime", length = 0)
	public Timestamp getFaddTime() {
		return this.faddTime;
	}

	public void setFaddTime(Timestamp faddTime) {
		this.faddTime = faddTime;
	}


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fvirtualcointype")
	public Set<Fvirtualcaptualoperation> getFvirtualcaptualoperations() {
		return this.fvirtualcaptualoperations;
	}

	public void setFvirtualcaptualoperations(
			Set<Fvirtualcaptualoperation> fvirtualcaptualoperations) {
		this.fvirtualcaptualoperations = fvirtualcaptualoperations;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fvirtualcointype")
	public Set<Fvirtualwallet> getFvirtualwallets() {
		return this.fvirtualwallets;
	}

	public void setFvirtualwallets(Set<Fvirtualwallet> fvirtualwallets) {
		this.fvirtualwallets = fvirtualwallets;
	}

	@Column(name = "fstatus")
	public int getFstatus() {
		return fstatus;
	}

	public void setFstatus(int fstatus) {
		this.fstatus = fstatus;
	}

	@Column(name = "fShortName")
	public String getfShortName() {
		return fShortName;
	}

	public void setfShortName(String fShortName) {
		this.fShortName = fShortName;
	}

	@Transient
	public String getFstatus_s() {
		return VirtualCoinTypeStatusEnum.getEnumString(this.getFstatus());
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Column(name = "fSymbol")
	public String getfSymbol() {
		return fSymbol;
	}

	public void setfSymbol(String fSymbol) {
		this.fSymbol = fSymbol;
	}

	@Column(name = "faccess_key")
	public String getFaccess_key() {
		return faccess_key;
	}

	public void setFaccess_key(String faccess_key) {
		this.faccess_key = faccess_key;
	}

	@Column(name = "fsecrt_key")
	public String getFsecrt_key() {
		return fsecrt_key;
	}

	public void setFsecrt_key(String fsecrt_key) {
		this.fsecrt_key = fsecrt_key;
	}

	@Column(name = "fip")
	public String getFip() {
		return fip;
	}

	public void setFip(String fip) {
		this.fip = fip;
	}

	@Column(name = "fport")
	public String getFport() {
		return fport;
	}

	public void setFport(String fport) {
		this.fport = fport;
	}

	@Transient
	public double getLastDealPrize() {
		return lastDealPrize;
	}

	public void setLastDealPrize(double lastDealPrize) {
		this.lastDealPrize = lastDealPrize;
	}

	@Transient
	public double getHigestBuyPrize() {
		return higestBuyPrize;
	}

	public void setHigestBuyPrize(double higestBuyPrize) {
		this.higestBuyPrize = higestBuyPrize;
	}

	@Transient
	public double getLowestSellPrize() {
		return lowestSellPrize;
	}

	public void setLowestSellPrize(double lowestSellPrize) {
		this.lowestSellPrize = lowestSellPrize;
	}

	@Transient
	public String getFid_s() {
		Integer id = this.getFid();
		if (id != null) {
			return String.valueOf(id);
		}
		return String.valueOf(0);
	}

	public void setFid_s(int fid_s) {
		this.fid_s = fid_s;
	}

	@Column(name = "FIsWithDraw")
	public boolean isFIsWithDraw() {
		return FIsWithDraw;
	}

	public void setFIsWithDraw(boolean fIsWithDraw) {
		FIsWithDraw = fIsWithDraw;
	}

	@Column(name = "furl")
	public String getFurl() {
		return furl;
	}

	public void setFurl(String furl) {
		this.furl = furl;
	}

	@Column(name = "fweburl")
	public String getFweburl() {
		return fweburl;
	}

	public void setFweburl(String fweburl) {
		this.fweburl = fweburl;
	}
	
	@Column(name = "ftype")
	public int getFtype() {
		return ftype;
	}

	public void setFtype(int ftype) {
		this.ftype = ftype;
	}
	
	@Column(name = "fisauto")
	public boolean isFisauto() {
		return fisauto;
	}

	public void setFisauto(boolean fisauto) {
		this.fisauto = fisauto;
	}
	

	@Column(name = "fisrecharge")
	public boolean isFisrecharge() {
		return fisrecharge;
	}

	public void setFisrecharge(boolean fisrecharge) {
		this.fisrecharge = fisrecharge;
	}

	
	@Column(name = "fistransport")
	public boolean isFistransport() {
		return fistransport;
	}


	public void setFistransport(boolean fistransport) {
		this.fistransport = fistransport;
	}
	
	@Column(name = "ftotalqty")
	public double getFtotalqty() {
		return ftotalqty;
	}


	public void setFtotalqty(double ftotalqty) {
		this.ftotalqty = ftotalqty;
	}

	
	@Transient
	public String getFtype_s() {
		return CoinTypeEnum.getEnumString(this.getFtype());
	}

	public void setFtype_s(String ftype_s) {
		this.ftype_s = ftype_s;
	}

	@Transient
	public boolean isCanLend() {
		return true;
	}

	public void setCanLend(boolean canLend) {
		this.canLend = canLend;
	}

    @Transient
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	
	@Column(name="fisautosend")
	public boolean isFisautosend() {
		return fisautosend;
	}

	public void setFisautosend(boolean fisautosend) {
		this.fisautosend = fisautosend;
	}

	@Column(name="fpassword")
	public String getFpassword() {
		return fpassword;
	}

	public void setFpassword(String fpassword) {
		this.fpassword = fpassword;
	}
	
	@Column(name="fregDesc")
	public String getFregDesc() {
		return fregDesc;
	}


	public void setFregDesc(String fregDesc) {
		this.fregDesc = fregDesc;
	}

	@Column(name="fwidDesc")
	public String getFwidDesc() {
		return fwidDesc;
	}


	public void setFwidDesc(String fwidDesc) {
		this.fwidDesc = fwidDesc;
	}
	

	@Column(name="fisEth")
	public boolean isFisEth() {
		return fisEth;
	}


	public void setFisEth(boolean fisEth) {
		this.fisEth = fisEth;
	}

	@Column(name="fisToken")
	public boolean isFisToken() {
		return fisToken;
	}

	public void setFisToken(boolean fisToken) {
		this.fisToken = fisToken;
	}

	@Column(name="mainAddr")
	public String getMainAddr() {
		return mainAddr;
	}


	public void setMainAddr(String mainAddr) {
		this.mainAddr = mainAddr;
	}

	@Column(name="startBlockId")
	public long getStartBlockId() {
		return startBlockId;
	}


	public void setStartBlockId(long startBlockId) {
		this.startBlockId = startBlockId;
	}
}