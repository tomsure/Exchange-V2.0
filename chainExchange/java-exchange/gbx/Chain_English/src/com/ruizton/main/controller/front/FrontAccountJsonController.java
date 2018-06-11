package com.ruizton.main.controller.front;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.ruizton.main.Enum.CapitalOperationInStatus;
import com.ruizton.main.Enum.CapitalOperationOutStatus;
import com.ruizton.main.Enum.CapitalOperationTypeEnum;
import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.CountLimitTypeEnum;
import com.ruizton.main.Enum.MessageTypeEnum;
import com.ruizton.main.Enum.RemittanceTypeEnum;
import com.ruizton.main.Enum.SystemBankInfoEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationOutStatusEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationTypeEnum;
import com.ruizton.main.Enum.VirtualCoinTypeStatusEnum;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.comm.ValidateMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.FbankinfoWithdraw;
import com.ruizton.main.model.Fcapitaloperation;
import com.ruizton.main.model.Fpool;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualaddress;
import com.ruizton.main.model.FvirtualaddressWithdraw;
import com.ruizton.main.model.Fvirtualcaptualoperation;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.model.Systembankinfo;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.PoolService;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.admin.VirtualaddressService;
import com.ruizton.main.service.comm.redis.RedisConstant;
import com.ruizton.main.service.comm.redis.RedisUtil;
import com.ruizton.main.service.front.FrontAccountService;
import com.ruizton.main.service.front.FrontBankInfoService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontValidateService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.util.Constant;
import com.ruizton.util.GoogleAuth;
import com.ruizton.util.Utils;

import net.sf.json.JSONObject;

@Controller
public class FrontAccountJsonController extends BaseController{

	@Autowired
	private FrontAccountService frontAccountService ;
	@Autowired
	private FrontBankInfoService frontBankInfoService ;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontValidateService frontValidateService ;
	@Autowired
	private ValidateMap messageValidateMap ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private ConstantMap map;
	@Autowired
	private AdminService adminService;
	@Autowired
	private ValidateMap validateMap ;
	@Autowired
	private ConstantMap constantMap;
	@Autowired
	private PoolService poolService;
	@Autowired
	private VirtualaddressService virtualaddressService;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private RedisUtil redisUtil ;
	
	@RequestMapping(value="/account/alipayManual",produces = {JsonEncode})
	@ResponseBody
	public String alipayManual(
			HttpServletRequest request,
			@RequestParam(required=true) double money,
			@RequestParam(required=true) double cnymoney,
			@RequestParam(required=true) int type,
			@RequestParam(required=true) int sbank
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		money = Utils.getDouble(money, 2);
		double minRecharge = Double.parseDouble(this.map.get("minrechargecny").toString()) ;
		if(money < minRecharge){
			//非法
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", "Minimum recharge amount is$"+minRecharge);
			return jsonObject.toString();
		}
		
		if(type != 0){
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", "Illegal operation");
			return jsonObject.toString();
		}
		
		Systembankinfo systembankinfo = this.frontBankInfoService.findSystembankinfoById(sbank) ;
		if(systembankinfo==null || systembankinfo.getFstatus()==SystemBankInfoEnum.ABNORMAL ){
			//银行账号停用
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", "The bank account does not exist");
			return jsonObject.toString();
		}
		
		String filter = "where fuser.fid="+GetSession(request).getFid()+" and ftype=1 and fstatus in(1,2)";
		int count = this.adminService.getAllCount("Fcapitaloperation", filter);
		if(count >0){
			//银行账号停用
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", "You have an incomplete recharge record");
			return jsonObject.toString();
		}
		
		Fcapitaloperation fcapitaloperation = new Fcapitaloperation() ;
		fcapitaloperation.setFamount(money) ;
		fcapitaloperation.setfCnyAmount(cnymoney);
		fcapitaloperation.setSystembankinfo(systembankinfo) ;
		fcapitaloperation.setFcreateTime(Utils.getTimestamp()) ;
		fcapitaloperation.setFtype(CapitalOperationTypeEnum.RMB_IN) ;
		fcapitaloperation.setFuser(this.GetSession(request)) ;
		fcapitaloperation.setFstatus(CapitalOperationInStatus.NoGiven) ;
		fcapitaloperation.setFremittanceType(systembankinfo.getFbankName());
		
		this.frontAccountService.addFcapitaloperation(fcapitaloperation) ;
		
		jsonObject.accumulate("code", 0);
		jsonObject.accumulate("money", String.valueOf(fcapitaloperation.getFamount())) ;
		jsonObject.accumulate("tradeId", fcapitaloperation.getFid()) ;
		jsonObject.accumulate("fbankName", systembankinfo.getFbankName()) ;
		jsonObject.accumulate("fownerName", systembankinfo.getFownerName()) ;
		jsonObject.accumulate("fbankAddress", systembankinfo.getFbankAddress()) ;
		jsonObject.accumulate("fbankNumber", systembankinfo.getFbankNumber()) ;
		return jsonObject.toString() ;  
	}
	
	
	@ResponseBody
	@RequestMapping(value="/account/rechargeCnySubmit",produces={JsonEncode})
	public String rechargeCnySubmit(
			HttpServletRequest request,
			@RequestParam(required=false) String bank,
			@RequestParam(required=false) String account,
			@RequestParam(required=false) String payee,
			@RequestParam(required=false) String phone,
			@RequestParam(required=false) int type,
			@RequestParam(required=true) int desc//记录的id
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(desc) ;
		if(fcapitaloperation==null ||fcapitaloperation.getFuser().getFid() !=GetSession(request).getFid() ){
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", "Illegal operation");
			return jsonObject.toString();
		}
		
		if(fcapitaloperation.getFstatus() != CapitalOperationInStatus.NoGiven
				|| fcapitaloperation.getFtype() != CapitalOperationTypeEnum.RMB_IN){
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", "Network anomaly");
			return jsonObject.toString();
		}
		
//		fcapitaloperation.setfBank(bank) ;
//		fcapitaloperation.setfAccount(account) ;
//		fcapitaloperation.setfPayee(payee) ;
//		fcapitaloperation.setfPhone(phone) ;
		fcapitaloperation.setFstatus(CapitalOperationInStatus.WaitForComing) ;
		fcapitaloperation.setFischarge(false);
		fcapitaloperation.setfLastUpdateTime(Utils.getTimestamp());
		try {
			this.frontAccountService.updateCapitalOperation(fcapitaloperation) ;
			jsonObject.accumulate("code", 0);
			jsonObject.accumulate("msg", "Success");
		} catch (Exception e) {
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", "Network anomaly");
		}
		
		return jsonObject.toString();
	}
	

	@ResponseBody
	@RequestMapping(value="/account/alipayTransfer",produces={JsonEncode})
	public String alipayTransfer(
			HttpServletRequest request,
			@RequestParam(required=true) double money,
			@RequestParam(required=true) String accounts,
			@RequestParam(required=true) String imageCode,
			@RequestParam(required=true) int type
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		accounts= HtmlUtils.htmlEscape(accounts.trim());
		money = Utils.getDouble(money, 2);
		if(type !=3 && type !=4){
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", "Illegal operation");
			return jsonObject.toString();
		}
		if(accounts.length() >100){
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", "Account error");
			return jsonObject.toString();
		}
		double minRecharge = Double.parseDouble(this.map.get("minrechargecny").toString()) ;
		if(money < minRecharge){
			//非法
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", "Minimum recharge amount is $"+minRecharge);
			return jsonObject.toString();
		}
		
		if(vcodeValidate(request, imageCode) == false ){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Please enter the correct picture verification code");
			return jsonObject.toString() ;
		}
 
//		if(type ==3){
//			jsonObject.accumulate("code", 0);
//			jsonObject.accumulate("msg", "操作成功");
//			jsonObject.accumulate("payId", GetSession(request).getFid());
//			return jsonObject.toString();
//		}else{
			Fcapitaloperation fcapitaloperation = new Fcapitaloperation();
			fcapitaloperation.setFuser(GetSession(request));
			fcapitaloperation.setFamount(money);
			fcapitaloperation.setFremittanceType(RemittanceTypeEnum.getEnumString(type));
			fcapitaloperation.setfBank(RemittanceTypeEnum.getEnumString(type)) ;
			fcapitaloperation.setfAccount(accounts) ;
			fcapitaloperation.setfPayee(null) ;
			fcapitaloperation.setfPhone(null) ;
			fcapitaloperation.setFstatus(CapitalOperationInStatus.WaitForComing) ;
			fcapitaloperation.setFcreateTime(Utils.getTimestamp());
			fcapitaloperation.setfLastUpdateTime(Utils.getTimestamp());
			fcapitaloperation.setFtype(CapitalOperationTypeEnum.RMB_IN);
			fcapitaloperation.setFfees(0d);
			fcapitaloperation.setFischarge(false);
			this.frontAccountService.addFcapitaloperation(fcapitaloperation);
			jsonObject.accumulate("code", 0);
			jsonObject.accumulate("msg", "Successful operation");
			jsonObject.accumulate("payId", fcapitaloperation.getFid());
			return jsonObject.toString();
//		}

	}
	
	/*
	 * var param={tradePwd:tradePwd,withdrawBalance:withdrawBalance,phoneCode:phoneCode,totpCode:totpCode};
	 * 
	 * */
	@ResponseBody
	@RequestMapping("/account/withdrawCnySubmit")
	public String withdrawCnySubmit(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")String tradePwd,
			@RequestParam(required=true)double withdrawBalance,
			@RequestParam(required=false,defaultValue="0")String phoneCode,
			@RequestParam(required=false,defaultValue="0")String totpCode,
			@RequestParam(required=true)int withdrawBlank
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		//最大提现人民币
		double max_double = Double.parseDouble(this.map.get("maxwithdrawcny").toString()) ;
		double min_double = Double.parseDouble(this.map.get("minwithdrawcny").toString()) ;
		
		if(withdrawBalance<min_double){
			//提现金额不能小于10
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg","The amount of cash payable must not be less than"+min_double) ;
			return jsonObject.toString() ;
		}
		
		
		if(withdrawBalance>max_double){
			//提现金额不能大于指定值
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg","The amount of cash payable must not exceed "+max_double) ;
			return jsonObject.toString() ;
		}
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		int time = this.frontAccountService.getTodayCnyWithdrawTimes(fuser) ;
		int VirtualCNYWithdrawTimes = Integer.parseInt(this.map.getString("VirtualCNYWithdrawTimes"));
		if(time>=VirtualCNYWithdrawTimes){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg","Allow withdrawals every day："+VirtualCNYWithdrawTimes+" times！") ;
			return jsonObject.toString() ;
		}
		
		Fvirtualwallet fwallet = this.frontUserService.findWalletByUser(fuser.getFid());
		FbankinfoWithdraw fbankinfoWithdraw = this.frontUserService.findByIdWithBankInfos(withdrawBlank);
		if(fbankinfoWithdraw == null || fbankinfoWithdraw.getFuser().getFid() != fuser.getFid()){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg","Illegal withdrawals of accounts") ;
			return jsonObject.toString() ;
		}
		
		if(fwallet.getFtotal()<withdrawBalance){
			//资金不足
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg","Your balance is insufficient") ;
			return jsonObject.toString() ;
		}
		
		if(fuser.getFtradePassword()==null){
			//没有设置交易密码
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg","Please set the transaction password first") ;
			return jsonObject.toString() ;
		}
		
		if(!fuser.getFgoogleBind() && !fuser.isFisTelephoneBind()){
			//没有绑定谷歌或者手机
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg","Please bind the GOOGLE to verify or bind the phone number first") ;
			return jsonObject.toString() ;
		}
		
		String ip = getIpAddr(request) ;
		if(fuser.getFtradePassword()!=null){
			int trade_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
			if(trade_limit<=0){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg","This IP operation is frequent. Please try again after 2 hours!") ;
				return jsonObject.toString() ;
			}else{
				boolean flag = fuser.getFtradePassword().equals(Utils.MD5(tradePwd,fuser.getSalt())) ;
				if(!flag){
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg","Transaction password error,you still have "+trade_limit+" chances.") ;
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
					return jsonObject.toString() ;
				}else if(trade_limit<new Constant().ErrorCountLimit){
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
				}
			}
		}
		
		if(fuser.getFgoogleBind()){
			int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
			if(google_limit<=0){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg","This IP operation is frequent. Please try again after 2 hours!") ;
				return jsonObject.toString() ;
			}else{
				boolean flag = GoogleAuth.auth(Long.parseLong(totpCode.trim()), fuser.getFgoogleAuthenticator()) ;
				if(!flag){
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg","Google authentication code error,you still have "+google_limit+" chances") ;
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
					return jsonObject.toString() ;
				}else if(google_limit<new Constant().ErrorCountLimit){
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
				}
			}
		}
		
		if(fuser.isFisTelephoneBind()){
			int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
			if(tel_limit<=0){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg","This IP operation is frequent. Please try again after 2 hours!") ;
				return jsonObject.toString() ;
			}else{
				boolean flag = validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.CNY_TIXIAN, phoneCode) ;
				if(!flag){
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg","Mobile phone verification code error,you still have "+tel_limit+" chance") ;
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
					return jsonObject.toString() ;
				}else if(tel_limit<new Constant().ErrorCountLimit){
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
				}
			}
		}
		
		boolean withdraw = false ;
		try {
			withdraw = this.frontAccountService.updateWithdrawCNY(withdrawBalance, fuser,fbankinfoWithdraw) ;
		} catch (Exception e) {}finally{
			this.messageValidateMap.removeMessageMap(MessageTypeEnum.getEnumString(MessageTypeEnum.CNY_TIXIAN)) ;
			this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.CNY_TIXIAN);
		}
		
		if(withdraw){
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", "Success in cash withdrawal") ;
		}else{
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Network anomaly") ;
		}
		
		return jsonObject.toString() ;
	}
	


	@ResponseBody
	@RequestMapping(value="/account/withdrawBtcSubmit",produces={JsonEncode})
	public String withdrawBtcSubmit(
			HttpServletRequest request,
			@RequestParam(required=true)int withdrawAddr,
			@RequestParam(required=true)double withdrawAmount,
			@RequestParam(required=true)String tradePwd,
			@RequestParam(required=false,defaultValue="0")String totpCode,
			@RequestParam(required=false,defaultValue="0")String phoneCode,
			@RequestParam(required=true)int symbol
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		if(fvirtualcointype==null 
				|| !fvirtualcointype.isFIsWithDraw() 
				|| fvirtualcointype.getFtype()==CoinTypeEnum.FB_CNY_VALUE
				|| fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Illegal operation") ;
			return jsonObject.toString() ;
		}
		Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid()) ;
		FvirtualaddressWithdraw fvirtualaddressWithdraw = this.frontVirtualCoinService.findFvirtualaddressWithdraw(withdrawAddr);
		if(fvirtualaddressWithdraw == null
				|| fvirtualaddressWithdraw.getFuser().getFid() != fuser.getFid()
				|| fvirtualaddressWithdraw.getFvirtualcointype().getFid() != symbol){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Illegal operation") ;
			return jsonObject.toString() ;
		}
		
		if(!fuser.isFisTelephoneBind() && !fuser.getFgoogleBind()){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Please bind Google authentication or cell phone number first") ;
			return jsonObject.toString() ;
		}
		
		int time = this.frontAccountService.getTodayVirtualCoinWithdrawTimes(fuser) ;
		int VirtualCoinWithdrawTimes = Integer.parseInt(this.map.getString("VirtualCoinWithdrawTimes"));
		if(time>=VirtualCoinWithdrawTimes){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg","Allow withdrawals every day："+VirtualCoinWithdrawTimes+" times！") ;
			return jsonObject.toString() ;
		}
		
		String ip = getIpAddr(request) ;
		int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE ) ;
		int mobil_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
		
		if(fuser.getFtradePassword()==null){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Please set the transaction password first") ;
			return jsonObject.toString() ;
		}else{
			int trade_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
			if(trade_limit<=0){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "This IP operation is frequent. Please try again after 2 hours!") ;
				return jsonObject.toString() ;
			}
			
			if(!fuser.getFtradePassword().equals(Utils.MD5(tradePwd,fuser.getSalt()))){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "Transaction password error,you still have "+trade_limit+" chances") ;
				this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
				return jsonObject.toString() ;
			}else if(trade_limit<new Constant().ErrorCountLimit){
				this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TRADE_PASSWORD) ;
			}
		}
		

		double max_double = Double.parseDouble(this.map.get("maxwithdrawbtc").toString()) ;
		double min_double = Double.parseDouble(this.map.get("minwithdrawbtc").toString()) ;
		if(withdrawAmount<min_double){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "The minimum number of withdrawals is："+min_double) ;
			return jsonObject.toString() ;
		}
		
		if(withdrawAmount>max_double){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "The maximum number of withdrawals is："+max_double) ;
			return jsonObject.toString() ;
		}
		
		//余额不足
		if(fvirtualwallet.getFtotal()<withdrawAmount){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Your balance is insufficient") ;
			return jsonObject.toString() ;
		}
		
		String filter = "where fadderess='"+fvirtualaddressWithdraw.getFadderess()+"'";
		int cc = this.adminService.getAllCount("Fvirtualaddress", filter);
		if(cc >0){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "The members in the station are not allowed to transfer each other") ;
			return jsonObject.toString() ;
		}
		
		if(fuser.getFgoogleBind()){
			if(google_limit<=0){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "This IP operation is frequent. Please try again after 2 hours!") ;
				return jsonObject.toString() ;
			}
			
			boolean googleValidate = GoogleAuth.auth(Long.parseLong(totpCode.trim()), fuser.getFgoogleAuthenticator()) ;
			if(!googleValidate){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "Google authentication code error,you still have "+google_limit+" chances") ;
				this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
				return jsonObject.toString() ;
			}else if(google_limit<new Constant().ErrorCountLimit){
				this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
			}
		}
		
		if(fuser.isFisTelephoneBind()){
			if(mobil_limit<=0){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "This IP operation is frequent. Please try again after 2 hours!") ;
				return jsonObject.toString() ;
			}
			
			boolean mobilValidate = validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.VIRTUAL_TIXIAN, phoneCode) ;
			if(!mobilValidate){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "Mobile phone certificate code error,you still have "+mobil_limit+" chance") ;
				this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
				return jsonObject.toString() ;
			}else if(mobil_limit<new Constant().ErrorCountLimit){
				this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
			}
		}
		
		try{
			Map<Integer,Integer> tradeMappings = (Map)this.constantMap.get("tradeMappings");
			Integer ftrademapping_id = tradeMappings.get(fvirtualcointype.getFid()) ;
			double  curPrice = 0d;
			if(ftrademapping_id != null){
				curPrice = (Double)this.redisUtil.get(RedisConstant.getLatestDealPrizeKey(ftrademapping_id)) ;
			}
			this.frontVirtualCoinService.updateWithdrawBtc(fvirtualaddressWithdraw,fvirtualcointype, 
					fvirtualwallet, withdrawAmount, fuser,curPrice) ;
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", "Success") ;
		}catch(Exception e){
			e.printStackTrace() ;
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Network anomaly") ;
		}finally{
			this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.VIRTUAL_TIXIAN);
		}
		
		return jsonObject.toString() ;
	}
	
	@ResponseBody
	@RequestMapping("/account/cancelRechargeCnySubmit")
	public String cancelRechargeCnySubmit(
			HttpServletRequest request,
			int id
			) throws Exception{
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(id) ;
		if(fcapitaloperation.getFuser().getFid() ==fuser.getFid() 
			&&fcapitaloperation.getFtype()==CapitalOperationTypeEnum.RMB_IN
			&&(fcapitaloperation.getFstatus()==CapitalOperationInStatus.NoGiven || fcapitaloperation.getFstatus()==CapitalOperationInStatus.WaitForComing)){
			fcapitaloperation.setFstatus(CapitalOperationInStatus.Invalidate) ;
			fcapitaloperation.setfLastUpdateTime(Utils.getTimestamp()) ;
			try {
				this.frontAccountService.updateCapitalOperation(fcapitaloperation) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return String.valueOf(0) ;
	}
	
	@ResponseBody
	@RequestMapping("/account/subRechargeCnySubmit")
	public String subRechargeCnySubmit(
			HttpServletRequest request,
			int id
			) throws Exception{
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(id) ;
		if(fcapitaloperation.getFuser().getFid() ==fuser.getFid() 
			&&fcapitaloperation.getFtype()==CapitalOperationTypeEnum.RMB_IN
			&&fcapitaloperation.getFstatus()==CapitalOperationInStatus.NoGiven){
			fcapitaloperation.setFstatus(CapitalOperationInStatus.WaitForComing) ;
			fcapitaloperation.setfLastUpdateTime(Utils.getTimestamp()) ;
			try {
				this.frontAccountService.updateCapitalOperation(fcapitaloperation) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return String.valueOf(0) ;
	}
	
	@ResponseBody
	@RequestMapping("/account/cancelWithdrawcny")
	public String cancelWithdrawcny(
			HttpServletRequest request,
			int id
			) throws Exception{
		Fcapitaloperation fcapitaloperation = this.frontAccountService.findCapitalOperationById(id) ;
		if(fcapitaloperation!=null
				&&fcapitaloperation.getFuser().getFid() ==GetSession(request).getFid()
				&&fcapitaloperation.getFtype()==CapitalOperationTypeEnum.RMB_OUT
				&&fcapitaloperation.getFstatus()==CapitalOperationOutStatus.WaitForOperation){
			try {
				this.frontAccountService.updateCancelWithdrawCny(fcapitaloperation, this.frontUserService.findById(GetSession(request).getFid())) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return String.valueOf(0) ;
	}
	
	@ResponseBody
	@RequestMapping("/account/cancelWithdrawBtc")
	public String cancelWithdrawBtc(
			HttpServletRequest request,
			@RequestParam(required=true)int id
			) throws Exception{
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fvirtualcaptualoperation fvirtualcaptualoperation = this.frontVirtualCoinService.findFvirtualcaptualoperationById(id) ;
		if(fvirtualcaptualoperation!=null
				&&fvirtualcaptualoperation.getFuser().getFid() ==fuser.getFid() 
				&&fvirtualcaptualoperation.getFtype()==VirtualCapitalOperationTypeEnum.COIN_OUT
				&&fvirtualcaptualoperation.getFstatus()==VirtualCapitalOperationOutStatusEnum.WaitForOperation
				){
			
			try{
				this.frontAccountService.updateCancelWithdrawBtc(fvirtualcaptualoperation, fuser) ;
			}catch(Exception e){
				e.printStackTrace() ;
			}
			
		}
		return String.valueOf(0) ;
	}
	
	@ResponseBody
	@RequestMapping("/account/getVirtualAddress")
	public String getVirtualAddress(
			HttpServletRequest request,
			int symbol
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		String filter = "where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+symbol;
		int count = this.adminService.getAllCount("Fvirtualaddress", filter);
		if(count >0){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Address already exists, no duplicate access is allowed") ;
			return jsonObject.toString() ;
		}
		Fvirtualcointype coin = this.virtualCoinService.findById(symbol);
		if(coin.isFisrecharge()){
			Fpool fpool = this.poolService.getOneFpool(coin) ;
			if(fpool == null){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "The address of the address library is insufficient. Please contact the administrator") ;
				return jsonObject.toString() ;
			}
			String address = fpool.getFaddress() ;
			Fvirtualaddress fvirtualaddress = new Fvirtualaddress() ;
			fvirtualaddress.setFadderess(address) ;
			fvirtualaddress.setFcreateTime(Utils.getTimestamp()) ;
			fvirtualaddress.setFuser(fuser) ;
			fvirtualaddress.setFvirtualcointype(coin) ;
			if(address==null || address.trim().equalsIgnoreCase("null") || address.trim().equals("")){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "The address of the address library is insufficient. Please contact the administrator") ;
				return jsonObject.toString() ;
			}
			fpool.setFstatus(1) ;
			this.poolService.updateObj(fpool) ;
			this.virtualaddressService.saveObj(fvirtualaddress) ;
			jsonObject.accumulate("address", fvirtualaddress.getFadderess()) ;
		}else{
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "The virtual currency does not support recharge") ;
			return jsonObject.toString() ;
		}
		
		jsonObject.accumulate("code", 0) ;
		return jsonObject.toString();
	}
}
