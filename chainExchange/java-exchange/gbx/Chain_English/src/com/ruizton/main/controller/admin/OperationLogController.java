package com.ruizton.main.controller.admin;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.OperationlogEnum;
import com.ruizton.main.Enum.RemittanceTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fadmin;
import com.ruizton.main.model.Foperationlog;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.OperationLogService;
import com.ruizton.main.service.admin.UserService;
import com.ruizton.util.Utils;

@Controller
public class OperationLogController extends BaseController {
	@Autowired
	private OperationLogService operationLogService ;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private UserService userService ;
	@Autowired
	private HttpServletRequest request ;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/ssadmin/operationLogList")
	public ModelAndView Index() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/operationLogList") ;
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
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (fuser.floginName like '%"+keyWord+"%' or \n");
				filter.append("fuser.fnickName like '%"+keyWord+"%' or \n");
				filter.append("fkey1 like '%"+keyWord+"%' or \n");
				filter.append("fuser.frealName like '%"+keyWord+"%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}

		String logDate = request.getParameter("logDate");
		if(logDate != null && logDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') >= '"+logDate+"' \n");
			modelAndView.addObject("logDate", logDate);
		}
		
		String logDate2 = request.getParameter("logDate2");
		if(logDate2 != null && logDate2.trim().length() >0){
			filter.append("and  DATE_FORMAT(fcreateTime,'%Y-%m-%d') <= '"+logDate2+"' \n");
			modelAndView.addObject("logDate2", logDate2);
		}
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by fid \n");
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		List<Foperationlog> list = this.operationLogService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("operationlogList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "operationLogList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Foperationlog", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("ssadmin/goOperationLogJSP")
	public ModelAndView goOperationLogJSP() throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Foperationlog operationlog = this.operationLogService.findById(fid);
			modelAndView.addObject("operationlog", operationlog);
		}
		modelAndView.setViewName(url) ;
		Map<Integer,String> typeMap = new HashMap<Integer,String>();
		typeMap.put(RemittanceTypeEnum.Type1, RemittanceTypeEnum.getEnumString(RemittanceTypeEnum.Type1));
		typeMap.put(RemittanceTypeEnum.Type2, RemittanceTypeEnum.getEnumString(RemittanceTypeEnum.Type2));
		typeMap.put(RemittanceTypeEnum.Type3, RemittanceTypeEnum.getEnumString(RemittanceTypeEnum.Type3));
		modelAndView.addObject("typeMap", typeMap);
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/saveOperationLog")
	public ModelAndView saveOperationLog() throws Exception{
		Foperationlog operationlog = new Foperationlog();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
		String dateString = sdf.format(new java.util.Date());
		Timestamp tm = Timestamp.valueOf(dateString);
		int userId = Integer.parseInt(request.getParameter("userLookup.id"));
		int ftype = Integer.parseInt(request.getParameter("ftype"));
		String famount = request.getParameter("famount");
		String fdescription = request.getParameter("fdescription");
		
		Fuser user = this.userService.findById(userId);
		operationlog.setFcreateTime(tm);
		operationlog.setFlastUpdateTime(tm);
		operationlog.setFamount(Double.valueOf(famount));
		operationlog.setFdescription(fdescription);
		operationlog.setFtype(ftype);
		operationlog.setFuser(user);
		operationlog.setFstatus(OperationlogEnum.SAVE);
		this.operationLogService.saveObj(operationlog);
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Add Success");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/deleteOperationLog")
	public ModelAndView deleteOperationLog() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		Foperationlog operationLog = this.operationLogService.findById(fid);
		if(operationLog.getFstatus() != OperationlogEnum.SAVE){
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","Delete Fail");
			return modelAndView;
		}
		
		this.operationLogService.deleteObj(fid);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Delete Success");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/auditOperationLog")
	public ModelAndView auditOperationLog() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		boolean flag = false;
		try {
			Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
			flag = this.operationLogService.updateOperationLog(fid,sessionAdmin);
		} catch (Exception e) {
			flag = false;
		}
		if(!flag){
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","Approve Fail");
			return modelAndView;
		}
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Approve Success");
		return modelAndView;
	}
}
