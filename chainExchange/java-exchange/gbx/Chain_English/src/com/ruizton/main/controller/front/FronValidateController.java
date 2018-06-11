package com.ruizton.main.controller.front;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.UserStatusEnum;
import com.ruizton.main.comm.MultipleValues;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Emailvalidate;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontValidateService;
import com.ruizton.util.Mobilutils;
import com.ruizton.util.Utils;

@Controller
public class FronValidateController extends BaseController {
	@Autowired
	protected FrontValidateService frontValidateService ;
	@Autowired
	private FrontUserService frontUserService ;
	
	@RequestMapping("/validate/reset")//密码重置请求
	public ModelAndView reset(
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		if(GetSession(request) != null){
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
			if(fuser.getFstatus()==UserStatusEnum.FORBBIN_VALUE){
				CleanSession(request) ;
			}
			
			modelAndView.setViewName("redirect:/") ;
			return modelAndView;
		}
		
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/validate/reset") ;
		return modelAndView ;
	}
	
	@RequestMapping("/validate/resetEmail")//密码重置请求
	public ModelAndView resetEmail(
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		if(GetSession(request) != null){
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
			if(fuser.getFstatus()==UserStatusEnum.FORBBIN_VALUE){
				CleanSession(request) ;
			}
			
			modelAndView.setViewName("redirect:/") ;
			return modelAndView;
		}
		
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/validate/resetEmail") ;
		return modelAndView ;
	}
	
	@RequestMapping("/validate/resetPhone")//密码重置请求
	public ModelAndView resetPhone(
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		if(GetSession(request) != null){
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
			if(fuser.getFstatus()==UserStatusEnum.FORBBIN_VALUE){
				CleanSession(request) ;
			}
			
			modelAndView.setViewName("redirect:/") ;
			return modelAndView;
		}
		
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/validate/resetPhone") ;
		return modelAndView ;
	}

	//通过邮件找回密码第二步
	@RequestMapping("validate/resetPwd")
	public ModelAndView resetPwd(
			HttpServletRequest request,
			@RequestParam(required=true)int uid,
			@RequestParam(required=true)String uuid
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Emailvalidate emailvalidate = null ;
		try {
			emailvalidate = this.frontValidateService.updateFindPasswordMailValidate(uid, uuid) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(emailvalidate!=null){
			Fuser fuser = this.frontUserService.findById(emailvalidate.getFuser().getFid()) ;
			modelAndView.addObject("emailvalidate", emailvalidate) ; 
			modelAndView.addObject("fuser", fuser) ; 
		}else{
			modelAndView.setViewName("redirect:/validate/resetEmail.html") ;
			return modelAndView ;
		}
		
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/validate/resetPwd") ;
		return modelAndView ;
	}
	
	//通过手机找回密码第二步
	@RequestMapping("validate/resetPwdPhone")
	public ModelAndView resetPwd(
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		Object resetPasswordByPhone = request.getSession().getAttribute("resetPasswordByPhone") ;
		if(resetPasswordByPhone!=null ){
			MultipleValues values = (MultipleValues)resetPasswordByPhone ;
			Integer fuserid = (Integer)values.getValue1() ;
			Timestamp time = (Timestamp)values.getValue2() ;
			
			if(Utils.timeMinus(Utils.getTimestamp(), time)<300){
				Fuser fuser = this.frontUserService.findById(fuserid) ;
				modelAndView.addObject("fuser", fuser) ;
				modelAndView.setViewName("front"+Mobilutils.M(request)+"/validate/resetPwdPhone") ;
				return modelAndView ;
			}
		}
		
		modelAndView.setViewName("redirect:/validate/resetPhone.html") ;
		return modelAndView ;
	}
	
	
	/*
	 * 邮件验证点击
	 * */
	@RequestMapping(value="/validate/mail_validate")
	public ModelAndView mailValidate(
			HttpServletRequest request,
			@RequestParam(required=true)int uid,
			@RequestParam(required=true)String uuid
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		boolean flag = false ;
		try {
			flag = this.frontValidateService.updateMailValidate(uid, uuid) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		modelAndView.addObject("validate", flag) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/user/reg_regconfirm") ;
		return modelAndView ;
	}
	
}
