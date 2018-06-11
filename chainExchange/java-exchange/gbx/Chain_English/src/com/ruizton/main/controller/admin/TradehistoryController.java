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
import com.ruizton.main.model.Ftradehistory;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.TradehistoryService;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.util.Utils;

@Controller
public class TradehistoryController extends BaseController {
	@Autowired
	private TradehistoryService tradehistoryService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private HttpServletRequest request ;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/ssadmin/tradehistoryList")
	public ModelAndView Index() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/tradehistoryList") ;
		//当前页
		int currentPage = 1;
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		
		if(request.getParameter("ftype") != null){
			int type = Integer.parseInt(request.getParameter("ftype"));
			if(type != 0){
				filter.append("and ftrademapping.fvirtualcointypeByFvirtualcointype2.fid="+type+"\n");
			}
			modelAndView.addObject("ftype", type);
		}else{
			modelAndView.addObject("ftype", 0);
		}
		
		if(request.getParameter("ftype1") != null){
			int type1 = Integer.parseInt(request.getParameter("ftype1"));
			if(type1 != 0){
				filter.append("and ftrademapping.fvirtualcointypeByFvirtualcointype1.fid="+type1+"\n");
			}
			modelAndView.addObject("ftype1", type1);
		}else{
			modelAndView.addObject("ftype1", 0);
		}
		
		String logDate = request.getParameter("logDate");
		if(logDate != null && logDate.trim().length() >0){
			filter.append("and  DATE_FORMAT(fdate,'%Y-%m-%d') = '"+logDate+"' \n");
			modelAndView.addObject("logDate", logDate);
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
		
		List<Fvirtualcointype> type1 = this.virtualCoinService.findAll(CoinTypeEnum.COIN_VALUE,1);
		Map<Integer,String> typeMap1 = new HashMap<Integer,String>();
		for (Fvirtualcointype fvirtualcointype : type1) {
			typeMap1.put(fvirtualcointype.getFid(), fvirtualcointype.getfShortName());
		}
		typeMap1.put(0, "ALL");
		modelAndView.addObject("typeMap1", typeMap1);
		
		List<Ftradehistory> list = this.tradehistoryService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("tradehistoryList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "tradehistoryList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Ftradehistory", filter+""));
		return modelAndView ;
	}
}
