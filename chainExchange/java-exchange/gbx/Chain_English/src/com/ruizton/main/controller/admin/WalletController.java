package com.ruizton.main.controller.admin;

import java.util.ArrayList;
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
import com.ruizton.main.model.Fcapitaloperation;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.CapitaloperationService;
import com.ruizton.main.service.admin.VirtualWalletService;
import com.ruizton.util.Utils;

@Controller
public class WalletController extends BaseController {
	@Autowired
	private VirtualWalletService virtualWalletService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private CapitaloperationService capitaloperationService;
	@Autowired
	private HttpServletRequest request ;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/ssadmin/walletList")
	public ModelAndView walletList() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/walletList") ;
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
				filter.append("fuser.frealName like '%"+keyWord+"%') \n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		filter.append(" and fvirtualcointype.ftype ="+CoinTypeEnum.FB_CNY_VALUE+" \n");

		
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
		List<Fvirtualwallet> list = this.virtualWalletService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("walletList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "walletList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fvirtualwallet", filter+""));
		return modelAndView ;
	}
	
	
	@RequestMapping("/ssadmin/viewUserWallet")
	public ModelAndView viewUserWallet() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/viewUserWallet") ;

		List<Map<String, Object>> allLists = new ArrayList<Map<String,Object>>();
		if(request.getParameter("cid") != null){
			int cid =Integer.parseInt(request.getParameter("cid"));
			Fcapitaloperation c = this.capitaloperationService.findById(cid);
			int userid = c.getFuser().getFid();
			String sqlString = "SELECT * from totalView where fuid="+userid;
			List all = this.adminService.getSQLList(sqlString);
			if(all != null && all.size() >0){
				for(int i=0;i<all.size();i++){
					Map<String, Object> map = new HashMap<String, Object>();
					Object[] objects = (Object[])all.get(i);
					String uid =objects[0].toString();
					String loginName =objects[1].toString();
					String realName =objects[2].toString();
					String telephone =objects[3].toString();
					String coinName =objects[4].toString();
					String total =objects[5].toString();
					String frozen =objects[6].toString();
					String regAmt =objects[7].toString();
					String wAmt =objects[8].toString();
					String buy =objects[9].toString();
					String sell =objects[10].toString();
					
					map.put("uid", uid);
					map.put("loginName", loginName);
					map.put("realName", realName);
					map.put("telephone", telephone);
					map.put("coinName", coinName);
					map.put("total", total);
					map.put("frozen", frozen);
					map.put("regAmt", regAmt);
					map.put("wAmt", wAmt);
					map.put("buy", buy);
					map.put("sell", sell);
					
					allLists.add(map);
				}
			}
		}
		
		modelAndView.addObject("userList", allLists);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", 1);
		modelAndView.addObject("rel", "viewUserWallet");
		//总数量
		modelAndView.addObject("totalCount",allLists.size());
		return modelAndView ;
	}
}
