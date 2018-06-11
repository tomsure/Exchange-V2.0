package com.ruizton.main.controller.admin;

import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fabout;
import com.ruizton.main.service.admin.AboutService;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AboutController extends BaseController {
	@Autowired
	private AboutService aboutService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private HttpServletRequest request ;
	@Autowired
	private ConstantMap map;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/ssadmin/aboutList")
	public ModelAndView Index() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/aboutList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and ftitle like '%"+keyWord+"%' \n");
			modelAndView.addObject("keywords", keyWord);
		}
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by ftype \n");
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		List<Fabout> list = this.aboutService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("aboutList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "aboutList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fabout", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("ssadmin/goAboutJSP")
	public ModelAndView goAboutJSP() throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fabout about = this.aboutService.findById(fid);
			modelAndView.addObject("fabout", about);
		}
		
		String[] args = this.map.get("helpType").toString().split("#");
		Map<String,String> type = new HashMap<String,String>();
		for (int i = 0; i < args.length; i++) {
			type.put(args[i], args[i]);
		}
		modelAndView.addObject("type", type);
		
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/updateAbout")
	public ModelAndView updateAbout() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
        int fid = Integer.parseInt(request.getParameter("fid"));
        Fabout about = this.aboutService.findById(fid);
        about.setFcontent(request.getParameter("fcontent"));
		about.setFcontentEn(request.getParameter("fcontentEn"));
        about.setFtitle(request.getParameter("ftitle"));
		about.setFtitleEn(request.getParameter("ftitleEn"));
        about.setFtype(request.getParameter("ftype"));
        this.aboutService.updateObj(about);
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Modify Success");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/saveAbout")
	public ModelAndView saveAbout() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
        Fabout about = new Fabout();
        about.setFcontent(request.getParameter("fcontent"));
		about.setFcontentEn(request.getParameter("fcontentEn"));
        about.setFtitle(request.getParameter("ftitle"));
		about.setFtitleEn(request.getParameter("ftitleEn"));
        about.setFtype(request.getParameter("ftype"));
        this.aboutService.saveObj(about);
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Add Success");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/deleteAbout")
	public ModelAndView deleteAbout() throws Exception{
		int fid = Integer.parseInt(request.getParameter("uid"));
		this.aboutService.deleteObj(fid);
		
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Delete Success");
		return modelAndView;
	}
}
