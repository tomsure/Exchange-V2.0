package com.ruizton.main.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

import com.ruizton.main.comm.MultipleValues;

import net.sf.json.JSONObject;

/**
 * Fasset entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fasset", catalog = "yunshu333_new_vcoin")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fasset implements java.io.Serializable {

	// Fields

	private Integer fid;
	private Integer version;
	private Fuser fuser;
	private Double ftotal;
	private Timestamp fcreatetime;
	private Timestamp flastupdatetime;
	private Boolean status;
	private String detail;

	private List<MultipleValues> list;

	// Constructors

	/** default constructor */
	public Fasset() {
	}

	/** full constructor */
	public Fasset(Fuser fuser, Double ftotal, Timestamp fcreatetime,
			Timestamp flastupdatetime, Boolean status) {
		this.fuser = fuser;
		this.ftotal = ftotal;
		this.fcreatetime = fcreatetime;
		this.flastupdatetime = flastupdatetime;
		this.status = status;
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
	@JoinColumn(name = "fuser")
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

	@Column(name = "ftotal", precision = 16, scale = 6)
	public Double getFtotal() {
		return this.ftotal;
	}

	public void setFtotal(Double ftotal) {
		this.ftotal = ftotal;
	}

	@Column(name = "fcreatetime", length = 0)
	public Timestamp getFcreatetime() {
		return this.fcreatetime;
	}

	public void setFcreatetime(Timestamp fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	@Column(name = "flastupdatetime", length = 0)
	public Timestamp getFlastupdatetime() {
		return this.flastupdatetime;
	}

	public void setFlastupdatetime(Timestamp flastupdatetime) {
		this.flastupdatetime = flastupdatetime;
	}

	@Column(name = "status")
	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Column(name = "detail")
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Transient
	public List<MultipleValues> getList() {
		return list;
	}

	public void setList(List<MultipleValues> list) {
		this.list = list;
	}

	public void parseJson(List<Fvirtualcointype> fvirtualcointypes) {
		try {
			List<MultipleValues> multipleValues = new ArrayList<MultipleValues>();

			JSONObject jsonObject = JSONObject.fromObject(this.getDetail());
			// cny
			MultipleValues cny = new MultipleValues();
			JSONObject cnyJson = jsonObject.getJSONObject("0");
			cny.setValue1(cnyJson.getDouble("total"));
			cny.setValue2(cnyJson.getDouble("frozen"));
			multipleValues.add(cny);

			for (Fvirtualcointype fvirtualcointype : fvirtualcointypes) {
				MultipleValues mulItem = new MultipleValues();
				if (jsonObject
						.containsKey(String.valueOf(fvirtualcointype.getFid()))) {
					JSONObject jsonItem = jsonObject.getJSONObject(String
							.valueOf(fvirtualcointype.getFid()));
					mulItem.setValue1(jsonItem.getDouble("total"));
					mulItem.setValue2(jsonItem.getDouble("frozen"));
				} else {
					mulItem.setValue1(0);
					mulItem.setValue2(0);
				}
				multipleValues.add(mulItem);
			}

			this.setList(multipleValues);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}