package com.ruizton.main.controller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.BankTypeEnum;
import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.VirtualCoinTypeStatusEnum;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.FbankinfoWithdraw;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.FvirtualaddressWithdraw;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.comm.redis.RedisConstant;
import com.ruizton.main.service.comm.redis.RedisUtil;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.util.Mobilutils;
import com.ruizton.util.Utils;

@Controller
public class FrontFinancialController extends BaseController {

	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private ConstantMap constantMap ;
	@Autowired
	private RedisUtil redisUtil ;
	
	@RequestMapping("/financial/index")
	public ModelAndView index(
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		//用户钱包
		Fvirtualwallet fwallet = this.frontUserService.findWalletByUser(GetSession(request).getFid()) ;
		modelAndView.addObject("fwallet", fwallet) ;
		//虚拟钱包
		Map<Integer,Fvirtualwallet> fvirtualwallets = this.frontUserService.findVirtualWallet(GetSession(request).getFid()) ;
		modelAndView.addObject("fvirtualwallets", fvirtualwallets) ; 
		//估计总资产
		//CNY+冻结CNY+（币种+冻结币种）*最高买价
		double totalCapital = 0F ;
		totalCapital+=fwallet.getFtotal()+fwallet.getFfrozen() ;
		Map<Integer,Integer> tradeMappings = (Map)this.constantMap.get("tradeMappings");
		for (Map.Entry<Integer, Fvirtualwallet> entry : fvirtualwallets.entrySet()) {
			if(entry.getValue().getFvirtualcointype().getFtype() == CoinTypeEnum.FB_CNY_VALUE) continue;
			try {
				double latestDealPrize = (Double)this.redisUtil.get(RedisConstant.getLatestDealPrizeKey(tradeMappings.get(entry.getValue().getFvirtualcointype().getFid()))) ;
				totalCapital += ( entry.getValue().getFfrozen()+entry.getValue().getFtotal() )* latestDealPrize ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		modelAndView.addObject("totalCapitalTrade", Utils.getDouble(totalCapital,2)) ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		modelAndView.addObject("fuser", fuser) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/financial/index") ;
		return modelAndView ;
	}
	
	@RequestMapping("/financial/accountbank")
	public ModelAndView accountbank(
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		modelAndView.addObject("fuser", fuser) ;
		
		Map<Integer,String> bankTypes = new HashMap<Integer,String>() ;
		for(int i=1;i<=BankTypeEnum.QT;i++){
			if(BankTypeEnum.getEnumString(i) != null && BankTypeEnum.getEnumString(i).trim().length() >0){
				bankTypes.put(i,BankTypeEnum.getEnumString(i)) ;
			}			
		}
		modelAndView.addObject("bankTypes", bankTypes) ;
		
		String filter = "where fuser.fid="+fuser.getFid()+" and fbankType >0";
		List<FbankinfoWithdraw> bankinfos = this.frontUserService.findFbankinfoWithdrawByFuser(0, 0, filter, false);
		for (FbankinfoWithdraw fbankinfoWithdraw : bankinfos) {
			try {
				int length = fbankinfoWithdraw.getFbankNumber().length();
				String number = "**** **** **** "+fbankinfoWithdraw.getFbankNumber().substring(length-4,length);
				fbankinfoWithdraw.setFbankNumber(number);
			} catch (Exception e) {}
		}
		modelAndView.addObject("bankinfos", bankinfos) ;
		
		boolean isBindGoogle = fuser.getFgoogleBind() ;
		boolean isBindTelephone = fuser.isFisTelephoneBind() ;
		modelAndView.addObject("isBindGoogle", isBindGoogle) ;
        modelAndView.addObject("isBindTelephone", isBindTelephone) ;
		
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/financial/accountbank") ;
		return modelAndView ;
	}
/*	
	@RequestMapping("/financial/accountalipay")
	public ModelAndView accountalipay(
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		modelAndView.addObject("fuser", fuser) ;
		
		String filter = "where fuser.fid="+fuser.getFid()+" and fbankType =0";
		List<FbankinfoWithdraw> bankinfos = this.frontUserService.findFbankinfoWithdrawByFuser(0, 0, filter, false);
		for (FbankinfoWithdraw fbankinfoWithdraw : bankinfos) {
			try {
				int length = fbankinfoWithdraw.getFbankNumber().length();
				String number = fbankinfoWithdraw.getFbankNumber().substring(0,3)+"****"+fbankinfoWithdraw.getFbankNumber().substring(length-4,length);
				fbankinfoWithdraw.setFbankNumber(number);
			} catch (Exception e) {}
		}
		modelAndView.addObject("bankinfos", bankinfos) ;
		
		boolean isBindGoogle = fuser.getFgoogleBind() ;
		boolean isBindTelephone = fuser.isFisTelephoneBind() ;
		modelAndView.addObject("isBindGoogle", isBindGoogle) ;
        modelAndView.addObject("isBindTelephone", isBindTelephone) ;
		
		
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/financial/accountalipay") ;
		return modelAndView ;
	}*/
	
	@RequestMapping("/financial/accountcoin")
	public ModelAndView accountcoin(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int symbol
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		if(fvirtualcointype==null ||fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal
				||fvirtualcointype.getFtype()==CoinTypeEnum.FB_CNY_VALUE
				 || !fvirtualcointype.isFIsWithDraw()){
			String filter = "where fstatus=1 and FIsWithDraw=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE+" order by fid asc";
			List<Fvirtualcointype> alls = this.virtualCoinService.list(0, 1, filter, true);
			if(alls==null || alls.size() ==0){
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
			fvirtualcointype = alls.get(0);
		}
		symbol = fvirtualcointype.getFid();
		String coinName = fvirtualcointype.getfShortName();
		
		String filter = "where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+symbol;
		List<FvirtualaddressWithdraw> alls = this.frontVirtualCoinService.findFvirtualaddressWithdraws(0, 0, filter, false);
		modelAndView.addObject("alls", alls) ;
		
		boolean isBindGoogle = fuser.getFgoogleBind() ;
		boolean isBindTelephone = fuser.isFisTelephoneBind() ;
		modelAndView.addObject("isBindGoogle", isBindGoogle) ;
        modelAndView.addObject("isBindTelephone", isBindTelephone) ;
		
		modelAndView.addObject("fuser", fuser) ;
		modelAndView.addObject("symbol", symbol) ;
		modelAndView.addObject("coinName", coinName) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/financial/accountcoin") ;
		return modelAndView ;
	}
}
