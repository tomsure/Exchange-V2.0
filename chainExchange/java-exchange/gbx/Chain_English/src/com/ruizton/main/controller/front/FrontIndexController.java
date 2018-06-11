package com.ruizton.main.controller.front;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.CoinEnum;
import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.UserStatusEnum;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.comm.KeyValues;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Farticle;
import com.ruizton.main.model.Farticletype;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.service.comm.redis.RedisConstant;
import com.ruizton.main.service.comm.redis.RedisUtil;
import com.ruizton.main.service.front.FrontOthersService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FtradeMappingService;
import com.ruizton.main.service.front.UtilsService;
import com.ruizton.util.Mobilutils;
import com.ruizton.util.Utils;

@Controller
public class FrontIndexController extends BaseController {
	
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private FtradeMappingService ftradeMappingService ;
	@Autowired
	private FrontOthersService frontOtherService ;
	@Autowired
	private ConstantMap constantMap ;
	@Autowired
	private RedisUtil redisUtil ;

	@RequestMapping(value={"/index"})
	public ModelAndView index(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int index,
			@RequestParam(required=false,defaultValue="")String forwardUrl,
			@RequestParam(required=false,defaultValue="0")int symbol,
			HttpServletResponse resp
			){
		ModelAndView modelAndView = new ModelAndView() ;


		/*if(request.getParameter("remove-m")!=null ){//moo
			request.getSession().setAttribute(Mobilutils.CONS_IS_FORCE_PC, true);
			modelAndView.setViewName("redirect:/index.html");
			return modelAndView ;
		}*/
		List<Integer> types = new ArrayList<Integer>() ;
		for (int i = CoinEnum.ONE_VALUE; i <=CoinEnum.THREE_VALUE; i++) {
			types.add(i) ;
		}
		modelAndView.addObject("types", types) ;

		//推广注册
		try{
			int id = 0 ;
			id = Integer.parseInt(request.getParameter("r")) ;
			if(id!=0){
				Fuser intro = this.frontUserService.findById(id) ;
				if(intro!=null){
					resp.addCookie(new Cookie("r", String.valueOf(id))) ;
				}
			}
		}catch(Exception e){}

		if(GetSession(request)==null){
			modelAndView.addObject("forwardUrl",forwardUrl) ;
		}else{

			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
			if(fuser.getFstatus()==UserStatusEnum.FORBBIN_VALUE){
				CleanSession(request) ;
			}
//			modelAndView.addObject("times",fuser.getFscore().getFkillQty()) ;
		}

		if(index==1){
			RemoveSecondLoginSession(request) ;
		}

		List<KeyValues> articles = new ArrayList<KeyValues>() ;
		List<Farticletype> farticletypes = this.frontOtherService.findFarticleTypeAll() ;
		for (int i = 0; i < farticletypes.size(); i++) {
			KeyValues keyValues = new KeyValues() ;
			Farticletype farticletype = farticletypes.get(i) ;
			List<Farticle> farticles = this.frontOtherService.findFarticle(farticletype.getFid(), 0, 6) ;
			keyValues.setKey(farticletype) ;
			keyValues.setValue(farticles) ;
			articles.add(keyValues) ;
		}


		modelAndView.addObject("articles", articles) ;

		Map<Fvirtualcointype, List<Ftrademapping>> fMap = new TreeMap<Fvirtualcointype, List<Ftrademapping>>(new Comparator<Fvirtualcointype>() {

			public int compare(Fvirtualcointype o1, Fvirtualcointype o2) {
				Integer type1 =Integer.parseInt(o1.getFtype()+"");
				Integer type2 =Integer.parseInt(o2.getFtype()+"");
				if(o1.getFtype() == o2.getFtype()){
					return o1.getFid().compareTo(o2.getFid());
				}else{
					return type1.compareTo(type2);
				}
			}
		}) ;
		List<Fvirtualcointype> fbs =  this.utilsService.list(0, 0, " where ftype=? or ftype=? order by fid asc ", false, Fvirtualcointype.class,CoinTypeEnum.FB_CNY_VALUE,CoinTypeEnum.FB_COIN_VALUE) ;
		for (Fvirtualcointype fvirtualcointype : fbs) {
			List<Ftrademapping> ftrademappings = this.ftradeMappingService.findActiveTradeMappingByFB(fvirtualcointype) ;
			if(ftrademappings.size()>0){
				fMap.put(fvirtualcointype, ftrademappings) ;
			}
		}
		modelAndView.addObject("fMap", fMap) ;

		int isIndex = 1;
		modelAndView.addObject("isIndex", isIndex) ;

		try{
			int alert=1;
			Cookie cs[] = request.getCookies() ;
			for (Cookie cookie : cs) {
				if(cookie.getName().endsWith("alert")){
					alert=0;
					break ;
				}
			}
			if(alert ==1){
				resp.addCookie(new Cookie("alert", String.valueOf(1))) ;
			}
			modelAndView.addObject("alert", alert) ;
		}catch(Exception e){}

		if(GetSession(request) != null){
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
		}

		modelAndView.setViewName("front"+Mobilutils.M(request)+"/index") ;
		return modelAndView ;
	}

	/**
	 * 测试首页
	 */
	@RequestMapping(value={"/indexBitbs"})
	public ModelAndView indexConF(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int index,
			@RequestParam(required=false,defaultValue="")String forwardUrl,
			@RequestParam(required=false,defaultValue="0")int symbol,
			HttpServletResponse resp
	){
		ModelAndView modelAndView = new ModelAndView() ;


		/*if(request.getParameter("remove-m")!=null ){//moo
			request.getSession().setAttribute(Mobilutils.CONS_IS_FORCE_PC, true);
			modelAndView.setViewName("redirect:/index.html");
			return modelAndView ;
		}*/
		List<Integer> types = new ArrayList<Integer>() ;
		for (int i = CoinEnum.ONE_VALUE; i <=CoinEnum.THREE_VALUE; i++) {
			types.add(i) ;
		}
		modelAndView.addObject("types", types) ;

		//推广注册
		try{
			int id = 0 ;
			id = Integer.parseInt(request.getParameter("r")) ;
			if(id!=0){
				Fuser intro = this.frontUserService.findById(id) ;
				if(intro!=null){
					resp.addCookie(new Cookie("r", String.valueOf(id))) ;
				}
			}
		}catch(Exception e){}

		if(GetSession(request)==null){
			modelAndView.addObject("forwardUrl",forwardUrl) ;
		}else{

			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
			if(fuser.getFstatus()==UserStatusEnum.FORBBIN_VALUE){
				CleanSession(request) ;
			}
//			modelAndView.addObject("times",fuser.getFscore().getFkillQty()) ;
		}

		if(index==1){
			RemoveSecondLoginSession(request) ;
		}

		List<KeyValues> articles = new ArrayList<KeyValues>() ;
		List<Farticletype> farticletypes = this.frontOtherService.findFarticleTypeAll() ;
		for (int i = 0; i < farticletypes.size(); i++) {
			KeyValues keyValues = new KeyValues() ;
			Farticletype farticletype = farticletypes.get(i) ;
			List<Farticle> farticles = this.frontOtherService.findFarticle(farticletype.getFid(), 0, 6) ;
			keyValues.setKey(farticletype) ;
			keyValues.setValue(farticles) ;
			articles.add(keyValues) ;
		}


		modelAndView.addObject("articles", articles) ;

		Map<Fvirtualcointype, List<Ftrademapping>> fMap = new TreeMap<Fvirtualcointype, List<Ftrademapping>>(new Comparator<Fvirtualcointype>() {

			public int compare(Fvirtualcointype o1, Fvirtualcointype o2) {
				Integer type1 =Integer.parseInt(o1.getFtype()+"");
				Integer type2 =Integer.parseInt(o2.getFtype()+"");
				if(o1.getFtype() == o2.getFtype()){
					return o1.getFid().compareTo(o2.getFid());
				}else{
					return type1.compareTo(type2);
				}
			}
		}) ;
		List<Fvirtualcointype> fbs =  this.utilsService.list(0, 0, " where ftype=? or ftype=? order by fid asc ", false, Fvirtualcointype.class,CoinTypeEnum.FB_CNY_VALUE,CoinTypeEnum.FB_COIN_VALUE) ;
		for (Fvirtualcointype fvirtualcointype : fbs) {
			List<Ftrademapping> ftrademappings = this.ftradeMappingService.findActiveTradeMappingByFB(fvirtualcointype) ;
			if(ftrademappings.size()>0){
				fMap.put(fvirtualcointype, ftrademappings) ;
			}
		}
		modelAndView.addObject("fMap", fMap) ;

		int isIndex = 1;
		modelAndView.addObject("isIndex", isIndex) ;

		try{
			int alert=1;
			Cookie cs[] = request.getCookies() ;
			for (Cookie cookie : cs) {
				if(cookie.getName().endsWith("alert")){
					alert=0;
					break ;
				}
			}
			if(alert ==1){
				resp.addCookie(new Cookie("alert", String.valueOf(1))) ;
			}
			modelAndView.addObject("alert", alert) ;
		}catch(Exception e){}

		if(GetSession(request) != null){
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
		}

		modelAndView.setViewName("front"+Mobilutils.M(request)+"/indexBitbs") ;
		return modelAndView ;
	}

}
