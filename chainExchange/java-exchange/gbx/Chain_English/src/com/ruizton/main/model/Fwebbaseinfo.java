package com.ruizton.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * Fwebbaseinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fwebbaseinfo", catalog = "yunshu333_new_vcoin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fwebbaseinfo implements java.io.Serializable {

	// Fields

	private int fid;
	private String ftitle;
	private String fkeywords;
	private String fdescription;
	private String fcopyRights;
	private String fbeianInfo;
	private String fsystemMail;

	private int version;

	// Constructors

	/** default constructor */
	public Fwebbaseinfo() {
	}

	/** full constructor */
	public Fwebbaseinfo(String ftitle, String fkeywords, String fdescription,
			String fcopyRights, String fbeianInfo, String fsystemMail) {
		this.ftitle = ftitle;
		this.fkeywords = fkeywords;
		this.fdescription = fdescription;
		this.fcopyRights = fcopyRights;
		this.fbeianInfo = fbeianInfo;
		this.fsystemMail = fsystemMail;
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

	@Column(name = "fTitle", length = 256)
	public String getFtitle() {
		return this.ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}

	@Column(name = "fKeywords", length = 256)
	public String getFkeywords() {
		return this.fkeywords;
	}

	public void setFkeywords(String fkeywords) {
		this.fkeywords = fkeywords;
	}

	@Column(name = "fDescription", length = 256)
	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	@Column(name = "fCopyRights", length = 256)
	public String getFcopyRights() {
		return this.fcopyRights;
	}

	public void setFcopyRights(String fcopyRights) {
		this.fcopyRights = fcopyRights;
	}

	@Column(name = "fBeianInfo", length = 256)
	public String getFbeianInfo() {
		return this.fbeianInfo;
	}

	public void setFbeianInfo(String fbeianInfo) {
		this.fbeianInfo = fbeianInfo;
	}

	@Column(name = "fSystemMail", length = 256)
	public String getFsystemMail() {
		return this.fsystemMail;
	}

	public void setFsystemMail(String fsystemMail) {
		this.fsystemMail = fsystemMail;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}