package com.ruizton.main.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.EntrustStatusEnum;
import com.ruizton.main.Enum.TrademappingStatusEnum;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fentrust;
import com.ruizton.main.model.Flimittrade;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.EntrustService;
import com.ruizton.main.service.admin.UserService;
import com.ruizton.main.service.comm.redis.RedisConstant;
import com.ruizton.main.service.comm.redis.RedisUtil;
import com.ruizton.main.service.front.FrontTradeService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.main.service.front.FtradeMappingService;
import com.ruizton.main.service.front.UtilsService;
import com.ruizton.util.Mobilutils;
import com.ruizton.util.PaginUtil;
import com.ruizton.util.Utils;

@Controller
public class FrontTradeController extends BaseController {
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private FrontTradeService frontTradeService ;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private UserService userService;
	@Autowired
	private ConstantMap constantMap ;
	@Autowired
	private FtradeMappingService ftradeMappingService ;
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private EntrustService entrustService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private RedisUtil redisUtil ;
	
	@RequestMapping("/trade/coin")
	public ModelAndView coin(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int coinType,
			@RequestParam(required=false,defaultValue="0")int tradeType
			) throws Exception{
		
		
		ModelAndView modelAndView = new ModelAndView() ;
		int userid = 0;
		Fuser fuser = null;
		boolean isTelephoneBind =false;
		if(GetSession(request) != null){
			fuser = this.userService.findById(GetSession(request).getFid());
			userid = fuser.getFid();
			isTelephoneBind = fuser.isFisTelephoneBind();
		}
		
		
		tradeType = tradeType < 0? 0: tradeType ;
		tradeType = tradeType > 1? 1: tradeType ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(coinType) ;
		List<Ftrademapping> ftrademappings = null ;
		if(ftrademapping==null ||ftrademapping.getFstatus()==TrademappingStatusEnum.FOBBID ){
			ftrademappings = this.utilsService.list(0, 1, " where fstatus=? order by fid asc ", true, Ftrademapping.class,TrademappingStatusEnum.ACTIVE) ;
			if(ftrademappings.size()>0){
				modelAndView.setViewName("redirect:/trade/coin.html?coinType="+ftrademappings.get(0).getFid()+"&tradeType=0") ;
				return modelAndView ;
			}else{
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
		}
		
		//限制法币为人民币和比特币
		if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_CNY_VALUE
				&& ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_COIN_VALUE){
			modelAndView.setViewName("redirect:/") ;
			return modelAndView ;
		}
		
		ftrademappings = this.ftradeMappingService.findActiveTradeMappingByFB(ftrademapping.getFvirtualcointypeByFvirtualcointype1()) ;
		Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
		Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;
		
		Flimittrade limittrade = this.isLimitTrade(ftrademapping.getFid());
		boolean isLimittrade = false;
		double upPrice = 0d;
		double downPrice = 0d;
		if(limittrade != null){
			isLimittrade = true;
			upPrice = Utils.getDouble(limittrade.getFupprice()+limittrade.getFupprice()*limittrade.getFpercent(), ftrademapping.getFcount1());
			downPrice = Utils.getDouble(limittrade.getFdownprice()-limittrade.getFdownprice()*limittrade.getFpercent(), ftrademapping.getFcount1());
			if(downPrice <0) downPrice=0;
		}
		modelAndView.addObject("isLimittrade", isLimittrade) ;
		modelAndView.addObject("upPrice", upPrice) ;
		modelAndView.addObject("downPrice", downPrice) ;
		
		boolean isTradePassword = false;
		if(userid !=0 && fuser.getFtradePassword() != null && fuser.getFtradePassword().trim().length() >0){
			isTradePassword = true;
		}
		
		List<Fentrust> fentrusts = this.frontTradeService.findFentrustHistory(
				userid, coinType,null, 0, 10, " fid desc ", new int[]{EntrustStatusEnum.Going,EntrustStatusEnum.PartDeal}) ;
		
		modelAndView.addObject("needTradePasswd", super.isNeedTradePassword(request)) ;
				
		modelAndView.addObject("fentrusts", fentrusts) ;
		modelAndView.addObject("fuser", fuser) ;
		modelAndView.addObject("userid", userid) ;
		modelAndView.addObject("isTradePassword", isTradePassword) ;
		modelAndView.addObject("isTelephoneBind", isTelephoneBind) ;
		modelAndView.addObject("recommendPrizesell", this.redisUtil.get(RedisConstant.getHighestBuyPrizeKey(coinType))) ;
		modelAndView.addObject("recommendPrizebuy", this.redisUtil.get(RedisConstant.getLowestSellPrizeKey(coinType))) ;
		modelAndView.addObject("coin1",coin1) ;
		modelAndView.addObject("coin2",coin2) ;
		modelAndView.addObject("ftrademappings",ftrademappings) ;
		modelAndView.addObject("ftrademapping",ftrademapping) ;
		modelAndView.addObject("coinType", coinType) ;
		modelAndView.addObject("tradeType", tradeType) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/trade/trade_coin") ;
		return modelAndView ;
	}
	
	/*https://www.okcoin.com/trade/entrust.do?symbol=1
	 * */
	@RequestMapping("/trade/entrust")
	public ModelAndView entrust(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int symbol,
			@RequestParam(required=false,defaultValue="0")int status,
			@RequestParam(required=false,defaultValue="1")int currentPage
			)throws Exception{
		
		
		ModelAndView modelAndView = new ModelAndView() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		if(ftrademapping != null){	
			modelAndView.addObject("ftrademapping", ftrademapping) ;
		}else{
			symbol = 0;
		}
			
		String filter = "where fuser.fid="+GetSession(request).getFid();
		if(status==0){
			//正在委托
			filter =filter+" and fstatus in (1,2)";
		}else{
			//委托完成
			filter =filter+" and fstatus in (3,4)";
		}

		if(symbol != 0){
			filter =filter+" and ftrademapping.fid="+symbol;
		}
		filter = filter+" order by fid desc";
		List<Fentrust> fentrusts = this.entrustService.list((currentPage-1)*maxResults, maxResults, filter, true);
					
//					
//					this.frontTradeService.findFentrustHistory(
//							GetSession(request).getFid(), 
//							ftrademapping.getFid(),
//							null, PaginUtil.firstResult(currentPage, maxResults), 
//							maxResults, 
//							"id desc ", fstatus) ;
			
			int total = this.adminService.getAllCount("Fentrust", filter);
			String pagin = PaginUtil.generatePagin((int)(total/maxResults+(total%maxResults==0?0:1) ), currentPage, "/trade/entrust.html?symbol="+symbol+"&status="+status+"&") ;

			modelAndView.addObject("currentPage", currentPage) ;
			modelAndView.addObject("pagin",pagin) ;
			modelAndView.addObject("fentrusts", fentrusts) ;
		
		modelAndView.addObject("status", status) ;
		modelAndView.addObject("symbol", symbol) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/trade/trade_entrust") ;
		return modelAndView ;
	}
	
	
	/*
	 * http://localhost:8899/trade/entrustInfo.html?type=0&random=74&_=1393130976495
	 * */
	/*
	 * @param type:0未成交前十条，1成交前10条
	 * @param symbol:1币种
	 * */
	@RequestMapping("/trade/entrustInfo")
	public ModelAndView entrustInfo(
			HttpServletRequest request,
			@RequestParam(required=true)int type,
			@RequestParam(required=true)int symbol,
			@RequestParam(required=true)int tradeType
			) throws Exception{
		
		
		ModelAndView modelAndView = new ModelAndView() ;
		
		int userid = 0;
		if(GetSession(request) != null){
			userid = GetSession(request).getFid();
		}
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		if(ftrademapping==null || ftrademapping.getFstatus()==TrademappingStatusEnum.FOBBID){
			modelAndView.setViewName("redirect:/") ;
			return modelAndView ;
		}
		Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
		Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;

		
		
		List<Fentrust> fentrusts1 = null ;
		fentrusts1 = this.frontTradeService.findFentrustHistory(
				userid, symbol,null, 0, 10, " fid desc ", new int[]{EntrustStatusEnum.Going,EntrustStatusEnum.PartDeal}) ;
		
		List<Fentrust> fentrusts2 = null ;
		fentrusts2 = this.frontTradeService.findFentrustHistory(
				userid, symbol,null, 0, 10, " fid desc ", new int[]{EntrustStatusEnum.Cancel,EntrustStatusEnum.AllDeal}) ;
		
		if(GetSession(request) == null){
			modelAndView.addObject("ispost", false) ;
		}else {
			modelAndView.addObject("ispost", GetSession(request).getFpostRealValidate()) ;
		}
		
		modelAndView.addObject("ftrademapping", ftrademapping) ;
		modelAndView.addObject("coin1", coin1) ;
		modelAndView.addObject("coin2", coin2) ;
		modelAndView.addObject("tradeType", tradeType) ;
		modelAndView.addObject("symbol",symbol) ;
		modelAndView.addObject("type", type) ;
		modelAndView.addObject("fentrusts1", fentrusts1) ;
		modelAndView.addObject("fentrusts2", fentrusts2) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/trade/entrust_info") ;
		return modelAndView ;
	}
}
