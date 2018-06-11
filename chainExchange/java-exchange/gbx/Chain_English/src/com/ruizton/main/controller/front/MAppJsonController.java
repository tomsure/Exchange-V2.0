package com.ruizton.main.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.UtilsService;
import com.ruizton.util.Utils;

import net.sf.json.JSONObject;

@Controller
public class MAppJsonController extends BaseController {

	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private FrontUserService frontUserService ;
	
	@ResponseBody
	@RequestMapping("/m/json2/indexStatis")
	public String indexStatis() throws Exception {
		JSONObject jsonObject = new JSONObject() ;
		
		int totalUser = this.utilsService.count("", Fuser.class) ;
		jsonObject.accumulate("totalUser", totalUser) ;
		return jsonObject.toString() ;
		
	}
	
	@ResponseBody
	@RequestMapping("/m/json/tradePwd")
	public String tradePwd(HttpServletRequest request,
			@RequestParam(required=true)String password1,
			@RequestParam(required=true)String password2
			) throws Exception {
		JSONObject jsonObject = new JSONObject() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		if(password1.equals(password2)== false ){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "两次密码输入不一致");
			return jsonObject.toString() ;
		}
		if(password1.length()<6){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "交易密码最小长度为6");
			return jsonObject.toString() ;
		}
		
		if(fuser.getFtradePassword()!=null){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "非法提交");
			return jsonObject.toString() ;
		}
		
		fuser.setFtradePassword(Utils.MD5(password1,fuser.getSalt())) ;
		this.frontUserService.updateFuser(fuser);
		

		jsonObject.accumulate("code", 0) ;
		jsonObject.accumulate("msg", "密码设置成功");
		return jsonObject.toString() ;
	
	}
}
