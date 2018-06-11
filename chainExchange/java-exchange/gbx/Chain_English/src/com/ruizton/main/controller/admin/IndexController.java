package com.ruizton.main.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.CountLimitTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fadmin;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.UserService;
import com.ruizton.main.service.front.FrontValidateService;
import com.ruizton.util.GoogleAuth;
import com.ruizton.util.Utils;

@Controller
public class IndexController extends BaseController {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private FrontValidateService frontValidateService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;
	

	@RequestMapping("/ssadmin/index")
	public ModelAndView Index() throws Exception {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/index");
		modelAndView.addObject("dateTime", sdf1.format(new Date()));
		modelAndView.addObject("login_admin", request.getSession()
				.getAttribute("login_admin"));
		return modelAndView;
	}


	@RequestMapping("/ssadmin/login_zblt")
	public ModelAndView login() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/login");
		return modelAndView;
	}

	@RequestMapping("/ssadmin/submitLogin_zblt")
	public ModelAndView submitLogin_ys333(
			HttpServletRequest request,
			@RequestParam(required = true) String name,
			@RequestParam(required = true) String password,
			@RequestParam(required = false,defaultValue="0") String google,
			@RequestParam(required = true) String vcode) throws Exception {

		ModelAndView modelAndView = new ModelAndView();

		if (name == null || "".equals(name.trim()) || password == null
				|| "".equals(password.trim()) || vcode == null
				|| "".equals(vcode.trim())) {
			modelAndView.setViewName("redirect:/ssadmin/login_zblt.html");
			return modelAndView;
		} else {
			String ip = getIpAddr(request);
			int admin_limit = this.frontValidateService.getLimitCount(ip,
					CountLimitTypeEnum.AdminLogin);
			if (admin_limit <= 0) {
				modelAndView.addObject("error", "Consecutive 30 failed login attempts! For safety reason this account has been locked. Please try again in 2 hours.");
				modelAndView.setViewName("/ssadmin/login");
				return modelAndView;
			}

			if (!vcode.equalsIgnoreCase((String) getSession(request).getAttribute(
					"checkcode"))) {
				modelAndView.addObject("error", "Verification code error!");
				modelAndView.setViewName("/ssadmin/login");
				return modelAndView;
			}

			List<Fadmin> admins = this.adminService.findByProperty("fname", name);
			if(admins == null || admins.size() !=1){
				modelAndView.addObject("error", "Non-existing admin");
				modelAndView.setViewName("/ssadmin/login");
				return modelAndView;
			}else{
				Fadmin fadmin = admins.get(0) ;
				int userid = fadmin.getFuserid();
				if(userid != 0){
					//验证谷歌认证
					boolean flag = false ;
					try{
						Fuser fuser = this.userService.findById(userid);
						flag = GoogleAuth.auth(Long.parseLong(google.trim()),fuser.getFgoogleAuthenticator()) ;
					}catch(Exception e){
						e.printStackTrace() ;
						flag = false ;
					}
					if(flag==false){
						modelAndView.addObject("error", "Google verification code error!");
						modelAndView.setViewName("/ssadmin/login");
						return modelAndView;
					}
				}
			}
			
			Subject admin = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(name,
					Utils.MD5(password,admins.get(0).getSalt()));
			token.setRememberMe(true);
			token.setHost(getIpAddr(request));
			try {
				admin.login(token);
			} catch (Exception e) {
				token.clear();
				this.frontValidateService.updateLimitCount(ip,
						CountLimitTypeEnum.AdminLogin);
				modelAndView.addObject("error", e.getMessage());
				modelAndView.setViewName("/ssadmin/login");
				return modelAndView;
			}
		}
		modelAndView.setViewName("redirect:/ssadmin/index.html");
		return modelAndView;
	}

}
