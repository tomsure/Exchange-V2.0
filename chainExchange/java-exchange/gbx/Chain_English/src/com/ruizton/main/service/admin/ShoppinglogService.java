package com.ruizton.main.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruizton.main.dao.FgoodsDAO;
import com.ruizton.main.dao.FgoodscommDAO;
import com.ruizton.main.dao.FintrolinfoDAO;
import com.ruizton.main.dao.FmessageDAO;
import com.ruizton.main.dao.FscoreDAO;
import com.ruizton.main.dao.FshoppinglogDAO;
import com.ruizton.main.dao.FuserDAO;
import com.ruizton.main.dao.FvirtualwalletDAO;
import com.ruizton.main.model.Fgoods;
import com.ruizton.main.model.Fgoodscomm;
import com.ruizton.main.model.Fintrolinfo;
import com.ruizton.main.model.Fmessage;
import com.ruizton.main.model.Fscore;
import com.ruizton.main.model.Fshoppinglog;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualwallet;

@Service
public class ShoppinglogService {
	@Autowired
	private FshoppinglogDAO shoppinglogDAO;
	@Autowired
	private FuserDAO fuserDAO;
	@Autowired
	private FgoodsDAO goodsDAO;
	@Autowired
	private FvirtualwalletDAO virtualwalletDAO;
	@Autowired
	private FscoreDAO scoreDAO;
	@Autowired
	private FmessageDAO messageDAO;
	@Autowired
	private FgoodscommDAO goodscommDAO;
	@Autowired
	private FintrolinfoDAO introlinfoDAO;

	public Fshoppinglog findById(int id) {
		return this.shoppinglogDAO.findById(id);
	}

	public void saveObj(Fshoppinglog obj) {
		this.shoppinglogDAO.save(obj);
	}

	public void deleteObj(int id) {
		Fshoppinglog obj = this.shoppinglogDAO.findById(id);
		this.shoppinglogDAO.delete(obj);
	}

	public void updateObj(Fshoppinglog obj) {
		this.shoppinglogDAO.attachDirty(obj);
	}
	
	public void updateObj(Fshoppinglog obj,List<Fvirtualwallet> fwallets,List<Fintrolinfo> fintrolinfos) {
		try {
			this.shoppinglogDAO.attachDirty(obj);
			for (Fintrolinfo fintrolinfo : fintrolinfos) {
				this.introlinfoDAO.save(fintrolinfo);
			}
			
			for (Fvirtualwallet fwallet : fwallets) {
				this.virtualwalletDAO.attachDirty(fwallet);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public void updateObj1(Fshoppinglog obj,List<Fvirtualwallet> fvirtualwallets,List<Fintrolinfo> fintrolinfos) {
		try {
			this.shoppinglogDAO.attachDirty(obj);
			for (Fintrolinfo fintrolinfo : fintrolinfos) {
				this.introlinfoDAO.save(fintrolinfo);
			}
			for (Fvirtualwallet fvirtualwallet : fvirtualwallets) {
				this.virtualwalletDAO.attachDirty(fvirtualwallet);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public List<Fshoppinglog> findByProperty(String name, Object value) {
		return this.shoppinglogDAO.findByProperty(name, value);
	}

	public List<Fshoppinglog> findAll() {
		return this.shoppinglogDAO.findAll();
	}

	public List<Fshoppinglog> list(int firstResult, int maxResults,
			String filter,boolean isFY) {
		return this.shoppinglogDAO.list(firstResult, maxResults, filter,isFY);
	}
	
	public Fshoppinglog checkMessage(Fshoppinglog fshoppinglog) {
		Fshoppinglog shoppinglog = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fpasswordA", fshoppinglog.getFpasswordA());
			map.put("fpasswordB", fshoppinglog.getFpasswordB());
			List<Fshoppinglog> all = this.shoppinglogDAO.findByMap(map);
			if(all != null && all.size() >0 && all.get(0) != null){
				shoppinglog = all.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			shoppinglog = null;
			throw new RuntimeException();
		}
		return shoppinglog;
	}
	
	public void updateUseLog(Fuser fuser,Fshoppinglog fshoppinglog) {
		try {
			this.shoppinglogDAO.attachDirty(fshoppinglog);
			this.fuserDAO.attachDirty(fuser);
		} catch (Exception e) {
			throw new RuntimeException();
		}

	}
	
	public void updateBuylog(Fscore fscore,Fgoods fgoods,Fshoppinglog shoppinglog) {
		try {
			this.scoreDAO.attachDirty(fscore);
			this.goodsDAO.attachDirty(fgoods);
			this.shoppinglogDAO.save(shoppinglog);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateCancellog(Fvirtualwallet virtualwallet,
			Fshoppinglog shoppinglog) {
		try {
			this.virtualwalletDAO.attachDirty(virtualwallet);
			this.shoppinglogDAO.attachDirty(shoppinglog);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateChargelog(Fvirtualwallet virtualwallet,
			Fshoppinglog shoppinglog,Fmessage fmessage,Fvirtualwallet f) {
		try {
			this.virtualwalletDAO.attachDirty(f);
			this.virtualwalletDAO.attachDirty(virtualwallet);
			this.shoppinglogDAO.attachDirty(shoppinglog);
			this.messageDAO.save(fmessage);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateBuylog(Fvirtualwallet userWallet,Fvirtualwallet supplierWallet,
			Fgoods fgoods,Fshoppinglog shoppinglog) {
		try {
			this.virtualwalletDAO.attachDirty(userWallet);
			this.virtualwalletDAO.attachDirty(supplierWallet);
			this.goodsDAO.attachDirty(fgoods);
			this.shoppinglogDAO.save(shoppinglog);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateSendObj(Fshoppinglog obj,Fmessage message,Fvirtualwallet fwallet) {
		try {
			this.messageDAO.save(message);
			this.virtualwalletDAO.attachDirty(fwallet);
			this.shoppinglogDAO.attachDirty(obj);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateComm(Fshoppinglog log,Fgoodscomm comm) {
		try {
			this.shoppinglogDAO.attachDirty(log);
			this.goodscommDAO.save(comm);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}