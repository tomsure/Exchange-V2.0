package com.ruizton.main.controller.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.CapitalOperationInStatus;
import com.ruizton.main.Enum.CapitalOperationOutStatus;
import com.ruizton.main.Enum.CapitalOperationTypeEnum;
import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.EntrustTypeEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationInStatusEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationOutStatusEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.CapitaloperationService;
import com.ruizton.main.service.admin.EntrustService;
import com.ruizton.main.service.admin.UserService;
import com.ruizton.main.service.admin.VirtualCapitaloperationService;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.admin.VirtualWalletService;

import net.sf.json.JSONArray;

@Controller
public class ReportController extends BaseController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private CapitaloperationService capitaloperationService;
	@Autowired
	private VirtualWalletService virtualWalletService;
	@Autowired
	private VirtualCapitaloperationService virtualCapitaloperationService;
	@Autowired
	private EntrustService entrustService;
	@Autowired
	private UserService userService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private VirtualCoinService virtualCoinService;
	// 每页显示多少条数据
	private int numPerPage = 500;
	
	@RequestMapping("ssadmin/totalReport")
	public ModelAndView totalReport() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.setViewName("ssadmin/totalReport");
		// 总会员数
		modelAndView.addObject("totalUser",
				this.adminService.getAllCount("Fuser", "where fstatus=1"));
		// 总认证数
		modelAndView.addObject("totalValidateUser",this.adminService.getAllCount("Fuser", "where fhasRealValidate=1"));

		// 今天注册数
		modelAndView.addObject("todayTotalUser",this.adminService.getAllCount("Fuser", "where date_format(fregisterTime,'%Y-%m-%d')=date_format(now(),'%Y-%m-%d')"));
		// 今天认证数
		modelAndView.addObject("todayValidateUser", this.adminService.getAllCount("Fuser", "where date_format(fhasRealValidateTime,'%Y-%m-%d')=date_format(now(),'%Y-%m-%d')"));

		// 全站总币数量
		List<Map> virtualQtyList = this.virtualWalletService.getTotalQty();
		modelAndView.addObject("virtualQtyList", virtualQtyList);

		// 今日充值总金额
		Map amountInMap = this.capitaloperationService.getTotalAmountIn(
				CapitalOperationTypeEnum.RMB_IN, "("
						+ CapitalOperationInStatus.Come + ")", true);
		Map totalAmountInMap = this.capitaloperationService.getTotalAmountIn(
				CapitalOperationTypeEnum.RMB_IN, "("
						+ CapitalOperationInStatus.Come + ")", false);
		modelAndView.addObject("amountInMap", amountInMap);
		modelAndView.addObject("totalAmountInMap", totalAmountInMap);
		
		String s1 = "SELECT sum(famount) from foperationlog where fstatus=2 and date_format(fcreatetime,'%Y-%m-%d')=date_format(now(),'%Y-%m-%d')";
		String s2 = "SELECT sum(famount) from foperationlog where fstatus=2";
		double todayOpAmt = this.adminService.getSQLValue(s1);
		double totalOpAmt = this.adminService.getSQLValue(s2);
		modelAndView.addObject("todayOpAmt", todayOpAmt);
		modelAndView.addObject("totalOpAmt", totalOpAmt);
		
		// 今日提现总金额
		Map amountOutMap = this.capitaloperationService.getTotalAmount(
				CapitalOperationTypeEnum.RMB_OUT, "("
						+ CapitalOperationOutStatus.OperationSuccess + ")",
				true);
		Map amountOutMap1 = this.capitaloperationService.getTotalAmount(
				CapitalOperationTypeEnum.RMB_OUT, "("
						+ CapitalOperationOutStatus.OperationSuccess + ")",
				false);
		modelAndView.addObject("amountOutMap", amountOutMap);
		modelAndView.addObject("amountOutMap1", amountOutMap1);

		// 所有提现未转帐总金额
		String coStatus = "(" + CapitalOperationOutStatus.WaitForOperation
				+ "," + CapitalOperationOutStatus.OperationLock + ")";
		Map amountOutWaitingMap = this.capitaloperationService.getTotalAmount(
				CapitalOperationTypeEnum.RMB_OUT, coStatus, false);
		modelAndView.addObject("amountOutWaitingMap", amountOutWaitingMap);

		// 今日充值虚拟币总数量
		List virtualInMap = this.virtualCapitaloperationService.getTotalAmount(
				VirtualCapitalOperationTypeEnum.COIN_IN, "("
						+ VirtualCapitalOperationInStatusEnum.SUCCESS + ")",
				true);
		modelAndView.addObject("virtualInMap", virtualInMap);

		// 今日提现虚拟币
		List virtualOutSuccessMap = this.virtualCapitaloperationService
				.getTotalAmount(VirtualCapitalOperationTypeEnum.COIN_OUT, "("
						+ VirtualCapitalOperationOutStatusEnum.OperationSuccess
						+ ")", true);
		modelAndView.addObject("virtualOutSuccessMap", virtualOutSuccessMap);
		
		// 累计提现虚拟币
		List virtualOutSuccessTotalMap = this.virtualCapitaloperationService
				.getTotalAmount(VirtualCapitalOperationTypeEnum.COIN_OUT, "("
						+ VirtualCapitalOperationOutStatusEnum.OperationSuccess
						+ ")", false);
		modelAndView.addObject("virtualOutSuccessTotalMap", virtualOutSuccessTotalMap);
		
		
		// 今日提现虚拟币
		List virtualOutFeesMap = this.virtualCapitaloperationService
				.getTotalFees(VirtualCapitalOperationTypeEnum.COIN_OUT, "("
						+ VirtualCapitalOperationOutStatusEnum.OperationSuccess
						+ ")", true);
		modelAndView.addObject("virtualOutFeesMap", virtualOutFeesMap);
		
		// 累计提现虚拟币
		List virtualOutFeesTotalMap = this.virtualCapitaloperationService
				.getTotalFees(VirtualCapitalOperationTypeEnum.COIN_OUT, "("
						+ VirtualCapitalOperationOutStatusEnum.OperationSuccess
						+ ")", false);
		modelAndView.addObject("virtualOutFeesTotalMap", virtualOutFeesTotalMap);

		// 等待提现虚拟币
		String voStatus = "("
				+ VirtualCapitalOperationOutStatusEnum.WaitForOperation + ","
				+ VirtualCapitalOperationOutStatusEnum.OperationLock + ")";

		List virtualOutWaitingMap = this.virtualCapitaloperationService
				.getTotalAmount(VirtualCapitalOperationTypeEnum.COIN_OUT,
						voStatus, false);
		modelAndView.addObject("virtualOutWaitingMap", virtualOutWaitingMap);

		// 当前委托买入
		List entrustBuyMap = this.entrustService
				.getTotalQty(EntrustTypeEnum.BUY);
		modelAndView.addObject("entrustBuyMap", entrustBuyMap);

		// 当前委托卖出
		List entrustSellMap = this.entrustService
				.getTotalQty(EntrustTypeEnum.SELL);
		modelAndView.addObject("entrustSellMap", entrustSellMap);
		
		// 今日提现总手续费
		Map amountOutFeeMap1 = this.capitaloperationService.getTotalAmount(
				CapitalOperationTypeEnum.RMB_OUT, "("
						+ CapitalOperationOutStatus.OperationSuccess + ")",
				true,true);
		Map amountOutFeeMap2 = this.capitaloperationService.getTotalAmount(
				CapitalOperationTypeEnum.RMB_OUT, "("
						+ CapitalOperationOutStatus.OperationSuccess + ")",
				false,true);
		modelAndView.addObject("amountOutFeeMap1", amountOutFeeMap1);
		modelAndView.addObject("amountOutFeeMap2", amountOutFeeMap2);
		
		List entrustBuyFeesMap1 = this.entrustService
				.getTotalQty(EntrustTypeEnum.BUY,true);
		List entrustBuyFeesMap2 = this.entrustService
				.getTotalQty(EntrustTypeEnum.BUY,false);
		modelAndView.addObject("entrustBuyFeesMap1", entrustBuyFeesMap1);
		modelAndView.addObject("entrustBuyFeesMap2", entrustBuyFeesMap2);
		
		List entrustSellFeesMap1 = this.entrustService
				.getTotalQty(EntrustTypeEnum.SELL,true);
		List entrustSellFeesMap2 = this.entrustService
				.getTotalQty(EntrustTypeEnum.SELL,false);
		modelAndView.addObject("entrustSellFeesMap1", entrustSellFeesMap1);
		modelAndView.addObject("entrustSellFeesMap2", entrustSellFeesMap2);
		
		//累计手续费
		double entrustSellFeesMap11 = this.entrustService
				.getTotalSumFees(EntrustTypeEnum.SELL,true);
		double entrustSellFeesMap22 = this.entrustService
				.getTotalSumFees(EntrustTypeEnum.SELL,false);
		modelAndView.addObject("entrustSellFeesMap11", entrustSellFeesMap11);
		modelAndView.addObject("entrustSellFeesMap22", entrustSellFeesMap22);


		modelAndView.addObject("rel", "totalReport");
		return modelAndView;
	}
	

	@RequestMapping("/ssadmin/userReport")
	public ModelAndView userReport() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/userReport");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		if ((startDate == null || startDate.trim().length() == 0)
				&&(endDate == null || endDate.trim().length() == 0)) {
			Calendar cal_1=Calendar.getInstance();
			//设置为1号,当前日期既为本月第一天 
			cal_1.set(Calendar.DAY_OF_MONTH,1);
			startDate = sdf.format(cal_1.getTime());
			Calendar cale = Calendar.getInstance();
			//设置为1号,当前日期既为本月第一天 
			cale.set(Calendar.DATE, cale.getActualMaximum(Calendar.DATE)); 
			endDate = sdf.format(cale.getTime());
		}


		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(endDate));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.get(Calendar.DAY_OF_MONTH) + 1);
		if(startDate != null && startDate.trim().length() >0){
			filter.append("and date_format(Fregistertime,'%Y-%m-%d') >='" + startDate + "' \n");
		}
		if(endDate != null && endDate.trim().length() >0){
    		filter.append("and date_format(Fregistertime,'%Y-%m-%d') <='" + endDate+ "' \n");
		}
		List all = this.userService.getUserGroup(filter + "");
		double total = 0;
		JSONArray key =new JSONArray();
		JSONArray value =new JSONArray();
		
		if(all != null && all.size() >0){
			Iterator it = all.iterator();
			while (it.hasNext()) {
				Object[] o = (Object[]) it.next();
				key.add(o[1]);
				value.add(o[0]);
				total = total + Double.valueOf(o[0].toString());
			}
		}
		modelAndView.addObject("key", key);
		modelAndView.addObject("value",value);
		modelAndView.addObject("startDate", startDate);
		modelAndView.addObject("endDate", endDate);
		modelAndView.addObject("total", total);
		return modelAndView;
	}

	@RequestMapping("/ssadmin/capitaloperationReport")
	public ModelAndView capitaloperationReport() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(request.getParameter("url"));
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		if ((startDate == null || startDate.trim().length() == 0)
				&&(endDate == null || endDate.trim().length() == 0)) {
			Calendar cal_1=Calendar.getInstance();
			//设置为1号,当前日期既为本月第一天 
			cal_1.set(Calendar.DAY_OF_MONTH,1);
			startDate = sdf.format(cal_1.getTime());
			Calendar cale = Calendar.getInstance();
			//设置为1号,当前日期既为本月第一天 
			cale.set(Calendar.DATE, cale.getActualMaximum(Calendar.DATE)); 
			endDate = sdf.format(cale.getTime());
		}

		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		filter.append("and date_format(fLastUpdateTime,'%Y-%m-%d') >='" + startDate + "' \n");
		filter.append("and date_format(fLastUpdateTime,'%Y-%m-%d') <='" + endDate + "' \n");
		filter.append("and ftype=" + request.getParameter("type") + "\n");
		filter.append("and fstatus =" + request.getParameter("status") + "\n");
		List all = this.capitaloperationService.getTotalGroup(filter + "");

		double total = 0;
		JSONArray key =new JSONArray();
		JSONArray value =new JSONArray();
		
		if(all != null && all.size() >0){
			Iterator it = all.iterator();
			while (it.hasNext()) {
				Object[] o = (Object[]) it.next();
				key.add(o[1]);
				value.add(o[0]);
				total = total + Double.valueOf(o[0].toString());
			}
		}
		
		modelAndView.addObject("key", key);
		modelAndView.addObject("value",value);
		modelAndView.addObject("startDate", startDate);
		modelAndView.addObject("endDate", endDate);
		modelAndView.addObject("total", total);
		return modelAndView;
	}

	@RequestMapping("/ssadmin/vcOperationReport")
	public ModelAndView vcOperationReport() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ModelAndView modelAndView = new ModelAndView();
		List<Fvirtualcointype> allType = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		modelAndView.addObject("allType", allType);
		if (request.getParameter("vid") != null) {
			int vid = Integer.parseInt(request.getParameter("vid"));
			Fvirtualcointype coinType = this.virtualCoinService.findById(vid);
			modelAndView.addObject("vid", vid);
			modelAndView.addObject("coinType", coinType.getFname());
		}
		modelAndView.setViewName(request.getParameter("url"));
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		if ((startDate == null || startDate.trim().length() == 0)
				&&(endDate == null || endDate.trim().length() == 0)) {
			Calendar cal_1=Calendar.getInstance();
			//设置为1号,当前日期既为本月第一天 
			cal_1.set(Calendar.DAY_OF_MONTH,1);
			startDate = sdf.format(cal_1.getTime());
			Calendar cale = Calendar.getInstance();
			//设置为1号,当前日期既为本月第一天 
			cale.set(Calendar.DATE, cale.getActualMaximum(Calendar.DATE)); 
			endDate = sdf.format(cale.getTime());
		}

		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		filter.append("and date_format(fLastUpdateTime,'%Y-%m-%d') >='" + startDate + "' \n");
		filter.append("and date_format(fLastUpdateTime,'%Y-%m-%d') <='" + endDate + "' \n");
		filter.append("and ftype=" + request.getParameter("type") + "\n");
		filter.append("and fstatus =" + request.getParameter("status") + "\n");
		filter.append("and fVi_fId2 =" + request.getParameter("vid") + "\n");
		List all = null;

		if (request.getParameter("vid") != null) {
			all = this.virtualCapitaloperationService
					.getTotalGroup(filter + "");
		}

		
		double total = 0;
		JSONArray key =new JSONArray();
		JSONArray value =new JSONArray();
		
		if(all != null && all.size() >0){
			Iterator it = all.iterator();
			while (it.hasNext()) {
				Object[] o = (Object[]) it.next();
				key.add(o[1]);
				value.add(o[0]);
				total = total + Double.valueOf(o[0].toString());
			}
		}

		
		modelAndView.addObject("key", key);
		modelAndView.addObject("value",value);
		modelAndView.addObject("startDate", startDate);
		modelAndView.addObject("endDate", endDate);
		modelAndView.addObject("total", total);
		return modelAndView;
	}
	
	@RequestMapping("/ssadmin/userWalletReport")
	public ModelAndView viewUserWallet() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/userWalletReport") ;

		List<Map<String, Object>> allLists = new ArrayList<Map<String,Object>>();
		// 当前页
		int currentPage = 1;
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		
		String keywords = request.getParameter("keywords");
		StringBuffer sqlString = new StringBuffer();
		sqlString.append("SELECT * from totalView where 1=1 \n");
		if(keywords != null && keywords.trim().length() >0){
			try {
				int userid = Integer.parseInt(keywords);
				sqlString.append(" and fuid="+userid+" \n");
			} catch (Exception e) {
				sqlString.append(" and (floginName like '%"+keywords+"%' or \n");
				sqlString.append(" frealName like '%"+keywords+"%' or \n");
				sqlString.append(" ftelephone like '%"+keywords+"%') \n");
			}
			modelAndView.addObject("keywords", keywords);
		}
		String coin = request.getParameter("coin");
		if(coin != null && coin.trim().length() >0){
			if(!coin.equals("ALL")){
				sqlString.append(" and coinName ='"+coin+"' \n");
			}
			modelAndView.addObject("coin", coin);
		}
		sqlString.append("and (ftotal >0 or ffrozen >0 or regAmt >0 or wAmt >0 or buy >0 or sell >0) \n");
		sqlString.append(" order by fuid desc,ftotal desc,ffrozen desc,regAmt desc,wAmt desc,buy desc,sell desc \n");
		List xx = this.adminService.getSQLList(sqlString.toString());
		int totalNum = xx.size();
		sqlString.append("limit "+(currentPage - 1)* numPerPage+","+numPerPage+" \n");
		
		List all = this.adminService.getSQLList(sqlString.toString());
		if(all != null && all.size() >0){
			for(int i=0;i<all.size();i++){
				Map<String, Object> map = new HashMap<String, Object>();
				Object[] objects = (Object[])all.get(i);
				String uid =objects[0].toString();
				String loginName =objects[1].toString();
				String realName =objects[2]+"";
				String telephone =objects[3]+"";
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
		
		List allList =new ArrayList();
		allList.add("ALL");
		List<Fvirtualcointype> allType = this.virtualCoinService.findAll();
		for (Fvirtualcointype fvirtualcointype : allType) {
			allList.add(fvirtualcointype.getFname());
		}
		modelAndView.addObject("allList", allList);
		
		modelAndView.addObject("userList", allLists);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "viewUserWallet");
		//总数量
		modelAndView.addObject("totalCount",totalNum);
		return modelAndView ;
	}
}
