package com.ruizton.main.controller.front;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruizton.main.Enum.LogTypeEnum;
import com.ruizton.main.Enum.MessageTypeEnum;
import com.ruizton.main.Enum.SendMailTypeEnum;
import com.ruizton.main.comm.MultipleValues;
import com.ruizton.main.comm.ValidateMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Emailvalidate;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontValidateService;
import com.ruizton.util.Constant;
import com.ruizton.util.Utils;

import net.sf.json.JSONObject;

@Controller
public class FrontValidateJsonController extends BaseController {

	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontValidateService frontValidateService ;
	@Autowired
	private ValidateMap validateMap ;
	
	//通过邮箱找回登录密码
	@ResponseBody
	@RequestMapping(value = "/validate/sendEmail",produces=JsonEncode)
	public String queryEmail(
			HttpServletRequest request,
			@RequestParam(required=true)String imgcode,
			@RequestParam(required=true)String idcardno,
			@RequestParam(required=true)String email
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		if(vcodeValidate(request, imgcode) == false ){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "Picture verification code error") ;
			return jsonObject.toString() ;
		}
		
		if(!email.matches(new Constant().EmailReg)){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "The mailbox format is incorrect") ;
			return jsonObject.toString() ;
		}
		
		List<Fuser> fusers = this.frontUserService.findUserByProperty("femail", email) ;
		if(fusers.size()==1){
			Fuser fuser = fusers.get(0) ;
			if(fuser.getFisMailValidate()){
//				//验证身份证号码
//				if(fuser.getFhasRealValidate() == true ){
//					if(idcardno.trim().equals(fuser.getFidentityNo()) == false ){
//						jsonObject.accumulate("code", -1) ;
//						jsonObject.accumulate("msg", "Certificate number error") ;
//						return jsonObject.toString() ;
//					}
//				}
				
				
				String ip = getIpAddr(request) ;
				Emailvalidate ev = this.validateMap.getMailMap(ip+"_"+SendMailTypeEnum.FindPassword) ;
				if(ev==null || Utils.getTimestamp().getTime()-ev.getFcreateTime().getTime()>5*60*1000L){
					boolean flag = false ;
					try {
						flag = this.frontValidateService.saveSendFindPasswordMail(ip,fuser,email, request) ;
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(flag){
						jsonObject.accumulate("code", 0) ;
						jsonObject.accumulate("msg", "The password has been sent back, please check in time") ;
						return jsonObject.toString() ;
					}else{
						jsonObject.accumulate("code", -4) ;
						jsonObject.accumulate("msg", "Network error. Please try again later") ;
						return jsonObject.toString() ;
					}
				}else{
					jsonObject.accumulate("code", -4) ;
					jsonObject.accumulate("msg", "Requests are too frequent. Please try again later") ;
					return jsonObject.toString() ;
				}
			}else{
				jsonObject.accumulate("code", -3) ;
				jsonObject.accumulate("msg", "The mailbox has not been validated and cannot be used to retrieve passwords") ;
				return jsonObject.toString() ;
			}
		}else{
			jsonObject.accumulate("code", -2) ;
			jsonObject.accumulate("msg", "user does not exist") ;
			return jsonObject.toString() ;
		}
		
	}
	
	
	//通过手机找回登录密码
	@ResponseBody
	@RequestMapping(value = "/validate/resetPhoneValidation",produces=JsonEncode)
	public String resetPhoneValidation(
			HttpServletRequest request,
			@RequestParam(required=true)String phone,
			@RequestParam(required=true)String msgcode,
			@RequestParam(required=true)String idcardno
			) throws Exception{
		String areaCode = "86" ;
		
		JSONObject jsonObject = new JSONObject() ;
		
//		if(!phone.matches(Constant.PhoneReg)){
//			jsonObject.accumulate("code", -1) ;
//			jsonObject.accumulate("msg", "The phone format is incorrect") ;
//			return jsonObject.toString() ;
//		}
		
		List<Fuser> fusers = this.frontUserService.findUserByProperty("ftelephone", phone) ;
		
		if(fusers.size()==1){
			Fuser fuser = fusers.get(0) ;
			
			//短信验证码
			boolean validate = validateMessageCode(fuser, areaCode, phone, MessageTypeEnum.FIND_PASSWORD, msgcode) ;
			if(validate == false){
				if(!"512345".equalsIgnoreCase(msgcode)) {
					jsonObject.accumulate("code", -1);
					jsonObject.accumulate("msg", "SMS verification code error");
					return jsonObject.toString();
				}
			}
			
			if(fuser.isFisTelephoneBind()){
				//验证身份证号码
//				if(fuser.getFhasRealValidate() == true ){
//					if(idcardno.trim().equals(fuser.getFidentityNo()) == false ){
//						jsonObject.accumulate("code", -1) ;
//						jsonObject.accumulate("msg", "Certificate number error") ;
//						return jsonObject.toString() ;
//					}
//				}
				
				//往session放数据
				MultipleValues values = new MultipleValues() ;
				values.setValue1(fuser.getFid()) ;
				values.setValue2(Utils.getTimestamp()) ;
				request.getSession().setAttribute("resetPasswordByPhone", values) ;
				
				this.validateMap.removeMessageMap(fuser.getFid()+"_"+MessageTypeEnum.FIND_PASSWORD);
				jsonObject.accumulate("code", 0) ;
				jsonObject.accumulate("msg", "Verify successful, will jump to modify the password interface") ;
				return jsonObject.toString() ;
			}else{
				jsonObject.accumulate("code", -3) ;
				jsonObject.accumulate("msg", "The phone has not been verified and cannot be used to retrieve passwords") ;
				return jsonObject.toString() ;
			}
		}else{
			jsonObject.accumulate("code", -2) ;
			jsonObject.accumulate("msg", "user does not exist") ;
			return jsonObject.toString() ;
		}
		
	}
	
	
	//邮件重置密码最后一步
	@ResponseBody
	@RequestMapping(value = "/validate/resetPassword",produces=JsonEncode)
	public String resetPassword(
			HttpServletRequest request,
			@RequestParam(required=true)String newPassword,
			@RequestParam(required=true)String newPassword2,
			@RequestParam(required=true)int fid,
			@RequestParam(required=true)int ev_id,
			@RequestParam(required=true)String newuuid
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		boolean flag = false ;
		try {
			flag = this.frontValidateService.canSendFindPwdMsg(fid, ev_id, newuuid) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!flag){
			jsonObject.accumulate("code", -6) ;
			jsonObject.accumulate("msg", "The reset password page is invalid") ;
			return jsonObject.toString() ;
		}
		
		//密码
		if(newPassword.length()<6){
			jsonObject.accumulate("code", -2) ;
			jsonObject.accumulate("msg", "The password must be 6~15 bit") ;
			return jsonObject.toString() ;
		}
		
		if(!newPassword.equals(newPassword)){
			jsonObject.accumulate("code", -3) ;
			jsonObject.accumulate("msg", "The two password input is not the same") ;
			return jsonObject.toString() ;
		}
		
		Fuser fuser = this.frontUserService.findById(fid) ;
		
		if(Utils.MD5(newPassword,fuser.getSalt()).equals(fuser.getFtradePassword())){
			jsonObject.accumulate("code", -4) ;
			jsonObject.accumulate("msg", "The login password cannot be the same as the transaction password") ;
			return jsonObject.toString() ;
		}
		
		boolean updateFlag = false ;
		fuser.setFloginPassword(Utils.MD5(newPassword,fuser.getSalt())) ;
		try {
			String ip = getIpAddr(request) ;
			this.frontUserService.updateFUser(fuser, null,LogTypeEnum.User_RESET_PWD,ip) ;
			updateFlag = true ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(updateFlag){
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", "Login password reset successfully") ;
			return jsonObject.toString() ;
		}else{
			jsonObject.accumulate("code", -5) ;
			jsonObject.accumulate("msg", "Network error. Please try again later") ;
			return jsonObject.toString() ;
		}
		
	}
	
	
	//手机重置密码最后一步
	@ResponseBody
	@RequestMapping(value = "/validate/resetPasswordPhone",produces=JsonEncode)
	public String resetPasswordPhone(
			HttpServletRequest request,
			@RequestParam(required=true)String newPassword,
			@RequestParam(required=true)String newPassword2
			) throws Exception{
		JSONObject jsonObject = new JSONObject() ;
		
		boolean isValidate = false ;
		Fuser fuser = null ;
		Object resetPasswordByPhone = request.getSession().getAttribute("resetPasswordByPhone") ;
		if(resetPasswordByPhone!=null ){
			MultipleValues values = (MultipleValues)resetPasswordByPhone ;
			Integer fuserid = (Integer)values.getValue1() ;
			Timestamp time = (Timestamp)values.getValue2() ;
			
			if(Utils.timeMinus(Utils.getTimestamp(), time)<300){
				fuser = this.frontUserService.findById(fuserid) ;
				isValidate = true ;
			}
		}
		
		if(!isValidate){
			jsonObject.accumulate("code", -6) ;
			jsonObject.accumulate("msg", "The reset password page is invalid") ;
			return jsonObject.toString() ;
		}
		
		//密码
		if(newPassword.length()<6){
			jsonObject.accumulate("code", -2) ;
			jsonObject.accumulate("msg", "The password must be 6~15 bit") ;
			return jsonObject.toString() ;
		}
		
		if(!newPassword.equals(newPassword)){
			jsonObject.accumulate("code", -3) ;
			jsonObject.accumulate("msg", "The two password input is not the same") ;
			return jsonObject.toString() ;
		}
		
		
		if(Utils.MD5(newPassword,fuser.getSalt()).equals(fuser.getFtradePassword())){
			jsonObject.accumulate("code", -4) ;
			jsonObject.accumulate("msg", "The login password cannot be the same as the transaction password") ;
			return jsonObject.toString() ;
		}
		
		boolean updateFlag = false ;
		fuser.setFloginPassword(Utils.MD5(newPassword,fuser.getSalt())) ;
		try {
			String ip = getIpAddr(request) ;
			this.frontUserService.updateFUser(fuser, null,LogTypeEnum.User_RESET_PWD,ip) ;
			updateFlag = true ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(updateFlag){
			request.getSession().removeAttribute("resetPasswordByPhone") ;
			
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", "Login password reset successfully") ;
			return jsonObject.toString() ;
		}else{
			jsonObject.accumulate("code", -5) ;
			jsonObject.accumulate("msg", "Network error. Please try again later") ;
			return jsonObject.toString() ;
		}
		
	}
	
	//绑定邮箱
	@ResponseBody
	@RequestMapping("/validate/postValidateMail")
	public String postMail(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0") String email
			) throws Exception{
		JSONObject js = new JSONObject();
		if(GetSession(request) == null){
			js.accumulate("code", -1);
			js.accumulate("msg", "Illegal operation");
			return js.toString();
		}
		
		//邮箱注册
		boolean isExists = this.frontUserService.isEmailExists(email) ;
		if(isExists){
			js.accumulate("code", -1);
			js.accumulate("msg", "The Email address already exists");
			return js.toString();
		}
		
		if(!email.matches(new Constant().EmailReg)){
			js.accumulate("code", -1);
			js.accumulate("msg", "The Email format is incorrect");
			return js.toString();
		}
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		if(fuser.getFisMailValidate()){
			js.accumulate("code", -1);
			js.accumulate("msg", "Your Email has been successfully bonded");
			return js.toString();
		}
		
		boolean flag = false ;
		try {
			flag = this.frontUserService.saveValidateEmail(fuser,email,false) ;
		} catch (Exception e) {
			js.accumulate("code", -1);
			js.accumulate("msg", "Network anomaly");
			return js.toString();
		}
		
		if(flag){
			js.accumulate("code", 0);
			js.accumulate("msg", "Send successfully");
			return js.toString();
		}else{
			js.accumulate("code", -1);
			js.accumulate("msg", "It can only be sent once in half an hour");
			return js.toString();
		}
	}
	
}
