package com.ruizton.main.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.TrademappingStatusEnum;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.main.service.front.FtradeMappingService;
import com.ruizton.main.service.front.UtilsService;
import com.ruizton.util.Mobilutils;

@Controller
public class FrontMarketController extends BaseController {

	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private ConstantMap constantMap;
	@Autowired
	private FtradeMappingService ftradeMappingService ;
	@Autowired
	private UtilsService utilsService ;
	
	/*
	 * https://www.okcoin.com/market.do?symbol=0
	 * */
	@RequestMapping("/market")
	public ModelAndView market(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int symbol
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		List<Ftrademapping> ftrademappings = this.utilsService.list1(0, 0, " where fstatus=? order by fvirtualcointypeByFvirtualcointype1.ftype asc" , false, Ftrademapping.class,TrademappingStatusEnum.ACTIVE) ;
		if(ftrademapping == null || ftrademapping.getFstatus() != TrademappingStatusEnum.ACTIVE){
			if(ftrademappings.size()>0){
				modelAndView.setViewName("redirect:/market.html?symbol="+ftrademappings.get(0).getFid()) ;
				return modelAndView ;
			}
		}
		
		modelAndView.addObject("ftrademappings", ftrademappings) ;
		modelAndView.addObject("ftrademapping", ftrademapping) ;
		modelAndView.addObject("symbol", symbol) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/market/market") ;
		return modelAndView ;
	}
	
	
	@RequestMapping("/trademarket")
	public ModelAndView trademarket(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int symbol
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		List<Ftrademapping> ftrademappings = this.utilsService.list1(0, 0, " where fstatus=?" , false, Ftrademapping.class,TrademappingStatusEnum.ACTIVE) ;
		if(ftrademapping==null || ftrademapping.getFstatus() != TrademappingStatusEnum.ACTIVE){
			if(ftrademappings.size()>0){
				modelAndView.setViewName("redirect:/trademarket.html?symbol="+ftrademappings.get(0).getFid()) ;
				return modelAndView ;
			}else{
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
		}
		
//		Flimittrade limittrade = this.isLimitTrade(ftrademapping.getFid());
//		boolean isLimittrade = false;
//		double upPrice = 0d;
//		double downPrice = 0d;
//		if(limittrade != null){
//			isLimittrade = true;
//			upPrice = Utils.getDouble(limittrade.getFupprice()+limittrade.getFupprice()*limittrade.getFpercent(), ftrademapping.getFcount1());
//			downPrice = Utils.getDouble(limittrade.getFdownprice()-limittrade.getFdownprice()*limittrade.getFpercent(), ftrademapping.getFcount1());
//			if(downPrice <0) downPrice=0;
//		}
//		modelAndView.addObject("isLimittrade", isLimittrade) ;
//		modelAndView.addObject("upPrice", upPrice) ;
//		modelAndView.addObject("downPrice", downPrice) ;
		
		String userid = "";
		boolean tradePassword = false;
		boolean isTelephoneBind = false;
		int login = 0;
		if(GetSession(request) != null){
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
			userid = String.valueOf(fuser.getFid());
			if(fuser.isFisTelephoneBind()){
				isTelephoneBind = true;
			}
			if(fuser.getFtradePassword() != null && fuser.getFtradePassword().trim().length() >0){
				tradePassword = true;
			}
			login = 1;
			modelAndView.addObject("fuser", fuser) ;
		}
		modelAndView.addObject("userid", userid) ;
		modelAndView.addObject("tradePassword", tradePassword) ;
		modelAndView.addObject("isTelephoneBind", isTelephoneBind) ;
		modelAndView.addObject("login", login) ;
		
		//是否需要输入交易密码
		modelAndView.addObject("needTradePasswd", super.isNeedTradePassword(request)) ;
		
		modelAndView.addObject("ftrademappings", ftrademappings) ;
		modelAndView.addObject("ftrademapping", ftrademapping) ;
		modelAndView.addObject("symbol", symbol) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/market/trademarket") ;
		return modelAndView ;
	}
	
	@RequestMapping("/kline/fullstart")
	public ModelAndView start(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int symbol
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		List<Ftrademapping> ftrademappings = this.utilsService.list1(0, 1, " where fstatus=? " , true, Ftrademapping.class,TrademappingStatusEnum.ACTIVE) ;

		
		if(ftrademapping==null ){
			if(ftrademappings.size()>0){
				modelAndView.setViewName("redirect:/kline/fullstart.html?symbol="+ftrademappings.get(0).getFid()) ;
				return modelAndView ;
			}else{
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
		}
		
		modelAndView.addObject("ftrademapping", ftrademapping) ;
		modelAndView.addObject("ftrademappings", ftrademappings) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/market/start") ;
		return modelAndView ;
	}
	
	@RequestMapping("/kline/trade")
	public ModelAndView trade(
			HttpServletRequest request,
			@RequestParam(required=true )int id
			) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(id) ;
		modelAndView.addObject("ftrademapping", ftrademapping) ;
		
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/market/kline") ;
		return modelAndView ;
	}
}
