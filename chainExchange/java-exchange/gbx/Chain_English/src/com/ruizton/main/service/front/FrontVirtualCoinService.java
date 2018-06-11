package com.ruizton.main.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationInStatusEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationOutStatusEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationTypeEnum;
import com.ruizton.main.Enum.VirtualCoinTypeStatusEnum;
import com.ruizton.main.dao.FvirtualaddressDAO;
import com.ruizton.main.dao.FvirtualaddressWithdrawDAO;
import com.ruizton.main.dao.FvirtualcaptualoperationDAO;
import com.ruizton.main.dao.FvirtualcointypeDAO;
import com.ruizton.main.dao.FvirtualwalletDAO;
import com.ruizton.main.dao.FwithdrawfeesDAO;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualaddress;
import com.ruizton.main.model.FvirtualaddressWithdraw;
import com.ruizton.main.model.Fvirtualcaptualoperation;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.model.Fwithdrawfees;
import com.ruizton.util.Utils;

@Service
public class FrontVirtualCoinService {
	@Autowired
	private FvirtualcointypeDAO fvirtualcointypeDAO ;
	@Autowired
	private FwithdrawfeesDAO withdrawfeesDAO;
	@Autowired
	private FvirtualaddressDAO fvirtualaddressDAO ;
	@Autowired
	private FvirtualaddressWithdrawDAO fvirtualaddressWithdrawDAO ;
	@Autowired
	private FvirtualcaptualoperationDAO fvirtualcaptualoperationDAO ;
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO ;
	
	public void updateWithdrawBtc(FvirtualaddressWithdraw fvirtualaddressWithdraw,Fvirtualcointype fvirtualcointype,Fvirtualwallet fvirtualwallet ,double withdrawAmount,double ffees,Fuser fuser){
		try {
			fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()-withdrawAmount) ;
			fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen()+withdrawAmount) ;
			fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp()) ;
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet) ;
			
			Fvirtualcaptualoperation fvirtualcaptualoperation = new Fvirtualcaptualoperation() ;
			fvirtualcaptualoperation.setFamount(withdrawAmount-ffees) ;
			fvirtualcaptualoperation.setFcreateTime(Utils.getTimestamp()) ;
			fvirtualcaptualoperation.setFfees(ffees) ;
			fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp()) ;
			fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationOutStatusEnum.WaitForOperation) ;
			fvirtualcaptualoperation.setFtype(VirtualCapitalOperationTypeEnum.COIN_OUT) ;
			fvirtualcaptualoperation.setFuser(fuser) ;
			fvirtualcaptualoperation.setFvirtualcointype(fvirtualcointype) ;
			fvirtualcaptualoperation.setWithdraw_virtual_address(fvirtualaddressWithdraw.getFadderess()) ;
			this.fvirtualcaptualoperationDAO.save(fvirtualcaptualoperation) ;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public List<Fvirtualcointype> findFvirtualCoinType(int status,int coinType){
		List<Fvirtualcointype> list = this.fvirtualcointypeDAO.findByParam(0, 0, " where fstatus="+status+" and ftype ="+coinType+" order by fid asc ", false, Fvirtualcointype.class) ;
		return list ;
	}

	/**
	 * 查询以太坊一条记录
	 * @return
	 */
	public List<Fvirtualcointype> findFvirtualCoinTypeEth(){
		List<Fvirtualcointype> list = this.fvirtualcointypeDAO.findByParam(0, 0, " where fisEth=1 order by fid asc ", false, Fvirtualcointype.class) ;
		return list ;
	}

	public Fvirtualcointype findFvirtualCoinById(int id){
		Fvirtualcointype fvirtualcointype = this.fvirtualcointypeDAO.findById(id) ;
		return fvirtualcointype ;
	}
	
	public Fvirtualcointype findFirstFirtualCoin(){
		Fvirtualcointype fvirtualcointype = null ;
		String filter ="where fstatus="+VirtualCoinTypeStatusEnum.Normal+" and ftype="+CoinTypeEnum.COIN_VALUE;
		List<Fvirtualcointype> list = this.fvirtualcointypeDAO.list(0, 0, filter, false);
		if(list.size()>0){
			fvirtualcointype = list.get(0) ;
		}else{
			fvirtualcointype = (Fvirtualcointype)this.fvirtualcointypeDAO.findAll(CoinTypeEnum.FB_CNY_VALUE,1).get(0);
		}
		return fvirtualcointype ;
	}
	
	public Fvirtualcointype findFirstFirtualCoin_Wallet(){
		Fvirtualcointype fvirtualcointype = null ;
		String filter = "where fstatus="+VirtualCoinTypeStatusEnum.Normal+" and FIsWithDraw=1";
		List<Fvirtualcointype> list = this.fvirtualcointypeDAO.list(0, 0, filter, false);
		if(list.size()>0){
			fvirtualcointype = list.get(0) ;
		}
		return fvirtualcointype ;
	}
	
	public Fvirtualaddress findFvirtualaddress(Fuser fuser,Fvirtualcointype fvirtualcointype){
		return this.fvirtualaddressDAO.findFvirtualaddress(fuser, fvirtualcointype) ;
	}
	
	public List<Fvirtualaddress> findFvirtualaddress(Fvirtualcointype fvirtualcointype,String address){
		return this.fvirtualaddressDAO.findFvirtualaddress(fvirtualcointype, address) ;
	}
	
	public FvirtualaddressWithdraw findFvirtualaddressWithdraw(int fid){
		return this.fvirtualaddressWithdrawDAO.findById(fid);
	}
	
	public List<FvirtualaddressWithdraw> findFvirtualaddressWithdraws(int firstResult, int maxResults,
			String filter,boolean isFY) {
		return this.fvirtualaddressWithdrawDAO.list(firstResult, maxResults, filter,isFY);
	}
	
	public int findFvirtualcaptualoperationCount(
			Fuser fuser,int type[],int status[],Fvirtualcointype[] fvirtualcointypes,String order){
		return this.fvirtualcaptualoperationDAO.findFvirtualcaptualoperationCount(fuser, type, status, fvirtualcointypes, order) ;
	}
	
	public List<Fvirtualcaptualoperation> findFvirtualcaptualoperations(int firstResult, int maxResults,String filter, boolean isFY){
		return this.fvirtualcaptualoperationDAO.findByParam(firstResult, maxResults, filter, isFY, Fvirtualcaptualoperation.class) ;
	}
	public int findFvirtualcaptualoperationsCount(String filter){
		return this.fvirtualcaptualoperationDAO.findByParamCount(filter, Fvirtualcaptualoperation.class) ;
	}
	
	public void updateFvirtualaddressWithdraw(FvirtualaddressWithdraw fvirtualaddressWithdraw){
		this.fvirtualaddressWithdrawDAO.save(fvirtualaddressWithdraw) ;
	}
	
	public void updateDelFvirtualaddressWithdraw(FvirtualaddressWithdraw fvirtualaddressWithdraw){
		this.fvirtualaddressWithdrawDAO.delete(fvirtualaddressWithdraw) ;
	}
	
	public Fwithdrawfees findFfees(int virtualCoinTypeId,int level){
		return this.withdrawfeesDAO.findFfee(virtualCoinTypeId, level) ;
	}
	
	public void updateWithdrawBtc(FvirtualaddressWithdraw fvirtualaddressWithdraw,Fvirtualcointype fvirtualcointype,
			Fvirtualwallet fvirtualwallet ,double withdrawAmount,Fuser fuser,double curPrice){
		try {
			fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()-withdrawAmount) ;
			fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen()+withdrawAmount) ;
			fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp()) ;
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet) ;
			
			double feeRate = this.withdrawfeesDAO.findFfee(fvirtualcointype.getFid(), fuser.getFscore().getFlevel()).getFfee();
			Fvirtualcaptualoperation fvirtualcaptualoperation = new Fvirtualcaptualoperation() ;
			fvirtualcaptualoperation.setFamount(withdrawAmount*(1-feeRate)) ;
			fvirtualcaptualoperation.setFcreateTime(Utils.getTimestamp()) ;
			fvirtualcaptualoperation.setFfees(withdrawAmount*feeRate) ;
			fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp()) ;
			fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationOutStatusEnum.WaitForOperation) ;
			fvirtualcaptualoperation.setFtype(VirtualCapitalOperationTypeEnum.COIN_OUT) ;
			fvirtualcaptualoperation.setFuser(fuser) ;
			fvirtualcaptualoperation.setFremark("价格："+curPrice);
			fvirtualcaptualoperation.setFvirtualcointype(fvirtualcointype) ;
			fvirtualcaptualoperation.setWithdraw_virtual_address(fvirtualaddressWithdraw.getFadderess()) ;
			this.fvirtualcaptualoperationDAO.save(fvirtualcaptualoperation) ;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void addFvirtualcaptualoperation(Fvirtualcaptualoperation fvirtualcaptualoperation){
		this.fvirtualcaptualoperationDAO.save(fvirtualcaptualoperation) ;
	}
	
	public List<Fvirtualcaptualoperation> findFvirtualcaptualoperationByProperty(String key,Object value){
		return this.fvirtualcaptualoperationDAO.findByProperty(key, value) ;
	}
	
	public Fvirtualcaptualoperation findFvirtualcaptualoperationById(int id){
		return this.fvirtualcaptualoperationDAO.findById(id) ;
	}
	
	//比特币自动充值并加币
	public void updateFvirtualcaptualoperationCoinIn(Fvirtualcaptualoperation fvirtualcaptualoperation) throws Exception{
		try {
			Fvirtualcaptualoperation real = this.fvirtualcaptualoperationDAO.findById(fvirtualcaptualoperation.getFid()) ;
			if(real!=null && real.getFstatus()!=VirtualCapitalOperationInStatusEnum.SUCCESS){
				real.setFstatus(fvirtualcaptualoperation.getFstatus()) ;
				real.setFconfirmations(fvirtualcaptualoperation.getFconfirmations()) ;
				real.setFlastUpdateTime(Utils.getTimestamp()) ;
				real.setFamount(fvirtualcaptualoperation.getFamount());
				this.fvirtualcaptualoperationDAO.attachDirty(real) ;
				
				if(real.getFstatus()==VirtualCapitalOperationInStatusEnum.SUCCESS && real.isFhasOwner()){
					Fvirtualcointype fvirtualcointype = this.fvirtualcointypeDAO.findById(real.getFvirtualcointype().getFid()) ;
					Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO.findVirtualWallet(real.getFuser().getFid(), fvirtualcointype.getFid()) ;
					fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+ real.getFamount()) ;
					fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp()) ;
					this.fvirtualwalletDAO.attachDirty(fvirtualwallet) ;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	//比特币自动充值并加币
	public void updateFvirtualcaptualoperationCoinInxx(Fvirtualcaptualoperation fvirtualcaptualoperation) throws Exception{
		try {
			fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp());
			this.fvirtualcaptualoperationDAO.save(fvirtualcaptualoperation);
			if(fvirtualcaptualoperation.getFstatus()==VirtualCapitalOperationInStatusEnum.SUCCESS && fvirtualcaptualoperation.isFhasOwner()){
				Fvirtualcointype fvirtualcointype = this.fvirtualcointypeDAO.findById(fvirtualcaptualoperation.getFvirtualcointype().getFid()) ;
				Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO.findVirtualWallet(fvirtualcaptualoperation.getFuser().getFid(), fvirtualcointype.getFid()) ;
				fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+ fvirtualcaptualoperation.getFamount()) ;
				fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp()) ;
				this.fvirtualwalletDAO.attachDirty(fvirtualwallet) ;
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public List<Fvirtualaddress> findFvirtualaddressByProperty(String key,Object value){
		List<Fvirtualaddress> fvirtualaddresses = this.fvirtualaddressDAO.findByProperty(key, value) ;
		for (Fvirtualaddress fvirtualaddress : fvirtualaddresses) {
			fvirtualaddress.getFuser().getFnickName() ;
		}
		return fvirtualaddresses ;
	}
	
	public boolean isExistsCanWithdrawCoinType(){
		List<Fvirtualcointype> fvirtualcointypes = this.fvirtualcointypeDAO.findByParam(0, 0, " where FIsWithDraw=1 and fstatus=1 ", false, Fvirtualcointype.class) ;
		return fvirtualcointypes.size()>0 ;
	}
	
}
