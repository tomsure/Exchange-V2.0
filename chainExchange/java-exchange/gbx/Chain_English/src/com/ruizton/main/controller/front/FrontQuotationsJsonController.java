package com.ruizton.main.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruizton.main.Enum.TrademappingStatusEnum;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Ftradehistory;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.service.comm.redis.RedisConstant;
import com.ruizton.main.service.comm.redis.RedisUtil;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.main.service.front.FtradeMappingService;
import com.ruizton.main.service.front.UtilsService;
import com.ruizton.util.Utils;

import net.sf.json.JSONObject;

@Controller
public class FrontQuotationsJsonController extends BaseController {
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private ConstantMap constantMap ;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private FtradeMappingService ftradeMappingService ;
	@Autowired
	private RedisUtil redisUtil ;
	
	@ResponseBody
	@RequestMapping(value="/real/price",produces={JsonEncode})
	public String priceMarket(){
		JSONObject jsonObject = new JSONObject() ;
		
		double latestDealPrize = (Double)this.redisUtil.get(RedisConstant.getLatestDealPrizeKey(1)) ;
		jsonObject.accumulate("result", true);
		jsonObject.accumulate("last", latestDealPrize);
	    return jsonObject.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/real/indexmarket",produces={JsonEncode})
	public String indexmarket(){
		JSONObject jsonObject = new JSONObject() ;
		List<Ftrademapping> ftrademappings = this.utilsService.list1(0, 0, " where fstatus=? order by fid asc ", false, Ftrademapping.class,TrademappingStatusEnum.ACTIVE) ;
		for (Ftrademapping ftrademapping : ftrademappings) {
			JSONObject js = new JSONObject() ;
			double price = (Double)this.redisUtil.get(RedisConstant.getLatestDealPrizeKey(ftrademapping.getFid())) ;
			
			js.accumulate("symbol", ftrademapping.getFvirtualcointypeByFvirtualcointype1().getfSymbol());
			js.accumulate("price", price);
			js.accumulate("total", this.redisUtil.get(RedisConstant.getTotal(ftrademapping.getFid())));
			js.accumulate("amt",this.redisUtil.get(RedisConstant.get24Total(ftrademapping.getFid())));
			js.accumulate("max", Utils.getDouble(price*ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFtotalqty(),2));
			double s = (Double)this.redisUtil.get(RedisConstant.getstart24Price(ftrademapping.getFid())) ; 
			double s7 = -8888d;
			
			List<Ftradehistory> ftradehistorys = (List<Ftradehistory>)constantMap.get("tradehistory");
			for (Ftradehistory ftradehistory : ftradehistorys) {
				if(ftradehistory.getFtrademapping().getFid().intValue() == ftrademapping.getFid().intValue()){
					s= ftradehistory.getFprice();
					break;
				}
			}
			List<Ftradehistory> ftradehistoryss = (List<Ftradehistory>)constantMap.get("ftradehistory7D");
			for (Ftradehistory ftradehistory : ftradehistoryss) {
				if(ftradehistory.getFtrademapping().getFid().intValue() == ftrademapping.getFid().intValue()){
					s7= ftradehistory.getFprice();
					break;
				}
			}
			
			double last = 0d;
			double last7 = 0d;
			try {
				last = Utils.getDouble((price-s)/s*100, 2);
			} catch (Exception e) {}
			try {
				if(s7==-8888d){
					last7=0d;
				}else{
					last7 = Utils.getDouble((price-s7)/s7*100, 2);
				}
			} catch (Exception e) {}
			js.accumulate("rose", last);
			js.accumulate("rose7",last7) ;
			jsonObject.accumulate(String.valueOf(ftrademapping.getFid()), js);
		}
		
		return jsonObject.toString();
	}
	
	
	@ResponseBody
	@RequestMapping("/real/userassets")
	public String userassets(
			HttpServletRequest request,
			@RequestParam(required=true)int symbol
			) throws Exception {
		JSONObject jsonObject = new JSONObject() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping(symbol) ;
		Fuser fuser = GetSession(request) ;
		if(fuser==null){
			//可用
			jsonObject.accumulate("availableCny", 0) ;
			jsonObject.accumulate("availableCoin", 0) ;
			jsonObject.accumulate("frozenCny", 0) ;
			jsonObject.accumulate("frozenCoin", 0) ;
			//借貸明細
			JSONObject leveritem = new JSONObject() ;
			leveritem.accumulate("total", 0) ;
			leveritem.accumulate("asset", 0) ;
			leveritem.accumulate("score", 0) ;
			jsonObject.accumulate("leveritem", leveritem) ;
			//人民幣明細
			JSONObject cnyitem = new JSONObject() ;
			cnyitem.accumulate("total", 0) ;
			cnyitem.accumulate("frozen",0) ;
			cnyitem.accumulate("borrow", 0) ;
			jsonObject.accumulate("cnyitem", cnyitem) ;
			//人民幣明細
			JSONObject coinitem = new JSONObject() ;
			coinitem.accumulate("id", symbol) ;
			coinitem.accumulate("total", 0) ;
			coinitem.accumulate("frozen",0) ;
			coinitem.accumulate("borrow", 0) ;
			jsonObject.accumulate("coinitem", coinitem) ;
		}else{
			fuser = this.frontUserService.findById(fuser.getFid()) ;
			Fvirtualwallet fwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(),ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFid());
			Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid()) ;
			
			//可用
			jsonObject.accumulate("availableCny", fwallet.getFtotal()) ;
			jsonObject.accumulate("availableCoin", fvirtualwallet.getFtotal()) ;
			jsonObject.accumulate("frozenCny", fwallet.getFfrozen()) ;
			jsonObject.accumulate("frozenCoin", fvirtualwallet.getFfrozen()) ;
			//借貸明細
			JSONObject leveritem = new JSONObject() ;
			leveritem.accumulate("total", 0) ;
			leveritem.accumulate("asset", 0) ;
			leveritem.accumulate("score", 0) ;
			jsonObject.accumulate("leveritem", leveritem) ;
			//人民幣明細
			JSONObject cnyitem = new JSONObject() ;
			cnyitem.accumulate("total", fwallet.getFtotal()) ;
			cnyitem.accumulate("frozen", fwallet.getFfrozen()) ;
			cnyitem.accumulate("borrow", 0) ;
			jsonObject.accumulate("cnyitem", cnyitem) ;
			//人民幣明細
			JSONObject coinitem = new JSONObject() ;
			coinitem.accumulate("id", symbol) ;
			coinitem.accumulate("total", fvirtualwallet.getFtotal()) ;
			coinitem.accumulate("frozen", fvirtualwallet.getFfrozen()) ;
			coinitem.accumulate("borrow", 0) ;
			jsonObject.accumulate("coinitem", coinitem) ;
		}

	    
		
		return jsonObject.toString() ;
		
	}
	
}
