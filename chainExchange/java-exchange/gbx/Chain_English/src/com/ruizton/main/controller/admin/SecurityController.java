package com.ruizton.main.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fsecurity;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.SecurityService;
import com.ruizton.util.Utils;

@Controller
public class SecurityController extends BaseController {
	@Autowired
	private SecurityService securityService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private HttpServletRequest request ;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("ssadmin/goSecurityJSP")
	public ModelAndView goSecurityJSP() throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("treeId") != null){
			int fid = Integer.parseInt(request.getParameter("treeId"));
			Fsecurity security = this.securityService.findById(fid);
			modelAndView.addObject("security", security);
			modelAndView.addObject("treeId", fid);
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
				filter.append("and fname like '%"+keyWord+"%' \n");
				modelAndView.addObject("keywords", keyWord);
			}
			filter.append("and fsecurity.fid="+fid+" \n");
			if(orderField != null && orderField.trim().length() >0){
				filter.append("order by "+orderField+"\n");
			}
			if(orderDirection != null && orderDirection.trim().length() >0){
				filter.append(orderDirection+"\n");
			}
			List<Fsecurity> list = this.securityService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
			modelAndView.addObject("securityList", list);
			modelAndView.addObject("numPerPage", numPerPage);
			modelAndView.addObject("currentPage", currentPage);
			//总数量
			modelAndView.addObject("totalCount",this.adminService.getAllCount("Fsecurity", filter+""));
		}else if(request.getParameter("treeId1") != null){//add
			int fid = Integer.parseInt(request.getParameter("treeId1"));
			Fsecurity security = this.securityService.findById(fid);
			modelAndView.addObject("security", security);
			if(request.getParameter("status").equals("update")){
				int uid = Integer.parseInt(request.getParameter("uid"));
				Fsecurity oldSecurity = this.securityService.findById(uid);
				modelAndView.addObject("oldSecurity", oldSecurity);
			}
		}
		
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/saveSecurity")
	public ModelAndView saveSecurity() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int fparentid = Integer.parseInt(request.getParameter("fparentid"));
		int fpriority = Integer.parseInt(request.getParameter("fpriority"));
		Fsecurity security = new Fsecurity();
		Fsecurity parent = this.securityService.findById(fparentid);
		security.setFsecurity(parent);
		security.setFname(request.getParameter("fname"));
		security.setFdescription(request.getParameter("fdescription"));
		security.setFpriority(fpriority+1);
		security.setFurl(request.getParameter("furl"));
		this.securityService.saveObj(security);
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "Add Success");
		modelAndView.addObject("rel", "jbsxBox2moduleList");
		modelAndView.addObject("callbackType", "closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/updateSecurity")
	public ModelAndView updateSecurity() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("fid"));
		Fsecurity security = this.securityService.findById(fid);
		security.setFname(request.getParameter("fname"));
		security.setFdescription(request.getParameter("fdescription"));
		security.setFurl(request.getParameter("furl"));
		this.securityService.updateObj(security);
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "Modify Success");
		modelAndView.addObject("rel", "jbsxBox2moduleList");
		modelAndView.addObject("callbackType", "closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/deleteSecurity")
	public ModelAndView deleteSecurity() throws Exception {
		int fid = Integer.parseInt(request.getParameter("uid"));
		this.securityService.deleteObj(fid);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("rel", "jbsxBox2moduleList");
		modelAndView.addObject("message", "Delete Success");
		return modelAndView;
	}
	
}
