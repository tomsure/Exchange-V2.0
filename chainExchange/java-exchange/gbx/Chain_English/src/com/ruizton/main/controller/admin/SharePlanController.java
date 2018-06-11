package com.ruizton.main.controller.admin;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.SharePlanLogStatusEnum;
import com.ruizton.main.Enum.SharePlanStatusEnum;
import com.ruizton.main.Enum.SharePlanTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fadmin;
import com.ruizton.main.model.Fshareplan;
import com.ruizton.main.model.Fshareplanlog;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.SharePlanLogService;
import com.ruizton.main.service.admin.SharePlanService;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.admin.VirtualWalletService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.util.Utils;

@Controller
public class SharePlanController extends BaseController {
	@Autowired
	private SharePlanService sharePlanService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private SharePlanLogService sharePlanLogService;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private VirtualWalletService virtualWalletService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private FrontUserService frontUserService;
	// 每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();

	@RequestMapping("/ssadmin/sharePlanList")
	public ModelAndView sharePlanList() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/sharePlanList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			filter.append("and ftitle like '%" + keyWord + "%' \n");
			modelAndView.addObject("keywords", keyWord);
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
		filter.append("and ftype=" + SharePlanTypeEnum.NORMAL + "\n");
		List<Fshareplan> list = this.sharePlanService.list((currentPage - 1)
				* numPerPage, numPerPage, filter + "", true);
		modelAndView.addObject("sharePlanList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "sharePlanList");
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fshareplan", filter + ""));

		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
		}
		typeMap.put(0, "全部");
		modelAndView.addObject("typeMap", typeMap);

		return modelAndView;
	}
	
	@RequestMapping("/ssadmin/handingSharePlanList")
	public ModelAndView handingSharePlanList() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/handingSharePlanList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			filter.append("and ftitle like '%" + keyWord + "%' \n");
			modelAndView.addObject("keywords", keyWord);
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
		filter.append("and ftype=" + SharePlanTypeEnum.HANDING + "\n");
		List<Fshareplan> list = this.sharePlanService.list((currentPage - 1)
				* numPerPage, numPerPage, filter + "", true);
		modelAndView.addObject("handingSharePlanList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "handingSharePlanList");
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fshareplan", filter + ""));

		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getFname());
		}
		typeMap.put(0, "全部");
		modelAndView.addObject("typeMap", typeMap);

		return modelAndView;
	}

	@RequestMapping("ssadmin/goSharePlanJSP")
	public ModelAndView goSharePlanJSP() throws Exception {
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(url);
		if (request.getParameter("uid") != null) {
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fshareplan shareplan = this.sharePlanService.findById(fid);
			modelAndView.addObject("shareplan", shareplan);
		}
		List<Fvirtualcointype> allType = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		modelAndView.addObject("allType", allType);
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/auditSharePlan")
	public ModelAndView auditSharePlan() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		boolean flag = false;
		int uid = Integer.parseInt(request.getParameter("uid"));
		Fshareplan sharePlan = this.sharePlanService.findById(uid);
		if (sharePlan.getFstatus() != SharePlanStatusEnum.SAVE_VALUE) {
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "分红计划已审核");
			return modelAndView;
		}
		sharePlan.setFstatus(SharePlanStatusEnum.AUDITED_VALUE);
		sharePlan.setFcreateTime(Utils.getTimestamp());
		if(request.getSession().getAttribute("login_admin") != null){
			Fadmin sessionAdmin = (Fadmin)request.getSession().getAttribute("login_admin");
			sharePlan.setFcreator(sessionAdmin);
		}
		
		try {
			flag = this.sharePlanService.update(sharePlan);
		} catch (Exception e) {
			e.printStackTrace() ;
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
		    modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败");
			return modelAndView;
		}
		
		if(!flag){
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
		    modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "审核失败");
			return modelAndView;
		}
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "审核成功");
		return modelAndView;
	}
	
	/**
	 * 分钱：平台总交易手续费*比例/平台总币数*每个用户币数=每个用户得到的钱
	 * */
	@RequestMapping("ssadmin/saveSharePlan")
	public ModelAndView saveSharePlan() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		String title = request.getParameter("ftitle");
		int vid = Integer.parseInt(request.getParameter("vid"));
		Timestamp startDate = Timestamp.valueOf(request.getParameter("startDate"));
		Timestamp endDate = Timestamp.valueOf(request.getParameter("endDate"));
		BigDecimal famount = new BigDecimal(request.getParameter("famount"));

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		calendar.add(Calendar.DATE, 1);
		Fvirtualcointype vType = this.virtualCoinService.findById(vid);
		Fshareplan sharePlan = new Fshareplan();
		sharePlan.setFstatus(SharePlanStatusEnum.SAVE_VALUE);
		sharePlan.setFtitle(title);
		sharePlan.setFamount(famount);
		sharePlan.setFvirtualcointype(vType);
		sharePlan.setFtype(SharePlanTypeEnum.NORMAL);
		sharePlan.setFpercent(BigDecimal.ZERO);
		sharePlan.setFstartDate(startDate);
		sharePlan.setFendDate(endDate);
		sharePlan.setFtotalCoinQty(BigDecimal.ZERO);
		sharePlan.setFtypes("手工分红");
		this.sharePlanService.saveObj(sharePlan);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "新增成功");
		modelAndView.addObject("callbackType", "closeCurrent");
		return modelAndView;
	}
	
	/**
	 * 分币数量 * 本人交易金额 / 总交易金额
	 * */
	@RequestMapping("ssadmin/saveHandingSharePlan")
	public ModelAndView saveHandingSharePlan() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		String title = request.getParameter("ftitle");
		BigDecimal ftotalCoinQty = new BigDecimal(request.getParameter("ftotalCoinQty"));
		if(ftotalCoinQty.compareTo(BigDecimal.ZERO) <= 0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "数量不能小于等于0");
			return modelAndView;
		}
		int vid = Integer.parseInt(request.getParameter("vid"));
		int vid1 = Integer.parseInt(request.getParameter("vid1"));
		Fvirtualcointype vType = this.virtualCoinService.findById(vid);
		Fvirtualcointype vType1 = this.virtualCoinService.findById(vid1);
		Fshareplan sharePlan = new Fshareplan();
		sharePlan.setFstatus(SharePlanStatusEnum.SAVE_VALUE);
		sharePlan.setFtitle(title);
		sharePlan.setFvirtualcointype(vType);
		sharePlan.setFsendcointype(vType1);
		sharePlan.setFtype(SharePlanTypeEnum.HANDING);
		sharePlan.setFtotalCoinQty(ftotalCoinQty);
		sharePlan.setFamount(ftotalCoinQty);
		sharePlan.setFcreateTime(Utils.getTimestamp());
		sharePlan.setFtypes(vType1.getFname());
		Timestamp startDate = Timestamp.valueOf(request.getParameter("startDate"));
		Timestamp endDate = Timestamp.valueOf(request.getParameter("endDate"));
		sharePlan.setFstartDate(startDate);
		sharePlan.setFendDate(endDate);
		this.sharePlanService.saveObj(sharePlan);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "新增成功");
		modelAndView.addObject("callbackType", "closeCurrent");
		return modelAndView;
	}

	@RequestMapping("ssadmin/deleteSharePlan")
	public ModelAndView deleteSharePlan() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fshareplan shareplan = this.sharePlanService.findById(fid);
		if (shareplan.getFstatus() == SharePlanStatusEnum.AUDITED_VALUE) {
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "分红计划已审核，不允许删除");
			return modelAndView;
		}
		this.sharePlanService.deleteObj(fid);

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "删除成功");
		return modelAndView;
	}

	@RequestMapping("ssadmin/updateSharePlan")
	public ModelAndView updateSharePlan() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int uid = Integer.parseInt(request.getParameter("uid"));
		String title = request.getParameter("ftitle");
		int vid = Integer.parseInt(request.getParameter("vid"));
		Fvirtualcointype vType = this.virtualCoinService.findById(vid);
		Fshareplan sharePlan = this.sharePlanService.findById(uid);
		BigDecimal famount = new BigDecimal(request.getParameter("famount"));
		if(famount.compareTo(BigDecimal.ZERO) <= 0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "分红金额不能小于等于0");
			return modelAndView;
		}
		sharePlan.setFamount(famount);
		sharePlan.setFstatus(SharePlanStatusEnum.SAVE_VALUE);
		sharePlan.setFtitle(title);
		sharePlan.setFvirtualcointype(vType);
		sharePlan.setFpercent(BigDecimal.ZERO);
		Timestamp startDate = Timestamp.valueOf(request.getParameter("startDate"));
		Timestamp endDate = Timestamp.valueOf(request.getParameter("endDate"));
		sharePlan.setFstartDate(startDate);
		sharePlan.setFendDate(endDate);
		sharePlan.setFtotalCoinQty(BigDecimal.ZERO);
		this.sharePlanService.updateObj(sharePlan);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "更新成功");
		modelAndView.addObject("callbackType", "closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/updateHandingSharePlan")
	public ModelAndView updateHandingSharePlan() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int uid = Integer.parseInt(request.getParameter("uid"));
		String title = request.getParameter("ftitle");
		BigDecimal ftotalCoinQty = new BigDecimal(request.getParameter("ftotalCoinQty"));
		if(ftotalCoinQty.compareTo(BigDecimal.ZERO) <= 0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "数量不能小于等于0");
			return modelAndView;
		}

		int vid = Integer.parseInt(request.getParameter("vid"));
		Fvirtualcointype vType = this.virtualCoinService.findById(vid);
		int vid1 = Integer.parseInt(request.getParameter("vid1"));
		Fvirtualcointype vType1 = this.virtualCoinService.findById(vid1);
		Fshareplan sharePlan = this.sharePlanService.findById(uid);
		if (sharePlan.getFstatus() == SharePlanStatusEnum.AUDITED_VALUE) {
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "分红计划已审核，不允许修改");
			return modelAndView;
		}
		sharePlan.setFstatus(SharePlanStatusEnum.SAVE_VALUE);
		sharePlan.setFtitle(title);
		sharePlan.setFvirtualcointype(vType);
		sharePlan.setFsendcointype(vType1);
		sharePlan.setFtotalCoinQty(ftotalCoinQty);
		Timestamp startDate = Timestamp.valueOf(request.getParameter("startDate"));
		Timestamp endDate = Timestamp.valueOf(request.getParameter("endDate"));
		sharePlan.setFstartDate(startDate);
		sharePlan.setFendDate(endDate);
		sharePlan.setFamount(ftotalCoinQty);
		sharePlan.setFcreateTime(Utils.getTimestamp());
		sharePlan.setFtypes(vType1.getFname());
		this.sharePlanService.updateObj(sharePlan);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "更新成功");
		modelAndView.addObject("callbackType", "closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/sendMoney")
	public ModelAndView sendMoney() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		boolean flag = false;
		int uid = Integer.parseInt(request.getParameter("uid"));
		Fshareplan sharePlan = this.sharePlanService.findById(uid);
		if (sharePlan.getFstatus() != SharePlanStatusEnum.AUDITED_VALUE) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "分红计划未审核，不允许发钱");
			return modelAndView;
		}
		String filter = "where fshareplan.fid="+uid+" and fstatus="+SharePlanLogStatusEnum.NOSEND;
		List<Fshareplanlog> allLog = this.sharePlanLogService.list(0, 0, filter, false);
		if(allLog == null || allLog.size() ==0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "已发钱完毕，无需再发");
			return modelAndView;
		}
		//总数
		int totalCount = allLog.size();
		//总成功数
		int successCount = 0;
		for (Fshareplanlog fshareplanlog : allLog) {
			if(fshareplanlog.getFstatus() != SharePlanLogStatusEnum.NOSEND) continue;
			if(fshareplanlog.getFshareplan().getFtype() == SharePlanTypeEnum.NORMAL){
				//钱包
				Fvirtualwallet wallet = this.frontUserService.findWalletByUser(fshareplanlog.getFuser().getFid());
				BigDecimal total = new BigDecimal(wallet.getFtotal());
				wallet.setFtotal(total.add(fshareplanlog.getFamount()).doubleValue());
				//记录
				fshareplanlog.setFstatus(SharePlanLogStatusEnum.HASSEND);
				try {
					flag = this.sharePlanService.updateSharePlanLog(wallet, fshareplanlog);
				} catch (Exception e) {
					modelAndView.addObject("statusCode", 300);
					modelAndView.addObject("message", "部分成功，请重新发送!待发数："+totalCount+",本次发送数："+successCount);
					return modelAndView;
				}
				successCount = successCount+1;
			}else if(fshareplanlog.getFshareplan().getFtype() == SharePlanTypeEnum.HANDING){
				int vid = fshareplanlog.getFshareplan().getFsendcointype().getFid();
				String filter1 = "where fuser.fid="+fshareplanlog.getFuser().getFid()+" and fvirtualcointype.fid="+vid;
				List<Fvirtualwallet> all = this.virtualWalletService.list(0, 0, filter1, false);
				if(all != null && all.size() >= 0){
					Fvirtualwallet virtualwallet = all.get(0);
					virtualwallet.setFtotal(virtualwallet.getFtotal()+Double.valueOf(fshareplanlog.getFamount().toString()));
					//记录
					fshareplanlog.setFstatus(SharePlanLogStatusEnum.HASSEND);
					try {
						flag = this.sharePlanService.updateSharePlanLog1(virtualwallet, fshareplanlog);
					} catch (Exception e) {
						modelAndView.addObject("statusCode", 300);
						modelAndView.addObject("message", "部分成功，请重新发送!待发数："+totalCount+",本次发送数："+successCount);
						return modelAndView;
					}
					successCount = successCount+1;
				}
			}
		}
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "发钱成功，本次发送数："+successCount);
		return modelAndView;
	}
	
}