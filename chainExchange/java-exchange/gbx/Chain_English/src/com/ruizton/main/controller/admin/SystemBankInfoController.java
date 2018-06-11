package com.ruizton.main.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.SystemBankInfoEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Systembankinfo;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.SystembankService;
import com.ruizton.util.Utils;

@Controller
public class SystemBankInfoController extends BaseController {
	@Autowired
	private SystembankService systembankService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private HttpServletRequest request ;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/ssadmin/systemBankList")
	public ModelAndView Index() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/systemBankList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and (fbankName like '%"+keyWord+"%' or\n");
			filter.append("fownerName like '%"+keyWord+"%' or\n");
			filter.append("fbankAddress like '%"+keyWord+"%' or\n");
			filter.append("fbankNumber like '%"+keyWord+"%')\n");
			modelAndView.addObject("keywords", keyWord);
		}
		
		List<Systembankinfo> list = this.systembankService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("systembankList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "systemBankList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Systembankinfo", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("ssadmin/goSystemBankJSP")
	public ModelAndView goSystemBankJSP() throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Systembankinfo systemBank = this.systembankService.findById(fid);
			modelAndView.addObject("systemBank", systemBank);
		}
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/saveSystemBank")
	public ModelAndView saveSystemBank() throws Exception{
		Systembankinfo bankInfo = new Systembankinfo();
		bankInfo.setFbankAddress(request.getParameter("systemBank.fbankAddress"));
		bankInfo.setFbankName(request.getParameter("systemBank.fbankName"));
		bankInfo.setFbankNumber(request.getParameter("systemBank.fbankNumber"));
		bankInfo.setFownerName(request.getParameter("systemBank.fownerName"));
		bankInfo.setFcreateTime(Utils.getTimestamp());
		bankInfo.setFstatus(SystemBankInfoEnum.NORMAL_VALUE);
        this.systembankService.saveObj(bankInfo);
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Add Success");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/forbbinSystemBank")
	public ModelAndView forbbinSystemBank() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("uid"));
        int status = Integer.parseInt(request.getParameter("status"));
        Systembankinfo bankInfo = this.systembankService.findById(fid);
        if(status == 1){
        	bankInfo.setFstatus(SystemBankInfoEnum.ABNORMAL);
        	modelAndView.addObject("message","Disable Success");
        }else{
        	bankInfo.setFstatus(SystemBankInfoEnum.NORMAL_VALUE);
        	modelAndView.addObject("message","Enable Success");
        }
        
		this.systembankService.updateObj(bankInfo);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		
		return modelAndView;
	}
	
}
