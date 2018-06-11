package com.ruizton.main.service.front;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruizton.main.Enum.EntrustStatusEnum;
import com.ruizton.main.Enum.EntrustTypeEnum;
import com.ruizton.main.dao.FentrustDAO;
import com.ruizton.main.dao.FentrustlogDAO;
import com.ruizton.main.dao.FentrustplanDAO;
import com.ruizton.main.dao.FfeesDAO;
import com.ruizton.main.dao.FintrolinfoDAO;
import com.ruizton.main.dao.FsubscriptionDAO;
import com.ruizton.main.dao.FsubscriptionlogDAO;
import com.ruizton.main.dao.FtrademappingDAO;
import com.ruizton.main.dao.FuserDAO;
import com.ruizton.main.dao.FvirtualwalletDAO;
import com.ruizton.main.dao.UtilsDAO;
import com.ruizton.main.model.Fentrust;
import com.ruizton.main.model.Fentrustlog;
import com.ruizton.main.model.Fentrustplan;
import com.ruizton.main.model.Fintrolinfo;
import com.ruizton.main.model.Fsubscription;
import com.ruizton.main.model.Fsubscriptionlog;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.util.Utils;

@Service
public class FrontTradeService {
	private static final Logger log = LoggerFactory
			.getLogger(FrontTradeService.class);

	@Autowired
	private FentrustDAO fentrustDAO;
	@Autowired
	private FentrustlogDAO fentrustlogDAO;
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO;
	@Autowired
	private FentrustplanDAO fentrustplanDAO;
	@Autowired
	private FfeesDAO ffeesDAO;
	@Autowired
	private FintrolinfoDAO fintrolinfoDAO;
	@Autowired
	private FsubscriptionDAO fsubscriptionDAO;
	@Autowired
	private FsubscriptionlogDAO fsubscriptionlogDAO;
	@Autowired
	private FtrademappingDAO ftrademappingDAO ;
	@Autowired
	private UtilsDAO utilsDAO ;
	@Autowired
	private FuserDAO fuserDAO ;

	//手续费率
	private Map<String, Double> rates = new HashMap<String, Double>() ;
	
	public void putRates(String key,double value){
		synchronized (this.rates) {
			this.rates.put(key, value) ;
		}
	}
	public double getRates(int vid,boolean isbuy,int level){
		String key = vid+"_"+(isbuy?"buy":"sell")+"_"+level ;
		synchronized (this.rates) {
			return this.rates.get(key) ;
		}
	}
	
	//撮合
		public void updateDealMaking(Ftrademapping ftrademapping ,Fentrust buy, Fentrust sell,
				Fentrustlog buyLog, Fentrustlog sellLog, int id) {

			
			double buyFee = 0D;
			if (buy.isFisLimit() == false ) {//limit=0
				buyFee = (buyLog.getFamount() / buy.getFamount())
						* buy.getFfees();
			}else{//limit==1
				double ffeeRate = this.getRates(buy.getFtrademapping().getFid(), true,buy.getFuser().getFscore().getFlevel()) ;
				buyFee = buyLog.getFcount()*ffeeRate ;
			}
			buyLog.setFfees(buyFee);
			if (buy.isFisLimit()) {
				buy.setFcount(buy.getFcount() + buyLog.getFcount());
				buy.setFsuccessAmount(buy.getFsuccessAmount()
						+ (buyLog.getFamount()));
				buy.setFfees(buy.getFfees()+buyFee) ;
				buy.setFlastUpdatTime(Utils.getTimestamp());
				if (buy.getFamount() - buy.getFsuccessAmount() < 0.000001D) {
					buy.setFstatus(EntrustStatusEnum.AllDeal);
				} else {
					buy.setFstatus(EntrustStatusEnum.PartDeal);
				}
			} else {
				buy.setFleftCount(buy.getFleftCount() - buyLog.getFcount());
				buy.setFsuccessAmount(buy.getFsuccessAmount()
						+ (buyLog.getFamount()));
				buy.setFlastUpdatTime(Utils.getTimestamp());
				buy.setFleftfees(buy.getFleftfees() - buyFee);
				if (buy.getFleftCount() < 0.000001D) {
					buy.setFstatus(EntrustStatusEnum.AllDeal);
				} else {
					buy.setFstatus(EntrustStatusEnum.PartDeal);
				}
			}

			double sellFee = 0D;
			if (sell.isFisLimit() == false ) {//limit==0
				sellFee = (buyLog.getFcount() / sell.getFcount())
						* sell.getFfees();
			}else{//limit==1
				double sellRate = this.getRates(sell.getFtrademapping().getFid(), false,sell.getFuser().getFscore().getFlevel()) ;
				sellFee = sellRate * sellLog.getFamount() ;
			}
			sellLog.setFfees(sellFee);
			if (sell.isFisLimit()) {
			sell.setFsuccessAmount(sell.getFsuccessAmount()
					+ buyLog.getFamount());
			sell.setFamount(sell.getFamount() + buyLog.getFamount());
			sell.setFleftCount(sell.getFleftCount() - buyLog.getFcount());
			sell.setFfees(sell.getFfees()+sellFee) ;
			sell.setFlastUpdatTime(Utils.getTimestamp());
			if (sell.getFleftCount() < 0.000001F) {
				sell.setFstatus(EntrustStatusEnum.AllDeal);
			} else {
				sell.setFstatus(EntrustStatusEnum.PartDeal);
			}
			
		} else {
			sell.setFleftCount(sell.getFleftCount() - buyLog.getFcount());
			sell.setFsuccessAmount(sell.getFsuccessAmount()
					+ (sellLog.getFamount()));
			sell.setFleftfees(sell.getFleftfees() - sellFee);
			sell.setFlastUpdatTime(Utils.getTimestamp());
			if (sell.getFleftCount() < 0.000001D) {
				sell.setFstatus(EntrustStatusEnum.AllDeal);
			} else {
				sell.setFstatus(EntrustStatusEnum.PartDeal);
			}
		}

		// 虚拟钱包
		/*Fvirtualwallet fbuyWallet = this.fvirtualWalletService.findFvirtualwallet(buy.getFuser().getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid()) ;
		Fvirtualwallet fbuyVirtualwallet = this.fvirtualwalletDAO.findVirtualWallet( buy.getFuser().getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid() );
		Fvirtualwallet fsellWallet = this.fvirtualWalletService.findFvirtualwallet(sell.getFuser().getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid()) ;
		Fvirtualwallet fsellVirtualwallet = this.fvirtualwalletDAO.findVirtualWallet( sell.getFuser().getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid() );
		*/
			
		Fvirtualwallet fbuyWallet = this.fackFvirtualwallet(buy.getFuser().getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid()) ;
		Fvirtualwallet fbuyVirtualwallet = this.fackFvirtualwallet( buy.getFuser().getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid() );
		Fvirtualwallet fsellWallet = this.fackFvirtualwallet(sell.getFuser().getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid()) ;
		Fvirtualwallet fsellVirtualwallet = this.fackFvirtualwallet( sell.getFuser().getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid() );
		
		fsellWallet = theSame(fsellWallet, fbuyWallet, fbuyVirtualwallet, fsellVirtualwallet) ;
		fbuyVirtualwallet = theSame(fbuyVirtualwallet, fbuyWallet, fsellWallet, fsellVirtualwallet) ;
		fsellVirtualwallet = theSame(fsellVirtualwallet, fbuyWallet, fbuyVirtualwallet, fsellWallet) ;

		//买法币
		fbuyWallet.setFfrozen(fbuyWallet.getFfrozen()
				- buyLog.getFamount());
		//卖法币
		fsellWallet.setFtotal(fsellWallet.getFtotal()
				+ buyLog.getFamount() - sellFee);
		//买虚拟
		fbuyVirtualwallet.setFtotal(fbuyVirtualwallet.getFtotal()
				+ buyLog.getFcount() - buyFee);
		//卖虚拟
		fsellVirtualwallet.setFfrozen(fsellVirtualwallet.getFfrozen()
				- buyLog.getFcount());
		
		

		if (buy.getFstatus() == EntrustStatusEnum.AllDeal) {
			// 因为有人低价卖出，冻结剩余部分返回钱包
			double left_amount = (buy.getFamount() - buy
					.getFsuccessAmount());
			fbuyWallet.setFfrozen(fbuyWallet.getFfrozen()
					- left_amount);
			fbuyWallet
					.setFtotal(fbuyWallet.getFtotal() + left_amount);
		}
		
		/*this.fvirtualwalletDAO.attachDirty(fsellVirtualwallet);
		this.fvirtualwalletDAO.attachDirty(fbuyVirtualwallet);
		this.fvirtualwalletDAO.attachDirty(fbuyWallet);
		this.fvirtualwalletDAO.attachDirty(fsellWallet);*/
		
		fentrustlogDAO.save(buyLog);
		fentrustlogDAO.save(sellLog);
		
		fentrustDAO.attachDirty(buy);
		fentrustDAO.attachDirty(sell);
		
		this.updateFackFvirtualwallet(fsellVirtualwallet,fbuyVirtualwallet,fbuyWallet,fsellWallet);

	}

		
		private void updateFackFvirtualwallet(Fvirtualwallet w1,Fvirtualwallet w2,Fvirtualwallet w3,Fvirtualwallet w4){
			String hql = "update Fvirtualwallet set ftotal=ftotal+? , ffrozen=ffrozen+? , version=version+1 where fuser.fid=? and fvirtualcointype.fid=?" ;
			int count = this.utilsDAO.executeHQL(hql, w1.getFtotal(),w1.getFfrozen(),w1.getFack_uid(),w1.getFack_vid()) ;
			
			if(w2.getFack_id().equals(w1.getFack_id()) == false ){
				count = this.utilsDAO.executeHQL(hql, w2.getFtotal(),w2.getFfrozen(),w2.getFack_uid(),w2.getFack_vid()) ;
			}

			if(w3.getFack_id().equals(w1.getFack_id()) == false &&w3.getFack_id().equals(w2.getFack_id()) == false ){
				count = this.utilsDAO.executeHQL(hql, w3.getFtotal(),w3.getFfrozen(),w3.getFack_uid(),w3.getFack_vid()) ;
			}

			if(w4.getFack_id().equals(w1.getFack_id()) == false &&w4.getFack_id().equals(w2.getFack_id()) == false &&w4.getFack_id().equals(w3.getFack_id()) == false ){
				count = this.utilsDAO.executeHQL(hql, w4.getFtotal(),w4.getFfrozen(),w4.getFack_uid(),w4.getFack_vid()) ;
			}
			
		}
		private Fvirtualwallet fackFvirtualwallet(int fuserid,int vid){
			Fvirtualwallet fvirtualwallet = new Fvirtualwallet() ;
			fvirtualwallet.setFack_uid(fuserid); 
			fvirtualwallet.setFack_vid(vid);
			
			fvirtualwallet.setFack_id(fuserid+"_"+vid);
			return fvirtualwallet ;
		}
		private Fvirtualwallet theSame(Fvirtualwallet v1,Fvirtualwallet v2,Fvirtualwallet v3,Fvirtualwallet v4){
			if(v1.getFack_id().equals(v2.getFack_id())){
				return v2 ;
			}
			if(v1.getFack_id().equals(v3.getFack_id())){
				return v3 ;
			}
			if(v1.getFack_id().equals(v4.getFack_id())){
				return v4 ;
			}
			return v1 ;
		}
		
	public Fentrust findFentrustById(int id) {
		return this.fentrustDAO.findById(id);
	}

	public List<Fentrustlog> findFentrustLogByFentrust(Fentrust fentrust) {
		return this.fentrustlogDAO.findByProperty("fentrust.fid",
				fentrust.getFid());
	}

	// 最新成交记录
	public List<Fentrust> findLatestSuccessDeal(int coinTypeId,
			int fentrustType, int count) {
		return this.fentrustDAO.findLatestSuccessDeal(coinTypeId, fentrustType,
				count);
	}

	public List<Fentrust> findAllGoingFentrust(int coinTypeId,
			int fentrustType, boolean isLimit) {
		return this.fentrustDAO.findAllGoingFentrust(coinTypeId, fentrustType,
				isLimit);
	}

	// 获得24小时内的成交记录
	public List<Fentrustlog> findLatestSuccessDeal24(int coinTypeId, int hour) {
		List<Fentrustlog> list = this.fentrustlogDAO.findLatestSuccessDeal24(coinTypeId, 24);
		if(list == null || list.size() == 0){
			return null;
		}
		return list;
	}

	public Fentrustlog findLatestDeal(int coinTypeId) {
		Fentrustlog fentrust = this.fentrustDAO.findLatestDeal(coinTypeId);
		if(fentrust == null) return null;
		return fentrust;
	}

	// 委托记录
	public List<Fentrust> findFentrustHistory(int fuid, int fvirtualCoinTypeId,
			int[] entrust_type, int first_result, int max_result, String order,
			int entrust_status[], Date beginDate, Date endDate)
			throws Exception {
		List<Fentrust> list = this.fentrustDAO.getFentrustHistory(fuid,
				fvirtualCoinTypeId, entrust_type, first_result, max_result,
				order, entrust_status, beginDate, endDate);
		return list;
	}

	// 计划委托
	public List<Fentrustplan> findEntrustPlan(int type, int status[]) {
		List<Fentrustplan> list = this.fentrustplanDAO.findEntrustPlan(type,
				status);

		return list;
	}

	// 委托买入
	public Fentrust updateEntrustBuy(int tradeMappingID, double tradeAmount,
			double tradeCnyPrice, Fuser fuser, boolean fisLimit,
			HttpServletRequest req) throws Exception {

		try {
			Ftrademapping mapping = this.ftrademappingDAO.findById(tradeMappingID);
			Fvirtualwallet fwallet = this.fvirtualwalletDAO.findVirtualWallet(fuser.getFid(), mapping.getFvirtualcointypeByFvirtualcointype1().getFid());

			double ffeeRate = this.ffeesDAO.findFfee(tradeMappingID,
					fuser.getFscore().getFlevel()).getFbuyfee();
			double ffee = 0F;

			// 买入总价格
			double totalTradePrice = 0F;
			if (fisLimit) {
				totalTradePrice = tradeCnyPrice;
				ffee = 0 ;
			} else {
				//总手续费人民币
				totalTradePrice = tradeAmount * tradeCnyPrice;
				ffee = tradeAmount * ffeeRate;
			}
			if(fwallet.getFtotal() < totalTradePrice){
				throw new RuntimeException();
			}
			
			fwallet.setFtotal(fwallet.getFtotal() - totalTradePrice);
			fwallet.setFfrozen(fwallet.getFfrozen()
					+ totalTradePrice);
			fwallet.setFlastUpdateTime(Utils.getTimestamp());
			this.fvirtualwalletDAO.attachDirty(fwallet);

			
			Fentrust fentrust = new Fentrust();
			
			if (fisLimit) {
				fentrust.setFcount(0F);
				fentrust.setFleftCount(0F);
				fentrust.setFprize(0);
			} else {
				fentrust.setFcount(tradeAmount);
				fentrust.setFleftCount(tradeAmount);
				fentrust.setFprize(tradeCnyPrice);
			}
			
			fentrust.setFamount(totalTradePrice);
			fentrust.setFfees(ffee);
			fentrust.setFleftfees(ffee);
			fentrust.setFcreateTime(Utils.getTimestamp());
			fentrust.setFentrustType(EntrustTypeEnum.BUY);
			fentrust.setFisLimit(fisLimit);
			fentrust.setFlastUpdatTime(Utils.getTimestamp());
			fentrust.setFstatus(EntrustStatusEnum.Going);
			fentrust.setFsuccessAmount(0F);
			fentrust.setFhasSubscription(false);
			fentrust.setFuser(fuser);
			fentrust.setFtrademapping(mapping) ;
			this.fentrustDAO.save(fentrust);
			
			this.fuserDAO.attachDirty(fuser);

			return fentrust ;
		} catch (Exception e) {
			throw new RuntimeException();
		}

	}

	// 委托卖出
	public Fentrust updateEntrustSell(int tradeMappingID, double tradeAmount,
			double tradeCnyPrice, Fuser fuser, boolean fisLimit,
			HttpServletRequest req) throws Exception {

		try {
			Ftrademapping mapping = this.ftrademappingDAO.findById(tradeMappingID);
			Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO.findVirtualWallet(fuser.getFid(), mapping.getFvirtualcointypeByFvirtualcointype2().getFid());
			if (fvirtualwallet.getFtotal() < tradeAmount) {
				throw new RuntimeException();
			}
			
			if(mapping.isFislimit()){
				if(fvirtualwallet.getFcanSellQty() <tradeAmount){
					throw new RuntimeException();
				}
				fvirtualwallet.setFcanSellQty(fvirtualwallet.getFcanSellQty()-tradeAmount);
			}

			fvirtualwallet.setFtotal(fvirtualwallet.getFtotal() - tradeAmount);
			fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen() + tradeAmount);
			fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp());
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet);

			double ffeeRate = this.ffeesDAO.findFfee(tradeMappingID,
					fuser.getFscore().getFlevel()).getFfee();
			Fentrust fentrust = new Fentrust();
			
			//总手续费人民币
			double ffee = 0 ;
			if (fisLimit) {
				fentrust.setFamount(0F);
				fentrust.setFfees(ffee);
				fentrust.setFleftfees(ffee);
			} else {
				ffee = tradeAmount*tradeCnyPrice * ffeeRate;
				fentrust.setFamount(tradeAmount* tradeCnyPrice);
				fentrust.setFfees(ffee);
				fentrust.setFleftfees(ffee);
			}
			
			fentrust.setFcount(tradeAmount);
			fentrust.setFleftCount(tradeAmount);
			fentrust.setFcreateTime(Utils.getTimestamp());
			fentrust.setFentrustType(EntrustTypeEnum.SELL);
			fentrust.setFisLimit(fisLimit);
			fentrust.setFlastUpdatTime(Utils.getTimestamp());
			fentrust.setFprize(tradeCnyPrice);
			fentrust.setFstatus(EntrustStatusEnum.Going);
			fentrust.setFsuccessAmount(0F);
			fentrust.setFuser(fuser);
			fentrust.setFhasSubscription(false);
			fentrust.setFtrademapping(mapping) ;
			this.fentrustDAO.save(fentrust);
			
			this.fuserDAO.attachDirty(fuser);

			return fentrust ;
		} catch (Exception e) {
			throw new RuntimeException();
		}

	}

	// 委托记录
	public List<Fentrust> findFentrustHistory(int firstResult, int maxResults,
			String filter, boolean isFY) throws Exception {
		List<Fentrust> list = this.fentrustDAO.list(firstResult, maxResults, filter, isFY);
		return list;
	}
	
	// 委托记录
	public List<Fentrust> findFentrustHistory(int fuid, int fvirtualCoinTypeId,
			int[] entrust_type, int first_result, int max_result, String order,
			int entrust_status[]) throws Exception {
		List<Fentrust> list = this.fentrustDAO.getFentrustHistory(fuid,
				fvirtualCoinTypeId, entrust_type, first_result, max_result,
				order, entrust_status);
		for (Fentrust fentrust : list) {
			Ftrademapping ftrademapping = fentrust.getFtrademapping() ;
			ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFname() ;
			ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFname() ;
		}
		return list;
	}

	public int findFentrustHistoryCount(int fuid, int fvirtualCoinTypeId,
			int[] entrust_type, int entrust_status[]) throws Exception {
		return this.fentrustDAO.getFentrustHistoryCount(fuid,
				fvirtualCoinTypeId, entrust_type, entrust_status);
	}

	public List<Fentrustplan> findFentrustplan(int fuser, int fvirtualcointype,
			int[] fstatus, int firtResult, int maxResult, String order) {
		return this.fentrustplanDAO.findFentrustplan(fuser, fvirtualcointype,
				fstatus, firtResult, maxResult, order);
	}

	public Fentrustplan findFentrustplanById(int id) {
		return this.fentrustplanDAO.findById(id);
	}

	public long findFentrustplanCount(int fuser, int fvirtualcointype,
			int[] fstatus) {
		return this.fentrustplanDAO.findFentrustplanCount(fuser,
				fvirtualcointype, fstatus);
	}



	public void updateCancelFentrust(Fentrust fentrust, Fuser fuser) {

		try {
			fentrust.setFlastUpdatTime(Utils.getTimestamp());
			fentrust.setFstatus(EntrustStatusEnum.Cancel);
			this.fentrustDAO.attachDirty(fentrust);

			if (fentrust.getFentrustType() == EntrustTypeEnum.BUY) {
				// 买
				Fvirtualwallet fwallet =this.fvirtualwalletDAO
						.findVirtualWallet(fuser.getFid(), fentrust.getFtrademapping().getFvirtualcointypeByFvirtualcointype1().getFid());
				double leftAmount = fentrust.getFamount()
						- fentrust.getFsuccessAmount();

				fwallet.setFtotal(fwallet.getFtotal() + leftAmount);
				fwallet.setFfrozen(fwallet.getFfrozen() - leftAmount);
				fwallet.setFlastUpdateTime(Utils.getTimestamp());
				this.fvirtualwalletDAO.attachDirty(fwallet);

			} else {
				// 卖
				Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO
						.findVirtualWallet(fuser.getFid(), fentrust.getFtrademapping().getFvirtualcointypeByFvirtualcointype2().getFid());
				double leftCount = fentrust.getFleftCount();
				fvirtualwallet.setFtotal(fvirtualwallet.getFtotal() + leftCount);
				fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen() - leftCount);
				if(fentrust.getFtrademapping().isFislimit()){
					fvirtualwallet.setFcanSellQty(fvirtualwallet.getFcanSellQty()+leftCount);
				}
				fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp());
				this.fvirtualwalletDAO.attachDirty(fvirtualwallet);

			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}


	public Fsubscription findFsubscriptionById(int id) {
		return this.fsubscriptionDAO.findById(id);
	}

	public Fsubscription findFirstSubscription(int type) {
		Fsubscription fsubscription = null;
		List<Fsubscription> fsubscriptions = this.fsubscriptionDAO.findByParam(
				0, 1, " where fisopen=1 and ftype = " + type
						+ " order by fid asc ", true, Fsubscription.class);
		if (fsubscriptions.size() > 0) {
			fsubscription = fsubscriptions.get(0);
		}
		return fsubscription;
	}

	public List<Fsubscriptionlog> findFsubscriptionlogByParam(int firstResult,
			int maxResults, String filter, boolean isFY) {
		return this.fsubscriptionlogDAO.findByParam(firstResult, maxResults,
				filter, isFY, Fsubscriptionlog.class);
	}

	public List<Fsubscriptionlog> findFsubScriptionLog(Fuser fuser, int id) {
		List<Fsubscriptionlog> fsubscriptionlogs = this.fsubscriptionlogDAO
				.findByParam(0, 0, " where fuser.fid=" + fuser.getFid()
						+ " and fsubscription.fid=" + id + " order by fid desc", false,
						Fsubscriptionlog.class);
		return fsubscriptionlogs;
	}

	public List<Fentrust> findFentrustByParam(int firstResult, int maxResults,
			String filter, boolean isFY) {
		return this.fentrustDAO.findByParam(firstResult, maxResults, filter,
				isFY, Fentrust.class);
	}

	public int findFentrustByParamCount(String filter) {
		return this.fentrustDAO.findByParamCount(filter, Fentrust.class);
	}
	
	public void updateFeeLog(Fentrust entrust,Fvirtualwallet fvirtualwallet) {
		try {
			this.fentrustDAO.attachDirty(entrust);
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	
	public void updateSubscription(Fvirtualwallet fvirtualwalletCost ,Fvirtualwallet fvirtualwalletCost2,Fvirtualwallet fvirtualwallet,
			Fsubscriptionlog fsubscriptionlog,Fsubscription fsubscription){
		try {
			if(fvirtualwalletCost != null){
				this.fvirtualwalletDAO.attachDirty(fvirtualwalletCost) ;
			}
			if(fvirtualwalletCost2 != null){
				this.fvirtualwalletDAO.attachDirty(fvirtualwalletCost2) ;
			}
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet) ;
			this.fsubscriptionDAO.attachDirty(fsubscription) ;
			this.fsubscriptionlogDAO.save(fsubscriptionlog) ;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateSubscription(Fvirtualwallet fvirtualwalletCost ,Fvirtualwallet fvirtualwallet,
			Fsubscriptionlog fsubscriptionlog,Fsubscription fsubscription){
		try {
			if(fvirtualwalletCost != null){
				this.fvirtualwalletDAO.attachDirty(fvirtualwalletCost) ;
			}
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet) ;
			this.fsubscriptionDAO.attachDirty(fsubscription) ;
			this.fsubscriptionlogDAO.save(fsubscriptionlog) ;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public List<Fentrust> findFentrustsByParam(int firstResult, int maxResults, String filter,boolean isFY){
		return this.fentrustDAO.findByParam(firstResult, maxResults, filter, isFY, Fentrust.class) ;
	}
	

	public void updateFentrust(Fentrust fentrust){
		try {
			this.fentrustDAO.attachDirty(fentrust) ;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateCoinFentrust(Fentrust fentrust,List<Fvirtualwallet> fvirtualwallets,List<Fintrolinfo> fintrolinfos){
		try {
			this.fentrustDAO.attachDirty(fentrust) ;
			for (Fintrolinfo fintrolinfo : fintrolinfos) {
				this.fintrolinfoDAO.save(fintrolinfo);
			}
			for (Fvirtualwallet fvirtualwallet : fvirtualwallets) {
				this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	// 加密
	private static final String KEY_ALGORITHM = "AES";
	private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

	private static Key toKey(byte[] key) throws Exception {
		return new SecretKeySpec(key, KEY_ALGORITHM);
	}

	private static String encrypt(String data, String key) throws Exception {
		Key k = toKey(Base64.decodeBase64(key.getBytes())); // 还原密钥
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM); // 实例化Cipher对象，它用于完成实际的加密操作
		cipher.init(Cipher.ENCRYPT_MODE, k); // 初始化Cipher对象，设置为加密模式
		return new String(Base64.encodeBase64(cipher.doFinal(data.getBytes()))); // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
	}

	private static String decrypt(String data, String key) throws Exception {
		Key k = toKey(Base64.decodeBase64(key.getBytes()));
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k); // 初始化Cipher对象，设置为解密模式
		return new String(cipher.doFinal(Base64.decodeBase64(data.getBytes()))); // 执行解密操作
	}
}
