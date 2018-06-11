package com.ruizton.main.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.util.Mobilutils;

@Controller
public class FrontQuotationsController extends BaseController {

	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private ConstantMap constantMap ;
	
	
	@RequestMapping("/json")
	public ModelAndView json(
			HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/real/json") ;
		return modelAndView ;
	}
}
