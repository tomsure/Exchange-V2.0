package com.ruizton.main.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fabout;
import com.ruizton.main.service.admin.AboutService;
import com.ruizton.main.service.front.FrontOthersService;
import com.ruizton.util.Mobilutils;

@Controller
public class FrontAboutController extends BaseController {

	@Autowired
	private FrontOthersService frontOthersService ;
	@Autowired
	private ConstantMap map;
	@Autowired
	private AboutService aboutService;
	
	@RequestMapping("/about/index")
	public ModelAndView index(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int id
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		Fabout fabout = this.frontOthersService.findFabout(id) ;
		
		String filter = "where ftype='帮助分类'";
		List<Fabout> abouts = this.aboutService.list(0, 0, filter, false);

		modelAndView.addObject("abouts", abouts) ;
		modelAndView.addObject("fabout", fabout) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/about/index") ;
		return modelAndView ;
	}
	
	@RequestMapping("/about/wallet")
	public ModelAndView wallet(
			HttpServletRequest request
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		Fabout fabout = this.frontOthersService.findFabout(61) ;
		
		String filter = "where ftype='帮助分类'";
		List<Fabout> abouts = this.aboutService.list(0, 0, filter, false);

		modelAndView.addObject("abouts", abouts) ;
		modelAndView.addObject("fabout", fabout) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/about/wallet") ;
		return modelAndView ;
	}
	
	@RequestMapping("/about/t_detail")
	public ModelAndView t_detail(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int id
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		Fabout fabout = this.frontOthersService.findFabout(id) ;
		if(fabout == null){
			modelAndView.setViewName("redirect:/") ;
			return modelAndView;
		}
		if(!fabout.getFtype().equals("团队信息")){
			modelAndView.setViewName("redirect:/") ;
			return modelAndView;
		}
		
		String filter = "where ftype='团队信息'";
		List<Fabout> abouts = this.aboutService.list(0, 0, filter, false);
		
		modelAndView.addObject("abouts", abouts) ;
		modelAndView.addObject("fabout", fabout) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/about/tdetail") ;
		return modelAndView ;
	}
	
	@RequestMapping("/about/t_index")
	public ModelAndView t_index(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int id
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		String filter = "where ftype='团队信息'";
		List<Fabout> abouts = this.aboutService.list(0, 0, filter, false);
		
		modelAndView.addObject("abouts", abouts) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/about/tindex") ;
		return modelAndView ;
	}
	
	/*
	@RequestMapping("/dowload/index")
	public ModelAndView dowload() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/dowload/index") ;
		return modelAndView ;
	}
	
	@RequestMapping("/business")
	public ModelAndView business() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int isIndex = 1;
		modelAndView.addObject("isIndex", isIndex) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/about/business") ;
		return modelAndView ;
	}*/
}
