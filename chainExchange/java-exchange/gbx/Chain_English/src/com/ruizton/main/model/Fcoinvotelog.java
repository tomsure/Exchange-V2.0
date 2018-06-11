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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * Fcoinvotelog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fcoinvotelog", catalog = "yunshu333_new_vcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fcoinvotelog implements java.io.Serializable {

	// Fields

	private Integer fid;
	private Fuser fuser;
	private Timestamp fcreatetime;

	private Fcoinvote fcoinvote ;
	private int vote ;
	
	// Constructors

	/** default constructor */
	public Fcoinvotelog() {
	}

	/** full constructor */
	public Fcoinvotelog(Fuser fuser, Timestamp fcreatetime) {
		this.fuser = fuser;
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
	@JoinColumn(name = "fuser")
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

	@Column(name = "fcreatetime", length = 0)
	public Timestamp getFcreatetime() {
		return this.fcreatetime;
	}

	public void setFcreatetime(Timestamp fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="fcoinvote")
	public Fcoinvote getFcoinvote() {
		return fcoinvote;
	}

	public void setFcoinvote(Fcoinvote fcoinvote) {
		this.fcoinvote = fcoinvote;
	}

	@Column(name="vote")
	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}
	
	

}