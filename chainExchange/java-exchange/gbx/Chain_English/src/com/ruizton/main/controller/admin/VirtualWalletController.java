package com.ruizton.main.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.admin.VirtualWalletService;
import com.ruizton.util.Utils;
import com.ruizton.util.XlsExport;

@Controller
public class VirtualWalletController extends BaseController {
	@Autowired
	private VirtualWalletService virtualWalletService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private HttpServletRequest request ;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/ssadmin/virtualwalletList")
	public ModelAndView Index() throws Exception{
		
	
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/virtualwalletList") ;
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
		
		if(request.getParameter("ftype") != null){
			int type = Integer.parseInt(request.getParameter("ftype"));
			if(type != 0){
				filter.append("and fvirtualcointype.fid="+type+"\n");
			}
			modelAndView.addObject("ftype", type);
		}else{
			modelAndView.addObject("ftype", 0);
		}
		
		filter.append(" and fvirtualcointype.ftype <>"+CoinTypeEnum.FB_CNY_VALUE+" \n");
		
		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getfShortName());
		}
		typeMap.put(0, "ALL");
		modelAndView.addObject("typeMap", typeMap);
		
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
		modelAndView.addObject("virtualwalletList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "virtualwalletList");
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fvirtualwallet", filter+""));
		return modelAndView ;
	}

	private static enum ExportFiled {
		登陆名,会员昵称,会员真实姓名,币种类型,总数量,冻结数量;
	}

	private List<Fvirtualwallet> getUserList() {
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and (fuser.floginName like '%"+keyWord+"%' or \n");
			filter.append("fuser.fnickName like '%"+keyWord+"%' or \n");
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("fuser.fid =" + fid + " or \n");
			} catch (Exception e) {}
			filter.append("fuser.frealName like '%"+keyWord+"%') \n");
		}
		
		filter.append("and (ftotal >0 or ffrozen >0) \n");
		
		if(request.getParameter("ftype") != null){
			int type = Integer.parseInt(request.getParameter("ftype"));
			if(type != 0){
				filter.append("and fvirtualcointype.fid="+type+"\n");
			}
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
		List<Fvirtualwallet> list = this.virtualWalletService.list(0, 0, filter.toString(),false);
        return list;
	}

	@RequestMapping("ssadmin/virtualwalletExport")
	public ModelAndView virtualwalletExport(HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=virtualwalletExport.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (ExportFiled filed : ExportFiled.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}

		List<Fvirtualwallet> lists = getUserList();
		for (Fvirtualwallet fvirtualwallet : lists) {
			e.createRow(rowIndex++);
			for (ExportFiled filed : ExportFiled.values()) {
				switch (filed) {
				case 登陆名:
					e.setCell(filed.ordinal(), fvirtualwallet.getFuser().getFloginName());
					break;
				case 会员昵称:
					e.setCell(filed.ordinal(), fvirtualwallet.getFuser().getFnickName());
					break;
				case 会员真实姓名:
					e.setCell(filed.ordinal(), fvirtualwallet.getFuser().getFrealName());
					break;	
				case 币种类型:
					e.setCell(filed.ordinal(), fvirtualwallet.getFvirtualcointype().getFname());
					break;
				case 总数量:
					e.setCell(filed.ordinal(),fvirtualwallet.getFtotal());
					break;
				case 冻结数量:
					e.setCell(filed.ordinal(),fvirtualwallet.getFfrozen());
					break;
				default:
					break;
				}
			}
		}

		e.exportXls(response);

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "导出成功");
		return modelAndView;
	}
	
}
