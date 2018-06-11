package com.ruizton.main.model;

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

import com.ruizton.main.Enum.CoinEnum;
import com.ruizton.main.Enum.TrademappingStatusEnum;

/**
 * Ftrademapping entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftrademapping", catalog = "yunshu333_new_vcoin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ftrademapping implements java.io.Serializable {

	// Fields

	private Integer fid;
	private Integer version;
	private Fvirtualcointype fvirtualcointypeByFvirtualcointype1;// 法币
	private Fvirtualcointype fvirtualcointypeByFvirtualcointype2;// 交易币
	private String ftradeTime;// 交易时间
	private int fstatus;//TrademappingStatusEnum
	private String fstatus_s;
	private int fcount1;// 单价小数位
	private int fcount2;// 数量小数位
	private double fminBuyCount;
	private double fminBuyPrice;
	private double fminBuyAmount;
	
	private double fminSellCount;
	private double fminSellPrice;
	private double fminSellAmount;
	private double fprice;//开盘价
	
	private boolean fislimit;//是否限卖
	private double ftraderate;//限卖比例
    private int ftype;
    private String ftype_s;
    
    private boolean fisIntrol;
	private String ftigerUid;
    private String fintrolRate;
    private String ftradedesc;
    private int fmaxtimes;
    
	private double fmaxBuyCount;
	private double fmaxSellCount;
    
	private double rose ;
	private double rose7 ;
	private double totalCny24;
	private double total24;

	// Constructors



	/** default constructor */
	public Ftrademapping() {
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fvirtualcointype2")
	public Fvirtualcointype getFvirtualcointypeByFvirtualcointype2() {
		return this.fvirtualcointypeByFvirtualcointype2;
	}

	public void setFvirtualcointypeByFvirtualcointype2(
			Fvirtualcointype fvirtualcointypeByFvirtualcointype2) {
		this.fvirtualcointypeByFvirtualcointype2 = fvirtualcointypeByFvirtualcointype2;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fvirtualcointype1")
	public Fvirtualcointype getFvirtualcointypeByFvirtualcointype1() {
		return this.fvirtualcointypeByFvirtualcointype1;
	}

	public void setFvirtualcointypeByFvirtualcointype1(
			Fvirtualcointype fvirtualcointypeByFvirtualcointype1) {
		this.fvirtualcointypeByFvirtualcointype1 = fvirtualcointypeByFvirtualcointype1;
	}

	@Column(name = "ftradeTime")
	public String getFtradeTime() {
		return this.ftradeTime;
	}

	public void setFtradeTime(String ftradeTime) {
		this.ftradeTime = ftradeTime;
	}

	@Column(name = "fstatus")
	public int getFstatus() {
		return fstatus;
	}

	public void setFstatus(int fstatus) {
		this.fstatus = fstatus;
	}

	@Column(name = "fcount1")
	public int getFcount1() {
		return fcount1;
	}

	public void setFcount1(int fcount1) {
		this.fcount1 = fcount1;
	}

	@Column(name = "fcount2")
	public int getFcount2() {
		return fcount2;
	}

	public void setFcount2(int fcount2) {
		this.fcount2 = fcount2;
	}

	@Column(name = "fminBuyCount")
	public double getFminBuyCount() {
		return fminBuyCount;
	}

	public void setFminBuyCount(double fminBuyCount) {
		this.fminBuyCount = fminBuyCount;
	}

	@Column(name = "fminBuyPrice")
	public double getFminBuyPrice() {
		return fminBuyPrice;
	}

	public void setFminBuyPrice(double fminBuyPrice) {
		this.fminBuyPrice = fminBuyPrice;
	}

	@Column(name = "fminBuyAmount")
	public double getFminBuyAmount() {
		return fminBuyAmount;
	}

	public void setFminBuyAmount(double fminBuyAmount) {
		this.fminBuyAmount = fminBuyAmount;
	}

	@Column(name = "fprice")
	public double getFprice() {
		return fprice;
	}

	public void setFprice(double fprice) {
		this.fprice = fprice;
	}
	
	@Transient
	public String getFstatus_s() {
		return TrademappingStatusEnum.getEnumString(this.getFstatus());
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}

	
	@Column(name = "ftype")
	public int getFtype() {
		return ftype;
	}

	public void setFtype(int ftype) {
		this.ftype = ftype;
	}
	
	@Column(name = "fisIntrol")
	public boolean isFisIntrol() {
		return fisIntrol;
	}

	public void setFisIntrol(boolean fisIntrol) {
		this.fisIntrol = fisIntrol;
	}

	@Column(name = "ftigerUid")
	public String getFtigerUid() {
		return ftigerUid;
	}

	public void setFtigerUid(String ftigerUid) {
		this.ftigerUid = ftigerUid;
	}

	@Column(name = "fintrolRate")
	public String getFintrolRate() {
		return fintrolRate;
	}

	public void setFintrolRate(String fintrolRate) {
		this.fintrolRate = fintrolRate;
	}

	@Transient
	public String getFtype_s() {
		return CoinEnum.getEnumString(this.getFtype());
	}

	public void setFtype_s(String ftype_s) {
		this.ftype_s = ftype_s;
	}
	
	@Column(name = "fislimit")
	public boolean isFislimit() {
		return fislimit;
	}

	public void setFislimit(boolean fislimit) {
		this.fislimit = fislimit;
	}

	@Column(name = "ftraderate")
	public double getFtraderate() {
		return ftraderate;
	}

	public void setFtraderate(double ftraderate) {
		this.ftraderate = ftraderate;
	}
	
	@Column(name = "fminSellCount")
	public double getFminSellCount() {
		return fminSellCount;
	}

	public void setFminSellCount(double fminSellCount) {
		this.fminSellCount = fminSellCount;
	}

	@Column(name = "fminSellPrice")
	public double getFminSellPrice() {
		return fminSellPrice;
	}

	public void setFminSellPrice(double fminSellPrice) {
		this.fminSellPrice = fminSellPrice;
	}

	@Column(name = "fminSellAmount")
	public double getFminSellAmount() {
		return fminSellAmount;
	}

	public void setFminSellAmount(double fminSellAmount) {
		this.fminSellAmount = fminSellAmount;
	}

	@Column(name = "ftradedesc")
    public String getFtradedesc() {
		return ftradedesc;
	}

	public void setFtradedesc(String ftradedesc) {
		this.ftradedesc = ftradedesc;
	}

	@Column(name = "fmaxtimes")
	public int getFmaxtimes() {
		return fmaxtimes;
	}

	public void setFmaxtimes(int fmaxtimes) {
		this.fmaxtimes = fmaxtimes;
	}
	
	@Column(name = "fmaxBuyCount")
	public double getFmaxBuyCount() {
		return fmaxBuyCount;
	}

	public void setFmaxBuyCount(double fmaxBuyCount) {
		this.fmaxBuyCount = fmaxBuyCount;
	}

	@Column(name = "fmaxSellCount")
	public double getFmaxSellCount() {
		return fmaxSellCount;
	}

	public void setFmaxSellCount(double fmaxSellCount) {
		this.fmaxSellCount = fmaxSellCount;
	}

	@Transient
	public Double getRose() {
		return rose;
	}

	public void setRose(double rose) {
		this.rose = rose;
	}

	@Transient
	public Double getRose7() {
		return rose7;
	}

	public void setRose7(double rose7) {
		this.rose7 = rose7;
	}

	@Transient
	public Double getTotalCny24() {
		return totalCny24;
	}

	public void setTotalCny24(double totalCny24) {
		this.totalCny24 = totalCny24;
	}

	@Transient
	public Double getTotal24() {
		return total24;
	}

	public void setTotal24(double total24) {
		this.total24 = total24;
	}
	
	
}