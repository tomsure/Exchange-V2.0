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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.ruizton.main.Enum.CapitalOperationInStatus;
import com.ruizton.main.Enum.CapitalOperationOutStatus;
import com.ruizton.main.Enum.CapitalOperationTypeEnum;

/**
 * Fcapitaloperation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fcapitaloperation", catalog = "yunshu333_new_vcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fcapitaloperation implements java.io.Serializable {

	// Fields

	private int fid;
	private Systembankinfo systembankinfo;// 用户向这个账号汇款,只有充值用
	private Fuser fuser;
	private Timestamp fcreateTime;
	private Timestamp fLastUpdateTime;

	private double famount;
	private double fCnyAmount;
	private int ftype;// CapitalOperationTypeEnum，汇款or提现
	private String ftype_s;
	private int fstatus;// CapitalOperationInStatus||CapitalOperationOutStatus
	private String fstatus_s;
	
	private String fremittanceType;// RemittanceTypeEnum汇款方式
	
	private String fremark;// 用不着

	private String fBank;
	private String fAccount;
	private String faccount_s;
	private String fPayee;// 汇款人or收款人
	private String fPhone;// 汇款人手机账号
	private Fadmin fAuditee_id ;
	private double ffees ;//手续费
	private int version ;
	private boolean fischarge;
	private String faddress;
	
	private boolean fisCheck;
	private boolean fone;
	private boolean ftwo;
	private boolean fthree;
	

	/** default constructor */
	public Fcapitaloperation() {
	}

     
	/** full constructor */
	public Fcapitaloperation(Fbankinfo fbankinfo,
			Systembankinfo systembankinfo, Fuser fuser, Timestamp fcreateTime,
			double famount,double fCnyAmount, int ftype, int fstatus,
			String fremittanceType, String fremark) {
		this.systembankinfo = systembankinfo;
		this.fuser = fuser;
		this.fcreateTime = fcreateTime;
		this.famount = famount;
		this.fCnyAmount = fCnyAmount;
		this.ftype = ftype;
		this.fstatus = fstatus;
		this.fremittanceType = fremittanceType;
		this.fremark = fremark;
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


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Sys_fId")
	public Systembankinfo getSystembankinfo() {
		return this.systembankinfo;
	}

	public void setSystembankinfo(Systembankinfo systembankinfo) {
		this.systembankinfo = systembankinfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
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

	@Column(name = "fAmount", precision = 12, scale = 0)
	public double getFamount() {
		return this.famount;
	}

	public void setFamount(double famount) {
		this.famount = famount;
	}

	@Column(name = "fCnyAmount", precision = 12, scale = 0)
	public double getfCnyAmount() {
		return fCnyAmount;
	}

	public void setfCnyAmount(double fCnyAmount) {
		this.fCnyAmount = fCnyAmount;
	}

	@Column(name = "fType")
	public int getFtype() {
		return this.ftype;
	}

	public void setFtype(int ftype) {
		this.ftype = ftype;
	}

	@Column(name = "fStatus")
	public int getFstatus() {
		return this.fstatus;
	}

	public void setFstatus(int fstatus) {
		this.fstatus = fstatus;
	}

	@Column(name = "fRemittanceType")
	public String getFremittanceType() {
		return this.fremittanceType;
	}

	public void setFremittanceType(String fremittanceType) {
		this.fremittanceType = fremittanceType;
	}

	@Column(name = "fRemark", length = 256)
	public String getFremark() {
		return this.fremark;
	}

	public void setFremark(String fremark) {
		this.fremark = fremark;
	}

	@Transient
	public String getFstatus_s() {
		int status = this.getFstatus();
		if (this.getFtype() == CapitalOperationTypeEnum.RMB_IN) {
			return CapitalOperationInStatus.getEnumString(status);
		} else {
			return CapitalOperationOutStatus.getEnumString(status);
		}
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}

	@Column(name = "fBank", length = 256)
	public String getfBank() {
		return fBank;
	}

	public void setfBank(String fBank) {
		this.fBank = fBank;
	}

	@Column(name = "fAccount", length = 256)
	public String getfAccount() {
		return fAccount;
	}

	public void setfAccount(String fAccount) {
		this.fAccount = fAccount;
	}

	@Column(name = "fPayee", length = 256)
	public String getfPayee() {
		return fPayee;
	}

	public void setfPayee(String fPayee) {
		this.fPayee = fPayee;
	}

	@Column(name = "fPhone", length = 256)
	public String getfPhone() {
		return fPhone;
	}

	public void setfPhone(String fPhone) {
		this.fPhone = fPhone;
	}

	@Transient
	public String getFtype_s() {
		return CapitalOperationTypeEnum.getEnumString(this.getFtype());
	}

	public void setFtype_s(String ftype_s) {
		this.ftype_s = ftype_s;
	}

	@Column( name="fLastUpdateTime" )
	public Timestamp getfLastUpdateTime() {
		return fLastUpdateTime;
	}


	public void setfLastUpdateTime(Timestamp fLastUpdateTime) {
		this.fLastUpdateTime = fLastUpdateTime;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fAuditee_id")
	public Fadmin getfAuditee_id() {
		return fAuditee_id;
	}


	public void setfAuditee_id(Fadmin fAuditee_id) {
		this.fAuditee_id = fAuditee_id;
	}


	@Column(name="ffees")
	public double getFfees() {
		return ffees;
	}


	public void setFfees(double ffees) {
		this.ffees = ffees;
	}
	
	@Column(name="fischarge")
	public boolean isFischarge() {
		return fischarge;
	}

	public void setFischarge(boolean fischarge) {
		this.fischarge = fischarge;
	}
	
	@Version
    @Column(name="version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	@Column(name = "faddress")
	public String getFaddress() {
		return faddress;
	}

	public void setFaddress(String faddress) {
		this.faddress = faddress;
	}

	@Column(name = "fisCheck")
	public boolean isFisCheck() {
		return fisCheck;
	}


	public void setFisCheck(boolean fisCheck) {
		this.fisCheck = fisCheck;
	}

	@Column(name = "fone")
	public boolean isFone() {
		return fone;
	}


	public void setFone(boolean fone) {
		this.fone = fone;
	}

	@Column(name = "ftwo")
	public boolean isFtwo() {
		return ftwo;
	}


	public void setFtwo(boolean ftwo) {
		this.ftwo = ftwo;
	}

	@Column(name = "fthree")
	public boolean isFthree() {
		return fthree;
	}


	public void setFthree(boolean fthree) {
		this.fthree = fthree;
	}

	
	@Transient
	public String getFaccount_s() {
		String account = this.getfAccount();
		if(account == null || account.trim().length() == 0) return "";
		return account.replaceAll(" ", "");
	}

	public void setFaccount_s(String faccount_s) {
		this.faccount_s = faccount_s;
	}
}
