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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.ruizton.main.Enum.CommtypeEnum;

/**
 * Fgoodscomm entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fgoodscomm", catalog = "yunshu333_new_vcoin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fgoodscomm implements java.io.Serializable {

	// Fields

	private Integer fid;
	private Fgoods fgoods;
	private Fshoppinglog fshoppinglog;
	private Integer ftype;
	private String ftype_s;
	private String fcontent;
	private Timestamp fcreatetime;

	// Constructors

	/** default constructor */
	public Fgoodscomm() {
	}

	/** full constructor */
	public Fgoodscomm(Fgoods fgoods, Fshoppinglog fshoppinglog, Integer ftype,
			String fcontent, Timestamp fcreatetime) {
		this.fgoods = fgoods;
		this.fshoppinglog = fshoppinglog;
		this.ftype = ftype;
		this.fcontent = fcontent;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fgoodsid")
	public Fgoods getFgoods() {
		return this.fgoods;
	}

	public void setFgoods(Fgoods fgoods) {
		this.fgoods = fgoods;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fshoppinglogid")
	public Fshoppinglog getFshoppinglog() {
		return this.fshoppinglog;
	}

	public void setFshoppinglog(Fshoppinglog fshoppinglog) {
		this.fshoppinglog = fshoppinglog;
	}

	@Column(name = "ftype")
	public Integer getFtype() {
		return this.ftype;
	}

	public void setFtype(Integer ftype) {
		this.ftype = ftype;
	}

	@Column(name = "fcontent", length = 500)
	public String getFcontent() {
		return this.fcontent;
	}

	public void setFcontent(String fcontent) {
		this.fcontent = fcontent;
	}

	@Column(name = "fcreatetime", length = 0)
	public Timestamp getFcreatetime() {
		return this.fcreatetime;
	}

	public void setFcreatetime(Timestamp fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	@Transient
	public String getFtype_s() {
		return CommtypeEnum.getEnumString(this.getFtype());
	}

	public void setFtype_s(String ftype_s) {
		this.ftype_s = ftype_s;
	}

}