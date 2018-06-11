package com.ruizton.main.controller.front;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.EntrustStatusEnum;
import com.ruizton.main.Enum.EntrustTypeEnum;
import com.ruizton.main.Enum.TrademappingStatusEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fentrust;
import com.ruizton.main.model.Fentrustlog;
import com.ruizton.main.model.Flimittrade;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.comm.listener.ChannelConstant;
import com.ruizton.main.service.comm.listener.MessageSender;
import com.ruizton.main.service.comm.redis.RedisUtil;
import com.ruizton.main.service.front.FrontTradeService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FtradeMappingService;
import com.ruizton.util.Utils;

import net.sf.json.JSONObject;

@Controller
public class FrontTradeJsonController extends BaseController {

	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FrontTradeService frontTradeService ;
	@Autowired
	private FtradeMappingService ftradeMappingService ;
	@Autowired
	private RedisUtil redisUtil ;
	@Autowired
	private MessageSender messageSender ;
	
	
	@ResponseBody
	@RequestMapping(value="/trade/buyBtcSubmit",produces={JsonEncode})
	public String buyBtcSubmit(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int limited,
			@RequestParam(required=true)int symbol,
			@RequestParam(required=true)double tradeAmount,
			@RequestParam(required=true)double tradeCnyPrice,
			@RequestParam(required=false,defaultValue="")String tradePwd
			) throws Exception{
		limited=0;
		
		JSONObject jsonObject = new JSONObject() ;

		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		if(ftrademapping==null  ||ftrademapping.getFstatus()==TrademappingStatusEnum.FOBBID){
			jsonObject.accumulate("resultCode", -100) ;
			jsonObject.accumulate("msg", "The currency can not be traded temporarily");
			return jsonObject.toString() ;
		}
		
		//限制法币为人民币和比特币
		if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_CNY_VALUE
				&& ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_COIN_VALUE){
			jsonObject.accumulate("resultCode", -1) ;
			jsonObject.accumulate("msg", "network error");
			return jsonObject.toString() ;
		
		}
		
		Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
		Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;
		double minBuyCount = ftrademapping.getFminBuyCount() ;
		double minBuyPrice = ftrademapping.getFminBuyPrice() ;
		double minBuyAmount = ftrademapping.getFminBuyAmount() ;
		double maxBuyCount = ftrademapping.getFmaxBuyCount() ;
		
		tradeAmount = Utils.getDouble(tradeAmount, ftrademapping.getFcount2()) ;
		tradeCnyPrice = Utils.getDouble(tradeCnyPrice, ftrademapping.getFcount1()) ;
		
		//定价单
		if(limited == 0 ){
			
			if(tradeAmount<minBuyCount){
				jsonObject.accumulate("resultCode", -1) ;
				jsonObject.accumulate("msg", "Minimum transaction volume："+coin1.getfSymbol()+minBuyCount);
				return jsonObject.toString() ;
			}
			
			if(tradeAmount>maxBuyCount){
				jsonObject.accumulate("resultCode", -1) ;
				jsonObject.accumulate("msg", "Maximum transaction volume："+coin1.getfSymbol()+maxBuyCount);
				return jsonObject.toString() ;
			}
			
			if(tradeCnyPrice < minBuyPrice){
				jsonObject.accumulate("resultCode", -3) ;
				jsonObject.accumulate("msg", "Minimum order price："+coin1.getfSymbol()+minBuyPrice);
				return jsonObject.toString() ;
			}
			
			double total = Utils.getDouble(tradeAmount*tradeCnyPrice,ftrademapping.getFcount1());
			if(total < minBuyAmount){
				jsonObject.accumulate("resultCode", -3) ;
				jsonObject.accumulate("msg", "Minimum order amount："+coin1.getfSymbol()+minBuyAmount);
				return jsonObject.toString() ;
			}
			
			Flimittrade limittrade = this.isLimitTrade(ftrademapping.getFid());
			double upPrice = 0d;
			double downPrice = 0d;
			if(limittrade != null){
				upPrice = Utils.getDouble(limittrade.getFupprice()+limittrade.getFupprice()*limittrade.getFpercent(), ftrademapping.getFcount1());
				downPrice = Utils.getDouble(limittrade.getFdownprice()-limittrade.getFdownprice()*limittrade.getFpercent(), ftrademapping.getFcount1());
				if(downPrice <0) downPrice=0;
				if(tradeCnyPrice > upPrice){
					jsonObject.accumulate("resultCode", -500) ;
					jsonObject.accumulate("msg", "Your price is not higher than the limit price:"+upPrice+coin1.getFname()) ;
					return jsonObject.toString() ; 
				}
				if(tradeCnyPrice < downPrice){
					jsonObject.accumulate("resultCode", -600) ;
					jsonObject.accumulate("msg", "Your prices are not lower than the limit price:"+downPrice+coin1.getFname()) ;
					return jsonObject.toString() ; 
				}
			}
			
		}else{
			if(tradeCnyPrice <minBuyAmount){
				jsonObject.accumulate("resultCode", -33) ;
				jsonObject.accumulate("msg", "Minimum transaction amount："+minBuyAmount+coin1.getFname());
				return jsonObject.toString() ;
			}
		}
		
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		if(Utils.openTrade(ftrademapping,Utils.getTimestamp())==false/* && !fuser.isFistiger()*/){
			jsonObject.accumulate("resultCode", -400) ;
			jsonObject.accumulate("msg", "It's not trading time");
			return jsonObject.toString() ;
		}
		
		double totalTradePrice = 0F ;
		if(limited==0){
			totalTradePrice = tradeAmount*tradeCnyPrice ;
		}else{
			totalTradePrice = tradeAmount ;
		}
		Fvirtualwallet fwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(),coin1.getFid());
		if(fwallet.getFtotal()<totalTradePrice){
			jsonObject.accumulate("resultCode", -4) ;
			jsonObject.accumulate("msg", coin1.getFname()+"Your balance is insufficient.");
			return jsonObject.toString() ;
		}
		
		if(isNeedTradePassword(request)){
			if(tradePwd == null || tradePwd.trim().length() == 0){
				jsonObject.accumulate("resultCode", -50) ;
				jsonObject.accumulate("msg", "Transaction password error");
				 return jsonObject.toString() ;
			}
			
			if(fuser.getFtradePassword() == null){
				 jsonObject.accumulate("resultCode", -5) ;
				 jsonObject.accumulate("msg", "You have not set up the transaction password. Please go to the security center<a class='text-danger' href='/user/security.html'>Security Center&gt;&gt;</a>");
				 return jsonObject.toString() ;
		    }
			if(!Utils.MD5(tradePwd,fuser.getSalt()).equals(fuser.getFtradePassword())){
				jsonObject.accumulate("resultCode", -2) ;
				jsonObject.accumulate("msg", "Transaction password error");
				return jsonObject.toString() ;
			}
		}
		
		
		String ip = getIpAddr(request) ;
		
		boolean flag = false ;
		Fentrust fentrust = null ;
		try {
			
			if(ftrademapping.getFmaxtimes() >0 && !fuser.isFistiger()){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String now = sdf.format(new Date());
				String sql = "where (fstatus in(1,2,3) or (fstatus =4 and fsuccessAmount >0)) and fuser.fid="+fuser.getFid()
						+" and ftrademapping.fid="+ftrademapping.getFid()+" and date_format(fCreateTime,'%Y-%m-%d')='"+now+"'";
				int times = this.adminService.getAllCount("Fentrust", sql);
				if(times >= ftrademapping.getFmaxtimes()){
					jsonObject.accumulate("resultCode", -600) ;
					jsonObject.accumulate("msg", "The maximum number of orders every day:"+ftrademapping.getFmaxtimes()) ;
					return jsonObject.toString() ; 
				}
			}
			
			fuser.setFlastUpdateTime(Utils.getTimestamp());
			fentrust = this.frontTradeService.updateEntrustBuy(symbol, tradeAmount, tradeCnyPrice, fuser, limited==1,request) ;
			flag = true ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(flag && fentrust != null){
			this.messageSender.publish(ChannelConstant.NEW_ENTRUSTS, String.valueOf(fentrust.getFid()));
			
			jsonObject.accumulate("resultCode", 0) ;
			jsonObject.accumulate("msg", "Transaction successfully completed.");
			setNoNeedPassword(request) ;
		}else{
			jsonObject.accumulate("resultCode", -200) ;
			jsonObject.accumulate("msg", "Network error. Please try again later");
		}
		
		return jsonObject.toString() ;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/trade/sellBtcSubmit",produces={JsonEncode})
	public String sellBtcSubmit(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int limited,//是否按照市场价买入
			@RequestParam(required=true)int symbol,//币种
			@RequestParam(required=true)double tradeAmount,//数量
			@RequestParam(required=true)double tradeCnyPrice,//单价
			@RequestParam(required=false,defaultValue="")String tradePwd
			) throws Exception{
		limited=0;
		
		JSONObject jsonObject = new JSONObject() ;
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(symbol) ;
		if(ftrademapping==null  || ftrademapping.getFstatus()!=TrademappingStatusEnum.ACTIVE){
			jsonObject.accumulate("resultCode", -100) ;
			jsonObject.accumulate("msg", "The currency can not be traded temporarily");
			return jsonObject.toString() ;
		}
		
		if(ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_CNY_VALUE
				&& ftrademapping.getFvirtualcointypeByFvirtualcointype1().getFtype() != CoinTypeEnum.FB_COIN_VALUE){
			jsonObject.accumulate("resultCode", -1) ;
			jsonObject.accumulate("msg", "network error");
			return jsonObject.toString() ;
		
		}
		
		tradeAmount = Utils.getDouble(tradeAmount, ftrademapping.getFcount2()) ;
		tradeCnyPrice = Utils.getDouble(tradeCnyPrice, ftrademapping.getFcount1()) ;
		Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
		Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;
		double minBuyCount = ftrademapping.getFminSellCount() ;
		double maxSellCount = ftrademapping.getFmaxSellCount() ;
		double minBuyPrice = ftrademapping.getFminSellPrice() ;
		double minBuyAmount = ftrademapping.getFminSellAmount() ;
		
		if(limited == 0 ){
			
			if(tradeAmount<minBuyCount){
				jsonObject.accumulate("resultCode", -1) ;
				jsonObject.accumulate("msg", "Minimum transaction volume："+coin1.getfSymbol()+minBuyCount);
				return jsonObject.toString() ;
			}
			if(tradeAmount>maxSellCount){
				jsonObject.accumulate("resultCode", -1) ;
				jsonObject.accumulate("msg", "Maximum transaction volume："+coin1.getfSymbol()+maxSellCount);
				return jsonObject.toString() ;
			}
			
			if(tradeCnyPrice < minBuyPrice){
				jsonObject.accumulate("resultCode", -3) ;
				jsonObject.accumulate("msg", "Minimum order price："+coin1.getfSymbol()+minBuyPrice);
				return jsonObject.toString() ;
			}
			double total = Utils.getDouble(tradeAmount*tradeCnyPrice,ftrademapping.getFcount1());
			if(total < minBuyAmount){
				jsonObject.accumulate("resultCode", -3) ;
				jsonObject.accumulate("msg", "Minimum order amount："+coin1.getfSymbol()+minBuyAmount);
				return jsonObject.toString() ;
			}
			Flimittrade limittrade = this.isLimitTrade(ftrademapping.getFid());
			double upPrice = 0d;
			double downPrice = 0d;
			if(limittrade != null){
				upPrice = Utils.getDouble(limittrade.getFupprice()+limittrade.getFupprice()*limittrade.getFpercent(), ftrademapping.getFcount1());
				downPrice = Utils.getDouble(limittrade.getFdownprice()-limittrade.getFdownprice()*limittrade.getFpercent(), ftrademapping.getFcount1());
				if(downPrice <0) downPrice=0;
				if(tradeCnyPrice > upPrice){
					jsonObject.accumulate("resultCode", -500) ;
					jsonObject.accumulate("msg", "Your price is not higher than the limit price:"+upPrice+coin1.getFname()) ;
					return jsonObject.toString() ; 
				}
				if(tradeCnyPrice < downPrice){
					jsonObject.accumulate("resultCode", -600) ;
					jsonObject.accumulate("msg", "Your prices are not lower than the limit price:"+downPrice+coin1.getFname()) ;
					return jsonObject.toString() ; 
				}
			}
			
		}else{
			if(tradeAmount <minBuyCount){
				jsonObject.accumulate("resultCode", -33) ;
				jsonObject.accumulate("msg", "Minimum transaction volume："+minBuyCount+coin2.getFname());
				return jsonObject.toString() ;
			}
		}
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		//是否开放交易
		if(Utils.openTrade(ftrademapping,Utils.getTimestamp())==false/* && !fuser.isFistiger()*/){
			jsonObject.accumulate("resultCode", -400) ;
			jsonObject.accumulate("msg", "It's not trading time");
			return jsonObject.toString() ;
		}
		
		
		Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), coin2.getFid()) ;
		if(fvirtualwallet==null){
			jsonObject.accumulate("resultCode", -200) ;
			jsonObject.accumulate("msg", "System error, please contact the administrator");
			return jsonObject.toString() ;
		}
		if(fvirtualwallet.getFtotal()<tradeAmount){
			jsonObject.accumulate("resultCode", -4) ;
			jsonObject.accumulate("msg", coin2.getFname()+"Your balance is insufficient.");
			return jsonObject.toString() ;
		}
		
		if(!fuser.isFistiger()){
			if(ftrademapping.isFislimit()){
				if(fvirtualwallet.getFcanSellQty() <tradeAmount){
					jsonObject.accumulate("resultCode", -4) ;
					jsonObject.accumulate("msg","You can sell at most today:"+fvirtualwallet.getFcanSellQty()) ;
					return jsonObject.toString() ;
				}
			}
		}
		
		if(isNeedTradePassword(request)){
			if(tradePwd == null || tradePwd.trim().length() == 0){
				jsonObject.accumulate("resultCode", -50) ;
				jsonObject.accumulate("msg", "Transaction password error");
				 return jsonObject.toString() ;
			}
			
			if(fuser.getFtradePassword() == null){
				 jsonObject.accumulate("resultCode", -5) ;
				 jsonObject.accumulate("msg", "You haven't set the transaction password yet，Please go to the security center<a class='text-danger' href='/user/security.html'>Security Center&gt;&gt;</a>");
				 return jsonObject.toString() ;
		    }
			if(!Utils.MD5(tradePwd,fuser.getSalt()).equals(fuser.getFtradePassword())){
				jsonObject.accumulate("resultCode", -2) ;
				jsonObject.accumulate("msg", "Transaction password error");
				return jsonObject.toString() ;
			}
		}
		

		String ip = getIpAddr(request) ;
		
		boolean flag = false ;
		Fentrust fentrust = null ;
		try {
			fuser.setFlastUpdateTime(Utils.getTimestamp());
			fentrust = this.frontTradeService.updateEntrustSell(symbol, tradeAmount, tradeCnyPrice, fuser, limited==1,request) ;
			flag = true ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(flag && fentrust != null){
			this.messageSender.publish(ChannelConstant.NEW_ENTRUSTS, String.valueOf(fentrust.getFid()));
			
			jsonObject.accumulate("resultCode", 0) ;
			jsonObject.accumulate("msg", "Transaction successfully completed.");
			setNoNeedPassword(request);
		}else{
			jsonObject.accumulate("resultCode", -200) ;
			jsonObject.accumulate("msg", "Network error. Please try again later");
		}
		
		return jsonObject.toString() ;
	}
	
	@ResponseBody
	@RequestMapping(value="/trade/cancelEntrust",produces=JsonEncode)
	public String cancelEntrust(
			HttpServletRequest request,
			@RequestParam(required=true)int id
			) throws Exception{
		
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fentrust fentrust = this.frontTradeService.findFentrustById(id) ;
		if(fentrust!=null
				&&(fentrust.getFstatus()==EntrustStatusEnum.Going || fentrust.getFstatus()==EntrustStatusEnum.PartDeal )
				&&fentrust.getFuser().getFid() == fuser.getFid() ){
			boolean flag = false ;
			try {
				this.frontTradeService.updateCancelFentrust(fentrust, fuser) ;
				flag = true ;
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(flag==true){
				this.messageSender.publish(ChannelConstant.CANCEL_ENTRUST, String.valueOf(fentrust.getFid()));
			}
		}
		
		jsonObject.accumulate("code", 0) ;
		jsonObject.accumulate("msg", "Cancel success") ;
		return jsonObject.toString() ;
	}
	
	@ResponseBody
	@RequestMapping(value="/trade/entrustLog",produces=JsonEncode)
	public String entrustLog(
			HttpServletRequest request,
			@RequestParam(required=true)int id
			) throws Exception{
		
		JSONObject jsonObject = new JSONObject() ;
		
		if(GetSession(request) == null){
			jsonObject.accumulate("result", false) ;
		}
		
		
		Fentrust fentrust = this.frontTradeService.findFentrustById(id) ;
		if(fentrust==null){
			jsonObject.accumulate("result", false) ;
		}else{
			List<Fentrustlog> fentrustlogs = this.frontTradeService.findFentrustLogByFentrust(fentrust) ;
			
			jsonObject.accumulate("result", true) ;
			jsonObject.accumulate("title", "Deal Info[OrderID:"+id+"]") ;
			
			StringBuffer content = new StringBuffer() ;
			content.append("<div> <table class=\"table\"> " +
					"<tr> " +
					"<td>Time</td> " +
					"<td>Type</td> " +
					"<td>Limit Price</td> " +
					"<td>Price</td> " +
					"<td>Executed Amount</td> " +
					"<td>Executed Price</td> " +
					"</tr>") ;
			
			if(fentrustlogs.size()==0){
				content.append("<tr><td colspan='6' class='no-data-tips'><span>No Record</span></td></tr>") ;
			}
			
			Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping2(fentrust.getFtrademapping().getFid()) ;
			Fvirtualcointype coin1 = ftrademapping.getFvirtualcointypeByFvirtualcointype1() ;
			Fvirtualcointype coin2 = ftrademapping.getFvirtualcointypeByFvirtualcointype2() ;
			
			for (Fentrustlog fentrustlog : fentrustlogs) {
				content.append("<tr> " +
									"<td class='gray'>"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fentrustlog.getFcreateTime())+"</td> " +
									"<td class='"+(fentrust.getFentrustType()==EntrustTypeEnum.BUY?"text-success":"text-danger")+"'>"+fentrust.getFentrustType_s()+"</td>" +
									"<td>"+coin1.getfSymbol()+Utils.number2String(fentrust.getFprize())+"</td>" +
									"<td>"+coin1.getfSymbol()+Utils.number2String(fentrustlog.getFprize())+"</td>" +
									"<td>"+coin2.getfSymbol()+Utils.number2String(fentrustlog.getFcount())+"</td>" +
									"<td>"+coin1.getfSymbol()+Utils.number2String(fentrustlog.getFamount())+"</td>" +
								"</tr>") ;
			}
			
			content.append("</table> </div>") ;
			jsonObject.accumulate("content", content.toString()) ;
		}
		return jsonObject.toString() ;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/trade/cancelAllEntrust",produces=JsonEncode)
	public String cancelAllEntrust(
			HttpServletRequest request,
			@RequestParam(required=true)int id,
			@RequestParam(required=true,defaultValue="0")int type
			) throws Exception{
		
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		String filter = "where fuser.fid="+fuser.getFid()+" and ftrademapping.fid="+id+" and fstatus in(1,2)";
		if(type ==1){
			filter = filter+" and fentrustType="+EntrustTypeEnum.BUY;
		}else if(type ==2){
			filter = filter+" and fentrustType="+EntrustTypeEnum.SELL;
		}
		List<Fentrust> fentrusts = this.frontTradeService.findFentrustByParam(0, 0, filter, false);
		for (Fentrust fentrust : fentrusts) {
			if(fentrust!=null
					&&(fentrust.getFstatus()==EntrustStatusEnum.Going || fentrust.getFstatus()==EntrustStatusEnum.PartDeal )
					&&fentrust.getFuser().getFid() == fuser.getFid() ){
				boolean flag = false ;
				try {
					this.frontTradeService.updateCancelFentrust(fentrust, fuser) ;
					flag = true ;
				} catch (Exception e) {
//					e.printStackTrace();
				}
				if(flag==true){
					this.messageSender.publish(ChannelConstant.CANCEL_ENTRUST, String.valueOf(fentrust.getFid()));
				}
			}
		}
		
		jsonObject.accumulate("code", 0) ;
		jsonObject.accumulate("msg", "Cancel success") ;
		return jsonObject.toString() ;
	}
	
	/*
	 * @param type:0未成交前十条，1成交前10条
	 * @param symbol:1币种
	 * */
	@ResponseBody
	@RequestMapping(value="/kline/trade_history",produces=JsonEncode)
	public String trade_history(
			HttpServletRequest request,
			@RequestParam(required=true)int symbol
			) throws Exception{
		
		JSONObject jsonObject = new JSONObject() ;
		
		int userid = 0;
		if(GetSession(request) != null){
			userid = GetSession(request).getFid();
		}
		
		Ftrademapping ftrademapping = this.ftradeMappingService.findFtrademapping(symbol) ;
		if(ftrademapping==null || ftrademapping.getFstatus()==TrademappingStatusEnum.FOBBID){
			return null;
		}
		{
			int entrust_status[] = null;
			entrust_status = new int[]{EntrustStatusEnum.Going,EntrustStatusEnum.PartDeal};
			
			List<Fentrust> fentrusts1 = null ;
			fentrusts1 = this.frontTradeService.findFentrustHistory(
					userid, symbol,null, 0, 10, " fid desc ", entrust_status) ;
	        for (Fentrust fentrust : fentrusts1) {
	        	fentrust.setFtrademapping(null);
	        	fentrust.setFuser(null);
			}
			jsonObject.accumulate("fentrusts1", fentrusts1);
		}
		{
			int entrust_status[] = null;
			entrust_status = new int[]{EntrustStatusEnum.Cancel,EntrustStatusEnum.AllDeal};
			
			List<Fentrust> fentrusts2 = null ;
			fentrusts2 = this.frontTradeService.findFentrustHistory(
					userid, symbol,null, 0, 10, " fid desc ", entrust_status) ;
	        for (Fentrust fentrust : fentrusts2) {
	        	fentrust.setFtrademapping(null);
	        	fentrust.setFuser(null);
			}
			jsonObject.accumulate("fentrusts2", fentrusts2);
		}
		jsonObject.accumulate("code", 0);
		return jsonObject.toString() ;
	}
}
