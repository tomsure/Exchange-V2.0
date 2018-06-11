package com.ruizton.main.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.TransportlogStatusEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Ftransportlog;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.TransportlogService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.util.Utils;

@Controller
public class TransportlogController extends BaseController {
	@Autowired
	private TransportlogService transportlogService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private FrontUserService frontUserService ;
	
	
	@Autowired
	private HttpServletRequest request ;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/ssadmin/transportlogList")
	public ModelAndView Index() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/transportlogList1") ;
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
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (fuser.floginName like '%" + keyWord + "%' or \n");
				filter.append("fuser.fnickName like '%" + keyWord + "%'  or \n");
				filter.append("fuser.frealName like '%" + keyWord + "%'  or \n");
				filter.append("fuser.ftelephone like '%" + keyWord + "%'  or \n");
				filter.append("fuser.femail like '%" + keyWord + "%'  or \n");
				filter.append("fuser.fidentityNo like '%" + keyWord + "%' )\n");
			}
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
			filter.append("desc \n");
		}
		List<Ftransportlog> list = this.transportlogService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("transportlogList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "transportlogList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Ftransportlog", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("ssadmin/transportlogAudit")
	public ModelAndView transportlogAudit() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("uid"));
		Ftransportlog log = this.transportlogService.findById(fid);
		if(log.getFstatus() != TransportlogStatusEnum.NORMAL_VALUE){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "非法操作");
			return modelAndView;
		}
		log.setFstatus(TransportlogStatusEnum.AUDIT_VALUE);
		Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(log.getFuser().getFid(), log.getFvirtualcointype().getFid()) ;
		Fuser user = this.frontUserService.findById(Integer.parseInt(log.getFaddress()));
		Fvirtualwallet fw2 = this.frontUserService.findVirtualWalletByUser(user.getFid(), log.getFvirtualcointype().getFid()) ;
		fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen()-log.getFamount()-log.getFfees());
		fw2.setFtotal(fw2.getFtotal()+log.getFamount());
		try {
			this.frontUserService.updateTransport(log, fvirtualwallet,fw2) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "审核成功");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/transportlogCancel")
	public ModelAndView transportlogCancel() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("uid"));
		Ftransportlog log = this.transportlogService.findById(fid);
		if(log.getFstatus() != TransportlogStatusEnum.NORMAL_VALUE){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "非法操作");
			return modelAndView;
		}
		log.setFstatus(TransportlogStatusEnum.CANCEL_VALUE);
		Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(log.getFuser().getFid(), log.getFvirtualcointype().getFid()) ;
		fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen()-log.getFamount()-log.getFfees());
		fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+log.getFamount()+log.getFfees());
		try {
			this.frontUserService.updateCancelTransport(log, fvirtualwallet) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "取消成功");
		return modelAndView;
	}
}
