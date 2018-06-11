package com.ruizton.main.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruizton.main.dao.FbalanceearningDAO;
import com.ruizton.main.dao.FbalancelogDAO;
import com.ruizton.main.dao.FintrolinfoDAO;
import com.ruizton.main.dao.FvirtualwalletDAO;
import com.ruizton.main.model.Fbalanceearning;
import com.ruizton.main.model.Fbalancelog;
import com.ruizton.main.model.Fintrolinfo;
import com.ruizton.main.model.Fvirtualwallet;

@Service
public class BalancelogService {
	@Autowired
	private FbalancelogDAO balancelogDAO;
	@Autowired
	private FvirtualwalletDAO virtualwalletDAO;
	@Autowired
	private FintrolinfoDAO introlinfoDAO;
	@Autowired
	private FbalanceearningDAO balanceearningDAO;

	public Fbalancelog findById(int id) {
		return this.balancelogDAO.findById(id);
	}

	public void saveObj(Fbalancelog obj) {
		this.balancelogDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fbalancelog obj = this.balancelogDAO.findById(id);
		this.balancelogDAO.delete(obj);
	}

	public void updateObj(Fbalancelog obj) {
		this.balancelogDAO.attachDirty(obj);
	}

	public List<Fbalancelog> findByProperty(String name, Object value) {
		return this.balancelogDAO.findByProperty(name, value);
	}

	public List<Fbalancelog> findAll() {
		return this.balancelogDAO.findAll();
	}

	public List<Fbalancelog> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		return this.balancelogDAO.list(firstResult, maxResults, filter,isFY);
	}
	
	public void updateInlog(Fvirtualwallet virtualwallet,Fbalancelog balancelog) {
		try {
			this.virtualwalletDAO.attachDirty(virtualwallet);
			this.balancelogDAO.save(balancelog);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateEffectlog(Fvirtualwallet virtualwallet,Fbalancelog balancelog) {
		try {
			this.virtualwalletDAO.attachDirty(virtualwallet);
			this.balancelogDAO.attachDirty(balancelog);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateEffectlog(Fvirtualwallet virtualwallet,
			Fbalanceearning balanceearning) {
		try {
			this.virtualwalletDAO.attachDirty(virtualwallet);
			this.balanceearningDAO.attachDirty(balanceearning);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateEffectlog(Fvirtualwallet virtualwallet,
			Fbalanceearning balanceearning,Fintrolinfo introlinfo,Fvirtualwallet fintrolwallet) {
		try {
			this.virtualwalletDAO.attachDirty(virtualwallet);
			this.balanceearningDAO.attachDirty(balanceearning);
			if(introlinfo != null){
				this.introlinfoDAO.save(introlinfo);
			}
			if(fintrolwallet != null){
				this.virtualwalletDAO.attachDirty(fintrolwallet);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateEffectlog(Fvirtualwallet fwallet,
			Fbalanceearning balanceearning,Fintrolinfo introlinfo) {
		try {
			this.virtualwalletDAO.attachDirty(fwallet);
			this.balanceearningDAO.attachDirty(balanceearning);
			if(introlinfo != null){
				this.introlinfoDAO.save(introlinfo);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateEndlog(Fbalanceearning balanceearning,Fbalancelog balancelog) {
		try {
			this.balanceearningDAO.save(balanceearning);
			this.balancelogDAO.attachDirty(balancelog);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public Map<String,Double> getReport() {
		return this.balancelogDAO.getReport();
	}
}