package com.ruizton.main.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.controller.BaseController;
import com.ruizton.util.Mobilutils;

@Controller
public class FrontErrorController extends BaseController {
	
	@RequestMapping("/error/error")
	public ModelAndView error404(
			HttpServletRequest request) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/error/error") ;
		return modelAndView ;
	}
}
