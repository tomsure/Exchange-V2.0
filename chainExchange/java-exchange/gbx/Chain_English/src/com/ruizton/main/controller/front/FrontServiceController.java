package com.ruizton.main.controller.front;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Farticle;
import com.ruizton.main.model.Farticletype;
import com.ruizton.main.service.admin.ArticleService;
import com.ruizton.main.service.front.FrontOthersService;
import com.ruizton.main.service.front.UtilsService;
import com.ruizton.util.Mobilutils;
import com.ruizton.util.PaginUtil;

@Controller
public class FrontServiceController extends BaseController {

	@Autowired
	private FrontOthersService frontOthersService ;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private UtilsService utilsService ;
	
	@RequestMapping(value={"/service/ourService","/m/service/ourService"})
	public ModelAndView ourService(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="1")int id,
			@RequestParam(required=false,defaultValue="1")int currentPage
			) throws Exception{//12,5,5
		ModelAndView modelAndView = new ModelAndView() ;
		int page =10;
		
		if(Mobilutils.isMURL(request)){
			List<Farticletype> farticletypes = this.utilsService.list(0, 0, "order by fid desc", false , Farticletype.class) ;
			modelAndView.addObject("farticletypes", farticletypes) ;
			List<List<Farticle>> arts = new ArrayList<List<Farticle>>() ;
			for (int i = 0; i < farticletypes.size(); i++) {
				List<Farticle> farticles = this.frontOthersService.findFarticle(farticletypes.get(i).getFid(), 0, Integer.MAX_VALUE) ;
				arts.add(farticles) ;
			}
			modelAndView.addObject("arts", arts) ;
		}else{
			List<Farticle> farticles = this.frontOthersService.findFarticle(id, (currentPage-1)*page, page) ;
			modelAndView.addObject("farticles", farticles) ;
		}
		
		int total = this.frontOthersService.findFarticleCount(id) ;
		String pagin = PaginUtil.generatePagin(total/page+(total%page==0?0:1), currentPage, "/service/ourService.html?id="+id+"&") ;

		
		Farticletype atype = this.frontOthersService.findFarticleTypeById(id);
		modelAndView.addObject("atype",atype) ;
		
		modelAndView.addObject("id",id) ;
		modelAndView.addObject("pagin",pagin) ;
		
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/service/index") ;
		return modelAndView ;
	}
	
	
	
	
	@RequestMapping(value={"/service/article","/m/service/article"})
	public ModelAndView article(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int id
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Farticle farticle = this.frontOthersService.findFarticleById(id) ;
		if(farticle == null){
			modelAndView.setViewName("redirect:/service/ourService.html") ;
			return modelAndView;
		}

		if(Mobilutils.isMURL(request)){
			farticle.setFcontent(farticle.getFcontent().replace("width", "widthx"));
		}
		modelAndView.addObject("farticle", farticle) ;
		
		String filter = "where farticletype.fid="+farticle.getFarticletype().getFid()+" order by fid desc";
		List<Farticle> hotsArticles = this.articleService.list(0, 6, filter, true);
		modelAndView.addObject("hotsArticles", hotsArticles) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/service/article") ;
		return modelAndView ;
	}
}
