package com.ruizton.main.controller.admin;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.AdminStatusEnum;
import com.ruizton.main.Enum.LogTypeEnum;
import com.ruizton.main.comm.ParamArray;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fadmin;
import com.ruizton.main.model.Frole;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.RoleService;
import com.ruizton.main.service.admin.UserService;
import com.ruizton.util.Utils;

@Controller
public class AdminController extends BaseController {
	@Autowired
	private AdminService adminService ;
	@Autowired
	private RoleService roleService;
	@Autowired
	private HttpServletRequest request ;
	@Autowired
	private UserService userService ;
	
	
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/ssadmin/adminList")
	public ModelAndView Index() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/adminList") ;
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
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by fid \n");
		}
		
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("asc \n");
		}
		List<Fadmin> list = this.adminService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("adminList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "adminList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fadmin", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("ssadmin/goAdminJSP")
	public ModelAndView goAdminJSP() throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fadmin admin = this.adminService.findById(fid);
			modelAndView.addObject("fadmin", admin);
		}
		if(request.getSession().getAttribute("login_admin") != null){
			Fadmin admin = (Fadmin)request.getSession().getAttribute("login_admin");
			modelAndView.addObject("login_admin", admin);
		}
		
		List<Frole> roleList = this.roleService.findAll();
		Map map = new HashMap();
		for (Frole frole : roleList) {
			map.put(frole.getFid(),frole.getFname());
		}
		modelAndView.addObject("roleMap", map);
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/saveAdmin")
	public ModelAndView saveAdmin(ParamArray param) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		Fadmin fadmin = param.getFadmin() ;
		String fname = fadmin.getFname();
		List<Fadmin> all = this.adminService.findByProperty("fname", fname);
		if(all != null && all.size() >0){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","The administrator login name has already existed！");
			return modelAndView;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
		String dateString = sdf.format(new java.util.Date());
		fadmin.setFcreateTime(Timestamp.valueOf(dateString));
		String passWord = fadmin.getFpassword();
		fadmin.setSalt(Utils.getUUID());
		fadmin.setFpassword(Utils.MD5(passWord,fadmin.getSalt()));
		fadmin.setFstatus(AdminStatusEnum.NORMAL_VALUE);
		fadmin.setFuserid(0);
		
		int roleId = Integer.parseInt(request.getParameter("frole.fid"));
		Frole role = this.roleService.findById(roleId);
		fadmin.setFrole(role);
		
		this.adminService.saveObj(fadmin);
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_ADD);
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Add Success");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/forbbinAdmin")
	public ModelAndView forbbinAdmin() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		if(fid == sessionAdmin.getFid()){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","Not allowed to disable the current login administrator！");
			return modelAndView;
		}
		Fadmin admin = this.adminService.findById(fid);
		int status = Integer.parseInt(request.getParameter("status"));
		if(status == 1){
			admin.setFstatus(AdminStatusEnum.FORBBIN_VALUE);
		}else if (status == 2){
			admin.setFstatus(AdminStatusEnum.NORMAL_VALUE);
		}
		this.adminService.updateObj(admin);
		this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_UPDATE);
		
		modelAndView.addObject("statusCode",200);
		if(status == 1){
			modelAndView.addObject("message","Disable Success");
		}else if(status == 1){
			modelAndView.addObject("message","Enable Success");
		}
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/updateAdmin")
	public ModelAndView updateAdmin() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("fadmin.fid"));
		Fadmin fadmin = this.adminService.findById(fid);
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		if(fid == sessionAdmin.getFid()){
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","Not allowed to modify the current login administrator password！");
			return modelAndView;
		}
		String passWord = request.getParameter("fadmin.fpassword");
		fadmin.setFpassword(Utils.MD5(passWord,fadmin.getSalt()));
	
		this.adminService.updateObj(fadmin);
        this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_UPDATE);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Modify Password Success");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/updateAdminPassword")
	public ModelAndView updateAdminPassword() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("fadmin.fid"));
		Fadmin fadmin = this.adminService.findById(fid);
		String truePassword = fadmin.getFpassword();
		String newPassWord = request.getParameter("fadmin.fpassword");
		String oldPassword = request.getParameter("oldPassword");
		String newPasswordMD5 = Utils.MD5(newPassWord,fadmin.getSalt());
		String oldPasswordMD5 = Utils.MD5(oldPassword,fadmin.getSalt());
        if(!truePassword.equals(oldPasswordMD5)){
    		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
    		modelAndView.addObject("statusCode",300);
    		modelAndView.addObject("message","The input of the original password is incorrect, please reenter it");
    		return modelAndView;
        }
        fadmin.setFpassword(newPasswordMD5);
		this.adminService.updateObj(fadmin);
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_UPDATE);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Modify Password Success");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}

	@RequestMapping("ssadmin/loginOut")
	public ModelAndView loginOut() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		Subject admin = SecurityUtils.getSubject();
		admin.getSession().removeAttribute("permissions");
		admin.logout();
		modelAndView.setViewName("ssadmin/login");
		return modelAndView;
	}
	
	
	@RequestMapping("ssadmin/updateAdminRole")
	public ModelAndView updateAdminRole() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("fadmin.fid"));
		Fadmin fadmin = this.adminService.findById(fid);
		if(fadmin.getFname().equals("admin")){
			modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","Super administrator role does not allow modification！");
			return modelAndView;
		}
		
		int roleId = Integer.parseInt(request.getParameter("frole.fid"));
		Frole role = this.roleService.findById(roleId);
		fadmin.setFrole(role);
		this.adminService.updateObj(fadmin);
		
		Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
		this.adminService.updateAdminlog(sessionAdmin, getIpAddr(request), LogTypeEnum.Admin_ROLE);
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Modify Success");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/updateAdminUser")
	public ModelAndView updateAdminUser() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		
		try {
			int fid = Integer.parseInt(request.getParameter("fadmin.fid"));
			Fadmin fadmin = this.adminService.findById(fid);
			if(fadmin.getFuserid() >0){
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","The associated user ID is not allowed to repeat the operation");
				return modelAndView;
			}
			
			int roleId = Integer.parseInt(request.getParameter("fuserid"));
			Fuser fuser = this.userService.findById(roleId);
			if(fuser == null){
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","UID Non-existent");
				return modelAndView;
			}
			if(!fuser.getFgoogleBind()){
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","This member does not bind GOOGLE authentication");
				return modelAndView;
			}
			fadmin.setFuserid(fuser.getFid());
			this.adminService.updateObj(fadmin);
		} catch (Exception e) {
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","UID Non-existent");
			return modelAndView;
		}
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Associate Success");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
}
