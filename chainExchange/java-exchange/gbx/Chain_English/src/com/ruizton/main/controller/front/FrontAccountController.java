package com.ruizton.main.controller.front;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.ruizton.main.Enum.BankTypeEnum;
import com.ruizton.main.Enum.CapitalOperationTypeEnum;
import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.RemittanceTypeEnum;
import com.ruizton.main.Enum.TradeRecordTypeEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationTypeEnum;
import com.ruizton.main.Enum.VirtualCoinTypeStatusEnum;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.comm.KeyValues;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fasset;
import com.ruizton.main.model.FbankinfoWithdraw;
import com.ruizton.main.model.Fcapitaloperation;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualaddress;
import com.ruizton.main.model.FvirtualaddressWithdraw;
import com.ruizton.main.model.Fvirtualcaptualoperation;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.model.Fwithdrawfees;
import com.ruizton.main.model.Systembankinfo;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.CapitaloperationService;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.admin.WithdrawFeesService;
import com.ruizton.main.service.front.FrontAccountService;
import com.ruizton.main.service.front.FrontBankInfoService;
import com.ruizton.main.service.front.FrontTradeService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.main.service.front.UtilsService;
import com.ruizton.util.Constant;
import com.ruizton.util.Mobilutils;
import com.ruizton.util.PaginUtil;
import com.ruizton.util.Utils;

@Controller
public class FrontAccountController extends BaseController{
	@Autowired
	private FrontBankInfoService frontBankInfoService ;
	@Autowired
	private FrontAccountService frontAccountService ;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontTradeService frontTradeService ;
	@Autowired
	private WithdrawFeesService withdrawFeesService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private CapitaloperationService capitaloperationService;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private ConstantMap constantMap;
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;


	@RequestMapping("account/rechargeCny")
	public ModelAndView rechargeCny(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage,
			@RequestParam(required=false,defaultValue="0")int type
			) throws Exception{
		
		if(type !=0 /* && type !=3 && type !=4*/){
			type =0;
		}
		
		ModelAndView modelAndView = new ModelAndView() ;
		
		//人民币随机尾数
		int randomMoney = (new Random().nextInt(80)+11) ;
		//系统银行账号
		List<Systembankinfo> systembankinfos = this.frontBankInfoService.findAllSystemBankInfo() ;
		modelAndView.addObject("randomMoney",randomMoney) ;
		modelAndView.addObject("bankInfo",systembankinfos) ;
		
		//record
		Fuser fuser = this.GetSession(request) ;
		StringBuffer filter = new StringBuffer();
		filter.append("where fuser.fid="+fuser.getFid()+" \n");
		filter.append("and ftype ="+CapitalOperationTypeEnum.RMB_IN+"\n");
		if(type !=0){
			filter.append("and fremittanceType='"+RemittanceTypeEnum.getEnumString(type)+"' \n");
		}else{
			filter.append("and systembankinfo is not null \n");
		}
		
		filter.append(" order by fid desc \n");
		List<Fcapitaloperation> list = this.utilsService.list(PaginUtil.firstResult(currentPage, maxResults), maxResults, filter.toString(), true,Fcapitaloperation.class);
		
		
		int totalCount = this.adminService.getAllCount("Fcapitaloperation", filter.toString());
		String url = "/account/rechargeCny.html?type="+type+"&";
		String pagin = PaginUtil.generatePagin(PaginUtil.totalPage(totalCount, maxResults),currentPage,url) ;
		
		//最小充值金额
		double minRecharge = Double.parseDouble(this.constantMap.get("minrechargecny").toString().trim()) ;
		modelAndView.addObject("minRecharge", minRecharge) ;
		
		Map<Integer,String> bankTypes = new HashMap<Integer,String>() ;
		for(int i=1;i<=BankTypeEnum.QT;i++){
			if(BankTypeEnum.getEnumString(i) != null && BankTypeEnum.getEnumString(i).trim().length() >0){
				bankTypes.put(i,BankTypeEnum.getEnumString(i)) ;
			}			
		}
		modelAndView.addObject("bankTypes", bankTypes) ;
		
//		boolean isproxy = false;
//		String ss = "where fuser.fid="+fuser.getFid()+" and fstatus=1";
//		int cc = this.adminService.getAllCount("Fproxy", ss);
//		if(cc >0){
//			isproxy = true;
//		}
//		modelAndView.addObject("isproxy", isproxy) ;
		
		modelAndView.addObject("list", list) ;
		modelAndView.addObject("pagin", pagin) ;
		modelAndView.addObject("currentPage_page", currentPage) ;
		modelAndView.addObject("fuser",GetSession(request)) ;
		modelAndView.addObject("type", type) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/account/account_rechargecny"+type) ;
		return modelAndView ;
	}
	
	
	@RequestMapping("account/withdrawCny")
	public ModelAndView withdrawCny(
			@RequestParam(required=false,defaultValue="1")int currentPage,
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		String filter = "where fuser.fid="+fuser.getFid()+" and fbankType >0";
		List<FbankinfoWithdraw> fbankinfoWithdraws =this.frontUserService.findFbankinfoWithdrawByFuser(0, 0, filter, false);
		for (FbankinfoWithdraw fbankinfoWithdraw : fbankinfoWithdraws) {
			int l = fbankinfoWithdraw.getFbankNumber().length();
			fbankinfoWithdraw.setFbankNumber(fbankinfoWithdraw.getFbankNumber().substring(l-4, l));
		}
		
		Map<Integer,String> bankTypes = new HashMap<Integer,String>() ;
		for(int i=1;i<=BankTypeEnum.QT;i++){
			if(BankTypeEnum.getEnumString(i) != null && BankTypeEnum.getEnumString(i).trim().length() >0){
				bankTypes.put(i,BankTypeEnum.getEnumString(i)) ;
			}			
		}
		modelAndView.addObject("bankTypes", bankTypes) ;
		
		Fvirtualwallet fwallet = this.frontUserService.findWalletByUser(GetSession(request).getFid());
		request.getSession().setAttribute("fwallet", fwallet) ;
		Fwithdrawfees ffees = this.withdrawFeesService.findFfee(fwallet.getFvirtualcointype().getFid(),fuser.getFscore().getFlevel());
		double fee = ffees.getFfee();
		double amt = ffees.getFamt();
		double last = ffees.getFlastFee();
		modelAndView.addObject("fee", fee) ;
		modelAndView.addObject("amt", amt) ;
		modelAndView.addObject("last", last) ;
		
		//獲得千12條交易記錄
		String param = "where fuser.fid="+fuser.getFid()+" and ftype="+CapitalOperationTypeEnum.RMB_OUT+" order by fid desc";
		List<Fcapitaloperation> fcapitaloperations = this.frontAccountService.findCapitalList(PaginUtil.firstResult(currentPage, maxResults),maxResults, param, true) ;
		int totalCount = this.adminService.getAllCount("Fcapitaloperation", param.toString());
		String url = "/account/withdrawCny.html?";
		String pagin = PaginUtil.generatePagin(totalCount/maxResults+( (totalCount%maxResults)==0?0:1), currentPage,url) ;
		
		boolean isBindGoogle = fuser.getFgoogleBind() ;
		boolean isBindTelephone = fuser.isFisTelephoneBind() ;
		modelAndView.addObject("isBindGoogle", isBindGoogle) ;
        modelAndView.addObject("isBindTelephone", isBindTelephone) ;
		
		modelAndView.addObject("pagin", pagin) ;
		modelAndView.addObject("fcapitaloperations", fcapitaloperations) ;
		modelAndView.addObject("fuser",fuser) ;
		modelAndView.addObject("fbankinfoWithdraws",fbankinfoWithdraws) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/account/account_withdrawcny") ;
		return modelAndView ;
	}
	
	@RequestMapping("account/rechargeBtc")
	public ModelAndView rechargeBtc(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage,
			@RequestParam(required=false,defaultValue="0")int symbol
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		if(fvirtualcointype==null 
				|| fvirtualcointype.getFtype()==CoinTypeEnum.FB_CNY_VALUE 
				|| fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal 
				|| !fvirtualcointype.isFisrecharge()){
			String filter = "where fstatus=1 and fisrecharge=1 and ftype <> "+CoinTypeEnum.FB_CNY_VALUE+" order by fid asc";
			List<Fvirtualcointype> alls = this.virtualCoinService.list(0, 1, filter, true);
			if(alls==null || alls.size() ==0){
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
			fvirtualcointype = alls.get(0);
		}
		symbol = fvirtualcointype.getFid();
		Fvirtualaddress fvirtualaddress = new Fvirtualaddress();
		//如果是以太代币，则显示以太坊充值地址
		if(fvirtualcointype.isFisToken())
		{
			List<Fvirtualcointype> tokenCoinList = this.frontVirtualCoinService.findFvirtualCoinTypeEth();
			Fvirtualcointype tokenCoinn = new Fvirtualcointype();
			if(tokenCoinList.size() > 0)
			{
				tokenCoinn = tokenCoinList.get(0);
			}
			fvirtualaddress = this.frontVirtualCoinService.findFvirtualaddress(fuser, tokenCoinn) ;
		}else
		{
			fvirtualaddress = this.frontVirtualCoinService.findFvirtualaddress(fuser, fvirtualcointype) ;
		}
		
		//最近十次充值记录
		String filter ="where fuser.fid="+fuser.getFid()+" and ftype="+VirtualCapitalOperationTypeEnum.COIN_IN
				+" and fvirtualcointype.fid="+fvirtualcointype.getFid()+" order by fid desc";
		List<Fvirtualcaptualoperation> fvirtualcaptualoperations = this.utilsService.list(PaginUtil.firstResult(currentPage, maxResults), maxResults, filter, true,Fvirtualcaptualoperation.class);
		int totalCount = this.adminService.getAllCount("Fvirtualcaptualoperation", filter.toString());
		String url = "/account/rechargeBtc.html?symbol="+symbol+"&";
		String pagin = PaginUtil.generatePagin(totalCount/maxResults+( (totalCount%maxResults)==0?0:1), currentPage,url) ;
		
		modelAndView.addObject("pagin", pagin) ;
		modelAndView.addObject("fvirtualcaptualoperations",fvirtualcaptualoperations) ;
		modelAndView.addObject("fvirtualcointype",fvirtualcointype) ;
		modelAndView.addObject("symbol", symbol) ;
		modelAndView.addObject("fvirtualaddress", fvirtualaddress) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/account/account_rechargebtc") ;
		return modelAndView ;
	}
	
	
	
	@RequestMapping("account/withdrawBtc")
	public ModelAndView withdrawBtc(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage,
			@RequestParam(required=false,defaultValue="0")int symbol
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		if(fvirtualcointype==null ||fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal
				 || !fvirtualcointype.isFIsWithDraw() ||fvirtualcointype.getFtype()==CoinTypeEnum.FB_CNY_VALUE){
			String filter = "where fstatus=1 and FIsWithDraw=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE+" order by fid asc";
			List<Fvirtualcointype> alls = this.virtualCoinService.list(0, 1, filter, true);
			if(alls==null || alls.size() ==0){
				modelAndView.setViewName("redirect:/") ;
				return modelAndView ;
			}
			fvirtualcointype = alls.get(0);
		}
		symbol = fvirtualcointype.getFid();
		
		
		Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fvirtualcointype.getFid()) ;
		String sql ="where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+symbol;
		List<FvirtualaddressWithdraw> fvirtualaddressWithdraws = this.frontVirtualCoinService.findFvirtualaddressWithdraws(0, 0, sql, false);
		
		//近10条提现记录
		String filter ="where fuser.fid="+fuser.getFid()+" and ftype="+VirtualCapitalOperationTypeEnum.COIN_OUT
				+" and fvirtualcointype.fid="+fvirtualcointype.getFid()+" order by fid desc";
		List<Fvirtualcaptualoperation> fvirtualcaptualoperations = this.utilsService.list(PaginUtil.firstResult(currentPage, maxResults), maxResults, filter, true,Fvirtualcaptualoperation.class);
		int totalCount = this.adminService.getAllCount("Fvirtualcaptualoperation", filter.toString());
		String url = "/account/withdrawBtc.html?symbol="+symbol+"&";
		String pagin = PaginUtil.generatePagin(totalCount/maxResults+( (totalCount%maxResults)==0?0:1), currentPage,url) ;
		
		modelAndView.addObject("pagin", pagin) ;
		
		int isEmptyAuth = 0;
		if(fuser.isFisTelephoneBind() || fuser.getFgoogleBind()){
			isEmptyAuth = 1;
		}
		modelAndView.addObject("isEmptyAuth",isEmptyAuth) ;
		
		boolean isBindGoogle = fuser.getFgoogleBind() ;
		boolean isBindTelephone = fuser.isFisTelephoneBind() ;
		modelAndView.addObject("isBindGoogle", isBindGoogle) ;
        modelAndView.addObject("isBindTelephone", isBindTelephone) ;
		
		modelAndView.addObject("symbol",symbol) ;
		modelAndView.addObject("fvirtualcaptualoperations", fvirtualcaptualoperations) ;
		modelAndView.addObject("fvirtualwallet",fvirtualwallet) ;
		modelAndView.addObject("fuser",fuser) ;
		modelAndView.addObject("fvirtualaddressWithdraws", fvirtualaddressWithdraws) ;
		modelAndView.addObject("fvirtualcointype",fvirtualcointype) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/account/account_withdrawbtc") ;
		return modelAndView ;
	}
	
	
	
	@RequestMapping("/account/record")
	public ModelAndView record(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int recordType,
			@RequestParam(required=false,defaultValue="0")int symbol,
			@RequestParam(required=false,defaultValue="1")int currentPage,
			@RequestParam(required=false,defaultValue="2")int datetype,
			@RequestParam(required=false,defaultValue="")String begindate,
			@RequestParam(required=false,defaultValue="")String enddate
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(!(datetype >=1 && datetype <=4)){
			datetype =2;
		}
		if(enddate == null || enddate.trim().length() ==0){
			enddate = sdf.format(new Date());
		}else{
			try {
				enddate = sdf.format(sdf.parse(HtmlUtils.htmlEscape(enddate)));
			} catch (Exception e) {
				enddate = "";
			}
		}
		if(begindate == null || begindate.trim().length() ==0){
			switch (datetype) {
			case 1:
				begindate = sdf.format(new Date());
				break;
	        case 2:
	        	begindate = Utils.getAfterDay(7);
				break;
	        case 3:
	        	begindate = Utils.getAfterDay(15);
	    	    break;
	        case 4:
	        	begindate = Utils.getAfterDay(30);
		       break;
			}
		}else{
			try {
				begindate = sdf.format(sdf.parse(HtmlUtils.htmlEscape(begindate)));
			} catch (Exception e) {
				begindate = "";
			}
		}
		
		
		
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		if(fvirtualcointype==null || fvirtualcointype.getFtype() != CoinTypeEnum.COIN_VALUE){
			fvirtualcointype = this.frontVirtualCoinService.findFirstFirtualCoin() ;
			symbol = fvirtualcointype.getFid() ;
		}
		
		if(recordType>TradeRecordTypeEnum.BTC_WITHDRAW){
			recordType = TradeRecordTypeEnum.BTC_WITHDRAW ;
		}
		
		String filter = "where fstatus=1 and ftype <>"+CoinTypeEnum.FB_CNY_VALUE+" and (FIsWithDraw=1 or fisrecharge=1)";
		List<Fvirtualcointype> fvirtualcointypes = this.virtualCoinService.list(0, 0, filter, false);
		//过滤器
		List<KeyValues> filters = new ArrayList<KeyValues>() ;
		for (int i = 1; i <= TradeRecordTypeEnum.BTC_WITHDRAW; i++) {
			if(i==1 || i==2){
				KeyValues keyValues = new KeyValues() ;
				String key = "/account/record.html?recordType="+i+"&symbol=0" ;
				String value = TradeRecordTypeEnum.getEnumString(i) ;
				keyValues.setKey(key) ;
				keyValues.setValue(value) ;
				filters.add(keyValues) ;
			}else{
				String key = "/account/record.html?recordType="+i+"&symbol=" ;
				for (int j = 0; j < fvirtualcointypes.size(); j++) {
					String value = TradeRecordTypeEnum.getEnumString(i) ;
					Fvirtualcointype vc = fvirtualcointypes.get(j) ;
					
					if(i==TradeRecordTypeEnum.BTC_WITHDRAW){
						if(!vc.isFIsWithDraw()){
							continue ;
						}
					}
					
					if(i==TradeRecordTypeEnum.BTC_RECHARGE){
						if(!vc.isFisrecharge()){
							continue ;
						}
					}
					
					value = vc.getfShortName()+value ;
					KeyValues keyValues = new KeyValues() ;
					keyValues.setKey(key+vc.getFid()) ;
					keyValues.setValue(value) ;
					filters.add(keyValues) ;
				}
			}
		}
		
		//内容
		List list = new ArrayList() ;
		int totalCount = 0 ;
		String pagin = "" ;
		String param = "";
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		switch (recordType) {
		case TradeRecordTypeEnum.CNY_RECHARGE:
			param = "where fuser.fid="+fuser.getFid()+" and ftype="+CapitalOperationTypeEnum.RMB_IN
			+" and date_format(fcreatetime,'%Y-%m-%d')>='"+begindate+"'"+" and date_format(fcreatetime,'%Y-%m-%d')<='"+enddate+"' order by fid desc";
			list = this.frontAccountService.findCapitalList(PaginUtil.firstResult(currentPage, totalCount),maxResults, param,true) ;
			totalCount = this.adminService.getAllCount("Fcapitaloperation", param);
			break;
			
		case TradeRecordTypeEnum.CNY_WITHDRAW:
			param = "where fuser.fid="+fuser.getFid()+" and ftype="+CapitalOperationTypeEnum.RMB_OUT
					+" and date_format(fcreatetime,'%Y-%m-%d')>='"+begindate+"'"+" and date_format(fcreatetime,'%Y-%m-%d')<='"+enddate+"' order by fid desc";
			list = this.frontAccountService.findCapitalList(PaginUtil.firstResult(currentPage, totalCount),maxResults, param,true) ;
			totalCount = this.adminService.getAllCount("Fcapitaloperation", param);

			break;
		case TradeRecordTypeEnum.BTC_RECHARGE:
			param = "where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+fvirtualcointype.getFid()+" and ftype ="+VirtualCapitalOperationTypeEnum.COIN_IN
					+" and date_format(fcreatetime,'%Y-%m-%d')>='"+begindate+"'"+" and date_format(fcreatetime,'%Y-%m-%d')<='"+enddate+"' order by fid desc";
			list =  this.frontVirtualCoinService.findFvirtualcaptualoperations(PaginUtil.firstResult(currentPage, totalCount),maxResults,param,true) ;
			totalCount = this.adminService.getAllCount("Fvirtualcaptualoperation", param);
			
			break;
		case TradeRecordTypeEnum.BTC_WITHDRAW:
			param = "where fuser.fid="+fuser.getFid()+" and fvirtualcointype.fid="+fvirtualcointype.getFid()+" and ftype ="+VirtualCapitalOperationTypeEnum.COIN_OUT
			+" and date_format(fcreatetime,'%Y-%m-%d')>='"+begindate+"'"+" and date_format(fcreatetime,'%Y-%m-%d')<='"+enddate+"' order by fid desc";
			list =  this.frontVirtualCoinService.findFvirtualcaptualoperations(PaginUtil.firstResult(currentPage, totalCount),maxResults,param,true) ;
			totalCount = this.adminService.getAllCount("Fvirtualcaptualoperation", param);
			
			break;
//		case TradeRecordTypeEnum.BTC_BUY:
//			param = "where fuser.fid="+fuser.getFid()+" and fentrustType="+EntrustTypeEnum.BUY+" and fvirtualcointype.fid="+fvirtualcointype.getFid()
//					+" and date_format(fcreatetime,'%Y-%m-%d')>='"+begindate+"'"+" and date_format(fcreatetime,'%Y-%m-%d')<='"+enddate+"' order by fid desc";
//			list = this.frontTradeService.findFentrustHistory(PaginUtil.firstResult(currentPage, totalCount), param, true);
//			totalCount = this.adminService.getAllCount("Fentrust", param);
//			
//			break;
//		case TradeRecordTypeEnum.BTC_SELL:
//			param = "where fuser.fid="+fuser.getFid()+" and fentrustType="+EntrustTypeEnum.SELL+" and fvirtualcointype.fid="+fvirtualcointype.getFid()
//			+" and date_format(fcreatetime,'%Y-%m-%d')>='"+begindate+"'"+" and date_format(fcreatetime,'%Y-%m-%d')<='"+enddate+"' order by fid desc";
//			list = this.frontTradeService.findFentrustHistory(PaginUtil.firstResult(currentPage, totalCount), param, true);
//			totalCount = this.adminService.getAllCount("Fentrust", param);
//			
//			break;
		}
		
		String url = "/account/record.html?recordType="+recordType+"&symbol="+symbol+"&datetype="+datetype+"&begindate="+begindate+"&enddate="+enddate+"&";
		pagin = PaginUtil.generatePagin(totalCount/maxResults+( (totalCount%maxResults)==0?0:1), currentPage,url) ;
		modelAndView.addObject("datetype", datetype) ;
		modelAndView.addObject("begindate", begindate) ;
		modelAndView.addObject("enddate", enddate) ;
		modelAndView.addObject("list", list) ;
		modelAndView.addObject("pagin",pagin) ;
		modelAndView.addObject("recordType",recordType ) ;
		modelAndView.addObject("symbol" ,symbol) ;
		modelAndView.addObject("filters", filters) ;
		modelAndView.addObject("fvirtualcointype", fvirtualcointype) ;
		if(recordType==1 || recordType==2){
			modelAndView.addObject("select", TradeRecordTypeEnum.getEnumString(recordType)) ;
		}else{
			modelAndView.addObject("select", fvirtualcointype.getfShortName()+TradeRecordTypeEnum.getEnumString(recordType)) ;
		}
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/account/account_record") ;
		return modelAndView ;
	}
	
	
	@RequestMapping(value="/account/refTenbody")
	public ModelAndView refTenbody(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage,
			@RequestParam(required=false,defaultValue="0")int type
			) throws Exception{
		if(type !=0 /*&& type !=2 && type !=3 && type !=4*/){
			type =0;
		}
		
		ModelAndView modelAndView = new ModelAndView() ;
		
		Fuser fuser = this.GetSession(request) ;
		StringBuffer filter = new StringBuffer();
		filter.append("where fuser.fid="+fuser.getFid()+" \n");
		filter.append("and ftype ="+CapitalOperationTypeEnum.RMB_IN+"\n");
		if(type !=0){
			filter.append("and fremittanceType='"+RemittanceTypeEnum.getEnumString(type)+"' \n");
		}else{
			filter.append("and systembankinfo is not null \n");
		}
		filter.append(" order by fid desc \n");
		List<Fcapitaloperation> list = this.capitaloperationService.list((currentPage-1)*Constant.AppRecordPerPage, Constant.AppRecordPerPage, filter.toString(), true);
		
		int totalCount = this.adminService.getAllCount("Fcapitaloperation", filter.toString());
		String url = "/account/rechargeCny.html?type="+type+"&";
		String pagin = PaginUtil.generatePagin(totalCount/Constant.AppRecordPerPage+( (totalCount%Constant.AppRecordPerPage)==0?0:1), currentPage,url) ;
		
		modelAndView.addObject("list", list) ;
		modelAndView.addObject("pagin", pagin) ;
		modelAndView.addObject("currentPage_page", currentPage) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/account/reftenbody") ;
		return modelAndView ;
	}
		
		@RequestMapping("financial/assetsrecord")
		public ModelAndView assetsrecord(
				HttpServletRequest request,
				@RequestParam(required=false,defaultValue="1")int currentPage
				)  throws Exception {
			ModelAndView modelAndView = new ModelAndView() ;
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
			
			List<Fvirtualcointype> fvirtualcointypes = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE, 1);
			modelAndView.addObject("fvirtualcointypes", fvirtualcointypes) ;
			
			int maxResults = Constant.RecordPerPage ;
			int firstResult = PaginUtil.firstResult(currentPage, maxResults) ;
			String filter =  " where fuser.fid="+fuser.getFid()+" and status=1 order by fid desc " ;
			List<Fasset> list= this.utilsService.list(firstResult, maxResults,filter, true, Fasset.class) ;
			int total = this.utilsService.count(filter, Fasset.class) ;
			String pagin = PaginUtil.generatePagin(PaginUtil.firstResult(currentPage, maxResults), currentPage, "/financial/assetsrecord.html?") ;
			modelAndView.addObject("list",list) ;
			modelAndView.addObject("pagin",pagin);
			
			//处理json
			for (Fasset fasset : list) {
				fasset.parseJson(fvirtualcointypes);
			}
			
			modelAndView.setViewName("front"+Mobilutils.M(request)+"/financial/assetsrecord") ;
			return modelAndView ;
		}
}
