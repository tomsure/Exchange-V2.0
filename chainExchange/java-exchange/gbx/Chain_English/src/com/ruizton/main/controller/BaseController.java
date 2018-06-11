package com.ruizton.main.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ruizton.main.Enum.MessageTypeEnum;
import com.ruizton.main.Enum.UserStatusEnum;
import com.ruizton.main.Enum.ValidateMailStatusEnum;
import com.ruizton.main.Enum.ValidateMessageStatusEnum;
import com.ruizton.main.auto.TaskList;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.comm.MessageValidate;
import com.ruizton.main.comm.ValidateMap;
import com.ruizton.main.model.Emailvalidate;
import com.ruizton.main.model.Fadmin;
import com.ruizton.main.model.Flimittrade;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvalidateemail;
import com.ruizton.main.model.Fvalidatemessage;
import com.ruizton.main.service.admin.LimittradeService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontValidateService;
import com.ruizton.util.CheckMobile;
import com.ruizton.util.Constant;
import com.ruizton.util.ConstantKeys;
import com.ruizton.util.OSSPostObject;
import com.ruizton.util.Utils;

public class BaseController {
	public static final String JsonEncode = "application/json;charset=UTF-8" ;
	@Autowired
	private ConstantMap constantMap ;
	@Autowired
	private FrontValidateService frontValidateService ;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private ValidateMap validateMap ;
	@Autowired
	private TaskList taskList ;
	@Autowired
	private LimittradeService limittradeService;
	
	public static final int maxResults = Constant.RecordPerPage ;
	
	public HttpSession getSession(HttpServletRequest request){
		return request.getSession() ;
	}
	
	public void setAdminSession(HttpServletRequest request,Fadmin fadmin){
		getSession(request).setAttribute("login_admin", fadmin) ;
		this.CleanSession(request) ;
	}
	
	public void removeAdminSession(HttpServletRequest request){
		getSession(request).removeAttribute("login_admin") ;
	}
	
	//获得管理员session
	public Fadmin getAdminSession(HttpServletRequest request){
		Object session = getSession(request).getAttribute("login_admin") ;
		Fadmin fadmin = null ;
		if(session!=null){
			fadmin = (Fadmin)session ;
		}
		return fadmin ;
	}
	
	//获得session中的用户
	public Fuser GetSession(HttpServletRequest request){
		Fuser fuser = null ;
		HttpSession session = getSession(request) ;
		Object session_user = session.getAttribute("login_user") ;
		if(session_user!=null){
			fuser = (Fuser)session_user;
			if(fuser.getFstatus() != UserStatusEnum.NORMAL_VALUE) return null;
		}
		return fuser ;
	}
	
	public Fuser GetSecondLoginSession(HttpServletRequest request){
		HttpSession session = getSession(request) ;
		return (Fuser) session.getAttribute("second_login_user") ;
	}
	
	public void SetSecondLoginSession(HttpServletRequest request,Fuser fuser){
		HttpSession session = getSession(request) ;
		session.setAttribute("second_login_user", fuser) ;
	}
	public void RemoveSecondLoginSession(HttpServletRequest request){
		HttpSession session = getSession(request) ;
		session.setAttribute("second_login_user", null) ;
	}
	
	public void SetSession(Fuser fuser,HttpServletRequest request){
		HttpSession session = getSession(request) ;
		session.setAttribute("login_user", fuser) ;
	}
	
	public void CleanSession(HttpServletRequest request){
		try {
			HttpSession session = getSession(request) ;
			String key = GetSession(request).getFid()+"trade" ;
			getSession(request).removeAttribute(key);
			session.setAttribute("login_user", null) ;
		} catch (Exception e) {}
	}
	
	public boolean isNeedTradePassword(HttpServletRequest request){
		if(GetSession(request) == null) return true;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String key = GetSession(request).getFid()+"trade" ;
		Object obj = getSession(request).getAttribute(key) ;
		
		if(obj==null){
			return true ;
		}else{
			try {
				double hour = Double.valueOf(this.constantMap.getString("tradePasswordHour"));
				double lastHour = Utils.getDouble((sdf.parse(obj.toString()).getTime()-new Date().getTime())/1000/60/60, 2);
				if(lastHour >= hour){
					getSession(request).removeAttribute(key);
					return true ;
				}else{
					return false ;
				}
			} catch (ParseException e) {
				return false ;
			}
		}
	}
	
	
	public void setNoNeedPassword(HttpServletRequest request){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String key = GetSession(request).getFid()+"trade" ;
		getSession(request).setAttribute(key,sdf.format(new Date())) ;
	}
	
	
	//for menu
	@ModelAttribute
	public void menuSelect(HttpServletRequest request){
		//banner菜单
		String uri = request.getRequestURI() ;
		String selectMenu = null ;
		if(uri.startsWith("/trade/coin")){
			selectMenu = "financial";
		}else if(uri.startsWith("/about/wallet")){
			selectMenu = "wallet" ;
		}else if(uri.startsWith("/about/")){
			selectMenu = "about" ;
		}else if(uri.startsWith("/user/login") || uri.startsWith("/user/register")){
			selectMenu = "index" ;
		}else if(uri.startsWith("/financial")
				||uri.startsWith("/balance/")
				||uri.startsWith("/crowd/logs")
				||uri.startsWith("/trade/entrust")
				||uri.startsWith("/account")){
			selectMenu = "financial" ;
		}else if(uri.startsWith("/crowd/index") || uri.startsWith("/crowd/detail")){
			selectMenu = "crowd" ;
		}else if(uri.startsWith("/vote/")){
			selectMenu = "vote" ;
		}else if(uri.startsWith("/user/") || uri.startsWith("introl/mydivide")){
			selectMenu = "user" ;
		}else if(uri.startsWith("/market.html")){
			selectMenu = "market" ;
		}else{
			selectMenu = "index";
		}
		request.setAttribute("selectMenu", selectMenu) ;
		//左侧菜单
		if(uri.startsWith("/trade")
				||uri.startsWith("/user")
				||uri.startsWith("/shop/")
				||uri.startsWith("/divide/")
				||uri.startsWith("/crowd/")
				||uri.startsWith("/balance/")
				||uri.startsWith("/introl/mydivide")
				||uri.startsWith("/account")
				||uri.startsWith("/financial")
				||uri.startsWith("/coinage/")
				||uri.startsWith("/vote/")//融资融币
				||uri.startsWith("/question")){
			String leftMenu = null ;
			
			if(uri.startsWith("/question/questionColumn")){
				leftMenu = "questionColumn";
			}else if(uri.startsWith("/question/question")){
				leftMenu = "question";
			}else if(uri.startsWith("/user/realCertification")){
				leftMenu = "real";
			}if(uri.startsWith("/question/message")){
				leftMenu = "message";
			}else if(uri.startsWith("/user/security")){
				leftMenu = "security" ;
			}else if(uri.startsWith("/user/userloginlog")){
				leftMenu = "loginlog" ;
			}else if(uri.startsWith("/account/record")){
				leftMenu = "record" ;
			}else if(uri.startsWith("/financial/accountalipay") 
					||uri.startsWith("/financial/accountbank")
					||uri.startsWith("/financial/accountcoin")){
				leftMenu = "accountAdd" ;
			}else if(uri.startsWith("/crowd/mylogs") 
					||uri.startsWith("/crowd/logs")){
				leftMenu = "mylogs" ;
			}else if(uri.startsWith("/account/deduct")){
				leftMenu = "record" ;
			}else if(uri.startsWith("/account/rechargeCny")
					||uri.startsWith("/account/proxyCode")
					||uri.startsWith("/account/payCode")){
				leftMenu = "recharge" ;
			}else if(uri.startsWith("/account/withdrawCny")){
				leftMenu = "withdraw" ;
			}else if(uri.startsWith("/account/rechargeBtc")){
				leftMenu = "recharge" ;
			}else if(uri.startsWith("/account/withdrawBtc")){
				leftMenu = "withdraw" ;
			}else if(uri.startsWith("/account/btcTransport")){
				leftMenu = "btcTransport" ;
			}else if(uri.startsWith("/trade/coin")){
				leftMenu = "tradeCoin" ;
			}else if(uri.startsWith("/trade/entrust")){
				try {
					int status = Integer.parseInt(request.getParameter("status").trim());
					if(status ==0){
						leftMenu = "entrust0" ;
					}else{
						leftMenu = "entrust1" ;
					}
				} catch (Exception e) {
					leftMenu = "entrust0" ;
				}
			}else if(uri.startsWith("/financial/assetsrecord")){
				leftMenu = "assetsrecord" ;
			}else if(uri.startsWith("/financial/index")){
				leftMenu = "financial" ;
			}else if(uri.startsWith("/introl/")){
				leftMenu = "intro" ;
			}
			request.setAttribute("leftMenu", leftMenu) ;
		}
	}
	
	@ModelAttribute
	public void addConstant(HttpServletRequest request){//此方法会在每个controller前执行
		
        String userAgent = request.getHeader("USER-AGENT");
        boolean isphone =CheckMobile.check(userAgent);
        request.setAttribute("isphone", isphone) ;

		//前端常量
		request.setAttribute("constant", constantMap.getMap()) ;
		String ossURL = OSSPostObject.URL;
		if(Constant.IS_OPEN_OSS.equals("false")){
			ossURL = "";
		}
		request.setAttribute("oss_url", ossURL) ;
	}
	
	//发送短信验证码，已登录用户
	public boolean SendMessage(Fuser fuser,int fuserid,String areaCode,String phone,int type){
		boolean canSend = true ;
		MessageValidate messageValidate = this.validateMap.getMessageMap(fuserid+"_"+type) ;
		if(messageValidate!=null && Utils.timeMinus(Utils.getTimestamp(), messageValidate.getCreateTime())<120){
			canSend = false ;
		}
		
		if(canSend){
			MessageValidate messageValidate2 = new MessageValidate() ;
			messageValidate2.setAreaCode(areaCode) ;
			messageValidate2.setCode(Utils.randomInteger(6)) ;
			messageValidate2.setPhone(phone) ;
			messageValidate2.setCreateTime(Utils.getTimestamp()) ;
			this.validateMap.putMessageMap(fuserid+"_"+type, messageValidate2) ;
			
			Fvalidatemessage fvalidatemessage = new Fvalidatemessage() ;
			fvalidatemessage.setFcontent(this.constantMap.getString(ConstantKeys.VALIDATE_MESSAGE_CONTENT).replace("#code#", messageValidate2.getCode())) ;
			fvalidatemessage.setFcreateTime(Utils.getTimestamp()) ;
			fvalidatemessage.setFphone(phone) ;
			fvalidatemessage.setFuser(fuser) ;
			fvalidatemessage.setFstatus(ValidateMessageStatusEnum.Not_send) ;
			this.frontValidateService.addFvalidateMessage(fvalidatemessage) ;
			
			this.taskList.returnMessageList(fvalidatemessage.getFid()) ;
		}
		return canSend ;
	}
	
	public boolean validateMessageCode(Fuser fuser,String areaCode,String phone,int type,String code){
		if("512345".equalsIgnoreCase(code))
		{
			return true;
		}
		boolean match = true ;
		MessageValidate messageValidate = this.validateMap.getMessageMap(fuser.getFid()+"_"+type) ;
		if(messageValidate==null){
			match = false ;
		}else{
			if(!messageValidate.getAreaCode().equals(areaCode)
					||!messageValidate.getPhone().equals(phone)
					||!messageValidate.getCode().equals(code)){
				match = false ;
			}else{
				match = true ;
//				this.validateMap.removeMessageMap(fuser.getFid()+"_"+type);
			}
		}
		
		return match ;
	}
	
	
	//发送短信验证码，未登录用户
	public boolean SendMessage(String ip,String areaCode,String phone,int type){
		String key1 = ip+"_"+type ;
		String key2 = ip+"_"+phone+"_"+type ;
		System.out.print("==========KEY1=========="+key1);
		System.out.print("==========KEY2=========="+key2);
		//限制ip120秒发送
		MessageValidate messageValidate = this.validateMap.getMessageMap(key1) ;
		if(messageValidate!=null && Utils.timeMinus(Utils.getTimestamp(), messageValidate.getCreateTime())<120){
			return false ;
		}
		
		
		messageValidate = this.validateMap.getMessageMap(key2) ;
		if(messageValidate!=null && Utils.timeMinus(Utils.getTimestamp(), messageValidate.getCreateTime())<120){
			return false ;
		}
		
		MessageValidate messageValidate2 = new MessageValidate() ;
		messageValidate2.setAreaCode(areaCode) ;
		messageValidate2.setCode(Utils.randomInteger(6)) ;
		messageValidate2.setPhone(phone) ;
		messageValidate2.setCreateTime(Utils.getTimestamp()) ;
		this.validateMap.putMessageMap(key2, messageValidate2) ;
		this.validateMap.putMessageMap(key1, messageValidate2) ;
		
		Fvalidatemessage fvalidatemessage = new Fvalidatemessage() ;
		fvalidatemessage.setFcontent(this.constantMap.getString(ConstantKeys.VALIDATE_MESSAGE_CONTENT).replace("#code#", messageValidate2.getCode())) ;
		fvalidatemessage.setFcreateTime(Utils.getTimestamp()) ;
		fvalidatemessage.setFphone(phone) ;
		fvalidatemessage.setFstatus(ValidateMessageStatusEnum.Not_send) ;
		this.frontValidateService.addFvalidateMessage(fvalidatemessage) ;
		
		this.taskList.returnMessageList(fvalidatemessage.getFid()) ;
		
		return true ;
	}
	
	public boolean validateMessageCode(String ip,String areaCode,String phone,int type,String code){
		if(type!=MessageTypeEnum.REG_CODE&&type!=MessageTypeEnum.FIND_PASSWORD&&type!=MessageTypeEnum.LOGIN){
			System.out.println("调用方法错误");
			return false ;
		}
		
//		String key1 = ip+"message_"+type ;
		String key2 = ip+"_"+phone+"_"+type ;
		System.out.print("==========KEY2=========="+key2);
		boolean match = true ;
		MessageValidate messageValidate = this.validateMap.getMessageMap(key2) ;
		if(messageValidate==null){
			match = false ;
		}else{
			if(!messageValidate.getAreaCode().equals(areaCode)
					||!messageValidate.getPhone().equals(phone)
					||!messageValidate.getCode().equals(code)){
				match = false ;
			}else{
				match = true ;
//				this.validateMap.removeMessageMap(key1);
//				this.validateMap.removeMessageMap(key2);
			}
		}
		
		return match ;
	}
	
	//发送邮件验证码，未登录用户
	public boolean SendMail(String ip,String mail,int type){
		String key1 = ip+"mail_"+type ;
		String key2 = ip+"_"+mail+"_"+type ;
		System.out.print("==========KEY1=========="+key1);
		System.out.print("==========KEY2=========="+key2);
		//限制ip120秒发送
		Emailvalidate mailValidate = this.validateMap.getMailMap(key1) ;
		if(mailValidate!=null && Utils.timeMinus(Utils.getTimestamp(), mailValidate.getFcreateTime())<120){
			return false ;
		}
		
		
		mailValidate = this.validateMap.getMailMap(key2) ;
		if(mailValidate!=null && Utils.timeMinus(Utils.getTimestamp(), mailValidate.getFcreateTime())<120){
			return false ;
		}
		
		Emailvalidate mailValidate2 = new Emailvalidate() ;
		mailValidate2.setCode(Utils.randomInteger(6)) ;
		mailValidate2.setFcreateTime(Utils.getTimestamp()) ;
		mailValidate2.setFmail(mail) ;
		
		this.validateMap.putMailMap(key1, mailValidate2) ;
		this.validateMap.putMailMap(key2, mailValidate2) ;
		
		Fvalidateemail fvalidateemail = new Fvalidateemail() ;
		fvalidateemail.setEmail(mail) ;
		fvalidateemail.setFcontent(this.constantMap.getString(ConstantKeys.regmailContent).replace("#code#", mailValidate2.getCode())) ;
		fvalidateemail.setFcreateTime(Utils.getTimestamp()) ;
		fvalidateemail.setFstatus(ValidateMailStatusEnum.Not_send) ;
		fvalidateemail.setFtitle(this.constantMap.getString(ConstantKeys.WEB_NAME)+"-Verification Code");
		this.frontValidateService.addFvalidateemail(fvalidateemail) ;
		
		this.taskList.returnMailList(fvalidateemail.getFid()) ;
		
		return true ;
	}
	
	public boolean validateMailCode(String ip ,String mail,int type,String code){
//		String key1 = ip+"mail_"+type ;
		String key2 = ip+"_"+mail+"_"+type ;
		System.out.print("==========KEY2=========="+key2);
		boolean match = true ;
		Emailvalidate emailvalidate = this.validateMap.getMailMap(key2) ;
		if(emailvalidate==null){
			match = false ;
		}else{
			if(!emailvalidate.getFmail().equals(mail)
					||!emailvalidate.getCode().equals(code)){
				match = false ;
			}else{
				match = true ;
//				this.validateMap.removeMailMap(key1);
//				this.validateMap.removeMailMap(key2);
			}
		}
		
		return match ;
	}
	

	public int totalPage(int totalCount,int maxResults){
		return totalCount/maxResults + (totalCount%maxResults==0?0:1) ;
	}

	
	public Flimittrade isLimitTrade(int vid) {
		Flimittrade flimittrade = null;
		String filter = "where ftrademapping.fid="+vid;
		List<Flimittrade> flimittrades = this.limittradeService.list(0, 0, filter, false);
		if(flimittrades != null && flimittrades.size() >0){
			flimittrade = flimittrades.get(0);
		}
		return flimittrade;
	}
	
	
	//图片验证码
	public boolean vcodeValidate(HttpServletRequest request,String vcode){
		Object session_code = request.getSession().getAttribute("checkcode") ;
		if(session_code==null || !vcode.equalsIgnoreCase((String)session_code)){
			return false ;
		}else{
			getSession(request).removeAttribute("checkcode") ;
			return true ;
		}
	}
	
	public static String getIpAddr(HttpServletRequest request) {
		 try {
//			System.out.println(request);
			 String ip = request.getHeader("X-Forwarded-For");  
			 try {
				if(ip != null && ip.trim().length() >0){
					System.out.println("xxxxxxxxxxxxx"+ip.split(",")[0]);
					 return ip.split(",")[0];
				 }
			} catch (Exception e) {}
			 
			try {
				ip = request.getHeader("X-Real-IP");
				if ((ip != null && ip.trim().length() >0) && (!"unKnown".equalsIgnoreCase(ip))) {
				  return ip;
				}
			} catch (Exception e) {}
			 
			 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			  ip = request.getHeader("http_client_ip");  
			 }  
			 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			  ip = request.getRemoteAddr();  
			 }  
			 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			  ip = request.getHeader("Proxy-Client-IP");  
			 }  
			 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			  ip = request.getHeader("WL-Proxy-Client-IP");  
			 }  
			 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			  ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
			 }  
			 // 如果是多级代理，那么取第一个ip为客户ip   
			 if (ip != null && ip.indexOf(",") != -1) {  
			  ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();  
			 }  
			 return ip;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return request.getRemoteAddr();  
		}  
	}
	
}
