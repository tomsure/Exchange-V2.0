package com.ruizton.main.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.LinkTypeEnum;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Ffriendlink;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.FriendLinkService;
import com.ruizton.main.service.comm.listener.ChannelConstant;
import com.ruizton.main.service.comm.listener.MessageSender;
import com.ruizton.util.Utils;

@Controller
public class QQController extends BaseController {
	@Autowired
	private FriendLinkService friendLinkService ;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private HttpServletRequest request ;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	@Autowired
	private ConstantMap constantMap ;
	@Autowired
	private MessageSender messageSender ;
	
	@RequestMapping("/ssadmin/qqList")
	public ModelAndView qqList() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/qqList") ;
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
        filter.append("where ftype="+LinkTypeEnum.QQ_VALUE+" \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and (fname like '%"+keyWord+"%' or \n");
			filter.append("furl like '%"+keyWord+"%' ) \n");
			modelAndView.addObject("keywords", keyWord);
		}
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by fcreateTime \n");
		}
		
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		
		List<Ffriendlink> list = this.friendLinkService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("linkList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "qqList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Ffriendlink", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("ssadmin/goQQJSP")
	public ModelAndView goQQJSP() throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Ffriendlink friendlink = this.friendLinkService.findById(fid);
			modelAndView.addObject("friendlink", friendlink);
		}
		Map map = new HashMap();
		map.put(LinkTypeEnum.QQ_VALUE, LinkTypeEnum.getEnumString(LinkTypeEnum.QQ_VALUE));
		modelAndView.addObject("linkTypeMap", map);
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/saveQQ")
	public ModelAndView saveQQ() throws Exception{
		Ffriendlink link = new Ffriendlink();
		link.setFdescription(request.getParameter("link.fdescription"));
		link.setFname(request.getParameter("link.fname"));
		link.setForder(Integer.parseInt(request.getParameter("link.forder")));
		link.setFurl(request.getParameter("link.furl"));
		link.setFcreateTime(Utils.getTimestamp());
		link.setFtype(LinkTypeEnum.QQ_VALUE);
		this.friendLinkService.saveObj(link);
		
		this.messageSender.publish(ChannelConstant.constantmap, "quns");
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","新增成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/deleteQQ")
	public ModelAndView deleteQQ() throws Exception{
		int fid = Integer.parseInt(request.getParameter("uid"));
		this.friendLinkService.deleteObj(fid);
		
		this.messageSender.publish(ChannelConstant.constantmap, "quns");
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","删除成功");
		return modelAndView;
	}
	
	
	@RequestMapping("ssadmin/updateQQ")
	public ModelAndView updateQQ() throws Exception{
		Ffriendlink link = this.friendLinkService.findById(Integer.parseInt(request.getParameter("link.fid")));
		link.setFdescription(request.getParameter("link.fdescription"));
		link.setFname(request.getParameter("link.fname"));
		link.setForder(Integer.parseInt(request.getParameter("link.forder")));
		link.setFurl(request.getParameter("link.furl"));
		this.friendLinkService.updateObj(link);
		
		this.messageSender.publish(ChannelConstant.constantmap, "quns");
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","修改成功");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
}
