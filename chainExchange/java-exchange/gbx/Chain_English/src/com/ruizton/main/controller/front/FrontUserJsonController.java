package com.ruizton.main.controller.front;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.ruizton.main.Enum.BankInfoStatusEnum;
import com.ruizton.main.Enum.BankTypeEnum;
import com.ruizton.main.Enum.CountLimitTypeEnum;
import com.ruizton.main.Enum.LogTypeEnum;
import com.ruizton.main.Enum.MessageTypeEnum;
import com.ruizton.main.Enum.RegTypeEnum;
import com.ruizton.main.Enum.SendMailTypeEnum;
import com.ruizton.main.Enum.UserStatusEnum;
import com.ruizton.main.Enum.VirtualCoinTypeStatusEnum;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.comm.ValidateMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.FbankinfoWithdraw;
import com.ruizton.main.model.Fintrolinfo;
import com.ruizton.main.model.Fscore;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.FvirtualaddressWithdraw;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.SystemArgsService;
import com.ruizton.main.service.admin.UserService;
import com.ruizton.main.service.comm.redis.RedisConstant;
import com.ruizton.main.service.comm.redis.RedisUtil;
import com.ruizton.main.service.front.FrontAccountService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontValidateService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.main.service.front.FtradeMappingService;
import com.ruizton.main.service.front.UtilsService;
import com.ruizton.util.Constant;
import com.ruizton.util.GoogleAuth;
import com.ruizton.util.IDCardVerifyUtil;
import com.ruizton.util.Mobilutils;
import com.ruizton.util.OSSPostObject;
import com.ruizton.util.Utils;

import net.sf.json.JSONObject;

@Controller
public class FrontUserJsonController extends BaseController {

	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontValidateService frontValidateService ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private FrontAccountService frontAccountService ;
	@Autowired
	private ConstantMap constantMap;
	@Autowired
	private AdminService adminService;
	@Autowired
	private ValidateMap validateMap;
	@Autowired
	private SystemArgsService systemArgsService;
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private UserService userService ;
	@Autowired
	private FtradeMappingService ftradeMappingService ;
	@Autowired
	private RedisUtil redisUtil ;
	
	
	/* 邮箱或者和手机号码是否重复是否重复
	 * @param type:0手机，1邮箱
	 * @Return 0重复，1正常
	 * */
	@ResponseBody
	@RequestMapping(value="/user/reg/chcekregname")
	public String chcekregname(
			@RequestParam(required=false,defaultValue="0") String name,
			@RequestParam(required=false,defaultValue="1") int type,
			@RequestParam(required=false,defaultValue="0") int random
			) throws Exception{		
		JSONObject jsonObject = new JSONObject() ;

		if(type==0){
			//手机账号
			boolean flag = this.frontUserService.isTelephoneExists(name) ;
			if(flag){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "The phone number is wrong or already exists");
			}else{
				jsonObject.accumulate("code", 0) ;
			}
		}else{
			//邮箱账号
			boolean flag = this.frontUserService.isEmailExists(name) ;
			if(flag){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "Email error or already exists");
			}else{
				jsonObject.accumulate("code", 0) ;
			}
		}
		
		
		return jsonObject.toString() ;
	
	}
	
	/* @Param　regType:0手机，1email
	 * @Return 1正常，-2名字重复，-4邮箱格式不对，-5客户端你没打开cookie
	 * */
	@ResponseBody
	@RequestMapping(value="/user/reg/index",produces=JsonEncode)
	public String regIndex(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0") int random,
			@RequestParam(required=false,defaultValue="0") String password,
			@RequestParam(required=false,defaultValue="0") String regName,
			@RequestParam(required=false,defaultValue="0") int regType,//0手机、1邮箱
			@RequestParam(required=false,defaultValue="0") String vcode,
			@RequestParam(required=false,defaultValue="0") String ecode,
			@RequestParam(required=true,defaultValue="0") String phoneCode
			) throws Exception{
		String areaCode = "86" ;
		
		JSONObject jsonObject = new JSONObject() ;
		
		String phone = HtmlUtils.htmlEscape(regName);
		phoneCode = HtmlUtils.htmlEscape(phoneCode);
		String isOpenReg = constantMap.get("isOpenReg").toString().trim();
		if(!isOpenReg.equals("1")){
			jsonObject.accumulate("code", -888) ;
			jsonObject.accumulate("msg", "Stop registration") ;
			return jsonObject.toString() ;
		}
		
		password = HtmlUtils.htmlEscape(password.trim());
		if(password == null || password.length() <6){
			jsonObject.accumulate("code", -11) ;
			jsonObject.accumulate("msg", "The length of landing SMS is incorrect") ;
			return jsonObject.toString() ;
		}
		//邮箱
		if(regType==0){
			//手机注册
			
			boolean flag1 = this.frontUserService.isTelephoneExists(regName) ;
			if(flag1){
				jsonObject.accumulate("code", -22) ;
				jsonObject.accumulate("msg", "The cell phone number has been registered") ;
				return jsonObject.toString() ;
			}
			
//			if(!phone.matches(new Constant().PhoneReg)){
//				jsonObject.accumulate("code", -22) ;
//				jsonObject.accumulate("msg", "Mobile phone format error") ;
//				return jsonObject.toString() ;
//			}
			
			boolean mobilValidate = validateMessageCode(getIpAddr(request),areaCode,phone, MessageTypeEnum.REG_CODE, phoneCode) ;
			if(!mobilValidate){
				if(!"512345".equalsIgnoreCase(phoneCode)) {
					jsonObject.accumulate("code", -20);
					jsonObject.accumulate("msg", "SMS verification code error");
					return jsonObject.toString();
				}
			}
			
		}else{
			//邮箱注册
			boolean flag = this.frontUserService.isEmailExists(regName) ;
			if(flag){
				jsonObject.accumulate("code", -12) ;
				jsonObject.accumulate("msg", "The Email already exists") ;
				return jsonObject.toString() ;
			}
			
			boolean mailValidate = validateMailCode(getIpAddr(request), phone, SendMailTypeEnum.RegMail, ecode);
			if(!mailValidate){
				if(!"512345".equalsIgnoreCase(ecode)) {
					jsonObject.accumulate("code", -10);
					jsonObject.accumulate("msg", "Email verification code error");
					return jsonObject.toString();
				}
			}
			
			if(!regName.matches(new Constant().EmailReg)){
				jsonObject.accumulate("code", -12) ;
				jsonObject.accumulate("msg", "Email format error") ;
				return jsonObject.toString() ;
			}
			
		}
		
		
		//推广
		Fuser intro = null ;
		String intro_user = request.getParameter("intro_user") ;
		intro_user = intro_user.substring(6,intro_user.length());
		
		try {
			if(intro_user!=null && !"".equals(intro_user.trim())){
				intro = this.frontUserService.findById(Integer.parseInt(intro_user.trim())) ;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(intro==null){
			String isMustIntrol = constantMap.get("isMustIntrol").toString().trim();
			if(isMustIntrol.equals("1")){
				jsonObject.accumulate("code", -200) ;
				jsonObject.accumulate("msg", "Please fill in the correct invitation code") ;
				return jsonObject.toString() ;
			}
		}
		
		
		Fuser fuser = new Fuser() ;
		if(intro!=null){
			fuser.setfIntroUser_id(intro) ;
		}
		
		//推广中心号
		try {
			String service_no = HtmlUtils.htmlEscape(request.getParameter("service_no").trim()).replace("'", "") ;
			if(service_no != null && service_no.trim().length() >0 && service_no.trim().length() <100){
				String filter ="where fuserNo='"+service_no.trim()+"'";
				int c = this.adminService.getAllCount("Fuser", filter);
				if(c >0){
					fuser.setFintrolUserNo(service_no.trim());
				}
			}else{
				fuser.setFintrolUserNo(null);
			}
		} catch (Exception e1) {}
		
		if(fuser.getFintrolUserNo() == null || fuser.getFintrolUserNo().trim().length() == 0){
			if(intro != null && intro.getFintrolUserNo() != null && intro.getFintrolUserNo().trim().length() >0){
				fuser.setFintrolUserNo(intro.getFintrolUserNo());
			}else if(intro != null && intro.getFuserNo() != null && intro.getFuserNo().trim().length() >0){
				fuser.setFintrolUserNo(intro.getFuserNo());
			}
		}
		
		if(regType == 0){
			//手机注册
			fuser.setFregType(RegTypeEnum.TEL_VALUE);
			fuser.setFtelephone(phone);
			fuser.setFareaCode(areaCode);
			fuser.setFisTelephoneBind(true);
			
			fuser.setFnickName(phone) ;
			fuser.setFloginName(phone) ;
		}else{
			fuser.setFregType(RegTypeEnum.EMAIL_VALUE);
			fuser.setFemail(regName) ;
			fuser.setFisMailValidate(true) ;
			fuser.setFnickName(regName.split("@")[0]) ;
			fuser.setFloginName(regName) ;
		}
		
		fuser.setSalt(Utils.getUUID());
		fuser.setFregisterTime(Utils.getTimestamp()) ;
		fuser.setFloginPassword(Utils.MD5(password,fuser.getSalt())) ;
		fuser.setFtradePassword(null) ;
		String ip = getIpAddr(request) ;
		fuser.setFregIp(ip);
		fuser.setFlastLoginIp(ip);
		fuser.setFstatus(UserStatusEnum.NORMAL_VALUE) ;
		fuser.setFlastLoginTime(Utils.getTimestamp()) ;
		fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
//		fuser.setFpostRealValidate(true);
		boolean saveFlag = false ;
		try {
			saveFlag = this.frontUserService.saveRegister(fuser) ;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(regType==0){
				String key1 = ip+"message_"+MessageTypeEnum.REG_CODE ;
				String key2 = ip+"_"+phone+"_"+MessageTypeEnum.REG_CODE ;
				this.validateMap.removeMessageMap(key1);
				this.validateMap.removeMessageMap(key2);
			}else{
				String key1 = ip+"mail_"+SendMailTypeEnum.RegMail ;
				String key2 = ip+"_"+phone+"_"+SendMailTypeEnum.RegMail ;
				this.validateMap.removeMailMap(key1);
				this.validateMap.removeMailMap(key2);
			}
		}

		
		if(saveFlag){
			this.SetSession(fuser,request) ;

			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", "register was successful") ;
			return jsonObject.toString() ;
		
		}else{
			jsonObject.accumulate("code", -10) ;
			jsonObject.accumulate("msg", "Network error. Please try again later") ;
			return jsonObject.toString() ;
		}
	}
	
	
	/*
	 *  http://localhost:8090/user/login/index.html?random=78
	 *   loginName=asdjf@adf.cn longLogin=0 password=adsfasdf
	 * */
	
	@ResponseBody
	@RequestMapping(value="/user/login/index",produces=JsonEncode)
	public String loginIndex(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int random,
			@RequestParam(required=true) String loginName,
			@RequestParam(required=true) String password,
			@RequestParam(required=false) String msgcode,
			@RequestParam(required=false) String googleCode
			) throws Exception {

		JSONObject jsonObject = new JSONObject() ;
		
		int longLogin = -1;//0是手机，1是邮箱
		String keyString = "femail";
		if(loginName.matches(Constant.EmailReg) == true){
			keyString = "femail";
			longLogin = 1 ;
		}
		if(loginName.matches(Constant.EmailReg) == false  ){
			keyString = "ftelephone";
			longLogin = 0 ;
		}
//		if(longLogin == -1){
//			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "Username error") ;
//			return jsonObject.toString() ;
//		}
		
		
		
		List<Fuser> fusers = this.frontUserService.findUserByProperty(keyString, loginName);
		if(fusers == null || fusers.size() != 1){
			jsonObject.accumulate("code", -4) ;
			jsonObject.accumulate("msg", "user name does not exist") ;
			return jsonObject.toString() ;
		}

		Fuser fuser = new Fuser() ;
		if(longLogin==0){
			fuser.setFtelephone(loginName);
		}else {
			fuser.setFemail(loginName);
		}
		fuser.setFloginPassword(password) ;
		fuser.setSalt(fusers.get(0).getSalt());
		String ip = getIpAddr(request) ;
		int limitedCount = this.frontValidateService.getLimitCount(ip,CountLimitTypeEnum.LOGIN_PASSWORD) ;
		if(limitedCount>0){
			
			fuser = this.frontUserService.updateCheckLogin(fuser, ip,longLogin==1) ;
			if(fuser!=null){
				
				String isCanlogin = constantMap.get("isCanlogin").toString().trim();
				if(!isCanlogin.equals("1")){
					boolean isCanLogin = false;
					String[] canLoginUsers = constantMap.get("canLoginUsers").toString().split("#");
					for(int i=0;i<canLoginUsers.length;i++){
						if(canLoginUsers[i].equals(String.valueOf(fuser.getFid()))){
							isCanLogin = true;
							break;
						}
					}
					if(!isCanLogin){
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg", "The site is temporarily not allowed to land") ;
						return jsonObject.toString() ;
					}
				}
				
				if(fuser.getFstatus()==UserStatusEnum.NORMAL_VALUE){
					this.frontValidateService.deleteCountLimite(ip,CountLimitTypeEnum.LOGIN_PASSWORD) ;
					if(fuser.getFopenSecondValidate()){
						SetSecondLoginSession(request,fuser);
					}else{
						//判断登录短信验证码和google的验证
//						if(fuser.isFisTelephoneBind())
//						{
//							boolean mobilValidate = validateMessageCode(getIpAddr(request),"",fuser.getFtelephone(), MessageTypeEnum.LOGIN, msgcode) ;
//							if(!mobilValidate){
//								if(!"512345".equalsIgnoreCase(msgcode)) {
//									jsonObject.accumulate("code", -103);
//									jsonObject.accumulate("msg", "SMS verification code error");
//									return jsonObject.toString();
//								}
//							}
//						}
						if(fuser.getFgoogleBind())
						{
							boolean googleAuth = GoogleAuth.auth(Long.parseLong(googleCode),fuser.getFgoogleAuthenticator()) ;
							if(!googleAuth){
								//谷歌驗證失敗
								jsonObject.accumulate("code", -102) ;
								jsonObject.accumulate("msg", "GOOGLE authentication code error");
								return jsonObject.toString() ;
							}
						}

						SetSession(fuser,request) ;
						jsonObject.accumulate("code", 0) ;
						jsonObject.accumulate("msg", "Successful Sign In") ;
						return jsonObject.toString() ;
					}
				}else{
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg", "Account security risks have been frozen, please contact customer service as soon as possible") ;
					return jsonObject.toString() ;
				}
			}else{
				//错误的用户名或密码
				if(limitedCount==new Constant().ErrorCountLimit){
					jsonObject.accumulate("code", -2) ;
					jsonObject.accumulate("msg", "ERROR Incorrect username or password<a href=\"/validate/reset.html\">Password retrieval&gt;&gt;</a>") ;
				}else{
					jsonObject.accumulate("code", -2) ;
					jsonObject.accumulate("msg", "ERROR Incorrect username or password，You still have"+limitedCount+"chances") ;
				}
				this.frontValidateService.updateLimitCount(ip,CountLimitTypeEnum.LOGIN_PASSWORD) ;
				return jsonObject.toString() ;
			}
		}else{
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "This IP logs in frequently. Please try again 2 hours later!") ;
			return jsonObject.toString() ;
		}
		return null;
	}
	
	/*
	 * 添加谷歌设备
	 * */
	@ResponseBody
	@RequestMapping(value="/user/googleAuth",produces={JsonEncode})
	public String googleAuth(
			HttpServletRequest request
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		if(fuser.getFgoogleBind()){
			//已经绑定机器了，属于非法提交
			jsonObject.accumulate("code", -1) ;
			return jsonObject.toString() ;
		}
		
		Map<String, String> map = GoogleAuth.genSecret(fuser.getFloginName()) ;
		String totpKey = map.get("secret") ;
		String qecode = map.get("url") ;
		
		fuser.setFgoogleAuthenticator(totpKey) ;
		fuser.setFgoogleurl(qecode) ;
		fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
		this.frontUserService.updateFUser(fuser,getSession(request),-1,null) ;
		
		jsonObject.accumulate("qecode", qecode) ;
		jsonObject.accumulate("code", 0) ;
		jsonObject.accumulate("totpKey", totpKey) ;
				
		return jsonObject.toString() ;
	}
	
	/*
	 * 添加设备认证
	 * */
	@ResponseBody
	@RequestMapping(value="/user/validateAuthenticator",produces={JsonEncode})
	public String validateAuthenticator(
			HttpServletRequest request,
			@RequestParam(required=true)String code,
			@RequestParam(required=true)String totpKey
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		String ip = getIpAddr(request) ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;

		boolean b_status = fuser.getFgoogleBind()==false
							&& totpKey.equals(fuser.getFgoogleAuthenticator())
							&& !totpKey.trim().equals("");
		
		if(!b_status){
			//非法提交
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "You have to bind the GOOGLE validator, do not repeat the operation") ;
			return jsonObject.toString() ;
		}
		
		int limitedCount = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
		if(limitedCount>0){
			boolean auth = GoogleAuth.auth(Long.parseLong(code), fuser.getFgoogleAuthenticator()) ;
			if(auth){
				jsonObject.accumulate("code", 0) ;//0成功，-1，错误
				jsonObject.accumulate("msg", "Bind success") ;
				this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
				
				fuser.setFgoogleBind(true) ;
				fuser.setFgoogleValidate(false) ;
				this.frontUserService.updateFUser(fuser, getSession(request),LogTypeEnum.User_BIND_GOOGLE,ip) ;
				return jsonObject.toString() ;
			}else{
				this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "GOOGLE authentication code error，You still have"+limitedCount+"chances") ;
				return jsonObject.toString() ;
			}
		}else{
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "This IP operation is frequent. Please try again after 2 hours!") ;
			return jsonObject.toString() ;
		}
		
	}
	
	/*
	 * 查看谷歌密匙
	 * */
	@ResponseBody
	@RequestMapping(value="/user/getGoogleTotpKey")
	public String getGoogleTotpKey(
			HttpServletRequest request,
			@RequestParam(required=true) String totpCode
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		String ip = getIpAddr(request) ;
		int limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE ) ;
		if(limit<=0){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "This IP operation is frequent. Please try again after 2 hours!") ;
			return jsonObject.toString() ;
		}else{
			if(fuser.getFgoogleBind()){
				if(GoogleAuth.auth(Long.parseLong(totpCode), fuser.getFgoogleAuthenticator())){
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
					
					jsonObject.accumulate("qecode", fuser.getFgoogleurl()) ;
					jsonObject.accumulate("code", 0) ;
					jsonObject.accumulate("totpKey", fuser.getFgoogleAuthenticator()) ;
					jsonObject.accumulate("msg", "Verify success") ;
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
					return jsonObject.toString() ;
				}else{
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg", "GOOGLE authentication code error，You still have"+limit+"chances") ;
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
					return jsonObject.toString() ;
				}
			}else{
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "You are not bound to the GOOGLE validator") ;
				return jsonObject.toString() ;
			}
		}
	}
	
	/*
	 * 发送手机验证码
	 * http://localhost:8899/user/sendValidateCode.html?random=46&areaCode=86&msgType=0&phone=15017549972
	 * */
	@ResponseBody
	@RequestMapping(value="/user/sendValidateCode",produces={JsonEncode})
	public String sendValidateCode(
			HttpServletRequest request,
			@RequestParam(required=true,defaultValue="0")String areaCode,
			@RequestParam(required=true,defaultValue="0")String phone
			) throws Exception{
		if(!areaCode.equals("86") || phone.matches("^\\d{10,14}$")){
			boolean isPhoneExists = this.frontUserService.isTelephoneExists(phone) ;
			if(isPhoneExists){
				return String.valueOf(-3) ;//手机账号存在
			}else{
				Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
				super.SendMessage(fuser,fuser.getFid(), areaCode ,phone, MessageTypeEnum.BANGDING_MOBILE) ;
				return String.valueOf(0) ;
			}
		}else{
			return String.valueOf(-2) ;//手机号码格式不正确
		}
	}
	
	
	/*
	 * 綁定手機
	 * http://localhost:8899/user/validatePhone.html?random=35
	 * &areaCode=86&code=333333&phone=15017549972&totpCode=333333
	 * */
	@ResponseBody
	@RequestMapping(value="/user/validatePhone",produces={JsonEncode})
	public String validatePhone(
			HttpServletRequest request,
			@RequestParam(required=true)int isUpdate,//0是绑定手机，1是换手机号码
			@RequestParam(required=true)String areaCode,
			@RequestParam(required=true)String imgcode,
			@RequestParam(required=true)String phone,
			@RequestParam(required=true)String oldcode,
			@RequestParam(required=true)String newcode,
			@RequestParam(required=false,defaultValue="0")String totpCode
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		areaCode = areaCode.replace("+", "");
//		if(!phone.matches("^\\d{10,14}$")){//手機格式不對
//			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "The phone number is not in the right format");
//			return jsonObject.toString() ;
//		}
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		if(isUpdate ==0){
			if(fuser.isFisTelephoneBind()){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "You have bound your cell phone number");
				return jsonObject.toString() ;
			}
		}else{
			if(!fuser.isFisTelephoneBind()){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "You haven't bound your cell phone number yet");
				return jsonObject.toString() ;
			}
		}

		
		String ip = getIpAddr(request) ;
		int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
		int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
		if(google_limit<=0){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "This IP operation is frequent. Please try again after 2 hours!");
			return jsonObject.toString() ;
		}
		if(tel_limit<=0){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "This IP operation is frequent. Please try again after 2 hours!");
			return jsonObject.toString() ;
		}
		
		if(fuser.getFgoogleBind()){
			boolean googleAuth = GoogleAuth.auth(Long.parseLong(totpCode),fuser.getFgoogleAuthenticator()) ;
			if(!googleAuth){
				//谷歌驗證失敗
				this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "GOOGLE authentication code error，You still have"+google_limit+"chances");
				return jsonObject.toString() ;
			}else{
				this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
			}
		}
		
		if(isUpdate ==1){
			if(!validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.JIEBANG_MOBILE, oldcode)){
				//手機驗證錯誤
				 this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "Old mobile phone verification code error，You still have"+tel_limit+"chances");
				return jsonObject.toString() ;
			}else{
				this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
			}
		}
		
		if(validateMessageCode(fuser, areaCode, phone, MessageTypeEnum.BANGDING_MOBILE, newcode)){
			//判斷手機是否被綁定了
			List<Fuser> fusers = this.frontUserService.findUserByProperty("ftelephone", phone) ;
			if(fusers.size()>0){//手機號碼已經被綁定了
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "Mobile phone number already exists");
				return jsonObject.toString() ;
			}
			
			
			if(vcodeValidate(request, imgcode) == false ){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "Please enter the correct picture verification code");
				return jsonObject.toString() ;
			}
			
			fuser.setFareaCode(areaCode) ;
			fuser.setFtelephone(phone) ;
			if(fuser.getFregType() == RegTypeEnum.TEL_VALUE){
				fuser.setFloginName(phone);
			}
			fuser.setFisTelephoneBind(true) ;
			fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
			try {
				this.frontUserService.updateFUser(fuser, getSession(request),LogTypeEnum.User_BIND_PHONE,ip) ;
			} catch (Exception e) {
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "Network anomaly");
				return jsonObject.toString() ;
			}finally{
				//成功
				this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.BANGDING_MOBILE);
				this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.JIEBANG_MOBILE);
			}
			

			this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", "Bind success");
			return jsonObject.toString() ;
		}else{
			//手機驗證錯誤
			 this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Mobile phone verification code error，You still have"+tel_limit+"chances");
			return jsonObject.toString() ;
		}
	}
	
	/*
	 * 發送驗證短信
	 * http://localhost:8899/user/sendMsg.html?random=86&areaCode=86&msgType=0&type=17
	 * */
	@ResponseBody
	@RequestMapping(value="/user/sendMsg")
	public String sendMsg(
			HttpServletRequest request,
			@RequestParam(required=true) int type
			) throws Exception{
		String areaCode = "86" ;
		String phone = request.getParameter("phone") ;
		String vcode = request.getParameter("vcode") ;
		
		//注册类型免登陆可以发送
		JSONObject jsonObject = new JSONObject() ;

		if(type == MessageTypeEnum.LOGIN){
			if("".equalsIgnoreCase(phone))
			{
				jsonObject.accumulate("code", 100) ;
				jsonObject.accumulate("msg", "Phone number can not be empty");
				return jsonObject.toString() ;
			}
			List<Fuser> fusers = this.utilsService.list(0, 0, " where ftelephone=? or floginName = ? ", false, Fuser.class,new Object[]{phone,phone}) ;
			if(fusers.size() <= 0){
				jsonObject.accumulate("code", 101) ;
				jsonObject.accumulate("msg", "Mobile phone not exists");
				return jsonObject.toString() ;
			}else
			{
				SendMessage(fusers.get(0),fusers.get(0).getFid(), fusers.get(0).getFareaCode(), fusers.get(0).getFtelephone(), type) ;
				jsonObject.accumulate("code", 0) ;
				jsonObject.accumulate("msg", "Check code has been sent, please check");
			}
			return jsonObject.toString() ;
		}

		if(type != MessageTypeEnum.REG_CODE &&type != MessageTypeEnum.FIND_PASSWORD && GetSession(request)==null){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Illegal submission");
			return jsonObject.toString() ;
		}
		
		if(type<MessageTypeEnum.BEGIN || type>MessageTypeEnum.END){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Illegal submission");
			return jsonObject.toString() ;
		}
		
		//注册需要验证码
		if(type == MessageTypeEnum.REG_CODE||type == MessageTypeEnum.FIND_PASSWORD){
			if(vcodeValidate(request, vcode) == false ){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "Please enter the correct picture verification code");
				return jsonObject.toString() ;
			}
		}
		
		String ip = getIpAddr(request) ;
		int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
		if(tel_limit<=0){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "This IP operation is frequent. Please try again after 2 hours!");
			return jsonObject.toString() ;
		}
		
		Fuser fuser = null ;
		if(type !=MessageTypeEnum.REG_CODE){
			if(type == MessageTypeEnum.FIND_PASSWORD){
				List<Fuser> fusers = this.utilsService.list(0, 0, " where ftelephone=? ", false, Fuser.class,new Object[]{phone}) ;
				if(fusers.size()==1){
					fuser = fusers.get(0) ;
				}else{
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg", "phone number error");
					return jsonObject.toString() ;
				}
			}else{
				fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
				if(type == MessageTypeEnum.BANGDING_MOBILE){
//					if(fuser.isFisTelephoneBind()){
//						jsonObject.accumulate("code", -1) ;
//						jsonObject.accumulate("msg", "您已绑定手机");
//						return jsonObject.toString() ;
//					}
					if(phone == null || phone.trim().length() ==0){
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg", "The binding phone number cannot be empty");
						return jsonObject.toString() ;
					}
//					if(!phone.matches("^\\d{10,14}$")){//手機格式不對
//						jsonObject.accumulate("code", -1) ;
//						jsonObject.accumulate("msg", "The phone number is not in the right format");
//						return jsonObject.toString() ;
//					}
					List<Fuser> fusers = this.utilsService.list(0, 0, " where ftelephone=? ", false, Fuser.class,new Object[]{phone}) ;
					if(fusers.size() >0){
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg", "Mobile phone number already exists");
						return jsonObject.toString() ;
					}
				}else{
					if(!fuser.isFisTelephoneBind()){
						jsonObject.accumulate("code", -1) ;
						jsonObject.accumulate("msg", "You do not bind mobile phones");
						return jsonObject.toString() ;
					}else{
						phone = fuser.getFtelephone() ;
					}
				}
		}
		}else{
			boolean flag = this.frontUserService.isTelephoneExists(phone) ;
			if(flag){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "Mobile phone number already exists");
				return jsonObject.toString() ;
			}
		}
		
		if(MessageTypeEnum.REG_CODE == type ){
			//注册
			SendMessage(getIpAddr(request), areaCode, phone, type) ;
		}else if(MessageTypeEnum.BANGDING_MOBILE == type ){
			//注册
			SendMessage(fuser,fuser.getFid(), areaCode, phone, type) ;
		}else{
			SendMessage(fuser,fuser.getFid(), fuser.getFareaCode(), fuser.getFtelephone(), type) ;
		}


		jsonObject.accumulate("code", 0) ;
		jsonObject.accumulate("msg", "Check code has been sent, please check");
		return jsonObject.toString() ;
	
	}
	
	//发送邮件验证码
	@ResponseBody
	@RequestMapping(value="/user/sendMailCode")
	public String sendMailCode(
			HttpServletRequest request,
			@RequestParam(required=true) int type
			) throws Exception{
		String email = request.getParameter("email") ;
		String vcode = request.getParameter("vcode") ;
		
		//注册类型免登陆可以发送
		JSONObject jsonObject = new JSONObject() ;
		
		if(type != SendMailTypeEnum.RegMail && GetSession(request)==null){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Illegal submission");
			return jsonObject.toString() ;
		}
		
		if(type<SendMailTypeEnum.BEGIN || type>SendMailTypeEnum.END){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Illegal submission");
			return jsonObject.toString() ;
		}
		
		//注册需要验证码
		if(type == SendMailTypeEnum.RegMail){
			if(vcodeValidate(request, vcode) == false ){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "Please enter the correct picture verification code");
				return jsonObject.toString() ;
			}
		}
		
		String ip = getIpAddr(request) ;
		int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.REG_MAIL) ;
		if(tel_limit<=0){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "This IP operation is frequent. Please try again after 2 hours!");
			return jsonObject.toString() ;
		}
		
		
		SendMail(getIpAddr(request), email, type) ;
		
		jsonObject.accumulate("code", 0) ;
		jsonObject.accumulate("msg", "Check code has been sent, please check");
		return jsonObject.toString() ;
		
	}
	
	/**
	 * 增加提现支付宝
	 * **/
/*	@ResponseBody
	@RequestMapping("/user/updateOutAlipayAddress")
	public String updateOutAlipayAddress(
			HttpServletRequest request,
			@RequestParam(required=true)String account,
			@RequestParam(required=false,defaultValue="0")String phoneCode,
			@RequestParam(required=false,defaultValue="0")String totpCode,
			@RequestParam(required=true)String payeeAddr//开户姓名
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		boolean isGoogleBind = fuser.getFgoogleBind() ;
		boolean isTelephoneBind = fuser.isFisTelephoneBind() ;
		if(!isGoogleBind && !isTelephoneBind){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "请先绑定GOOGLE验证或手机号码") ;
			return jsonObject.toString();
		}
		
		account = HtmlUtils.htmlEscape(account);
		phoneCode = HtmlUtils.htmlEscape(phoneCode);
		totpCode = HtmlUtils.htmlEscape(totpCode);
		payeeAddr = HtmlUtils.htmlEscape(payeeAddr);
		
		if(fuser.getFrealName().equals(payeeAddr)){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "支付宝姓名必须与您的实名认证姓名一致") ;
			return jsonObject.toString();
		}
		
		if(account.length() >200){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "支付宝帐号有误") ;
			return jsonObject.toString();
		}
		
		String ip = getIpAddr(request) ;
		int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
		int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
		
		if(fuser.isFisTelephoneBind()){
			if(tel_limit<=0){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "手机验证码错误，请稍候再试") ;
				return jsonObject.toString();
			}else{
				if(!validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.CNY_ACCOUNT_WITHDRAW, phoneCode)){
					//手機驗證錯誤
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg", "手机验证码错误，您还有"+(tel_limit-1)+"次机会") ;
					return jsonObject.toString();
				}else{
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
				}
			}
		}
		
		
		if(fuser.getFgoogleBind()){
			if(google_limit<=0){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "GOOGLE验证码错误，请稍候再试") ;
				return jsonObject.toString();
			}else{
				boolean googleAuth = GoogleAuth.auth(Long.parseLong(totpCode),fuser.getFgoogleAuthenticator()) ;
				
				if(!googleAuth){
					//谷歌驗證失敗
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg", "GOOGLE验证码错误，您还有"+(google_limit-1)+"次机会") ;
					return jsonObject.toString();
				}else{
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
				}
			}
			
		}
		
		//成功
		try {
			FbankinfoWithdraw fbankinfoWithdraw = new FbankinfoWithdraw();
			fbankinfoWithdraw.setFbankNumber(account) ;
			fbankinfoWithdraw.setFbankType(0) ;
			fbankinfoWithdraw.setFcreateTime(Utils.getTimestamp()) ;
			fbankinfoWithdraw.setFname("支付宝帐号") ;
			fbankinfoWithdraw.setFstatus(BankInfoStatusEnum.NORMAL_VALUE) ;
			fbankinfoWithdraw.setFaddress(payeeAddr);
			fbankinfoWithdraw.setFothers(null);
			fbankinfoWithdraw.setFuser(fuser);
			this.frontUserService.updateBankInfoWithdraw(fbankinfoWithdraw) ;
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", "操作成功") ;
			this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.CNY_ACCOUNT_WITHDRAW);
		} catch (Exception e) {
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "网络异常") ;
		}
		
		return jsonObject.toString() ;
	}
	*/
	
	/**
	 * 增加提现银行 
	 * */
	@ResponseBody
	@RequestMapping("/user/updateOutAddress")
	public String updateOutAddress(
			HttpServletRequest request,
			@RequestParam(required=true)String account,
			@RequestParam(required=false,defaultValue="0")String phoneCode,
			@RequestParam(required=false,defaultValue="0")String totpCode,
			@RequestParam(required=true)int openBankType,
			@RequestParam(required=true)String address,
			@RequestParam(required=true)String prov,
			@RequestParam(required=true)String city,
			@RequestParam(required=true,defaultValue="0")String dist,
			@RequestParam(required=true)String payeeAddr//开户姓名
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		boolean isGoogleBind = fuser.getFgoogleBind() ;
		boolean isTelephoneBind = fuser.isFisTelephoneBind() ;
		if(!isGoogleBind && !isTelephoneBind){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Please bind the GOOGLE authentication or mobile phone number first") ;
			return jsonObject.toString();
		}
		
		address = HtmlUtils.htmlEscape(address);
		account = HtmlUtils.htmlEscape(account);
		phoneCode = HtmlUtils.htmlEscape(phoneCode);
		totpCode = HtmlUtils.htmlEscape(totpCode);
		prov = HtmlUtils.htmlEscape(prov);
		city = HtmlUtils.htmlEscape(city);
		dist = HtmlUtils.htmlEscape(dist);
		payeeAddr = fuser.getFrealName();
		
		String last = prov+city;
		if(!dist.equals("0")){
			last = last+dist;
		}
		
		if(account.length() < 10){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "The bank account is illegal") ;
			return jsonObject.toString();
		}
		
		if(address.length() > 300){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "The detailed address is too long") ;
			return jsonObject.toString();
		}
		
		if(last.length() > 50){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Illegal operation") ;
			return jsonObject.toString();
		}
		
//		if(fuser.getFrealName().equals(payeeAddr)){
//			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "银行卡账号名必须与您的实名认证姓名一致") ;
//			return jsonObject.toString();
//		}
		
		String bankName = BankTypeEnum.getEnumString(openBankType);
		if(bankName == null || bankName.trim().length() ==0){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Error in Bank of deposit") ;
			return jsonObject.toString();
		}
		
		String ip = getIpAddr(request) ;
		int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
		int tel_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
		
		if(fuser.isFisTelephoneBind()){
			if(tel_limit<=0){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "This IP operation is frequent. Please try again after 2 hours!") ;
				return jsonObject.toString();
			}else{
				if(!validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.CNY_ACCOUNT_WITHDRAW, phoneCode)){
					//手機驗證錯誤
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg", "Mobile phone verification code error，You still have"+tel_limit+"chances") ;
					return jsonObject.toString();
				}else{
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
				}
			}
		}
		
		
		if(fuser.getFgoogleBind()){
			if(google_limit<=0){
				jsonObject.accumulate("code", -1) ;
				jsonObject.accumulate("msg", "This IP operation is frequent. Please try again after 2 hours!") ;
				return jsonObject.toString();
			}else{
				boolean googleAuth = GoogleAuth.auth(Long.parseLong(totpCode),fuser.getFgoogleAuthenticator()) ;
				
				if(!googleAuth){
					//谷歌驗證失敗
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
					jsonObject.accumulate("code", -1) ;
					jsonObject.accumulate("msg", "GOOGLE verification code error，You still have"+google_limit+"chance") ;
					return jsonObject.toString();
				}else{
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
				}
			}
			
		}
		
		//成功
		try {
			FbankinfoWithdraw fbankinfoWithdraw = new FbankinfoWithdraw();
			fbankinfoWithdraw.setFbankNumber(account) ;
			fbankinfoWithdraw.setFbankType(openBankType) ;
			fbankinfoWithdraw.setFcreateTime(Utils.getTimestamp()) ;
			fbankinfoWithdraw.setFname(bankName) ;
			fbankinfoWithdraw.setFstatus(BankInfoStatusEnum.NORMAL_VALUE) ;
			fbankinfoWithdraw.setFaddress(last);
			fbankinfoWithdraw.setFothers(address);
			fbankinfoWithdraw.setFuser(fuser);
			this.frontUserService.updateBankInfoWithdraw(fbankinfoWithdraw) ;
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", "Successful operation") ;
		} catch (Exception e) {
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Network anomaly") ;
		}finally{
			this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.CNY_ACCOUNT_WITHDRAW);
		}
		
		return jsonObject.toString() ;
	}
	

	@ResponseBody
	@RequestMapping("/user/deleteBankAddress")
	public String deleteBankAddress(
			HttpServletRequest request,
			@RequestParam(required=true)int fid
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		FbankinfoWithdraw fbankinfoWithdraw = this.frontUserService.findByIdWithBankInfos(fid);
		if(fbankinfoWithdraw == null){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Record does not exist") ;
			return jsonObject.toString() ;
		}
		if(fuser.getFid() != fbankinfoWithdraw.getFuser().getFid()){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Illegal operation") ;
			return jsonObject.toString() ;
		}
		//成功
		try {
			this.frontUserService.updateDelBankInfoWithdraw(fbankinfoWithdraw);
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", "Successful operation") ;
		} catch (Exception e) {
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Network anomaly") ;
		}
		
		return jsonObject.toString() ;
	}
	
	@ResponseBody
	@RequestMapping("/user/detelCoinAddress")
	public String detelCoinAddress(
			HttpServletRequest request,
			@RequestParam(required=true)int fid
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		FvirtualaddressWithdraw virtualaddressWithdraw = this.frontVirtualCoinService.findFvirtualaddressWithdraw(fid);
		if(virtualaddressWithdraw == null){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Record does not exist") ;
			return jsonObject.toString() ;
		}
		if(fuser.getFid() != virtualaddressWithdraw.getFuser().getFid()){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Illegal operation") ;
			return jsonObject.toString() ;
		}
		//成功
		try {
			this.frontVirtualCoinService.updateDelFvirtualaddressWithdraw(virtualaddressWithdraw);
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", "Successful operation") ;
		} catch (Exception e) {
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Network anomaly") ;
		}
		
		return jsonObject.toString() ;
	}

	@ResponseBody
	@RequestMapping("/user/modifyPwd")
	public String modifyPwd(
			HttpServletRequest request,
			@RequestParam(required=true) String newPwd,
			@RequestParam(required=false,defaultValue="0") String originPwd,
			@RequestParam(required=false,defaultValue="0")String phoneCode,
			@RequestParam(required=false,defaultValue="0")int pwdType,
			@RequestParam(required=true) String reNewPwd,
			@RequestParam(required=false,defaultValue="0")String totpCode
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		if(!newPwd.equals(reNewPwd)){
			jsonObject.accumulate("code", -3) ;
			jsonObject.accumulate("msg", "The two input password is not the same") ;
			return jsonObject.toString() ;//两次输入密码不一样
		}
		
//		if(!fuser.isFisTelephoneBind() && !fuser.getFgoogleBind()){
//			jsonObject.accumulate("resultCode", -13) ;
//			jsonObject.accumulate("msg", "需要绑定绑定谷歌或者手机号码才能修改密码") ;
//			return jsonObject.toString() ;//需要绑定绑定谷歌或者电话才能修改密码
//		}
		
		if(pwdType==0){
			//修改登陆密码
			if(!fuser.getFloginPassword().equals(Utils.MD5(originPwd,fuser.getSalt()))){
				jsonObject.accumulate("code", -5) ;
				jsonObject.accumulate("msg", "Original login password error") ;
				return jsonObject.toString() ;//原始密码错误
			}
			
		}else{
			//修改交易密码
			if(fuser.getFtradePassword()!=null && fuser.getFtradePassword().trim().length() >0){
				if(!fuser.getFtradePassword().equals(Utils.MD5(originPwd,fuser.getSalt()))){
					jsonObject.accumulate("code", -5) ;
					jsonObject.accumulate("msg", "Original transaction password error") ;
					return jsonObject.toString() ;//原始密码错误
				}
			}
		}
		
		if(pwdType==0){
			//修改交易密码
			if(fuser.getFloginPassword().equals(Utils.MD5(newPwd,fuser.getSalt()))){
				jsonObject.accumulate("code", -10) ;
				jsonObject.accumulate("msg", "Modification failed due to the same password as the old one.") ;
				return jsonObject.toString() ;
			}
		}else{
			//修改交易密码
			if(fuser.getFtradePassword()!=null && fuser.getFtradePassword().trim().length() >0
					&&fuser.getFtradePassword().equals(Utils.MD5(newPwd,fuser.getSalt()))){
				jsonObject.accumulate("code", -10) ;
				jsonObject.accumulate("msg", "Modification failed due to the same password as the old one.") ;
				return jsonObject.toString() ;
			}
		}
		
		String ip = getIpAddr(request) ;
		if(fuser.isFisTelephoneBind()){
			int mobil_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
			if(mobil_limit<=0){
				jsonObject.accumulate("code", -7) ;
				jsonObject.accumulate("msg", "This IP operation is frequent. Please try again after 2 hours!") ;
				return jsonObject.toString() ;
			}else{
				boolean mobilValidate = false ;
				if(pwdType==0){
					mobilValidate = validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.CHANGE_LOGINPWD, phoneCode) ;
				}else{
					mobilValidate = validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.CHANGE_TRADEPWD, phoneCode) ;
				}
				if(!mobilValidate){
					jsonObject.accumulate("code", -7) ;
					jsonObject.accumulate("msg", "Mobile phone verification code error，You still have"+mobil_limit+"chances") ;
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
					return jsonObject.toString() ;
				}else{
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
				}
			}
		}
		
		if(fuser.getFgoogleBind()){
			int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
			if(google_limit<=0){
				jsonObject.accumulate("code", -6) ;
				jsonObject.accumulate("msg", "This IP operation is frequent. Please try again after 2 hours!") ;
				return jsonObject.toString() ;
			}else{
				if(!GoogleAuth.auth(Long.parseLong(totpCode), fuser.getFgoogleAuthenticator())){
					jsonObject.accumulate("code", -6) ;
					jsonObject.accumulate("msg", "GOOGLE authentication code error，You still have"+google_limit+"chances") ;
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
					return jsonObject.toString() ;
				}else{
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
				}
			}
		}
		
		try {
			if(pwdType==0){
				//修改登陆密码
				fuser.setFloginPassword(Utils.MD5(newPwd,fuser.getSalt())) ;
				this.frontUserService.updateFUser(fuser, getSession(request),LogTypeEnum.User_UPDATE_LOGIN_PWD,ip) ;
			}else{
				//修改交易密码
				fuser.setFtradePassword(Utils.MD5(newPwd,fuser.getSalt())) ;
				int logType=0;
				if(fuser.getFtradePassword()!=null && fuser.getFtradePassword().trim().length() >0){
					logType = LogTypeEnum.User_UPDATE_TRADE_PWD;
				}else{
					logType = LogTypeEnum.User_SET_TRADE_PWD;
				}
				this.frontUserService.updateFUser(fuser, getSession(request),logType,ip) ;
			}
		} catch (Exception e) {
			jsonObject.accumulate("code", -3) ;
			jsonObject.accumulate("msg", "Network anomaly") ;
			return jsonObject.toString() ;
		}finally{
			this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.CHANGE_LOGINPWD);
			this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.CHANGE_TRADEPWD);
		}
		
		jsonObject.accumulate("code", 0) ;
		jsonObject.accumulate("msg", "Successful operation") ;
		return jsonObject.toString() ;
	}
	
	
	@ResponseBody
	@RequestMapping("/user/modifyWithdrawBtcAddr")
	public String modifyWithdrawBtcAddr(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")String phoneCode,
			@RequestParam(required=false,defaultValue="0")String totpCode,
			@RequestParam(required=true)int symbol,
			@RequestParam(required=true)String withdrawAddr,
			@RequestParam(required=true)String withdrawBtcPass,
			@RequestParam(required=false,defaultValue="REMARK")String withdrawRemark
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		withdrawAddr = HtmlUtils.htmlEscape(withdrawAddr.trim());
		withdrawRemark = HtmlUtils.htmlEscape(withdrawRemark.trim());
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		if(!fuser.getFgoogleBind() && !fuser.isFisTelephoneBind()){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg","Please bind the GOOGLE authentication or mobile phone number first") ;
			return jsonObject.toString() ;
		}
		
		if(fuser.getFtradePassword() == null || fuser.getFtradePassword().trim().length() ==0){
			jsonObject.accumulate("code", -4) ;
			jsonObject.accumulate("msg","Please set the transaction password first") ;
			return jsonObject.toString() ;
		}
		
		if(!fuser.getFtradePassword().equals(Utils.MD5(withdrawBtcPass,fuser.getSalt()))){
			jsonObject.accumulate("code", -4) ;
			jsonObject.accumulate("msg","Incorrect transaction password") ;
			return jsonObject.toString() ;
		}
		
		if(withdrawRemark.length() >100){
			jsonObject.accumulate("code", -4) ;
			jsonObject.accumulate("msg","Remark out of length") ;
			return jsonObject.toString() ;
		}
		
//		if(withdrawAddr.length() != 42 && withdrawAddr.length() != 34){
//			jsonObject.accumulate("code", -4) ;
//			jsonObject.accumulate("msg","提现地址格式有误") ;
//			return jsonObject.toString() ;
//		}
		
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(symbol) ;
		if(fvirtualcointype==null 
				|| fvirtualcointype.getFstatus()==VirtualCoinTypeStatusEnum.Abnormal
				|| !fvirtualcointype.isFIsWithDraw()){
			jsonObject.accumulate("code", -4) ;
			jsonObject.accumulate("msg","The currency does not exist") ;
			return jsonObject.toString() ;
		}
		
		String ip = getIpAddr(request) ;
		if(fuser.isFisTelephoneBind() && fuser.getFgoogleBind()){
			int mobil_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
			if(mobil_limit<=0){
				jsonObject.accumulate("code", -3) ;
				jsonObject.accumulate("msg","This IP operation is frequent. Please try again after 2 hours!") ;
				return jsonObject.toString() ;
			}else if(!validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.VIRTUAL_WITHDRAW_ACCOUNT, phoneCode)){
				jsonObject.accumulate("code", -3) ;
				jsonObject.accumulate("msg","The mobile phone verification code is incorrect,You still have"+mobil_limit+"chances") ;
				this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
				return jsonObject.toString() ;
			}else if(mobil_limit<new Constant().ErrorCountLimit){
				this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
			}
		}else
		{
			if(fuser.isFisTelephoneBind()){
				int mobil_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
				if(mobil_limit<=0){
					jsonObject.accumulate("code", -3) ;
					jsonObject.accumulate("msg","This IP operation is frequent. Please try again after 2 hours!") ;
					return jsonObject.toString() ;
				}else if(!validateMessageCode(fuser, fuser.getFareaCode(), fuser.getFtelephone(), MessageTypeEnum.VIRTUAL_WITHDRAW_ACCOUNT, phoneCode)){
					jsonObject.accumulate("code", -3) ;
					jsonObject.accumulate("msg","The mobile phone verification code is incorrect,You still have"+mobil_limit+"chances") ;
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.TELEPHONE) ;
					return jsonObject.toString() ;
				}else if(mobil_limit<new Constant().ErrorCountLimit){
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.TELEPHONE) ;
				}
			}

			if(fuser.getFgoogleBind()){
				int google_limit = this.frontValidateService.getLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
				if(google_limit<=0){
					jsonObject.accumulate("code", -2) ;
					jsonObject.accumulate("msg","This IP operation is frequent. Please try again after 2 hours!") ;
					return jsonObject.toString() ;
				}else if(!GoogleAuth.auth(Long.parseLong(totpCode.trim()), fuser.getFgoogleAuthenticator())){
					jsonObject.accumulate("code", -2) ;
					jsonObject.accumulate("msg","The GOOGLE verification code is incorrect,You still have"+google_limit+"chances") ;
					this.frontValidateService.updateLimitCount(ip, CountLimitTypeEnum.GOOGLE) ;
					return jsonObject.toString() ;
				}else if(google_limit<new Constant().ErrorCountLimit){
					this.frontValidateService.deleteCountLimite(ip, CountLimitTypeEnum.GOOGLE) ;
				}
			}
		}

		FvirtualaddressWithdraw fvirtualaddressWithdraw = new FvirtualaddressWithdraw();
		fvirtualaddressWithdraw.setFadderess(withdrawAddr) ;
		fvirtualaddressWithdraw.setFcreateTime(Utils.getTimestamp());
		fvirtualaddressWithdraw.setFremark(withdrawRemark);
		fvirtualaddressWithdraw.setFuser(fuser);
		fvirtualaddressWithdraw.setFvirtualcointype(fvirtualcointype);
		try {
			this.frontVirtualCoinService.updateFvirtualaddressWithdraw(fvirtualaddressWithdraw) ;
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg","Successful operation") ;
		} catch (Exception e) {
			jsonObject.accumulate("code", -4) ;
			jsonObject.accumulate("msg","Network anomaly") ;
		}finally{
			this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.VIRTUAL_WITHDRAW_ACCOUNT);
		}
 		
		return jsonObject.toString() ;
	}
	

	@ResponseBody
	@RequestMapping(value="/user/validateIdentity",produces={JsonEncode})
	public String validateIdentity(
			HttpServletRequest request,
			@RequestParam(required=true)String identityNo,
			@RequestParam(required=false,defaultValue="0")int identityType,
			@RequestParam(required=true)String realName
			) throws Exception {
		JSONObject js = new JSONObject();
		realName = HtmlUtils.htmlEscape(realName);
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
//		identityNo = identityNo.toLowerCase();
//		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",
//	                "3", "2" };
//        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
//                "9", "10", "5", "8", "4", "2" };
//		JSONObject jsonObject = new JSONObject();
//		if (identityNo.trim().length() != 15 && identityNo.trim().length() != 18) {
//			jsonObject.accumulate("code", 1);
//			jsonObject.accumulate("msg", "ID number should be 15 or 18 bits!");
//			return jsonObject.toString();
//		}
//
//		String Ai = "";
//        if (identityNo.length() == 18) {
//            Ai = identityNo.substring(0, 17);
//        } else if (identityNo.length() == 15) {
//            Ai = identityNo.substring(0, 6) + "19" + identityNo.substring(6, 15);
//        }
//        if (Utils.isNumeric(Ai) == false) {
//            jsonObject.accumulate("code", 1);
//			jsonObject.accumulate("msg", "The ID card number is wrong!");
//			return jsonObject.toString();
//        }
//        // ================ 出生年月是否有效 ================
//        String strYear = Ai.substring(6, 10);// 年份
//        String strMonth = Ai.substring(10, 12);// 月份
//        String strDay = Ai.substring(12, 14);// 月份
//        if (Utils.isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
//        	jsonObject.accumulate("code", 1);
//			jsonObject.accumulate("msg", "The ID card number is wrong!");
//			return jsonObject.toString();
//        }
//        GregorianCalendar gc = new GregorianCalendar();
//        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
//                    || (gc.getTime().getTime() - s.parse(
//                            strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
//            	jsonObject.accumulate("code", 1);
//				jsonObject.accumulate("msg", "The ID card number is wrong!");
//				return jsonObject.toString();
//            }
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        } catch (java.text.ParseException e) {
//            e.printStackTrace();
//        }
//        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
//        	jsonObject.accumulate("code", 1);
//			jsonObject.accumulate("msg", "The ID card number is wrong!");
//			return jsonObject.toString();
//        }
//        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
//        	jsonObject.accumulate("code", 1);
//			jsonObject.accumulate("msg", "The ID card number is wrong!");
//			return jsonObject.toString();
//        }
//        // =====================(end)=====================
//
//        // ================ 地区码时候有效 ================
//        Hashtable h = Utils.getAreaCode();
//        if (h.get(Ai.substring(0, 2)) == null) {
//        	jsonObject.accumulate("code", 1);
//			jsonObject.accumulate("msg", "The ID card number is wrong!");
//			return jsonObject.toString();
//        }
//        // ==============================================
//
//        // ================ 判断最后一位的值 ================
//        int TotalmulAiWi = 0;
//        for (int i = 0; i < 17; i++) {
//            TotalmulAiWi = TotalmulAiWi
//                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
//                    * Integer.parseInt(Wi[i]);
//        }
//        int modValue = TotalmulAiWi % 11;
//        String strVerifyCode = ValCodeArr[modValue];
//        Ai = Ai + strVerifyCode;
//
//        if (identityNo.length() == 18) {
//            if (Ai.equals(identityNo) == false) {
//            	jsonObject.accumulate("code", 1);
//				jsonObject.accumulate("msg", "The ID card number is wrong!");
//				return jsonObject.toString();
//            }
//        } else {
//            return "";
//        }
//
//		if (realName.trim().length() > 50) {
//			jsonObject.accumulate("code", 1);
//			jsonObject.accumulate("msg", "The ID card number is wrong!");
//			return jsonObject.toString();
//		}
//
//		String sql = "where fidentityNo='"+identityNo+"'";
//		int count = this.adminService.getAllCount("Fuser", sql);
//		if(count >0){
//			jsonObject.accumulate("code", 1);
//			jsonObject.accumulate("msg", "ID card already exists!");
//			return jsonObject.toString();
//		}
//		if(fuser.getFpostRealValidate()){
//			jsonObject.accumulate("code", 1);
//			jsonObject.accumulate("msg", "Do not repeat submission!");
//			return jsonObject.toString();
//		}
//		//		IDCardVerifyUtil verifyUtil = new IDCardVerifyUtil();
////		boolean isTrue = verifyUtil.isRealPerson(realName, identityNo);
//		boolean isTrue = true;
//		if(!isTrue){
//			jsonObject.accumulate("code", 1);
//			jsonObject.accumulate("msg", "Your name and ID number are incorrect, please check it!");
//			return jsonObject.toString();
//		}
		
		Fscore fscore = fuser.getFscore();
		Fuser fintrolUser = null;
		Fintrolinfo introlInfo = null;
		Fvirtualwallet fvirtualwallet = null;
		String[] auditSendCoin = this.systemArgsService.getValue("auditSendCoin").split("#");
		int coinID = Integer.parseInt(auditSendCoin[0]);
		double coinQty = Double.valueOf(auditSendCoin[1]);
		if(!fscore.isFissend() && fuser.getfIntroUser_id() != null){
			fintrolUser = this.frontUserService.findById(fuser.getfIntroUser_id().getFid());
			fintrolUser.setfInvalidateIntroCount(fintrolUser.getfInvalidateIntroCount()+1);
			fscore.setFissend(true);
		}
		if(coinQty >0){
			fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), coinID);
			fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+coinQty);
			introlInfo = new Fintrolinfo();
			introlInfo.setFcreatetime(Utils.getTimestamp());
			introlInfo.setFiscny(false);
			introlInfo.setFqty(coinQty);
			introlInfo.setFuser(fuser);
			introlInfo.setFname(fvirtualwallet.getFvirtualcointype().getFname());
			introlInfo.setFtitle("实名认证成功，奖励"+fvirtualwallet.getFvirtualcointype().getFname()+coinQty+"个！");
		}
		fuser.setFidentityType(0) ;
		fuser.setFidentityNo(identityNo) ;
		fuser.setFrealName(realName) ;
		fuser.setFpostRealValidate(true) ;
		fuser.setFpostRealValidateTime(Utils.getTimestamp()) ;
		fuser.setFhasRealValidate(false);
//		fuser.setFhasRealValidateTime(Utils.getTimestamp());
		fuser.setFisValid(true);
		try {
			String ip = getIpAddr(request) ;
			this.frontUserService.updateFUser(fuser,getSession(request),LogTypeEnum.User_CERT,ip) ;
			this.userService.updateObj(fuser,fscore,fintrolUser,fvirtualwallet,introlInfo);
			this.SetSession(fuser,request) ;
		} catch (Exception e) {
			js.accumulate("code", 1);
			js.accumulate("msg", "The certificate number already exists");
			return js.toString();
		}
		js.accumulate("code", 0);
		return js.toString();
	}

	
	@ResponseBody
	@RequestMapping(value="/common/upload",produces={"text/html;charset=UTF-8"})
	public String upload(
			HttpServletRequest request
		) throws Exception{
		
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request ;
		MultipartFile multipartFile = mRequest.getFile("file");
		InputStream inputStream = multipartFile.getInputStream() ;
		String realName = multipartFile.getOriginalFilename() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(mRequest).getFid());
		if(realName!=null && realName.trim().toLowerCase().endsWith("jsp")){
			return "xx" ;
		}
//		 if(fuser.isFpostImgValidate()){
//	        return "xxx";
//	    }
        if(fuser.isFhasImgValidate()){
        	return "xxx";
        }
		double size = multipartFile.getSize()/1000d;
		if(size > 1024d){
			return "xxx";
		}
		String[] nameSplit = realName.split("\\.") ;
		String ext = nameSplit[nameSplit.length-1] ;
		if(ext!=null && !ext.trim().toLowerCase().endsWith("jpg")
				&& !ext.trim().toLowerCase().endsWith("bmp")
				 && !ext.trim().toLowerCase().endsWith("png")){
			return "";
		}
		
		String realPath = request.getSession().getServletContext().getRealPath("/")+Constant.uploadPicDirectory;
		String fileName = Utils.getRandomImageName()+"."+ext;
		Utils.saveFile(realPath,fileName, inputStream,Constant.uploadPicDirectory) ;
		JSONObject resultJson = new JSONObject() ;
		resultJson.accumulate("code",0) ;
		if(Constant.IS_OPEN_OSS.equals("false")){
			resultJson.accumulate("resultUrl","/"+Constant.uploadPicDirectory+"/"+fileName) ;
		}else{
			resultJson.accumulate("resultUrl",OSSPostObject.URL+"/"+Constant.uploadPicDirectory+"/"+fileName) ;
		}
		
		return resultJson.toString();
	}
	
	

	@ResponseBody
	@RequestMapping(value="/user/validateKyc",produces={JsonEncode})
	public String validateKyc(
			HttpServletRequest request,
			@RequestParam(required=true)String identityUrlOn,
			@RequestParam(required=true)String identityUrlOff,
			@RequestParam(required=true)String identityHoldUrl
		) throws Exception{
        JSONObject js = new JSONObject();
        Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
        if(fuser.isFpostImgValidate()){
        	js.accumulate("code", -1);
        	js.accumulate("msg", "Submitted successfully. Please wait for approval.");
        	return js.toString();
        }
        if(fuser.isFhasImgValidate()){
        	js.accumulate("code", -1);
        	js.accumulate("msg", "Authenticated,no need to reupload.");
        	return js.toString();
        }
        identityUrlOn = HtmlUtils.htmlEscape(identityUrlOn);
        identityUrlOff = HtmlUtils.htmlEscape(identityUrlOff);
        identityHoldUrl = HtmlUtils.htmlEscape(identityHoldUrl);
        fuser.setFpostImgValidate(true);
        fuser.setFpostImgValidateTime(Utils.getTimestamp());
        fuser.setfIdentityPath(identityUrlOn);
        fuser.setfIdentityPath2(identityUrlOff);
        fuser.setfIdentityPath3(identityHoldUrl);
        
		try {
			this.frontUserService.updateFuser(fuser);
		} catch (Exception e) {
			e.printStackTrace();
			js.accumulate("code", -1);
        	js.accumulate("msg", "Network error, please reupload.");
        	return js.toString();
		}
		js.accumulate("msg", "Submitted successfully. Please wait for approval.");
		js.accumulate("code", 0);
		return js.toString();
	}
	
	@RequestMapping(value="/json/userAsset")
	public ModelAndView userAsset(HttpServletRequest request){
		if(GetSession(request)==null) return null;//用户没登陆不需执行以下内容

		//用户钱包
		Fvirtualwallet fwallet = this.frontUserService.findWalletByUser(GetSession(request).getFid());
		request.getSession().setAttribute("fwallet", fwallet) ;
		//虚拟钱包
		Map<Integer,Fvirtualwallet> fvirtualwallets = this.frontUserService.findVirtualWallet(GetSession(request).getFid()) ;
		request.getSession().setAttribute("fvirtualwallets", fvirtualwallets) ;
		//估计总资产
		//CNY+冻结CNY+（币种+冻结币种）*最高买价
		double totalCapital = 0F ;
		totalCapital+=fwallet.getFtotal()+fwallet.getFfrozen() ;
		Map<Integer,Integer> tradeMappings = (Map)this.constantMap.get("tradeMappings");
		for (Map.Entry<Integer, Fvirtualwallet> entry : fvirtualwallets.entrySet()) {
			if(tradeMappings.containsKey(entry.getValue().getFvirtualcointype().getFid()) == false )continue ;
			
			double price = (Double)this.redisUtil.get(RedisConstant.getLatestDealPrizeKey(tradeMappings.get(entry.getValue().getFvirtualcointype().getFid()))) ;
			totalCapital += ( entry.getValue().getFfrozen()+entry.getValue().getFtotal() )* price ;
		}
		
		request.getSession().setAttribute("totalNet", Utils.getDouble(totalCapital, 2)) ;
		request.getSession().setAttribute("totalCapital", Utils.getDouble(totalCapital,2)) ;
		
		request.getSession().setAttribute("totalNetTrade", Utils.getDouble(totalCapital, 2)) ;
		request.getSession().setAttribute("totalCapitalTrade", Utils.getDouble(totalCapital,2)) ;
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/comm/asset") ;
		return modelAndView ;
	}

}
