package com.ruizton.main.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fintrolinfo;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.IntrolinfoService;
import com.ruizton.main.service.admin.SharePlanLogService;
import com.ruizton.main.service.admin.SharePlanService;
import com.ruizton.main.service.admin.UserService;
import com.ruizton.main.service.admin.VirtualWalletService;
import com.ruizton.main.service.front.FrontDivideService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.util.Constant;
import com.ruizton.util.Mobilutils;
import com.ruizton.util.PaginUtil;

@Controller
public class FrontDivideController extends BaseController {

	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private IntrolinfoService introlinfoService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;
	@Autowired
	private SharePlanLogService sharePlanLogService;
	@Autowired
	private FrontDivideService frontDivideService;
	@Autowired
	private VirtualWalletService virtualWalletService;
	@Autowired
	private SharePlanService sharePlanService;
	
	
	
	
	@RequestMapping("/introl/mydivide")
	public ModelAndView introl(
			HttpServletRequest request,
			@RequestParam(required=true,defaultValue="1")int type,
			@RequestParam(required=false,defaultValue="1")int currentPage
			) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		String url = new Constant().Domain+"?r="+fuser.getFid();
		if(fuser.getFuserNo() != null && fuser.getFuserNo().trim().length() >0){
			url = url+"&sn="+fuser.getFuserNo().trim();
		}
		modelAndView.addObject("spreadLink", url) ;
		if(type == 1){
			String filter = "where fIntroUser_id.fid="+fuser.getFid()+" order by fid desc";
			int total = this.adminService.getAllCount("Fuser", filter);
			int totalPage = total/Constant.RecordPerPage + ((total%Constant.RecordPerPage) ==0?0:1) ;
			List<Fuser> fusers = this.userService.list((currentPage-1)*Constant.RecordPerPage, Constant.RecordPerPage,filter,true) ;
			String pagin = PaginUtil.generatePagin(totalPage, currentPage, "/introl/mydivide.html?type=1&") ;
			
			modelAndView.addObject("fusers", fusers) ;
			modelAndView.addObject("pagin", pagin) ;
			modelAndView.setViewName("front"+Mobilutils.M(request)+"/introl/index") ;
		}else if(type ==2){
			String filter = "where fuser.fid="+fuser.getFid()+" order by fid desc";
			int total = this.adminService.getAllCount("Fintrolinfo", filter);
			int totalPage = total/Constant.RecordPerPage + ((total%Constant.RecordPerPage)  ==0?0:1) ;
			List<Fintrolinfo> fintrolinfos = this.introlinfoService.list((currentPage-1)*Constant.RecordPerPage, Constant.RecordPerPage,filter,true) ;
			String pagin = PaginUtil.generatePagin(totalPage, currentPage, "/introl/mydivide.html?type=2&") ;
			
			modelAndView.addObject("fintrolinfos", fintrolinfos) ;
			modelAndView.addObject("pagin", pagin) ;
			modelAndView.setViewName("front"+Mobilutils.M(request)+"/introl/index2") ;
		}/*else if(type == 3){
			String userNo = "xx";
			if(fuser.getFuserNo() != null && fuser.getFuserNo().trim().length() >0){
				userNo = fuser.getFuserNo().trim();
			}
			String filter = "where fintrolUserNo='"+userNo+"' order by fid desc";
			int total = this.adminService.getAllCount("Fuser", filter);
			int totalPage = total/Constant.RecordPerPage + ((total%Constant.RecordPerPage) ==0?0:1) ;
			List<Fuser> fusers = this.userService.list((currentPage-1)*Constant.RecordPerPage, Constant.RecordPerPage,filter,true) ;
			String pagin = PaginUtil.generatePagin(totalPage, currentPage, "/introl/mydivide.html?type=3&") ;
			
			modelAndView.addObject("fusers", fusers) ;
			modelAndView.addObject("pagin", pagin) ;
			modelAndView.setViewName("front"+Mobilutils.M(request)+"/introl/index3") ;
		}*/
		modelAndView.addObject("type", type) ;
		
		return modelAndView ;
	}
/*
	@RequestMapping("/divide/myGivenCNY")
	public ModelAndView myGivenCNY(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage
			) throws Exception {
		
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		String filter = "where fuser.fid="+fuser.getFid()+" and ftype='CNY' order by fid desc";
		int totalCount = this.adminService.getAllCount("Fshareplanlog", filter);
		int totalPage = totalCount/Constant.RecordPerPage + ((totalCount%Constant.RecordPerPage==0)?0:1) ;
		List<Fshareplanlog> fshareplanlogs = this.sharePlanLogService.list((currentPage-1)*Constant.RecordPerPage, Constant.RecordPerPage, filter, true);
		String pagin = PaginUtil.generatePagin((int)totalPage, currentPage, "/divide/myGivenCNY.html?") ;
		
		modelAndView.addObject("fshareplanlogs", fshareplanlogs) ;
		modelAndView.addObject("pagin", pagin) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/divide/index_cny") ;
		return modelAndView ;
	}
	

	@RequestMapping("/divide/myGivenCoin")
	public ModelAndView myGivenCoin(HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage) throws Exception {
		
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		int totalCount = this.frontDivideService.findFshareplanlogsCount(fuser, SharePlanTypeEnum.HANDING) ;
		int totalPage = totalCount/Constant.RecordPerPage + ((totalCount%Constant.RecordPerPage==0)?0:1) ;
		List<Fshareplanlog> fshareplanlogs = this.frontDivideService.findFshareplanlogs(fuser,SharePlanTypeEnum.HANDING,(currentPage-1)*Constant.RecordPerPage, Constant.RecordPerPage) ;
		String pagin = PaginUtil.generatePagin((int)totalPage, currentPage, "/divide/myGivenCoin.html?") ;
		
		modelAndView.addObject("fshareplanlogs", fshareplanlogs) ;
		modelAndView.addObject("pagin", pagin) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/divide/index_sjg") ;
		return modelAndView ;
	}
//	
//	
//	@ResponseBody
//	@RequestMapping(value="/json/divide/getDividecoin",produces={JsonEncode})
//	public String getDividecoin(
//			HttpServletRequest request,
//			@RequestParam(required=true)String name,
//			@RequestParam(required=true)int id
//			) throws Exception {
//		JSONObject jsonObject = new JSONObject() ;
//		int vid =Integer.parseInt(this.constantMap.getString("divideCoinId").trim());
//		name = HtmlUtils.htmlEscape(name).trim().replaceAll("'", "");
//		Fshareplan fshareplan = null ;
//		String title = new SimpleDateFormat("yyyy-MM-dd").format(Utils.getTimestamp())+"_分红"+"("+name+")" ;
//        List<Fshareplan> fshareplans = this.utilsService.list(0, 0, " where ftitle='"+title+"' ", false, Fshareplan.class) ;
//        if(fshareplans.size()>0){
//        	fshareplan = fshareplans.get(0) ;
//        }
//        
//        if(fshareplan == null || fshareplan.getFcoinid() != id){
//        	jsonObject.accumulate("resultCode", -1) ;
//        	jsonObject.accumulate("message", "网络错误，请稍后再试！") ;
//        	return jsonObject.toString() ;
//        }
//        
//        Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
//        int count = this.utilsService.count(" where fshareplan.fid="+fshareplan.getFid()+" and fuser.fid="+fuser.getFid()+" ", Fshareplanlog.class) ;
//        
//        if(count>0){
//        	jsonObject.accumulate("resultCode", -2) ;
//        	jsonObject.accumulate("message", "已经领取分红，请勿重复领取！") ;
//        	return jsonObject.toString() ;
//        }
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Fvirtualwallet v = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), vid);
//		double selfQty = v.getFtotal();
//		
//      	//全站今日交易总额
//        double totalQty = virtualWalletService.getTotalQty(vid).doubleValue();
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String divideStartTime = sdf.format(new Date())+" "+this.constantMap.getString("divideStartTime");
//        String divideEndTime = sdf.format(new Date())+" "+this.constantMap.getString("divideEndTime");
//		long s1 = sdf1.parse(divideStartTime).getTime();
//		long s2 = sdf1.parse(divideEndTime).getTime();
//		long nowTime = new Date().getTime();
//		if(!(nowTime >=s1 && nowTime <=s2)){
//			jsonObject.accumulate("resultCode", -2) ;
//        	jsonObject.accumulate("message", "手续费领取时间为:"+divideStartTime+"~"+divideEndTime) ;
//        	return jsonObject.toString() ;
//		}
//		
//        //预估1TMG可分红：全网每日手续费（除以）全网TMG（乘以）持有TMG数量（乘以）5倍=显示的预估1TMG可分红数量
//        double divideCoinTimes =Double.valueOf(constantMap.get("divideCoinTimes").toString());
//        double total =  this.entrustService.getTotalBuyCoin(EntrustTypeEnum.BUY, sdf.format(new Date()), sdf.format(new Date()),id);
//        if(total <= 0){
//			jsonObject.accumulate("resultCode", -2) ;
//        	jsonObject.accumulate("message", name+"今日无交易手续费可分红！") ;
//        	return jsonObject.toString() ;
//		}
//        double canGet = Utils.getDouble((total/totalQty)*selfQty*divideCoinTimes, 4);
//        double oneDivide = Utils.getDouble((total/totalQty)*divideCoinTimes, 6) ;
//        
//        if(canGet<0.0001D){
//        	jsonObject.accumulate("resultCode", -3) ;
//        	jsonObject.accumulate("message", "当前分红太少，请稍后再来领取！") ;
//        	return jsonObject.toString() ;
//        }
//        
//        Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), id);
//        fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+canGet);
//        
//        Fshareplanlog fshareplanlog = new Fshareplanlog() ;
//        fshareplanlog.setFcreatetime(Utils.getTimestamp()) ;
//        fshareplanlog.setFoneAmount(oneDivide) ;
//        fshareplanlog.setFamount(BigDecimal.valueOf(canGet)) ;
//        fshareplanlog.setfTotalAmount(total) ;
//        fshareplanlog.setFselfAmount(selfQty) ;
//        fshareplanlog.setfTotalQty(totalQty) ;
//        fshareplanlog.setFuser(fuser) ;
//        fshareplanlog.setFshareplan(fshareplan) ;
//        fshareplanlog.setFstatus(SharePlanLogStatusEnum.HASSEND) ;
//        fshareplanlog.setFtype(name);
//        
//        boolean flag = false ;
//        try{
//        	this.sharePlanLogService.updateObj(fshareplanlog, fvirtualwallet) ;
//        	flag = true ;
//        }catch(Exception e){
//        	e.printStackTrace() ;
//        }
//        
//        if(flag == true ){
//        	jsonObject.accumulate("resultCode", 0) ;
//        	jsonObject.accumulate("message", "成功领取分红:"+new BigDecimal(canGet).setScale(4, BigDecimal.ROUND_HALF_UP)+"个"+name) ;
//        	return jsonObject.toString() ;
//        }else{
//        	jsonObject.accumulate("resultCode", -4) ;
//        	jsonObject.accumulate("message", "网络错误，请稍后再试！") ;
//        	return jsonObject.toString() ;
//        }
//        
//	}
//
//	
//	@RequestMapping("/divide/index")
//	public ModelAndView mydividecoin(
//			HttpServletRequest request
//			) throws Exception {
//		ModelAndView modelAndView = new ModelAndView() ;
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
//		int vid =Integer.parseInt(this.constantMap.getString("divideCoinId").trim());
//		//我的总量
//		Fvirtualwallet v = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), vid);
//		double selfQty = v.getFtotal();
//		
//		//全站总量
//		double totalQty = virtualWalletService.getTotalQty(vid).doubleValue();
//		Fvirtualcointype cointype = this.virtualCoinService.findById(vid);
//		modelAndView.addObject("cointype1",cointype ) ;
//		modelAndView.addObject("self_trade1",selfQty ) ;
//		modelAndView.addObject("total_trade1",totalQty ) ;
//		
//
//		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		boolean canTake = true;
//        String divideStartTime = sdf.format(new Date())+" "+this.constantMap.getString("divideStartTime");
//        String divideEndTime = sdf.format(new Date())+" "+this.constantMap.getString("divideEndTime");
//		long s1 = sdf1.parse(divideStartTime).getTime();
//		long s2 = sdf1.parse(divideEndTime).getTime();
//		long nowTime = new Date().getTime();
//		if(!(nowTime >=s1 && nowTime <=s2)){
//			canTake = false;
//		}
//		
//
//        double divideCoinTimes =Double.valueOf(constantMap.get("divideCoinTimes").toString());
//        Map<String,Double> map1 =  this.entrustService.getTotalBuyCoin(EntrustTypeEnum.BUY, sdf.format(new Date()), sdf.format(new Date()));
//        List allKey = new ArrayList();
//        if(!map1.isEmpty()){
//        	Iterator it = map1.keySet().iterator();
//        	while(it.hasNext()){
//        		String key = (String)it.next();
//        		String[] keys = key.split("#");
//        		double amt = map1.get(key);
//        		double divQty = Utils.getDouble((amt/totalQty)*selfQty*divideCoinTimes, 4);
//        		KeyValues keyValue = new KeyValues();
//        		keyValue.setKey(keys[0]);
//        		keyValue.setName(keys[1]);
//        		keyValue.setTotal(amt);
//        		keyValue.setValue(divQty);
//        		
//                String name = new SimpleDateFormat("yyyy-MM-dd").format(Utils.getTimestamp())+"_分红"+"("+keys[1]+")" ;
//                Fshareplan fshareplan = null ;
//                List<Fshareplan> fshareplans = this.utilsService.list(0, 0, " where ftitle='"+name+"' ", false, Fshareplan.class) ;
//                if(fshareplans.size()>0){
//                	fshareplan = fshareplans.get(0) ;
//                }else{
//                	try{
//                		fshareplan = new Fshareplan() ;
//                        fshareplan.setFamount(BigDecimal.valueOf(amt)) ;
//                        fshareplan.setFcreateTime(Utils.getTimestamp()) ;
//                        fshareplan.setFcreator(null) ;
//                        fshareplan.setFstatus(SharePlanStatusEnum.AUDITED_VALUE) ;
//                        fshareplan.setFtitle(name) ;
//                        fshareplan.setFcoinid(Integer.parseInt(keys[0]));
//                        fshareplan.setFtypes(keys[1]);
//                        fshareplan.setFtype(SharePlanTypeEnum.HANDING);
//                        fshareplan.setFsendcointype(this.frontVirtualCoinService.findFvirtualCoinById(Integer.parseInt(keys[0])));
//                        fshareplan.setFtotalCoinQty(BigDecimal.valueOf(amt));
//                		fshareplan.setFstartDate(Utils.getTimestamp());
//                		fshareplan.setFstartDate(Timestamp.valueOf(divideStartTime));
//                		fshareplan.setFendDate(Timestamp.valueOf(divideEndTime));
//                        fshareplan.setFvirtualcointype(this.frontVirtualCoinService.findFvirtualCoinById(vid)) ;
//                        this.sharePlanService.saveObj(fshareplan,fuser) ;
//                	}catch(Exception e){
//                		fshareplan = null ;
//                	}
//                }
//        		if(canTake){
//        	        int count = this.utilsService.count(" where fshareplan.fid="+fshareplan.getFid()+" and fuser.fid="+fuser.getFid()+" ", Fshareplanlog.class) ;
//        	        if(count>0){
//        	        	keyValue.setStatus(2);
//        	        }else{
//        	        	keyValue.setStatus(3);
//        	        }
//        		}else{
//        			keyValue.setStatus(1);
//        		}
//        		allKey.add(keyValue);
//        	}
//        }
//        
// 	   //从星期天到星期六的时间
//        SimpleDateFormat sdfxx = new SimpleDateFormat("yyyy-MM");
//        Calendar c = Calendar.getInstance();
//        c.setTime(new Date());
//        int day = c.get(Calendar.DAY_OF_MONTH);
//        int max = c.getActualMaximum(Calendar.DAY_OF_MONTH);
//		String sun = sdfxx.format(new Date());
//		String end = sdfxx.format(new Date());
//		if(day >15){
//			sun=sun+"-16";
//			end=end+"-"+max;
//		}else{
//			sun=sun+"-01";
//			end=end+"-15";
//		}
//		
// 		 double divideRate = Double.valueOf(constantMap.get("divideRate").toString());
//         double total =  this.entrustService.getTotalBuyCoin(EntrustTypeEnum.SELL,sun,end,0);
//         total = Utils.getDouble(divideRate*total, 2);
//         modelAndView.addObject("total",total ) ;
//         
//
//         modelAndView.addObject("divideStartTime",divideStartTime ) ;
//         modelAndView.addObject("divideEndTime",divideEndTime ) ;
//         
//
//        modelAndView.addObject("allKey",allKey ) ;
//		modelAndView.setViewName("front"+Mobilutils.M(request)+"/divide/index") ;
//		return modelAndView ;
//	}
//	
//	
	
	@ResponseBody
	@RequestMapping(value="/json/divide/takeAmt",produces={JsonEncode})
	public String takeAmt(
			HttpServletRequest request,
			@RequestParam(required=true)int id
			) throws Exception {
		JSONObject jsonObject = new JSONObject() ;
		Fshareplanlog log = this.sharePlanLogService.findById(id);
		Fuser fuser = this.userService.findById(GetSession(request).getFid());
		if(log == null || log.getFuser().getFid() != fuser.getFid()){
			jsonObject.accumulate("message", "非法操作");
			return jsonObject.toString();
		}
		
		if(log.getFstatus() != SharePlanLogStatusEnum.NOSEND){
			jsonObject.accumulate("message", "请勿重复领取");
			return jsonObject.toString();
		}
		
		String msg = "";
		long now = new Date().getTime();
		if(log.getFshareplan().getFtype() == SharePlanTypeEnum.NORMAL){
			//钱包
			BigDecimal last = BigDecimal.ZERO;
			if(now >= log.getFshareplan().getFstartDate().getTime() && now <= log.getFshareplan().getFendDate().getTime()){
				msg = "成功领取：￥"+log.getFamount();
				last = log.getFamount();
				//记录
				log.setFstatus(SharePlanLogStatusEnum.HASSEND);
			}else{
				msg = "已失效,领取失败!";
				//记录
				log.setFstatus(SharePlanLogStatusEnum.LOST);
			}
			Fvirtualwallet fvirtualwallet = this.frontUserService.findWalletByUser(fuser.getFid());
			BigDecimal totalRmb = new BigDecimal(fvirtualwallet.getFtotal());
			fvirtualwallet.setFtotal(totalRmb.add(last).doubleValue());
			
			try {
				this.sharePlanService.updateSharePlanLog(fvirtualwallet, log);
				
			} catch (Exception e) {
				jsonObject.accumulate("message", "网络异常");
				return jsonObject.toString();
			}
		}else if(log.getFshareplan().getFtype() == SharePlanTypeEnum.HANDING){
			BigDecimal last = BigDecimal.ZERO;
			if(log.getFshareplan().getFendDate() == null || log.getFshareplan().getFstartDate() == null){
				jsonObject.accumulate("message", "分红异常，请联系管理员");
				return jsonObject.toString();
			}
			if(now >= log.getFshareplan().getFstartDate().getTime() && now <= log.getFshareplan().getFendDate().getTime()){
				msg = "成功领取"+log.getFtype()+"："+log.getFamount();
				last = log.getFamount();
				//记录
				log.setFstatus(SharePlanLogStatusEnum.HASSEND);
			}else{
				msg = "已失效,领取失败!";
				//记录
				log.setFstatus(SharePlanLogStatusEnum.LOST);
			}
			int vid = log.getFshareplan().getFsendcointype().getFid();
			String filter1 = "where fuser.fid="+log.getFuser().getFid()+" and fvirtualcointype.fid="+vid;
			List<Fvirtualwallet> all = this.virtualWalletService.list(0, 0, filter1, false);
			if(all != null && all.size() >= 0){
				Fvirtualwallet virtualwallet = all.get(0);
				virtualwallet.setFtotal(virtualwallet.getFtotal()+last.doubleValue());
				try {
					this.sharePlanService.updateSharePlanLog1(virtualwallet, log);
				} catch (Exception e) {
					jsonObject.accumulate("message", "网络异常");
					return jsonObject.toString();
				}
			}
		}
		jsonObject.accumulate("message", msg);
		return jsonObject.toString();
	}*/
}
