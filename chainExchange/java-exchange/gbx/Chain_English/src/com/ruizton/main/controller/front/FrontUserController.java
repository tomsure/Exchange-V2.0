package com.ruizton.main.controller.front;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import com.ruizton.main.Enum.LogTypeEnum;
import com.ruizton.main.Enum.RegTypeEnum;
import com.ruizton.main.Enum.UserStatusEnum;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Flog;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.LogService;
import com.ruizton.main.service.admin.UserService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.util.Constant;
import com.ruizton.util.Mobilutils;
import com.ruizton.util.PaginUtil;
import com.ruizton.util.Utils;

import net.sf.json.JSONObject;

@Controller
public class FrontUserController extends BaseController {

	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private AdminService adminService;
	@Autowired
	private LogService logService;
	@Autowired
	private UserService userService;
	@Autowired
	private ConstantMap map;
	
	/*
	 * @param type:0登陆，1注册
	 * */
	@RequestMapping(value={"/user/login","/m/user/login"})
	public ModelAndView login(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="")String forwardUrl
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		//推荐注册
		try{
			Fuser intro = null ;
			Cookie cs[] = request.getCookies() ;
			for (Cookie cookie : cs) {
				if(cookie.getName().endsWith("r")){
					intro = this.frontUserService.findById(Integer.parseInt(cookie.getValue())) ;
					break ;
				}
			}
			if(intro!=null){
				modelAndView.addObject("intro", intro.getFid()) ;
			}
		}catch(Exception e){}
		
		//服务中心
		try{
			List<Fuser> services = null ;
			Cookie cs[] = request.getCookies() ;
			for (Cookie cookie : cs) {
				if(cookie.getName().endsWith("sn")){
					services = this.frontUserService.findUserByProperty("fuserNo", cookie.getValue());
					break ;
				}
			}
			if(services!=null && services.size() ==1){
				modelAndView.addObject("service", services.get(0).getFuserNo().trim()) ;
			}
		}catch(Exception e){}
		
		if(GetSession(request)==null){
			modelAndView.addObject("forwardUrl",forwardUrl) ;
		}else{
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
			if(fuser.getFstatus()==UserStatusEnum.NORMAL_VALUE){
				CleanSession(request) ;
			}
			modelAndView.setViewName("redirect:/") ;
			return modelAndView;
		}
		
		int isIndex = 1;
		modelAndView.addObject("isIndex", isIndex) ;
		
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/user/sub_user_login") ;
		return modelAndView ;
	}
	
	//注册
	@RequestMapping(value={"/user/register","/m/user/register"})
	public ModelAndView register(
			HttpServletRequest request,
			HttpServletResponse resp
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		//已经登录跳回首页
		if(GetSession(request) != null){
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
			if(fuser.getFstatus()==UserStatusEnum.NORMAL_VALUE){
				CleanSession(request) ;
			}
		}
		if(GetSession(request) != null){
			modelAndView.setViewName("redirect:/") ;
			return modelAndView;
		}
		
		//推荐注册
		try{
			Fuser intro = null ;
			Cookie cs[] = request.getCookies() ;
			for (Cookie cookie : cs) {
				if(cookie.getName().endsWith("r")){
					intro = this.frontUserService.findById(Integer.parseInt(cookie.getValue())) ;
					break ;
				}
			}
			if(intro!=null){
				modelAndView.addObject("intro", intro.getFid()) ;
			}
		}catch(Exception e){}
		
		//服务中心
		try{
			List<Fuser> services = null ;
			Cookie cs[] = request.getCookies() ;
			for (Cookie cookie : cs) {
				if(cookie.getName().endsWith("sn")){
					services = this.frontUserService.findUserByProperty("fuserNo", cookie.getValue());
					break ;
				}
			}
			if(services!=null && services.size() ==1){
				modelAndView.addObject("service", services.get(0).getFuserNo().trim()) ;
			}
		}catch(Exception e){}
		
		int isIndex = 1;
		modelAndView.addObject("isIndex", isIndex) ;
		
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/user/sub_user_register") ;
		return modelAndView ;
	}

	//短信注册
	@RequestMapping(value={"/user/registerSMS"})
	public ModelAndView registerSMS(
			HttpServletRequest request,
			HttpServletResponse resp
	) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;

		//已经登录跳回首页
		if(GetSession(request) != null){
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
			if(fuser.getFstatus()==UserStatusEnum.NORMAL_VALUE){
				CleanSession(request) ;
			}
		}
		if(GetSession(request) != null){
			modelAndView.setViewName("redirect:/") ;
			return modelAndView;
		}

		//推荐注册
		try{
			Fuser intro = null ;
			Cookie cs[] = request.getCookies() ;
			for (Cookie cookie : cs) {
				if(cookie.getName().endsWith("r")){
					intro = this.frontUserService.findById(Integer.parseInt(cookie.getValue())) ;
					break ;
				}
			}
			if(intro!=null){
				modelAndView.addObject("intro", intro.getFid()) ;
			}
		}catch(Exception e){}

		//服务中心
		try{
			List<Fuser> services = null ;
			Cookie cs[] = request.getCookies() ;
			for (Cookie cookie : cs) {
				if(cookie.getName().endsWith("sn")){
					services = this.frontUserService.findUserByProperty("fuserNo", cookie.getValue());
					break ;
				}
			}
			if(services!=null && services.size() ==1){
				modelAndView.addObject("service", services.get(0).getFuserNo().trim()) ;
			}
		}catch(Exception e){}

		int isIndex = 1;
		modelAndView.addObject("isIndex", isIndex) ;

		modelAndView.setViewName("front"+Mobilutils.M(request)+"/user/sub_user_registerSMS") ;
		return modelAndView ;
	}
	
	@RequestMapping(value="/user/logout")
	public ModelAndView logout(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		this.CleanSession(request) ;
		modelAndView.setViewName("redirect:/") ;
		return modelAndView ;
	}
	
	@RequestMapping(value="/user/security")
	public ModelAndView security(HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		int level = fuser.getFscore().getFlevel() ;
		modelAndView.addObject("level", level) ;
		
		String device_name = new Constant().GoogleAuthName+":"+fuser.getFloginName();
		
		boolean isBindGoogle = fuser.getFgoogleBind() ;
		boolean isBindTelephone = fuser.isFisTelephoneBind() ;
		boolean isBindEmail = fuser.getFisMailValidate();
		boolean isTradePassword = false;
		boolean isLoginPassword = true;
		String telNumber = "";
		String email = "";
		if(fuser.getFtradePassword() != null && fuser.getFtradePassword().trim().length() >0){
			isTradePassword = true;
		}
		int bindType = 0 ;
//		if(fuser.getFhasRealValidate()){
//			bindType = bindType+1 ;
//		}
		if(isBindGoogle){
			bindType = bindType+1 ;
		}
        if(isBindTelephone){
        	bindType = bindType+1 ;
        	telNumber = fuser.getFtelephone().substring(0, fuser.getFtelephone().length()-5)+"****";
		}
        if(isBindEmail){
        	bindType = bindType+1 ;
        	String[] args = fuser.getFemail().split("@");
//        	email = args[0].substring(0, args[0].length()-(args[0].length()>=3?3:1))+"****"+args[1];
			email = fuser.getFemail();
		}
        if(isTradePassword){
        	bindType = bindType+1 ;
		}
        if(isLoginPassword){
        	bindType = bindType+1 ;
		}
        
        modelAndView.addObject("bindType", bindType) ;
        
        String loginName = "";
        if(fuser.getFregType() != RegTypeEnum.EMAIL_VALUE){
        	loginName = fuser.getFloginName().substring(0, fuser.getFloginName().length()-5)+"****";
        }else{
        	String[] args = fuser.getFloginName().split("@");
//        	loginName = args[0].substring(0, args[0].length()-(args[0].length()>=3?3:1))+"****"+args[1];
			loginName = fuser.getFloginName();
        }
        
        modelAndView.addObject("loginName", loginName) ;
        modelAndView.addObject("telNumber", telNumber) ;
        modelAndView.addObject("email", email) ;
        modelAndView.addObject("isBindGoogle", isBindGoogle) ;
        modelAndView.addObject("isBindTelephone", isBindTelephone) ;
        modelAndView.addObject("isBindEmail", isBindEmail) ;
        modelAndView.addObject("isTradePassword", isTradePassword) ;
        modelAndView.addObject("isLoginPassword", isLoginPassword) ;
        
		modelAndView.addObject("device_name",device_name) ;
		modelAndView.addObject("fuser",fuser) ;
		modelAndView.setViewName("/front/user/user_security") ;
		return modelAndView ;
	}
	
	@RequestMapping("/user/realCertification")
	public ModelAndView realCertification(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		modelAndView.addObject("fuser", fuser) ;
//		if(fuser.getFpostRealValidate()){
//			modelAndView.setViewName("front"+Mobilutils.M(request)+"/user/realCertification2") ;
//		}else{
			modelAndView.setViewName("front"+Mobilutils.M(request)+"/user/realCertification") ;
//		}
		return modelAndView ;
	}
	
	@RequestMapping("/user/userloginlog")
	public ModelAndView userloginlog(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int currentPage,
			@RequestParam(required=false,defaultValue="1")int type
			) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		
		if(type != 1 && type !=2){
			type=1;
		}
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		modelAndView.addObject("fuser", fuser) ;
		
		String filter = null;
		String url = "";
		if(type ==1){
			url = "userloginlog";
			filter = "where fkey2='"+fuser.getFloginName()+"' and ftype="+LogTypeEnum.User_LOGIN+" order by fid desc";
		}else{
			url = "usersettinglog";
			filter = "where fkey2='"+fuser.getFloginName()+"' and ftype <>"+LogTypeEnum.User_LOGIN+" order by fid desc";
		}
		
		List<Flog> logs = this.logService.list((currentPage-1)*Constant.RecordPerPage, Constant.RecordPerPage, filter, true);
		int total = this.adminService.getAllCount("Flog", filter);
		String pagin = PaginUtil.generatePagin(total/Constant.RecordPerPage+(total%Constant.RecordPerPage==0?0:1), currentPage, "/user/userloginlog.html?type="+type+"&") ;
		modelAndView.addObject("pagin", pagin) ;
		modelAndView.addObject("logs", logs) ;
		
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/user/"+url) ;
		return modelAndView ;
	}
	
//	///user/userinfo
//	@RequestMapping(value="/user/userinfo")
//	public ModelAndView userinfo(
//			HttpServletRequest request,
//			@RequestParam(required=false,defaultValue="1")int currentPage
//			) throws Exception{
//		ModelAndView modelAndView = new ModelAndView() ;
//		
//		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
//		int level = fuser.getFscore().getFlevel() ;
//		modelAndView.addObject("level", level) ;
//		modelAndView.addObject("fuser", fuser) ;
//		
//		String device_name = new Constant().GoogleAuthName+":"+fuser.getFloginName();
//		boolean isBindGoogle = fuser.getFgoogleBind() ;
//		boolean isBindTelephone = fuser.isFisTelephoneBind() ;
//		boolean isBindEmail = fuser.getFisMailValidate();
//		boolean isTradePassword = false;
//		boolean isLoginPassword = true;
//		String telNumber = "";
//		String email = "";
//		if(fuser.getFtradePassword() != null && fuser.getFtradePassword().trim().length() >0){
//			isTradePassword = true;
//		}
//        if(isBindTelephone){
//        	telNumber = fuser.getFtelephone().substring(0, fuser.getFtelephone().length()-5)+"****";
//		}
//        if(isBindEmail){
//        	String[] args = fuser.getFemail().split("@");
//        	email = args[0].substring(0, args[0].length()-(args[0].length()>=3?3:1))+"****"+args[1];
//		}
//        
//        String loginName = "";
//        if(fuser.getFregType() != RegTypeEnum.EMAIL_VALUE){
//        	loginName = fuser.getFloginName().substring(0, fuser.getFloginName().length()-5)+"****";
//        }else{
//        	String[] args = fuser.getFloginName().split("@");
//        	loginName = args[0].substring(0, args[0].length()-(args[0].length()>=3?3:1))+"****"+args[1];
//        }
//        
////        String isActive = "未激活";
////        StringBuffer sf = new StringBuffer();			
////		sf.append("select count(fid) from fuser where fisTelephoneBind=1 \n");
////		sf.append(" and fpostRealValidate=1 and fhasRealValidate=1 and fid="+fuser.getFid()+" and \n");
////		sf.append("(fid in( \n");
////		sf.append("select FUs_fId from fcapitaloperation where ftype=1 and fStatus=3 and FUs_fId="+fuser.getFid()+"\n");
////		sf.append(") or fid in(SELECT fuser from fpaycode where fstatus=2 and fuser="+fuser.getFid()+") or fid in(SELECT fuid from foperationlog where fstatus=2 and fuid="+fuser.getFid()+")) \n");
////		double count = this.adminService.getSQLValue(sf.toString());
////		if(count >0d){
////			isActive = "已激活";
////		}
////		
////        modelAndView.addObject("isActive", isActive) ;
//        modelAndView.addObject("device_name", device_name) ;
//        modelAndView.addObject("loginName", loginName) ;
//        modelAndView.addObject("telNumber", telNumber) ;
//        modelAndView.addObject("email", email) ;
//        modelAndView.addObject("isBindGoogle", isBindGoogle) ;
//        modelAndView.addObject("isBindTelephone", isBindTelephone) ;
//        modelAndView.addObject("isBindEmail", isBindEmail) ;
//        modelAndView.addObject("isTradePassword", isTradePassword) ;
//        modelAndView.addObject("isLoginPassword", isLoginPassword) ;
//		
//		
//		modelAndView.setViewName("/front/user/userinfo") ;
//		return modelAndView ;
//	}
	
	@RequestMapping(value="/link/qq/call")
	public ModelAndView qqCall(
			HttpServletRequest request,
			HttpServletResponse response
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		if(GetSession(request)!=null){
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
			if(fuser.getFstatus()==UserStatusEnum.NORMAL_VALUE){
				CleanSession(request) ;
			}
			modelAndView.setViewName("redirect:/") ;
			return modelAndView;
		}
		
		response.setContentType("text/html;charset=utf-8");
        try {
            response.sendRedirect(new Oauth().getAuthorizeURL(request));
        } catch (QQConnectException e) {
            e.printStackTrace();
        }
        modelAndView.setViewName("/front/user/call") ;
		return modelAndView ;
		
	}
	
	@RequestMapping(value="/link/wx/callback")
	public ModelAndView AfterWeiXinLogin(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		
		if(GetSession(request)!=null){
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
			if(fuser.getFstatus()==UserStatusEnum.NORMAL_VALUE){
				CleanSession(request) ;
			}
			modelAndView.setViewName("redirect:/") ;
			return modelAndView;
		}
		
		try {
            String code = request.getParameter("code");
            String openID   = null;
            String access_token = null;
            if ("".equals(code) || code == null || code.trim().length() == 0) {
                System.out.print("没有获取到响应参数");
            } else {
            	String APPID = this.map.get("weixinAppID").toString().trim();
            	String SECRET = this.map.get("weixinSECRET").toString().trim();
            	String nickName = null;
            	try {
					String u = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+APPID+"&secret="+SECRET+"&code="+code+"&grant_type=authorization_code";
					URL url = new URL(u) ;
					BufferedReader br = new BufferedReader(new InputStreamReader( url.openStream()) ) ;
					StringBuffer sb = new StringBuffer() ;
					String tmp = null ;
					while((tmp=br.readLine())!=null){
						sb.append(tmp) ;
					}
					 openID = JSONObject.fromObject(sb.toString()).getString("openid");
					 access_token = JSONObject.fromObject(sb.toString()).getString("access_token");
				} catch (Exception e1) {}
                if(openID != null && openID.trim().length() >0){
                	 Fuser fuser = this.frontUserService.findByQQlogin(openID) ;
                     if(fuser==null){
                     	//推广
                 		Fuser intro = null ;
                 		try{
                 			Cookie cs[] = request.getCookies() ;
                 			for (Cookie cookie : cs) {
                 				if(cookie.getName().endsWith("r")){
                 					intro = this.frontUserService.findById(Integer.parseInt(cookie.getValue())) ;
                 					break ;
                 				}
                 			}
                 		}catch(Exception e){}
                 		
                 		fuser = new Fuser() ;
                 		if(intro!=null){
                 			fuser.setfIntroUser_id(intro) ;
                 		}
                 		
                 		String xx = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openID+"&lang=zh_CN";
                 		URL url = new URL(xx) ;
    					BufferedReader br = new BufferedReader(new InputStreamReader( url.openStream()) ) ;
    					StringBuffer sb = new StringBuffer() ;
    					String tmp = null ;
    					while((tmp=br.readLine())!=null){
    						sb.append(tmp) ;
    					}
    					nickName= HtmlUtils.htmlEscape(JSONObject.fromObject(sb.toString()).getString("nickname").trim());
    					fuser.setSalt(Utils.getUUID());
                 		fuser.setQqlogin(openID) ;
                 		fuser.setFnickName(nickName) ;
                 		fuser.setFloginName(nickName+"_"+Utils.getRandomString(6)) ;
                 		
            			fuser.setFregType(RegTypeEnum.WX_VALUE);
            			fuser.setFisMailValidate(false) ;
            			String ip = getIpAddr(request) ;
            			fuser.setFregIp(ip);
            			fuser.setFlastLoginIp(ip);
            			
                 		fuser.setFregisterTime(Utils.getTimestamp()) ;
                 		fuser.setFloginPassword(Utils.MD5(openID,fuser.getSalt())) ;
                 		fuser.setFtradePassword(null) ;
                 		fuser.setFstatus(UserStatusEnum.NORMAL_VALUE) ;
                 		fuser.setFlastLoginTime(Utils.getTimestamp()) ;
                 		fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
                 		boolean saveFlag = false ;
                 		try {
                 			saveFlag = this.frontUserService.saveRegister(fuser) ;
                 		} catch (Exception e) {
                 			e.printStackTrace();
                 		}
                 		if(saveFlag){
                 			this.SetSession(fuser,request) ;
                 		}      
                 		
                     }else{
                     	//登陆
                     	if(fuser.getFstatus()==UserStatusEnum.NORMAL_VALUE){
                     		String ip = getIpAddr(request) ;
                     		fuser.setFlastLoginIp(ip);
                     		fuser.setFlastLoginTime(Utils.getTimestamp());
                     		this.frontUserService.updateFUser(fuser, null, LogTypeEnum.User_LOGIN, ip);
                     		this.SetSession(fuser,request) ;
         				}
                     }
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
		modelAndView.setViewName("redirect:/index.html") ;
		return modelAndView ;
	}
	
	@RequestMapping(value="/link/qq/callback")
	public ModelAndView AfterQQLogin(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		
		if(GetSession(request)!=null){
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
			if(fuser.getFstatus()==UserStatusEnum.NORMAL_VALUE){
				CleanSession(request) ;
			}
			modelAndView.setViewName("redirect:/") ;
			return modelAndView;
		}
		
		try {
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
            String accessToken   = null,
                   openID        = null;
            
            if (accessTokenObj.getAccessToken().equals("")) {
                System.out.print("没有获取到响应参数");
            } else {
                accessToken = accessTokenObj.getAccessToken();

                // 利用获取到的accessToken 去获取当前用的openid -------- start
                OpenID openIDObj =  new OpenID(accessToken);
                openID = openIDObj.getUserOpenID();

                UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                String nickName = HtmlUtils.htmlEscape(userInfoBean.getNickname().trim()) ;
                
                if(openID != null && openID.trim().length() >0){
                	 Fuser fuser = this.frontUserService.findByQQlogin(openID) ;
                     if(fuser==null){
                     	//推广
                 		Fuser intro = null ;
                 		try{
                 			Cookie cs[] = request.getCookies() ;
                 			for (Cookie cookie : cs) {
                 				if(cookie.getName().endsWith("r")){
                 					intro = this.frontUserService.findById(Integer.parseInt(cookie.getValue())) ;
                 					break ;
                 				}
                 			}
                 		}catch(Exception e){}
                 		
                 		fuser = new Fuser() ;
                 		if(intro!=null){
                 			fuser.setfIntroUser_id(intro) ;
                 		}
                 		fuser.setSalt(Utils.getUUID());
                 		fuser.setQqlogin(openID) ;
                 		fuser.setFnickName(nickName) ;
                 		fuser.setFloginName(nickName+"_"+Utils.getRandomString(6)) ;
                 		
            			fuser.setFregType(RegTypeEnum.QQ_VALUE);
            			fuser.setFisMailValidate(false) ;
            			String ip = getIpAddr(request) ;
            			fuser.setFregIp(ip);
            			fuser.setFlastLoginIp(ip);
            			
                 		fuser.setFregisterTime(Utils.getTimestamp()) ;
                 		fuser.setFloginPassword(Utils.MD5(openID,fuser.getSalt())) ;
                 		fuser.setFtradePassword(null) ;
                 		fuser.setFstatus(UserStatusEnum.NORMAL_VALUE) ;
                 		fuser.setFlastLoginTime(Utils.getTimestamp()) ;
                 		fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
                 		boolean saveFlag = false ;
                 		try {
                 			saveFlag = this.frontUserService.saveRegister(fuser) ;
                 		} catch (Exception e) {
                 			e.printStackTrace();
                 		}
                 		if(saveFlag){
                 			this.SetSession(fuser,request) ;
                 		}      
                 		
                     }else{
                     	//登陆
                     	if(fuser.getFstatus()==UserStatusEnum.NORMAL_VALUE){
                     		String ip = getIpAddr(request) ;
                     		fuser.setFlastLoginIp(ip);
                     		fuser.setFlastLoginTime(Utils.getTimestamp());
                     		this.frontUserService.updateFUser(fuser, null, LogTypeEnum.User_LOGIN, ip);
                     		this.SetSession(fuser,request) ;
         				}
                     }
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
		modelAndView.setViewName("redirect:/index.html") ;
		return modelAndView ;
	}
}
