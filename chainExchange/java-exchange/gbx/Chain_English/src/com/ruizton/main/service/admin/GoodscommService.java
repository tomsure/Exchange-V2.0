package com.ruizton.main.service.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruizton.main.dao.FgoodscommDAO;
import com.ruizton.main.model.Fgoodscomm;

@Service
public class GoodscommService {
	@Autowired
	private FgoodscommDAO goodscommDAO;
	@Autowired
	private HttpServletRequest request;

	public Fgoodscomm findById(int id) {
		return this.goodscommDAO.findById(id);
	}

	public void saveObj(Fgoodscomm obj) {
		this.goodscommDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fgoodscomm obj = this.goodscommDAO.findById(id);
		this.goodscommDAO.delete(obj);
	}

	public void updateObj(Fgoodscomm obj) {
		this.goodscommDAO.attachDirty(obj);
	}

	public List<Fgoodscomm> findByProperty(String name, Object value) {
		return this.goodscommDAO.findByProperty(name, value);
	}

	public List<Fgoodscomm> findAll() {
		return this.goodscommDAO.findAll();
	}

	public List<Fgoodscomm> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		return this.goodscommDAO.list(firstResult, maxResults, filter,isFY);
	}
}