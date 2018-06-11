package com.broctagon.exchangeadmin.model;

import javax.persistence.Column;
import javax.persistence.Id;

public class BasicCoin {

	@Id
	@Column(name="ID")
	private int id;
	
	@Column(name="CoinName")
	private String name;

	public BasicCoin(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
