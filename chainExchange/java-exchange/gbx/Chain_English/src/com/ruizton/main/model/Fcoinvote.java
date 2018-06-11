package com.ruizton.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.ruizton.main.Enum.CoinVoteStatusEnum;

/**
 * Fcoinvote entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fcoinvote", catalog = "yunshu333_new_vcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fcoinvote implements java.io.Serializable {

	// Fields

	private int fid;
	private int version;
	private String fcnName;
	private String fenName;
	private String fshortName;
	private String fdesc;
	private String furl;
	private int fstatus;
	private String fstatus_s;
	private int fyes;
	private int fno;

	// Constructors

	/** default constructor */
	public Fcoinvote() {
	}

	/** full constructor */
	public Fcoinvote(String fcnName, String fenName, String fshortName,
			String fdesc, String furl, int fstatus, int fyes,
			int fno) {
		this.fcnName = fcnName;
		this.fenName = fenName;
		this.fshortName = fshortName;
		this.fdesc = fdesc;
		this.furl = furl;
		this.fstatus = fstatus;
		this.fyes = fyes;
		this.fno = fno;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "native")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "fid", unique = true, nullable = false)
	public int getFid() {
		return this.fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Column(name = "fcn_name", length = 256)
	public String getFcnName() {
		return this.fcnName;
	}

	public void setFcnName(String fcnName) {
		this.fcnName = fcnName;
	}

	@Column(name = "fen_name", length = 256)
	public String getFenName() {
		return this.fenName;
	}

	public void setFenName(String fenName) {
		this.fenName = fenName;
	}

	@Column(name = "fshort_name", length = 256)
	public String getFshortName() {
		return this.fshortName;
	}

	public void setFshortName(String fshortName) {
		this.fshortName = fshortName;
	}

	@Column(name = "fdesc", length = 1024)
	public String getFdesc() {
		return this.fdesc;
	}

	public void setFdesc(String fdesc) {
		this.fdesc = fdesc;
	}

	@Column(name = "furl", length = 1024)
	public String getFurl() {
		return this.furl;
	}

	public void setFurl(String furl) {
		this.furl = furl;
	}

	@Column(name = "fstatus")
	public int getFstatus() {
		return this.fstatus;
	}

	public void setFstatus(int fstatus) {
		this.fstatus = fstatus;
	}

	@Column(name = "fyes")
	public int getFyes() {
		return this.fyes;
	}

	public void setFyes(int fyes) {
		this.fyes = fyes;
	}

	@Column(name = "fno")
	public int getFno() {
		return this.fno;
	}

	public void setFno(int fno) {
		this.fno = fno;
	}

	@Transient
	public String getFstatus_s() {
		return CoinVoteStatusEnum.getEnumString(this.getFstatus());
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}
	
	

}