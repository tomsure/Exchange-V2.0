package com.ruizton.main.service.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruizton.main.dao.FbalancetypeDAO;
import com.ruizton.main.model.Fbalancetype;

@Service
public class BalancetypeService {
	@Autowired
	private FbalancetypeDAO balancetypeDAO;
	@Autowired
	private HttpServletRequest request;

	public Fbalancetype findById(int id) {
		return this.balancetypeDAO.findById(id);
	}

	public void saveObj(Fbalancetype obj) {
		this.balancetypeDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fbalancetype obj = this.balancetypeDAO.findById(id);
		this.balancetypeDAO.delete(obj);
	}

	public void updateObj(Fbalancetype obj) {
		this.balancetypeDAO.attachDirty(obj);
	}

	public List<Fbalancetype> findByProperty(String name, Object value) {
		return this.balancetypeDAO.findByProperty(name, value);
	}

	public List<Fbalancetype> findAll() {
		return this.balancetypeDAO.findAll();
	}

	public List<Fbalancetype> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		return this.balancetypeDAO.list(firstResult, maxResults, filter,isFY);
	}
}