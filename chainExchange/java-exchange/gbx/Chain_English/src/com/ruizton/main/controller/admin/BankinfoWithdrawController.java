package com.ruizton.main.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.FbankinfoWithdraw;
import com.ruizton.main.model.FvirtualaddressWithdraw;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.BankinfoWithdrawService;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.admin.VirtualaddressWithdrawService;
import com.ruizton.util.Utils;

@Controller
public class BankinfoWithdrawController extends BaseController {
	@Autowired
	private BankinfoWithdrawService bankinfoWithdrawService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private VirtualCoinService virtualCoinService ;
	@Autowired
	private HttpServletRequest request ;
	@Autowired
	private VirtualaddressWithdrawService virtualaddressWithdrawService;
	
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/ssadmin/bankinfoWithdrawList")
	public ModelAndView bankinfoWithdrawList() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/bankinfoWithdrawList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String address = request.getParameter("address");
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
				filter.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filter.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filter.append("fuser.frealName like '%" + keyWord + "%' ) \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		if(address != null && address.trim().length() >0){
			filter.append(" and fbankNumber like '%"+address+"%' \n");
			modelAndView.addObject("address", address);
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
		
		List<FbankinfoWithdraw> list = this.bankinfoWithdrawService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("bankinfoWithdrawList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "bankinfoWithdrawList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("FbankinfoWithdraw", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/ssadmin/virtualaddressWithdrawList")
	public ModelAndView virtualaddressWithdrawList() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/virtualaddressWithdrawList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String address = request.getParameter("address");
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
				filter.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filter.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filter.append("fuser.frealName like '%" + keyWord + "%' ) \n");
			}
		}
		
		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filter.append("and fvirtualcointype.fid=" + type + "\n");
			}
			modelAndView.addObject("ftype", type);
		} else {
			modelAndView.addObject("ftype", 0);
		}
		
		if(address != null && address.trim().length() >0){
			filter.append(" and fadderess like '%"+address+"%' \n");
			modelAndView.addObject("address", address);
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
		
		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		Map<Integer,String> typeMap = new HashMap<Integer,String>();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getfShortName());
		}
		typeMap.put(0, "ALL");
		modelAndView.addObject("typeMap", typeMap);
		
		List<FvirtualaddressWithdraw> list = this.virtualaddressWithdrawService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("virtualaddressWithdrawList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "virtualaddressWithdrawList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("FvirtualaddressWithdraw", filter+""));
		return modelAndView ;
	}
}
