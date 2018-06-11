package com.ruizton.main.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.CapitalOperationOutStatus;
import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationInStatusEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationOutStatusEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.BTCMessage;
import com.ruizton.main.model.Fvirtualcaptualoperation;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.VirtualCapitaloperationService;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.admin.VirtualWalletService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.util.BTCUtils;
import com.ruizton.util.Utils;
import com.ruizton.util.XlsExport;

@Controller
public class VirtualCapitaloperationController extends BaseController {
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private VirtualWalletService virtualWalletService;
	@Autowired
	private VirtualCapitaloperationService virtualCapitaloperationService;
	@Autowired
	private VirtualCoinService virtualCoinService;
	@Autowired
	private HttpServletRequest request;
	// 每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();

	@RequestMapping("/ssadmin/virtualCaptualoperationList")
	public ModelAndView Index() throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/virtualCaptualoperationList1");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}

		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}
			modelAndView.addObject("ftype", type);
		} else {
			modelAndView.addObject("ftype", 0);
		}
		filterSQL.append("and fuser.fid is not null \n");

		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		} else {
			filterSQL.append("order by fid \n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		} else {
			filterSQL.append("desc \n");
		}

		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getfShortName());
		}
		typeMap.put(0, "ALL");
		modelAndView.addObject("typeMap", typeMap);

		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
				.list((currentPage - 1) * numPerPage, numPerPage, filterSQL.toString(), true);
		modelAndView.addObject("virtualCapitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "virtualCapitaloperationList");
		// 总数量
		modelAndView.addObject("totalCount", this.adminService.getAllCount(
				"Fvirtualcaptualoperation", filterSQL.toString()));
		return modelAndView;
	}

	@RequestMapping("/ssadmin/virtualCapitalInList")
	public ModelAndView virtualCapitalInList() throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/virtualCapitalInList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 and ftype="
				+ VirtualCapitalOperationTypeEnum.COIN_IN + "\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}

		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}
			modelAndView.addObject("ftype", type);
		} else {
			modelAndView.addObject("ftype", 0);
		}

		filterSQL.append("and fuser.fid is not null \n");

		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		}

		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getfShortName());
		}
		typeMap.put(0, "ALL");
		modelAndView.addObject("typeMap", typeMap);

		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
				.list((currentPage - 1) * numPerPage, numPerPage, filterSQL
						+ "", true);
		modelAndView.addObject("virtualCapitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "virtualCapitalInList");
		// 总数量
		modelAndView.addObject("totalCount", this.adminService.getAllCount(
				"Fvirtualcaptualoperation", filterSQL + ""));
		return modelAndView;
	}

	@RequestMapping("/ssadmin/virtualCapitalOutList")
	public ModelAndView virtualCapitalOutList() throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/virtualCapitalOutList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 and ftype="
				+ VirtualCapitalOperationTypeEnum.COIN_OUT + "\n");
		filterSQL.append("and fstatus IN("
				+ VirtualCapitalOperationOutStatusEnum.WaitForOperation + ","
				+ VirtualCapitalOperationOutStatusEnum.OperationLock + ")\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("fuser.fid =" + fid + " or \n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}

		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}
			modelAndView.addObject("ftype", type);
		} else {
			modelAndView.addObject("ftype", 0);
		}
		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		}

		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getfShortName());
		}
		typeMap.put(0, "ALL");
		modelAndView.addObject("typeMap", typeMap);

		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
				.list((currentPage - 1) * numPerPage, numPerPage, filterSQL
						+ "", true);
		modelAndView.addObject("virtualCapitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "virtualCapitalOutList");
		// 总数量
		modelAndView.addObject("totalCount", this.adminService.getAllCount(
				"Fvirtualcaptualoperation", filterSQL + ""));
		return modelAndView;
	}
	
	@RequestMapping("/ssadmin/virtualCapitalOutSucList")
	public ModelAndView virtualCapitalOutSucList() throws Exception {

	

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/virtualCapitalOutSucList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 and ftype="
				+ VirtualCapitalOperationTypeEnum.COIN_OUT + "\n");
		filterSQL.append("and fstatus IN("
				+ VirtualCapitalOperationOutStatusEnum.OperationSuccess +")\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}

		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}
			modelAndView.addObject("ftype", type);
		} else {
			modelAndView.addObject("ftype", 0);
		}
		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		}

		List<Fvirtualcointype> type = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE,1);
		Map typeMap = new HashMap();
		for (Fvirtualcointype fvirtualcointype : type) {
			typeMap.put(fvirtualcointype.getFid(), fvirtualcointype.getfShortName());
		}
		typeMap.put(0, "ALL");
		modelAndView.addObject("typeMap", typeMap);

		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
				.list((currentPage - 1) * numPerPage, numPerPage, filterSQL
						+ "", true);
		modelAndView.addObject("virtualCapitaloperationList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "virtualCapitalOutSucList");
		// 总数量
		modelAndView.addObject("totalCount", this.adminService.getAllCount(
				"Fvirtualcaptualoperation", filterSQL + ""));
		return modelAndView;
	}

	@RequestMapping("ssadmin/goVirtualCapitaloperationChangeStatus")
	public ModelAndView goVirtualCapitaloperationChangeStatus(
			@RequestParam(required = true) int type,
			@RequestParam(required = true) int uid) throws Exception {

		

		ModelAndView modelAndView = new ModelAndView();
		Fvirtualcaptualoperation fvirtualcaptualoperation = this.virtualCapitaloperationService
				.findById(uid);
		fvirtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp());

		int userId = fvirtualcaptualoperation.getFuser().getFid();
		Fvirtualcointype fvirtualcointype = fvirtualcaptualoperation
				.getFvirtualcointype();
		int coinTypeId = fvirtualcointype.getFid();
		List<Fvirtualwallet> virtualWallet = this.virtualWalletService
				.findByTwoProperty("fuser.fid", userId, "fvirtualcointype.fid",
						coinTypeId);
		if (virtualWallet == null || virtualWallet.size() == 0) {
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "Audit failure, membership virtual Coin Wallet information abnormality!");
			return modelAndView;
		}
		Fvirtualwallet virtualWalletInfo = virtualWallet.get(0);

		int status = fvirtualcaptualoperation.getFstatus();
		String tips = "";
		switch (type) {
		case 1:
			tips = "Lock";
			if (status != CapitalOperationOutStatus.WaitForOperation) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				String status_s = CapitalOperationOutStatus
						.getEnumString(CapitalOperationOutStatus.WaitForOperation);
				modelAndView.addObject("message", "Lock failure, only state is:‘" + status_s
						+ "’recharge record is allowed to lock!");
				return modelAndView;
			}
			fvirtualcaptualoperation
					.setFstatus(VirtualCapitalOperationOutStatusEnum.OperationLock);
			break;
		case 2:
			tips = "Unlock";
			if (status != CapitalOperationOutStatus.OperationLock) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				String status_s = CapitalOperationOutStatus
						.getEnumString(CapitalOperationOutStatus.OperationLock);
				modelAndView.addObject("message", "Unlock failure,only state is:‘" + status_s
						+ "’recharge record is allowed to unlock!");
				return modelAndView;
			}
			fvirtualcaptualoperation
					.setFstatus(VirtualCapitalOperationOutStatusEnum.WaitForOperation);
			break;
		case 3:
			tips = "Cancel recharge";
			if (status == CapitalOperationOutStatus.Cancel) {
				modelAndView.setViewName("ssadmin/comm/ajaxDone");
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "Cancel recharge failure,The record is in a state of cancellation!");
				return modelAndView;
			}
			double fee = fvirtualcaptualoperation.getFfees();
			double famount = fvirtualcaptualoperation.getFamount();
			fvirtualcaptualoperation
					.setFstatus(VirtualCapitalOperationOutStatusEnum.Cancel);
			virtualWalletInfo.setFfrozen(virtualWalletInfo.getFfrozen() - fee
					- famount);
			virtualWalletInfo.setFtotal(virtualWalletInfo.getFtotal() + fee
					+ famount);
			virtualWalletInfo.setFlastUpdateTime(Utils.getTimestamp());
			break;
		}

		boolean flag = false;
		try {
			this.virtualCapitaloperationService
					.updateObj(fvirtualcaptualoperation);
			this.virtualWalletService.updateObj(virtualWalletInfo);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (flag) {
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "Successfully Locked");
		} else {
			modelAndView.setViewName("ssadmin/comm/ajaxDone");
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "Unknown error, please refresh the list and try again!");
		}

		return modelAndView;
	}

	@RequestMapping("ssadmin/goVirtualCapitaloperationJSP")
	public ModelAndView goVirtualCapitaloperationJSP() throws Exception {

		

		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(url);
		String xid = "";
		BTCMessage msg = new BTCMessage();
		if (request.getParameter("uid") != null) {
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fvirtualcaptualoperation virtualcaptualoperation = this.virtualCapitaloperationService
					.findById(fid);
			xid = virtualcaptualoperation.getFtradeUniqueNumber();
			msg.setACCESS_KEY(virtualcaptualoperation.getFvirtualcointype()
					.getFaccess_key());
			msg.setIP(virtualcaptualoperation.getFvirtualcointype().getFip());
			msg.setPORT(virtualcaptualoperation.getFvirtualcointype()
					.getFport());
			msg.setSECRET_KEY(virtualcaptualoperation.getFvirtualcointype()
					.getFsecrt_key());
			modelAndView.addObject("virtualCapitaloperation",
					virtualcaptualoperation);
		}
		return modelAndView;
	}

	@RequestMapping("ssadmin/virtualCapitalOutAudit")
	public ModelAndView virtualCapitalOutAudit() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fvirtualcaptualoperation virtualcaptualoperation = this.virtualCapitaloperationService.findById(fid);
		int status = virtualcaptualoperation.getFstatus();
		if (status != VirtualCapitalOperationOutStatusEnum.OperationLock) {
			modelAndView.addObject("statusCode", 300);
			String status_s = VirtualCapitalOperationOutStatusEnum
					.getEnumString(VirtualCapitalOperationOutStatusEnum.OperationLock);
			modelAndView.addObject("message", "The approve failed, only the state is:" + status_s
					+ "recharge record is allowed to be approved.");
			return modelAndView;
		}
		// 根据用户查钱包最后修改时间
		int userId = virtualcaptualoperation.getFuser().getFid();
		Fvirtualcointype fvirtualcointype = virtualcaptualoperation
				.getFvirtualcointype();
		int coinTypeId = fvirtualcointype.getFid();

		Fvirtualwallet virtualWalletInfo = frontUserService.findVirtualWalletByUser(userId, coinTypeId);
		double amount = Utils.getDouble(virtualcaptualoperation.getFamount()+virtualcaptualoperation.getFfees(), 4);
		double frozenRmb = Utils.getDouble(virtualWalletInfo.getFfrozen(), 4);
		if (frozenRmb-amount < -0.0001) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "approve failed,Freezing quantity:" + frozenRmb
					+ "Less than the amount of recharge:" + amount + "，Abnormal operation!");
			return modelAndView;
		}

		if(fvirtualcointype.isFisautosend()){
			BTCMessage btcMsg = new BTCMessage();
			btcMsg.setACCESS_KEY(fvirtualcointype.getFaccess_key());
			btcMsg.setSECRET_KEY(fvirtualcointype.getFsecrt_key());
			btcMsg.setIP(fvirtualcointype.getFip());
			btcMsg.setPASSWORD(fvirtualcointype.getFpassword());
			btcMsg.setPORT(fvirtualcointype.getFport());
			BTCUtils btcUtils = new BTCUtils(btcMsg);

			try {
				if (!fvirtualcointype.isFisEth()) {
					double balance = btcUtils.getbalanceValue();
					if (balance < amount+0.1d) {
						modelAndView.addObject("statusCode", 300);
						modelAndView.addObject("message", "approve failed，wallet balance：" + balance + "less than"
								+ amount);
						return modelAndView;
					}
					
					boolean isTrue = btcUtils.validateaddress(virtualcaptualoperation.getWithdraw_virtual_address());
					if(!isTrue){
						modelAndView.addObject("statusCode", 300);
						modelAndView.addObject("message", "Invalid address");
						return modelAndView;
					}
				}
				
				
			} catch (Exception e1) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "approve failed，Wallet connection failure");
				return modelAndView;
			}
		}
		
        if(virtualcaptualoperation.getFtradeUniqueNumber() != null &&
        		virtualcaptualoperation.getFtradeUniqueNumber().trim().length() >0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "Illegal operation! Check your wallet, please!");
			return modelAndView;
        }
		
		// 充值操作
//		virtualcaptualoperation
//				.setFstatus(VirtualCapitalOperationOutStatusEnum.OperationSuccess);
		virtualcaptualoperation.setFlastUpdateTime(Utils.getTimestamp());

		// 钱包操作
		virtualWalletInfo.setFlastUpdateTime(Utils.getTimestamp());
		virtualWalletInfo.setFfrozen(virtualWalletInfo.getFfrozen() - amount);
		try {
			this.virtualCapitaloperationService.updateCapital(
					virtualcaptualoperation, virtualWalletInfo,fvirtualcointype);
		} catch (Exception e) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", e.getMessage());
			return modelAndView;
		}
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "Approve Success");
		modelAndView.addObject("callbackType", "closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/auditVCInlog")
	public ModelAndView auditVCInlog() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fvirtualcaptualoperation virtualcaptualoperation = this.virtualCapitaloperationService.findById(fid);
		int status = virtualcaptualoperation.getFstatus();
		int type = virtualcaptualoperation.getFtype();
		if (status == VirtualCapitalOperationInStatusEnum.SUCCESS || type != VirtualCapitalOperationTypeEnum.COIN_IN) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "Network anomaly");
			return modelAndView;
		}
		
		// 根据用户查钱包最后修改时间
		int userId = virtualcaptualoperation.getFuser().getFid();
		Fvirtualcointype fvirtualcointype = virtualcaptualoperation
				.getFvirtualcointype();
		int coinTypeId = fvirtualcointype.getFid();

		Fvirtualwallet virtualWalletInfo = frontUserService.findVirtualWalletByUser(userId, coinTypeId);
		// 钱包操作
		virtualWalletInfo.setFlastUpdateTime(Utils.getTimestamp());
		virtualWalletInfo.setFtotal(virtualWalletInfo.getFtotal()+virtualcaptualoperation.getFamount());
		
		
		virtualcaptualoperation.setFstatus(VirtualCapitalOperationInStatusEnum.SUCCESS);
		try {
			this.virtualCapitaloperationService.updateCapital(
					virtualcaptualoperation, virtualWalletInfo);
		} catch (Exception e) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "Network anomaly");
			return modelAndView;
		}
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "Approve Success");
		return modelAndView;
	}
	
	private static enum Export1Filed {
		订单ID,会员UID,会员登陆名, 会员昵称, 会员真实姓名, 虚拟币类型,充值地址,交易ID,确认数,状态,交易类型,数量,手续费,创建时间,修改时间;
	}
	private static enum Export2Filed {
		订单ID,会员UID,会员登陆名, 会员昵称, 会员真实姓名, 虚拟币类型,提现地址,交易ID,状态,交易类型,数量,手续费,创建时间,修改时间;
	}
	private static enum Export3Filed {
		订单ID,会员UID,会员登陆名, 会员昵称, 会员真实姓名, 虚拟币类型,提现地址,充值地址,交易ID,状态,交易类型,数量,手续费,创建时间,修改时间;
	}
	
	public List<Fvirtualcaptualoperation> getInList() throws Exception {
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 and ftype="
				+ VirtualCapitalOperationTypeEnum.COIN_IN + "\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
			}
		}

		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}
		}

		filterSQL.append("and fuser.fid is not null \n");

		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		}

		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService.list(0, 0, filterSQL.toString(), false);

		return list;
	}
	
	public List<Fvirtualcaptualoperation> getAllList() throws Exception {
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
			}
		}

		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}
		}
		filterSQL.append("and fuser.fid is not null \n");

		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		} else {
			filterSQL.append("order by fid \n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		} else {
			filterSQL.append("desc \n");
		}


		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
				.list(0, 0, filterSQL.toString(), false);

		return list;
	}

	public List<Fvirtualcaptualoperation> getOutList() throws Exception {

		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");

		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 and ftype="
				+ VirtualCapitalOperationTypeEnum.COIN_OUT + "\n");
		filterSQL.append("and fstatus IN("
				+ VirtualCapitalOperationOutStatusEnum.WaitForOperation + ","
				+ VirtualCapitalOperationOutStatusEnum.OperationLock + ")\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("fuser.fid =" + fid + " or \n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
			}
		}

		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}
		}
		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		}

		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
				.list(0, 0, filterSQL
						+ "", false);
		return list;
	}
	
	public List<Fvirtualcaptualoperation> getOutSucList() throws Exception {
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		StringBuffer filterSQL = new StringBuffer();
		filterSQL.append("where 1=1 and ftype="
				+ VirtualCapitalOperationTypeEnum.COIN_OUT + "\n");
		filterSQL.append("and fstatus IN("
				+ VirtualCapitalOperationOutStatusEnum.OperationSuccess +")\n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filterSQL.append("fuser.fid =" + fid + " or \n");
			} catch (Exception e) {
				filterSQL.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filterSQL.append("fuser.frealName like '%" + keyWord + "%' OR \n");
				filterSQL.append("withdraw_virtual_address like '%" + keyWord
						+ "%' OR \n");
				filterSQL.append("recharge_virtual_address like '%" + keyWord
						+ "%' )\n");
			}
		}

		if (request.getParameter("ftype") != null) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filterSQL.append("and fvirtualcointype.fid=" + type + "\n");
			}
		}
		if (orderField != null && orderField.trim().length() > 0) {
			filterSQL.append("order by " + orderField + "\n");
		}
		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filterSQL.append(orderDirection + "\n");
		}


		List<Fvirtualcaptualoperation> list = this.virtualCapitaloperationService
				.list(0, 0, filterSQL.toString(), false);

		return list;
	}

	@RequestMapping("ssadmin/virtualCapitalInListExport.html")
	public ModelAndView virtualCapitalInListExport(HttpServletResponse response)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=virtualCapitalInList.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (Export1Filed filed : Export1Filed.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}

		List<Fvirtualcaptualoperation> alls = getInList();
		for (Fvirtualcaptualoperation virtualcaptualoperation : alls) {
			e.createRow(rowIndex++);
			for (Export1Filed filed : Export1Filed.values()) {
				switch (filed) {
				case 订单ID:
						e.setCell(filed.ordinal(), virtualcaptualoperation.getFid());
					break;
				case 会员UID:
					if (virtualcaptualoperation.getFuser() != null)
						e.setCell(filed.ordinal(), virtualcaptualoperation.getFuser().getFid());
					break;
				case 会员登陆名:
					if (virtualcaptualoperation.getFuser() != null)
						e.setCell(filed.ordinal(), virtualcaptualoperation.getFuser()
								.getFloginName());
					break;
				case 会员昵称:
					if (virtualcaptualoperation.getFuser() != null)
						e.setCell(filed.ordinal(), virtualcaptualoperation.getFuser()
								.getFnickName());
					break;
				case 会员真实姓名:
					if (virtualcaptualoperation.getFuser() != null)
						e.setCell(filed.ordinal(), virtualcaptualoperation.getFuser()
								.getFrealName());
					break;
				case 虚拟币类型:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFvirtualcointype().getFname());
					break;
				case 确认数:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFconfirmations());
					break;
				case 状态:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFstatus_s());
					break;
				case 交易类型:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFtype_s());
					break;
				case 充值地址:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getRecharge_virtual_address());
					break;
				case 交易ID:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFtradeUniqueNumber());
					break;	
				case 数量:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFamount());
					break;
				case 手续费:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFfees());
					break;
				case 修改时间:
					e.setCell(filed.ordinal(),virtualcaptualoperation.getFlastUpdateTime());
					break;
				case 创建时间:
					e.setCell(filed.ordinal(),virtualcaptualoperation.getFcreateTime());
					break;		
				default:
					break;
				}
			}
		}

		e.exportXls(response);
		response.getOutputStream().close();

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "Export Success");
		return modelAndView;
	}
	

	@RequestMapping("ssadmin/virtualCapitalOutSucListExport.html")
	public ModelAndView virtualCapitalOutSucListExport(HttpServletResponse response)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=virtualCapitalOutSucList.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (Export2Filed filed : Export2Filed.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}

		List<Fvirtualcaptualoperation> alls = getOutSucList();
		for (Fvirtualcaptualoperation virtualcaptualoperation : alls) {
			e.createRow(rowIndex++);
			for (Export2Filed filed : Export2Filed.values()) {
				switch (filed) {
				case 订单ID:
						e.setCell(filed.ordinal(), virtualcaptualoperation.getFid());
					break;
				case 会员UID:
					if (virtualcaptualoperation.getFuser() != null)
						e.setCell(filed.ordinal(), virtualcaptualoperation.getFuser().getFid());
					break;
				case 会员登陆名:
					if (virtualcaptualoperation.getFuser() != null)
						e.setCell(filed.ordinal(), virtualcaptualoperation.getFuser()
								.getFloginName());
					break;
				case 会员昵称:
					if (virtualcaptualoperation.getFuser() != null)
						e.setCell(filed.ordinal(), virtualcaptualoperation.getFuser()
								.getFnickName());
					break;
				case 会员真实姓名:
					if (virtualcaptualoperation.getFuser() != null)
						e.setCell(filed.ordinal(), virtualcaptualoperation.getFuser()
								.getFrealName());
					break;
				case 虚拟币类型:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFvirtualcointype().getFname());
					break;
				case 提现地址:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getWithdraw_virtual_address());
					break;
				case 交易ID:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFtradeUniqueNumber());
					break;	
				case 状态:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFstatus_s());
					break;
				case 交易类型:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFtype_s());
					break;
				case 数量:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFamount());
					break;
				case 手续费:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFfees());
					break;
				case 修改时间:
					e.setCell(filed.ordinal(),virtualcaptualoperation.getFlastUpdateTime());
					break;
				case 创建时间:
					e.setCell(filed.ordinal(),virtualcaptualoperation.getFcreateTime());
					break;		
				default:
					break;
				}
			}
		}

		e.exportXls(response);
		response.getOutputStream().close();

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "Export Success");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/virtualCapitalOutListExport.html")
	public ModelAndView virtualCapitalOutListExport(HttpServletResponse response)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=virtualCapitalOutList.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (Export2Filed filed : Export2Filed.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}

		List<Fvirtualcaptualoperation> alls = getOutList();
		for (Fvirtualcaptualoperation virtualcaptualoperation : alls) {
			e.createRow(rowIndex++);
			for (Export2Filed filed : Export2Filed.values()) {
				switch (filed) {
				case 订单ID:
						e.setCell(filed.ordinal(), virtualcaptualoperation.getFid());
					break;
				case 会员UID:
					if (virtualcaptualoperation.getFuser() != null)
						e.setCell(filed.ordinal(), virtualcaptualoperation.getFuser().getFid());
					break;
				case 会员登陆名:
					if (virtualcaptualoperation.getFuser() != null)
						e.setCell(filed.ordinal(), virtualcaptualoperation.getFuser()
								.getFloginName());
					break;
				case 会员昵称:
					if (virtualcaptualoperation.getFuser() != null)
						e.setCell(filed.ordinal(), virtualcaptualoperation.getFuser()
								.getFnickName());
					break;
				case 会员真实姓名:
					if (virtualcaptualoperation.getFuser() != null)
						e.setCell(filed.ordinal(), virtualcaptualoperation.getFuser()
								.getFrealName());
					break;
				case 虚拟币类型:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFvirtualcointype().getFname());
					break;
				case 提现地址:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getWithdraw_virtual_address());
					break;
				case 状态:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFstatus_s());
					break;
				case 交易类型:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFtype_s());
					break;
				case 数量:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFamount());
					break;
				case 手续费:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFfees());
					break;
				case 修改时间:
					e.setCell(filed.ordinal(),virtualcaptualoperation.getFlastUpdateTime());
					break;
				case 创建时间:
					e.setCell(filed.ordinal(),virtualcaptualoperation.getFcreateTime());
					break;		
				default:
					break;
				}
			}
		}

		e.exportXls(response);
		response.getOutputStream().close();

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "Export Success");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/virtualCapitalAllListExport.html")
	public ModelAndView virtualCapitalAllListExport(HttpServletResponse response)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=virtualCapitalAllList.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (Export3Filed filed : Export3Filed.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}

		List<Fvirtualcaptualoperation> alls = getAllList();
		for (Fvirtualcaptualoperation virtualcaptualoperation : alls) {
			e.createRow(rowIndex++);
			for (Export3Filed filed : Export3Filed.values()) {
				switch (filed) {
				case 订单ID:
						e.setCell(filed.ordinal(), virtualcaptualoperation.getFid());
					break;
				case 会员UID:
					if (virtualcaptualoperation.getFuser() != null)
						e.setCell(filed.ordinal(), virtualcaptualoperation.getFuser().getFid());
					break;
				case 会员登陆名:
					if (virtualcaptualoperation.getFuser() != null)
						e.setCell(filed.ordinal(), virtualcaptualoperation.getFuser()
								.getFloginName());
					break;
				case 会员昵称:
					if (virtualcaptualoperation.getFuser() != null)
						e.setCell(filed.ordinal(), virtualcaptualoperation.getFuser()
								.getFnickName());
					break;
				case 会员真实姓名:
					if (virtualcaptualoperation.getFuser() != null)
						e.setCell(filed.ordinal(), virtualcaptualoperation.getFuser()
								.getFrealName());
					break;
				case 虚拟币类型:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFvirtualcointype().getFname());
					break;
				case 提现地址:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getWithdraw_virtual_address());
					break;
				case 充值地址:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getRecharge_virtual_address());
					break;
				case 交易ID:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFtradeUniqueNumber());
					break;	
				case 状态:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFstatus_s());
					break;
				case 交易类型:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFtype_s());
					break;
				case 数量:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFamount());
					break;
				case 手续费:
					e.setCell(filed.ordinal(), virtualcaptualoperation.getFfees());
					break;
				case 修改时间:
					e.setCell(filed.ordinal(),virtualcaptualoperation.getFlastUpdateTime());
					break;
				case 创建时间:
					e.setCell(filed.ordinal(),virtualcaptualoperation.getFcreateTime());
					break;		
				default:
					break;
				}
			}
		}

		e.exportXls(response);
		response.getOutputStream().close();

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "Export Success");
		return modelAndView;
	}
	
	
	@RequestMapping("ssadmin/virtualSucOp")
	public ModelAndView virtualSucOp() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int uid = Integer.parseInt(request.getParameter("uid"));
		Fvirtualcaptualoperation ca = this.virtualCapitaloperationService.findById(uid);
		if(ca.getFtradeUniqueNumber() != null && ca.getFtradeUniqueNumber().trim().length() >0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "Do not repeat the coin");
			return modelAndView;
		}
		ca.setFisaudit(false);
		this.virtualCapitaloperationService.updateObj(ca);


		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "Success");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/updateVcTradeNumber")
	public ModelAndView updateVcTradeNumber() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		
		try {
			int fid = Integer.parseInt(request.getParameter("fid"));
			Fvirtualcaptualoperation vc = this.virtualCapitaloperationService.findById(fid);
			if(vc == null){
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","Records do not exist");
				return modelAndView;
			}
			if(vc.getFtype() != 2 && vc.getFstatus() !=3){
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","Only the record of success is allowed to make up for Trade ID");
				return modelAndView;
			}
			
			if(vc.getFtradeUniqueNumber() != null && vc.getFtradeUniqueNumber().trim().length() >0){
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","Record the existing transaction ID, not allowed to cover");
				return modelAndView;
			}
			vc.setFtradeUniqueNumber(request.getParameter("ftradeUniqueNumber"));
			this.virtualCapitaloperationService.updateObj(vc);
		} catch (Exception e) {
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","Network anomaly");
			return modelAndView;
		}
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Success");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
}
