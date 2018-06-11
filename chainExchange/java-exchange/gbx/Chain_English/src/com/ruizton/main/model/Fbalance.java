package com.ruizton.main.model;

// default package

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * Fbalance entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fbalance", catalog = "yunshu333_new_vcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fbalance implements java.io.Serializable {

	// Fields

	private Integer fid;
	private String ftitle;
	private Timestamp fcreatetime;
	private double fminqty;
	private double fmaxqty;

	// Constructors



	/** default constructor */
	public Fbalance() {
	}

	/** full constructor */
	public Fbalance(String ftitle, Timestamp fcreatetime) {
		this.ftitle = ftitle;
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

	@Column(name = "ftitle", length = 1000)
	public String getFtitle() {
		return this.ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}

	@Column(name = "fcreatetime", length = 0)
	public Timestamp getFcreatetime() {
		return this.fcreatetime;
	}

	public void setFcreatetime(Timestamp fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	@Column(name = "fminqty", precision = 20, scale = 8)
	public double getFminqty() {
		return fminqty;
	}

	public void setFminqty(double fminqty) {
		this.fminqty = fminqty;
	}

	@Column(name = "fmaxqty", precision = 20, scale = 8)
	public double getFmaxqty() {
		return fmaxqty;
	}

	public void setFmaxqty(double fmaxqty) {
		this.fmaxqty = fmaxqty;
	}
}