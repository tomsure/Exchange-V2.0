package com.ruizton.main.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Ftradehistory;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.service.comm.redis.RedisConstant;
import com.ruizton.main.service.comm.redis.RedisUtil;
import com.ruizton.main.service.front.FtradeMappingService;
import com.ruizton.util.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class FrontMarketJsonController extends BaseController {

	@Autowired
	private ConstantMap constantMap ;
	@Autowired
	private FtradeMappingService ftradeMappingService ;
	@Autowired
	private RedisUtil redisUtil ;
	
	//交易中心
	@ResponseBody
	@RequestMapping(value="/real/market",produces={JsonEncode})
	public String marketRefresh(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int symbol
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping(symbol) ;
		if(ftrademapping == null ){
			return null ;
		}
		
		jsonObject.accumulate("p_new", this.redisUtil.get(RedisConstant.getLatestDealPrizeKey(ftrademapping.getFid())));
		jsonObject.accumulate("high", this.redisUtil.get(RedisConstant.getHighestPrizeKey(ftrademapping.getFid())));
		jsonObject.accumulate("low", this.redisUtil.get(RedisConstant.getLowestPrizeKey(ftrademapping.getFid())));
		jsonObject.accumulate("vol", this.redisUtil.get(RedisConstant.getTotal(ftrademapping.getFid())));
		jsonObject.accumulate("buy1", this.redisUtil.get(RedisConstant.getHighestBuyPrizeKey(ftrademapping.getFid())));
		jsonObject.accumulate("sell1", this.redisUtil.get(RedisConstant.getLowestSellPrizeKey(ftrademapping.getFid())));		
		
		JSONArray sellDepthList = JSONArray.fromObject(this.redisUtil.get(RedisConstant.getDepthKey(ftrademapping.getFid(), false, 20))) ;
		jsonObject.accumulate("sells", sellDepthList);
		
		
		JSONArray buyDepthList = JSONArray.fromObject(this.redisUtil.get(RedisConstant.getDepthKey(ftrademapping.getFid(), true, 20))) ;
		jsonObject.accumulate("buys", buyDepthList);
		
		JSONArray recentDealList =JSONArray.fromObject(this.redisUtil.get(RedisConstant.getSuccessKey(ftrademapping.getFid(), 20))) ;
		jsonObject.accumulate("trades", recentDealList);
		
		return jsonObject.toString() ;
	}
	
	
	//K线中心
	@ResponseBody
	@RequestMapping(value="/real/market2",produces={JsonEncode})
	public String marketRefresh2(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int symbol
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping(symbol) ;
		if(ftrademapping == null ){
			return null ;
		}
		
		jsonObject.accumulate("p_new", JSONArray.fromObject(this.redisUtil.get(RedisConstant.getLatestDealPrizeKey(ftrademapping.getFid()))));
		jsonObject.accumulate("high", JSONArray.fromObject(this.redisUtil.get(RedisConstant.getHighestPrizeKey(ftrademapping.getFid()))));
		jsonObject.accumulate("low", JSONArray.fromObject(this.redisUtil.get(RedisConstant.getLowestPrizeKey(ftrademapping.getFid()))));
		jsonObject.accumulate("vol", JSONArray.fromObject(this.redisUtil.get(RedisConstant.getTotal(ftrademapping.getFid()))));
		jsonObject.accumulate("buy1", JSONArray.fromObject(this.redisUtil.get(RedisConstant.getHighestBuyPrizeKey(ftrademapping.getFid()))));
		jsonObject.accumulate("sell1", JSONArray.fromObject(this.redisUtil.get(RedisConstant.getLowestSellPrizeKey(ftrademapping.getFid()))));
		
		double s = (Double)this.redisUtil.get(RedisConstant.getstart24Price(ftrademapping.getFid())) ;
		List<Ftradehistory> ftradehistorys = (List<Ftradehistory>)constantMap.get("tradehistory");
		for (Ftradehistory ftradehistory : ftradehistorys) {
			if(ftradehistory.getFtrademapping().getFid().intValue() == ftrademapping.getFid().intValue()){
				s= ftradehistory.getFprice();
				break;
			}
		}
		double last = 0d;
		try {
			double LatestDealPrize = (Double)this.redisUtil.get(RedisConstant.getLatestDealPrizeKey(ftrademapping.getFid())) ;;
			last = 
					Utils.getDouble((LatestDealPrize-s)/s*100, 2);
		} catch (Exception e) {}
		jsonObject.accumulate("rose", last);
		
		JSONArray sellDepthList = JSONArray.fromObject(this.redisUtil.get(RedisConstant.getDepthKey(ftrademapping.getFid(), false, 20))) ;
		jsonObject.accumulate("sells", sellDepthList);
		
		
		JSONArray buyDepthList = JSONArray.fromObject(this.redisUtil.get(RedisConstant.getDepthKey(ftrademapping.getFid(), true, 20))) ;
		jsonObject.accumulate("buys", buyDepthList);
		
		JSONArray recentDealList =JSONArray.fromObject(this.redisUtil.get(RedisConstant.getSuccessKey(ftrademapping.getFid(), 20))) ;
		jsonObject.accumulate("trades", recentDealList);
		
		return jsonObject.toString() ;
	}
	
	@ResponseBody
	@RequestMapping("/kline/fullperiod")
	public String period(
			@RequestParam(required=true)int step,
			@RequestParam(required=true)int symbol
			) throws Exception{
		
		int key = 0 ;
		switch (step) {
		case 60:
			key = 0 ;
			break;
		case 60*3:
			key = 1 ;
			break;
		case 60*5:
			key = 2 ;
			break;
		case 60*15:
			key = 3 ;
			break;
		case 60*30:
			key = 4 ;
			break;
		case 60*60:
			key = 5 ;
			break;
		case 60*60*2:
			key = 6 ;
			break;
		case 60*60*4:
			key = 7 ;
			break;
		case 60*60*6:
			key = 8 ;
			break;
		case 60*60*12:
			key = 9 ;
			break;
		case 60*60*24:
			key = 10 ;
			break;
		case 60*60*24*3:
			key = 11 ;
			break;
		case 60*60*24*7:
			key = 12 ;
			break;
		}
		String ret = (String)this.redisUtil.get(RedisConstant.getfullperiodKey(symbol, key)) ;
		return ret ;
	}
	
	@ResponseBody
	@RequestMapping("/kline/fulldepth")
	public String depth(
			@RequestParam(required=true)int symbol,
			@RequestParam(required=true)int step
			) throws Exception{
		
		
		JSONObject jsonObject = new JSONObject() ;
		String depth = (String)this.redisUtil.get(RedisConstant.getaskBidJsonKey(symbol)) ;
		jsonObject.accumulate("depth", depth) ;
		JSONObject period = JSONObject.fromObject(this.redisUtil.get(RedisConstant.getperiodJsonKey(symbol, (int)(step/60)))) ;
		period.accumulate("data", this.redisUtil.get(RedisConstant.getlatestKlinePeroidKey(symbol, (int)(step/60)))) ;
		jsonObject.accumulate("period", period) ;		
		String  ret = jsonObject.toString() ;
		return ret ;
	}
	
	@ResponseBody
	@RequestMapping("/kline/trade_json")
	public String trade_json(
			@RequestParam(required=true )int id
			) throws Exception {
		
		StringBuffer content = new StringBuffer() ;
		content.append(
				"chart_1h = {symbol:\"BTC_CNY\",symbol_view:\"CNY/CNY\",ask:1.2,time_line:"+this.redisUtil.get(RedisConstant.getIndexJsonKey(id, 5)) +"};"
				) ;
		
		content.append(
				"chart_5m = {time_line:"+this.redisUtil.get(RedisConstant.getIndexJsonKey(id, 2)) +"};" 
				) ;
		content.append(
				"chart_15m = {time_line:"+this.redisUtil.get(RedisConstant.getIndexJsonKey(id, 3)) +"};"
				) ;
		content.append(
				"chart_30m = {time_line:"+this.redisUtil.get(RedisConstant.getIndexJsonKey(id, 4)) +"};"
				) ;
		content.append(
				"chart_8h = {time_line:"+this.redisUtil.get(RedisConstant.getIndexJsonKey(id, 8)) +"};"
				) ;
		content.append(
				"chart_1d = {time_line:"+this.redisUtil.get(RedisConstant.getIndexJsonKey(id, 10)) +"};"
				) ;
		
		return content.toString() ;
	}
}
