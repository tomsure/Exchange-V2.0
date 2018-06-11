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

import org.hibernate.annotations.GenericGenerator;

/**
 * Fgoodsaddress entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fgoodsaddress", catalog = "yunshu333_new_vcoin")
public class Fgoodsaddress implements java.io.Serializable {

	// Fields

	private Integer fid;
	private String fprovince;
	private String fcity;
	private String fdist;
	private String fdesc;
	private String frecName;
	private String fphone;
	private String postalcode;
	private Timestamp fcreatetime;
	private Fuser fuser;

	// Constructors

	/** default constructor */
	public Fgoodsaddress() {
	}

	/** full constructor */
	public Fgoodsaddress(String fprovince, String fcity, String fdesc,
			String frecName, String fphone, String postalcode,
			Timestamp fcreatetime) {
		this.fprovince = fprovince;
		this.fcity = fcity;
		this.fdesc = fdesc;
		this.frecName = frecName;
		this.fphone = fphone;
		this.postalcode = postalcode;
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

	@Column(name = "fprovince", length = 100)
	public String getFprovince() {
		return this.fprovince;
	}

	public void setFprovince(String fprovince) {
		this.fprovince = fprovince;
	}

	@Column(name = "fcity", length = 100)
	public String getFcity() {
		return this.fcity;
	}

	public void setFcity(String fcity) {
		this.fcity = fcity;
	}

	@Column(name = "fdesc", length = 200)
	public String getFdesc() {
		return this.fdesc;
	}

	public void setFdesc(String fdesc) {
		this.fdesc = fdesc;
	}

	@Column(name = "frecName", length = 100)
	public String getFrecName() {
		return this.frecName;
	}

	public void setFrecName(String frecName) {
		this.frecName = frecName;
	}

	@Column(name = "fphone", length = 100)
	public String getFphone() {
		return this.fphone;
	}

	public void setFphone(String fphone) {
		this.fphone = fphone;
	}

	@Column(name = "postalcode", length = 100)
	public String getPostalcode() {
		return this.postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	@Column(name = "fcreatetime", length = 0)
	public Timestamp getFcreatetime() {
		return this.fcreatetime;
	}

	public void setFcreatetime(Timestamp fcreatetime) {
		this.fcreatetime = fcreatetime;
	}
	
	@Column(name = "fdist")
	public String getFdist() {
		return fdist;
	}

	public void setFdist(String fdist) {
		this.fdist = fdist;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fuserid")
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

}