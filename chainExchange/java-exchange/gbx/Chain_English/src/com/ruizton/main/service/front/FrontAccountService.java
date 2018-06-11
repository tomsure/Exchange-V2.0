package com.ruizton.main.service.front;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruizton.main.Enum.CapitalOperationOutStatus;
import com.ruizton.main.Enum.CapitalOperationTypeEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationOutStatusEnum;
import com.ruizton.main.dao.FcapitaloperationDAO;
import com.ruizton.main.dao.FscoreDAO;
import com.ruizton.main.dao.FsystemargsDAO;
import com.ruizton.main.dao.FuserDAO;
import com.ruizton.main.dao.FvirtualcaptualoperationDAO;
import com.ruizton.main.dao.FvirtualwalletDAO;
import com.ruizton.main.dao.FwithdrawfeesDAO;
import com.ruizton.main.model.FbankinfoWithdraw;
import com.ruizton.main.model.Fcapitaloperation;
import com.ruizton.main.model.Fscore;
import com.ruizton.main.model.Fsystemargs;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualcaptualoperation;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.model.Fwithdrawfees;
import com.ruizton.util.Utils;

@Service
public class FrontAccountService {
	private static final Logger log = LoggerFactory.getLogger(FrontAccountService.class);
	@Autowired
	private FcapitaloperationDAO fcapitaloperationDAO ;
	@Autowired
	private FscoreDAO fscoreDAO ;
	@Autowired
	private FuserDAO fuserDAO ;
	@Autowired
	private FvirtualwalletDAO fvirtualwalletDAO ;
	@Autowired
	private FvirtualcaptualoperationDAO fvirtualcaptualoperationDAO ;
	@Autowired
	private FsystemargsDAO fsystemargsDAO ;
	@Autowired
	private FwithdrawfeesDAO withdrawfeesDAO;
	
	public void addFcapitaloperation(Fcapitaloperation fcapitaloperation){
		this.fcapitaloperationDAO.save(fcapitaloperation) ;
	}
	
	public List<Fcapitaloperation> findCapitalList(int firstResult, int maxResults,String filter,boolean isFY){
		return this.fcapitaloperationDAO.findByParam(firstResult, maxResults, filter,isFY) ;
	}
	
	public int findCapitalCount(Map<String, Object> param){
		return this.fcapitaloperationDAO.findByParamCount(param) ;
	}
	
	public Fcapitaloperation findCapitalOperationById(int id){
		Fcapitaloperation fcapitaloperation = this.fcapitaloperationDAO.findById(id) ;
		return fcapitaloperation ;
	}
	
	public void updateCapitalOperation(Fcapitaloperation fcapitaloperation){
		this.fcapitaloperationDAO.attachDirty(fcapitaloperation) ;
	}
	
	public void updateSaveCapitalOperation(Fcapitaloperation fcapitaloperation){
		this.fcapitaloperationDAO.save(fcapitaloperation) ;
	}
	
	public boolean updateWithdrawCNY(double withdrawBanlance,Fuser fuser,FbankinfoWithdraw fbankinfoWithdraw) throws Exception{
		boolean flag = false ;
		try {
			Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO.findWallet(fuser.getFid());
			if(fvirtualwallet.getFtotal()<withdrawBanlance){
				return false ;
			}
			
			Fwithdrawfees ffees = this.withdrawfeesDAO.findFfee(fvirtualwallet.getFvirtualcointype().getFid(), fuser.getFscore().getFlevel());
			double feesRate = ffees.getFfee();
			double amt = Utils.getDouble(withdrawBanlance*(1.0F-feesRate),2);
			double fees = Utils.getDouble(withdrawBanlance*feesRate,2);
			if(withdrawBanlance <= ffees.getFamt() && ffees.getFamt() >0){
				fees = ffees.getFlastFee();
			}
			amt = Utils.getDouble(withdrawBanlance-fees,2);
			Fcapitaloperation fcapitaloperation = new Fcapitaloperation() ;
			fcapitaloperation.setfBank(fbankinfoWithdraw.getFname()) ;
			fcapitaloperation.setfAccount(fbankinfoWithdraw.getFbankNumber()) ;
			fcapitaloperation.setFaddress(fbankinfoWithdraw.getFaddress()+fbankinfoWithdraw.getFothers());

			fcapitaloperation.setFamount(amt) ;
			fcapitaloperation.setFfees(fees) ;
			fcapitaloperation.setFcreateTime(Utils.getTimestamp()) ;
			fcapitaloperation.setFtype(CapitalOperationTypeEnum.RMB_OUT) ;
			fcapitaloperation.setFuser(fuser) ;
			fcapitaloperation.setfLastUpdateTime(Utils.getTimestamp()) ;
			fcapitaloperation.setfPhone(fuser.getFtelephone()) ;
			fcapitaloperation.setfPayee(fuser.getFrealName()) ;
			fcapitaloperation.setFremittanceType(null) ;
			fcapitaloperation.setFone(true);
			fcapitaloperation.setFstatus(CapitalOperationOutStatus.WaitForOperation) ;
			this.fcapitaloperationDAO.save(fcapitaloperation) ;
				
			fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()-amt-fees);
			fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen()+amt+fees);
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
			flag = true ;
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return flag ;
	}
	
	public Fscore findFscoreById(int id){
		return this.fscoreDAO.findById(id) ;
	}
	
	public void updateCancelWithdrawCny(Fcapitaloperation fcapitaloperation,Fuser fuser){
		try {
			fcapitaloperation.setFstatus(CapitalOperationOutStatus.Cancel) ;
			fcapitaloperation.setfLastUpdateTime(Utils.getTimestamp()) ;
			Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO.findWallet(fuser.getFid());
			fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen()-fcapitaloperation.getFfees()-fcapitaloperation.getFamount());
			fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+fcapitaloperation.getFfees()+fcapitaloperation.getFamount());
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet);
			this.fcapitaloperationDAO.attachDirty(fcapitaloperation) ;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void updateCancelWithdrawBtc(Fvirtualcaptualoperation fvirtualcaptualoperation,Fuser fuser){
		try {
			fvirtualcaptualoperation.setFstatus(VirtualCapitalOperationOutStatusEnum.Cancel) ;
			fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp()) ;
			
			double amount = fvirtualcaptualoperation.getFamount()+fvirtualcaptualoperation.getFfees() ;
			Fvirtualwallet fvirtualwallet = this.fvirtualwalletDAO.findVirtualWallet(fuser.getFid(), fvirtualcaptualoperation.getFvirtualcointype().getFid()) ;
			fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+amount) ;
			fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen()-amount) ;
			fvirtualwallet.setFlastUpdateTime(Utils.getTimestamp()) ;
			
			this.fvirtualwalletDAO.attachDirty(fvirtualwallet) ;
			this.fvirtualcaptualoperationDAO.attachDirty(fvirtualcaptualoperation) ;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public int getTodayCnyWithdrawTimes(Fuser fuser){
		return this.fcapitaloperationDAO.getTodayCnyWithdrawTimes(fuser) ;
	}
	
	public int getTodayVirtualCoinWithdrawTimes(Fuser fuser){
		return this.fvirtualcaptualoperationDAO.getTodayVirtualCoinWithdrawTimes(fuser) ;
	}
	
}
