package com.ruizton.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * Fwithdrawfees entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fwithdrawfees", catalog = "yunshu333_new_vcoin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fwithdrawfees implements java.io.Serializable {

	// Fields

	private int fid;
	private double ffee;
	private int flevel;
	private Fvirtualcointype fvirtualcointype;
	private int version;
	private double famt;
	private double flastFee;

	// Constructors

	/** default constructor */
	public Fwithdrawfees() {
	}

	/** full constructor */
	public Fwithdrawfees(double ffee, int flevel) {
		this.ffee = ffee;
		this.flevel = flevel;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "native")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "fid", unique = true, nullable = false)
	public Integer getFid() {
		return this.fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	@Column(name = "ffee", precision = 15, scale = 5)
	public double getFfee() {
		return this.ffee;
	}

	public void setFfee(double ffee) {
		this.ffee = ffee;
	}

	@Column(name = "flevel")
	public int getFlevel() {
		return this.flevel;
	}

	public void setFlevel(int flevel) {
		this.flevel = flevel;
	}
	
	@Column(name = "famt")
	public double getFamt() {
		return famt;
	}

	public void setFamt(double famt) {
		this.famt = famt;
	}

	@Column(name = "flastFee")
	public double getFlastFee() {
		return flastFee;
	}

	public void setFlastFee(double flastFee) {
		this.flastFee = flastFee;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fvid")
	public Fvirtualcointype getFvirtualcointype() {
		return this.fvirtualcointype;
	}

	public void setFvirtualcointype(Fvirtualcointype fvirtualcointype) {
		this.fvirtualcointype = fvirtualcointype;
	}
}