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

import com.ruizton.main.Enum.IdentityTypeEnum;
import com.ruizton.main.Enum.RegTypeEnum;
import com.ruizton.main.Enum.UserGradeEnum;
import com.ruizton.main.Enum.UserStatusEnum;
import com.ruizton.main.Enum.UserTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fcapitaloperation;
import com.ruizton.main.model.Fintrolinfo;
import com.ruizton.main.model.Fscore;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fusersetting;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.CapitaloperationService;
import com.ruizton.main.service.admin.ScoreService;
import com.ruizton.main.service.admin.SystemArgsService;
import com.ruizton.main.service.admin.UserService;
import com.ruizton.main.service.admin.UsersettingService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.util.Utils;
import com.ruizton.util.XlsExport;

@Controller
public class UserController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private CapitaloperationService capitaloperationService;
	@Autowired
	private UsersettingService usersettingService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private SystemArgsService systemArgsService;
	
	// 每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();

	@RequestMapping("/ssadmin/userList")
	public ModelAndView Index() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/userList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String uid = request.getParameter("uid");
		String startDate = request.getParameter("startDate");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (floginName like '%" + keyWord + "%' or \n");
				filter.append("fnickName like '%" + keyWord + "%'  or \n");
				filter.append("frealName like '%" + keyWord + "%'  or \n");
				filter.append("ftelephone like '%" + keyWord + "%'  or \n");
				filter.append("femail like '%" + keyWord + "%'  or \n");
				filter.append("fidentityNo like '%" + keyWord + "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}

		Map<Integer,String> typeMap = new HashMap<Integer,String>();
		typeMap.put(0, "All");
		typeMap.put(UserStatusEnum.NORMAL_VALUE,
				UserStatusEnum.getEnumString(UserStatusEnum.NORMAL_VALUE));
		typeMap.put(UserStatusEnum.FORBBIN_VALUE,
				UserStatusEnum.getEnumString(UserStatusEnum.FORBBIN_VALUE));
		modelAndView.addObject("typeMap", typeMap);

		if (request.getParameter("ftype") != null
				&& request.getParameter("ftype").trim().length() > 0) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filter.append("and fstatus=" + request.getParameter("ftype")
						+ " \n");
			}
			modelAndView.addObject("ftype", request.getParameter("ftype"));
		}

		try {
			if (request.getParameter("troUid") != null
					&& request.getParameter("troUid").trim().length() > 0) {
				int troUid = Integer.parseInt(request.getParameter("troUid"));
				filter.append("and fIntroUser_id.fid=" + troUid + " \n");
				modelAndView.addObject("troUid", troUid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (request.getParameter("troNo") != null
					&& request.getParameter("troNo").trim().length() > 0) {
				String troNo = request.getParameter("troNo").trim();
				filter.append("and fuserNo like '%" + troNo + "%' \n");
				modelAndView.addObject("troNo", troNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}

		List<Fuser> list = this.userService.list(
				(currentPage - 1) * numPerPage, numPerPage, filter + "", true);
		modelAndView.addObject("startDate", startDate);
		modelAndView.addObject("userList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "listUser");
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fuser", filter + ""));
		return modelAndView;
	}
	
	@RequestMapping("/ssadmin/viewUser1")
	public ModelAndView viewUser1() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/viewUser1");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			filter.append("and (floginName like '%" + keyWord + "%' or \n");
			filter.append("fnickName like '%" + keyWord + "%'  or \n");
			filter.append("frealName like '%" + keyWord + "%'  or \n");
			filter.append("ftelephone like '%" + keyWord + "%'  or \n");
			filter.append("femail like '%" + keyWord + "%'  or \n");
			filter.append("fidentityNo like '%" + keyWord + "%' )\n");
			modelAndView.addObject("keywords", keyWord);
		}

		Map typeMap = new HashMap();
		typeMap.put(0, "全部");
		typeMap.put(UserStatusEnum.NORMAL_VALUE,
				UserStatusEnum.getEnumString(UserStatusEnum.NORMAL_VALUE));
		typeMap.put(UserStatusEnum.FORBBIN_VALUE,
				UserStatusEnum.getEnumString(UserStatusEnum.FORBBIN_VALUE));
		modelAndView.addObject("typeMap", typeMap);

		if (request.getParameter("ftype") != null
				&& request.getParameter("ftype").trim().length() > 0) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filter.append("and fstatus=" + request.getParameter("ftype")
						+ " \n");
			}
			modelAndView.addObject("ftype", request.getParameter("ftype"));
		}

		try {
			if (request.getParameter("troUid") != null
					&& request.getParameter("troUid").trim().length() > 0) {
				int troUid = Integer.parseInt(request.getParameter("troUid"));
				filter.append("and fIntroUser_id.fid=" + troUid + " \n");
				modelAndView.addObject("troUid", troUid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(request.getParameter("cid") != null){
			int cid =Integer.parseInt(request.getParameter("cid"));
			Fcapitaloperation c = this.capitaloperationService.findById(cid);
			filter.append("and fid ="+c.getFuser().getFid()+" \n");
			modelAndView.addObject("cid",cid);
		}

		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by fregisterTime \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}

		List<Fuser> list = this.userService.list(
				(currentPage - 1) * numPerPage, numPerPage, filter + "", true);
		modelAndView.addObject("userList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "viewUser1");
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fuser", filter + ""));
		return modelAndView;
	}

	
	@RequestMapping("/ssadmin/userLookup")
	public ModelAndView userLookup() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/userLookup");
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
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (floginName like '%" + keyWord + "%' or \n");
				filter.append("fnickName like '%" + keyWord + "%'  or \n");
				filter.append("frealName like '%" + keyWord + "%'  or \n");
				filter.append("ftelephone like '%" + keyWord + "%'  or \n");
				filter.append("femail like '%" + keyWord + "%'  or \n");
				filter.append("fidentityNo like '%" + keyWord + "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		
		List<Fuser> list = this.userService.list(
				(currentPage - 1) * numPerPage, numPerPage, filter + "", true);
		modelAndView.addObject("userList1", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "operationLogList");
		modelAndView.addObject("currentPage", currentPage);
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fuser", filter + ""));
		return modelAndView;
	}

	@RequestMapping("/ssadmin/userAuditList")
	public ModelAndView userAuditList() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/userAuditList");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (floginName like '%" + keyWord + "%' or \n");
				filter.append("fnickName like '%" + keyWord + "%'  or \n");
				filter.append("frealName like '%" + keyWord + "%'  or \n");
				filter.append("ftelephone like '%" + keyWord + "%'  or \n");
				filter.append("femail like '%" + keyWord + "%'  or \n");
				filter.append("fidentityNo like '%" + keyWord + "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		filter.append("and fpostRealValidate=1 and fhasRealValidate=0 \n");

		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}
		List<Fuser> list = this.userService.listUserForAudit((currentPage - 1)
				* numPerPage, numPerPage, filter + "", true);
		modelAndView.addObject("userList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "listUser");
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fuser", filter + ""));
		return modelAndView;
	}
	
	@RequestMapping("/ssadmin/userAuditList1")
	public ModelAndView userAuditList1() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/userAuditList1");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (floginName like '%" + keyWord + "%' or \n");
				filter.append("fnickName like '%" + keyWord + "%'  or \n");
				filter.append("frealName like '%" + keyWord + "%'  or \n");
				filter.append("ftelephone like '%" + keyWord + "%'  or \n");
				filter.append("femail like '%" + keyWord + "%'  or \n");
				filter.append("fidentityNo like '%" + keyWord + "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		filter.append("and fpostImgValidate=1 and fhasImgValidate=0 \n");

		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}
		List<Fuser> list = this.userService.listUserForAudit((currentPage - 1)
				* numPerPage, numPerPage, filter + "", true);
		modelAndView.addObject("userList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "listUser");
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fuser", filter + ""));
		return modelAndView;
	}
	
	@RequestMapping("/ssadmin/userAuditList2")
	public ModelAndView userAuditList2() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/userAuditList2");
		// 当前页
		int currentPage = 1;
		// 搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		if (request.getParameter("pageNum") != null) {
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (floginName like '%" + keyWord + "%' or \n");
				filter.append("fnickName like '%" + keyWord + "%'  or \n");
				filter.append("frealName like '%" + keyWord + "%'  or \n");
				filter.append("ftelephone like '%" + keyWord + "%'  or \n");
				filter.append("femail like '%" + keyWord + "%'  or \n");
				filter.append("fidentityNo like '%" + keyWord + "%' )\n");
			}
			modelAndView.addObject("keywords", keyWord);
		}
		filter.append("and fhasVideoValidate=0 \n");

		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}
		List<Fuser> list = this.userService.listUserForAudit((currentPage - 1)
				* numPerPage, numPerPage, filter + "", true);
		modelAndView.addObject("userList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "listUser");
		// 总数量
		modelAndView.addObject("totalCount",
				this.adminService.getAllCount("Fuser", filter + ""));
		return modelAndView;
	}

	@RequestMapping("/ssadmin/ajaxDone")
	public ModelAndView ajaxDone() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		return modelAndView;
	}

	@RequestMapping("/ssadmin/userForbbin")
	public ModelAndView userForbbin() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		// modelAndView.setViewName("redirect:/pages/ssadmin/comm/ajaxDone.jsp")
		// ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		int status = Integer.parseInt(request.getParameter("status"));
		Fuser user = this.userService.findById(fid);
		if (status == 1) {
			if (user.getFstatus() == UserStatusEnum.FORBBIN_VALUE) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "The member has been disable and does not need to do this.");
				return modelAndView;
			}
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "Disable Success.");
			user.setFstatus(UserStatusEnum.FORBBIN_VALUE);
		} else if (status == 2) {
			if (user.getFstatus() == UserStatusEnum.NORMAL_VALUE) {
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "The member state is normal, no need to do this operation.");
				return modelAndView;
			}
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "Enable success.");
			user.setFstatus(UserStatusEnum.NORMAL_VALUE);
		} else if (status == 3) {// 重设登陆密码
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "Reset the login password to succeed,password:ABC123");
			user.setFloginPassword(Utils.MD5("ABC123",user.getSalt()));
		} else if (status == 4) {// 重设交易密码
			modelAndView.addObject("statusCode", 200);
			modelAndView.addObject("message", "Reset the transaction password success.");
			user.setFtradePassword(null);
		}

		this.userService.updateObj(user);
		return modelAndView;
	}

	@RequestMapping("/ssadmin/auditUser")
	public ModelAndView auditUser() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int status = Integer.parseInt(request.getParameter("status"));
		int fid = Integer.parseInt(request.getParameter("uid"));

		Fuser user = this.userService.findById(fid);
		Fscore fscore = user.getFscore();
		Fuser fintrolUser = null;
		Fintrolinfo introlInfo = null;
		Fvirtualwallet fvirtualwallet = null;
		String[] auditSendCoin = this.systemArgsService.getValue("auditSendCoin").split("#");
		int coinID = Integer.parseInt(auditSendCoin[0]);
		double coinQty = Double.valueOf(auditSendCoin[1]);
		if (status == 1) {
			if(!user.getFhasRealValidate()){
				if(!fscore.isFissend() && user.getfIntroUser_id() != null){
					fintrolUser = this.userService.findById(user.getfIntroUser_id().getFid());
					fintrolUser.setfInvalidateIntroCount(fintrolUser.getfInvalidateIntroCount()+1);
					fscore.setFissend(true);
				}
				if(coinQty >0){
					fvirtualwallet = this.frontUserService.findVirtualWalletByUser(user.getFid(), coinID);
					fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+coinQty);
					introlInfo = new Fintrolinfo();
					introlInfo.setFcreatetime(Utils.getTimestamp());
					introlInfo.setFiscny(false);
					introlInfo.setFqty(coinQty);
					introlInfo.setFuser(user);
					introlInfo.setFname(fvirtualwallet.getFvirtualcointype().getFname());
					introlInfo.setFtitle("实名认证成功，奖励"+fvirtualwallet.getFvirtualcointype().getFname()+coinQty+"个！");
				}
			}
			user.setFhasRealValidateTime(Utils.getTimestamp());
			user.setFhasRealValidate(true);
			user.setFisValid(true);
		} else {
			user.setFhasRealValidate(false);
			user.setFpostRealValidate(false);
			user.setFidentityNo(null);
			user.setFrealName(null);
		}
		try {
			this.userService.updateObj(user,fscore,fintrolUser,fvirtualwallet,introlInfo);
		} catch (Exception e) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "Network anomaly");
			return modelAndView;
		}
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "Approve success");

		return modelAndView;
	}
	

	@RequestMapping("/ssadmin/auditUser1")
	public ModelAndView auditUser1() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int status = Integer.parseInt(request.getParameter("status"));
		int fid = Integer.parseInt(request.getParameter("uid"));

		Fuser user = this.userService.findById(fid);
		if (status == 1) {
			user.setFhasImgValidateTime(Utils.getTimestamp());
			user.setFhasImgValidate(true);
		} else {
			user.setFhasImgValidateTime(null);
			user.setFhasImgValidate(false);
			user.setFpostImgValidate(false);
			user.setFpostImgValidateTime(null);
			user.setfIdentityPath(null);
			user.setfIdentityPath2(null);
			user.setfIdentityPath3(null);
		}
		try {
			this.userService.updateObj(user);
		} catch (Exception e) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "Network anomaly");
			return modelAndView;
		}
		
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "Approve success");

		return modelAndView;
	}
	
	
	@RequestMapping("/ssadmin/auditUser2")
	public ModelAndView auditUser2() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int status = Integer.parseInt(request.getParameter("status"));
		int fid = Integer.parseInt(request.getParameter("uid"));

		Fuser user = this.userService.findById(fid);
		if (status == 1) {
			user.setFhasVideoValidate(true);
			user.setFhasVideoValidateTime(Utils.getTimestamp());
		} else {
			user.setFhasVideoValidate(false);
			user.setFhasVideoValidateTime(null);
		}
		try {
			this.userService.updateObj(user);
		} catch (Exception e) {
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "Network anomaly");
			return modelAndView;
		}
		
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "Approve success");

		return modelAndView;
	}

	@RequestMapping("ssadmin/goUserJSP")
	public ModelAndView goUserJSP() throws Exception {
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(url);
		if (request.getParameter("uid") != null) {
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fuser user = this.userService.findById(fid);
			modelAndView.addObject("fuser", user);
			
			List<Fusersetting> alls = this.usersettingService.list(0, 0, "where fuser.fid="+fid, false);
			Fusersetting usersetting = alls.get(0);
			modelAndView.addObject("usersetting", usersetting);

			Map<Integer,String> map = new HashMap<Integer,String>();
			map.put(IdentityTypeEnum.SHENFENZHENG, IdentityTypeEnum
					.getEnumString(IdentityTypeEnum.SHENFENZHENG));
			map.put(IdentityTypeEnum.JUNGUANGZHEN, IdentityTypeEnum
					.getEnumString(IdentityTypeEnum.JUNGUANGZHEN));
			map.put(IdentityTypeEnum.HUZHAO,
					IdentityTypeEnum.getEnumString(IdentityTypeEnum.HUZHAO));
			map.put(IdentityTypeEnum.TAIWAN,
					IdentityTypeEnum.getEnumString(IdentityTypeEnum.TAIWAN));
			map.put(IdentityTypeEnum.GANGAO,
					IdentityTypeEnum.getEnumString(IdentityTypeEnum.GANGAO));
			modelAndView.addObject("identityTypeMap", map);
		}
		
		Map<Integer,String> map = new HashMap<Integer,String>();
		map.put(UserGradeEnum.LEVEL0, UserGradeEnum.getEnumString(UserGradeEnum.LEVEL0));
		map.put(UserGradeEnum.LEVEL1, UserGradeEnum.getEnumString(UserGradeEnum.LEVEL1));
		map.put(UserGradeEnum.LEVEL2, UserGradeEnum.getEnumString(UserGradeEnum.LEVEL2));
		map.put(UserGradeEnum.LEVEL3, UserGradeEnum.getEnumString(UserGradeEnum.LEVEL3));
		map.put(UserGradeEnum.LEVEL4, UserGradeEnum.getEnumString(UserGradeEnum.LEVEL4));
		map.put(UserGradeEnum.LEVEL5, UserGradeEnum.getEnumString(UserGradeEnum.LEVEL5));
		modelAndView.addObject("typeMap", map);
		
		
		Map<Integer,String> userType = new HashMap<Integer,String>();
		for(int i=0;i<=4;i++){
			userType.put(i, UserTypeEnum.getEnumString(i));
		}
		modelAndView.addObject("userType", userType);
		return modelAndView;
	}

	@RequestMapping("ssadmin/updateUserLevel")
	public ModelAndView updateUserLevel() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		int fid = Integer.parseInt(request.getParameter("fid"));
		Fuser user = this.userService.findById(fid);
		Fscore score = user.getFscore();
		int newLevel = Integer.parseInt(request.getParameter("newLevel"));
		score.setFlevel(newLevel);
		this.scoreService.updateObj(score);

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "Modify success");
		return modelAndView;
	}

	@RequestMapping("ssadmin/updateIntroCount")
	public ModelAndView updateIntroCount(
			@RequestParam(required = true) int fInvalidateIntroCount)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fuser user = this.userService.findById(fid);
		user.setfInvalidateIntroCount(fInvalidateIntroCount);
		this.userService.updateObj(user);

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "Modify success");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/updateUserGrade")
	public ModelAndView updateUserGrade()
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fuser user = this.userService.findById(fid);
		Fscore fscore = user.getFscore();
		fscore.setFlevel(Integer.parseInt(request.getParameter("fuserGrade")));
		this.scoreService.updateObj(fscore);

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "Modify success");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/updateIntroPerson")
	public ModelAndView updateIntroPerson()
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fuser user = this.userService.findById(fid);
		String id = request.getParameter("fintrolId");
		String no = request.getParameter("fintrolUserNo");
		if(no != null && no.trim().length() >0){
			String fintrolUserNo = no.trim();
			int c = this.adminService.getAllCount("Fuser", "where fuserNo='"+fintrolUserNo+"'");
			if(c == 0){
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "Referee not exist");
				return modelAndView;
			}
			user.setFintrolUserNo(fintrolUserNo);
		}else{
			user.setFintrolUserNo(null);
		}

		if(id != null && id.trim().length() >0){
			int fintrolId = Integer.parseInt(id.trim());
			Fuser fintrolUser = this.userService.findById(fintrolId);
			if(fintrolUser == null){
				modelAndView.addObject("statusCode", 300);
				modelAndView.addObject("message", "User does not exist");
				return modelAndView;
			}
			user.setfIntroUser_id(fintrolUser);
		}else{
			user.setfIntroUser_id(null);
		}

		
		this.userService.updateObj(user);
		
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "Modify success");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/updateUserScore")
	public ModelAndView updateUserScore()
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fusersetting usersetting = this.usersettingService.findById(fid);
		usersetting.setFscore(Double.valueOf(request.getParameter("fscore")));
		this.usersettingService.updateObj(usersetting);

		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType", "closeCurrent");
		modelAndView.addObject("message", "Modify success");
		return modelAndView;
	}

	@RequestMapping("/ssadmin/cancelGoogleCode")
	public ModelAndView cancelGoogleCode() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fuser user = this.userService.findById(fid);
		user.setFgoogleAuthenticator(null);
		user.setFgoogleBind(false);
		user.setFgoogleurl(null);
		user.setFgoogleValidate(false);
		user.setFopenSecondValidate(false);
		this.userService.updateObj(user);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "Resetting the success of Google authentication");
		return modelAndView;
	}
	
	@RequestMapping("/ssadmin/cancelTel")
	public ModelAndView cancelTel() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fuser user = this.userService.findById(fid);
		user.setFtelephone(null);
		user.setFisTelephoneBind(false);
		user.setFareaCode(null);
		this.userService.updateObj(user);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "Reset the mobile number success");
		return modelAndView;
	}


	 
	private static enum ExportFiled {
		MemberUID,Referee,Username,MemberStatus,Nickname,RealName,MemberLevel,CumulativeNumber,Mobile,
		Email,IDType,IDNumber,RegistrationTime,LastLoginTime;
	}

	private List<Fuser> getUserList() {
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		String uid = request.getParameter("uid");
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if (keyWord != null && keyWord.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (floginName like '%" + keyWord + "%' or \n");
				filter.append("fnickName like '%" + keyWord + "%'  or \n");
				filter.append("frealName like '%" + keyWord + "%'  or \n");
				filter.append("ftelephone like '%" + keyWord + "%'  or \n");
				filter.append("femail like '%" + keyWord + "%'  or \n");
				filter.append("fidentityNo like '%" + keyWord + "%' )\n");
			}
		}
		if (uid != null && uid.trim().length() > 0) {
			try {
				int fid = Integer.parseInt(uid);
				filter.append("and fid =" + fid + " \n");
			} catch (Exception e) {}
		}

		if (request.getParameter("ftype") != null
				&& request.getParameter("ftype").trim().length() > 0) {
			int type = Integer.parseInt(request.getParameter("ftype"));
			if (type != 0) {
				filter.append("and fstatus=" + request.getParameter("ftype")
						+ " \n");
			}
		}

		try {
			if (request.getParameter("troUid") != null
					&& request.getParameter("troUid").trim().length() > 0) {
				int troUid = Integer.parseInt(request.getParameter("troUid"));
				filter.append("and fIntroUser_id.fid=" + troUid + " \n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (orderField != null && orderField.trim().length() > 0) {
			filter.append("order by " + orderField + "\n");
		} else {
			filter.append("order by fid \n");
		}

		if (orderDirection != null && orderDirection.trim().length() > 0) {
			filter.append(orderDirection + "\n");
		} else {
			filter.append("desc \n");
		}

		List<Fuser> list = this.userService.list(0, 0, filter + "", false);
		return list;
	}

	@RequestMapping("ssadmin/userExport")
	public ModelAndView userExport(HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		response.setContentType("Application/excel");
		response.addHeader("Content-Disposition",
				"attachment;filename=userList.xls");
		XlsExport e = new XlsExport();
		int rowIndex = 0;

		// header
		e.createRow(rowIndex++);
		for (ExportFiled filed : ExportFiled.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}

		List<Fuser> userList = getUserList();
		for (Fuser user : userList) {
			e.createRow(rowIndex++);
			for (ExportFiled filed : ExportFiled.values()) {
				switch (filed) {
				case MemberUID:
					e.setCell(filed.ordinal(), user.getFid());
					break;
				case Referee:
					if(user.getfIntroUser_id() != null)
					e.setCell(filed.ordinal(), user.getfIntroUser_id().getFid());
					break;
				case Username:
					e.setCell(filed.ordinal(), user.getFloginName());
					break;
				case MemberStatus:
					e.setCell(filed.ordinal(), user.getFstatus_s());
					break;
				case Nickname:
					e.setCell(filed.ordinal(), user.getFnickName());
					break;
				case RealName:
					e.setCell(filed.ordinal(), user.getFrealName());
					break;
				case MemberLevel:
					if(user.getFscore() != null)
					e.setCell(filed.ordinal(), "VIP"
							+ user.getFscore().getFlevel());
					break;
				case CumulativeNumber:
					e.setCell(filed.ordinal(), user.getfInvalidateIntroCount());
					break;
				case Mobile:
					e.setCell(filed.ordinal(), user.getFtelephone());
					break;
				case Email:
					e.setCell(filed.ordinal(), user.getFemail());
					break;
				case IDType:
					e.setCell(filed.ordinal(), user.getFidentityType_s());
					break;
				case IDNumber:
					e.setCell(filed.ordinal(), user.getFidentityNo());
					break;
				case RegistrationTime:
					e.setCell(filed.ordinal(), user.getFregisterTime());
					break;
				case LastLoginTime:
					e.setCell(filed.ordinal(), user.getFlastLoginTime());
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
		modelAndView.addObject("message", "Export success");
		return modelAndView;
	}
	
	@RequestMapping("/ssadmin/setUserNo")
	public ModelAndView setUserNo() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("fid"));
		Fuser user = this.userService.findById(fid);
		String userNo = request.getParameter("fuserNo");
		if(userNo == null || userNo.trim().length() ==0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "Referee can't be empty");
			return modelAndView;
		}else if(userNo.trim().length() >100){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "Referee length can't be greater than 100 characters");
			return modelAndView;
		}
		
		if(user.getFuserNo() != null && user.getFuserNo().trim().length() > 0 && !user.getFuserNo().equals(userNo)){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "The user has a referee that is not allowed to be modified");
			return modelAndView;
		}
		
		String filter = "where fuserNo='"+userNo+"' and fid <>"+user.getFid();
		List<Fuser> fusers = this.userService.list(0, 0, filter, false);
		if(fusers.size() >0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "The referee has already existed");
			return modelAndView;
		}

		user.setFuserNo(userNo);
		user.setFuserType(Integer.parseInt(request.getParameter("fuserType")));
//		user.setfServiceSubRate(request.getParameter("fServiceSubRate"));
		user.setfServiceTradeRate(request.getParameter("fServiceTradeRate"));
		this.userService.updateObj(user);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType","closeCurrent");
		modelAndView.addObject("message", "Success");
		return modelAndView;
	}
	
	@RequestMapping("/ssadmin/setProxyNo")
	public ModelAndView setProxyNo() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("fid"));
		Fuser user = this.userService.findById(fid);
		String fProxyNumber = request.getParameter("fProxyNumber");
		if(fProxyNumber == null || fProxyNumber.trim().length() ==0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "省市县号不能为空");
			return modelAndView;
		}else if(fProxyNumber.trim().length() >100){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "省市县号长度不能大于100个字符");
			return modelAndView;
		}
		
		if(user.getfProxyNumber() != null && user.getfProxyNumber().trim().length() > 0 && !user.getfProxyNumber().equals(fProxyNumber)){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "该用户已存在省市县号，不允许修改！");
			return modelAndView;
		}
		
		String filter = "where fProxyNumber='"+fProxyNumber+"' and fid <>"+user.getFid();
		List<Fuser> fusers = this.userService.list(0, 0, filter, false);
		if(fusers.size() >0){
			modelAndView.addObject("statusCode", 300);
			modelAndView.addObject("message", "该省市县号已存在！");
			return modelAndView;
		}

		user.setfProxyNumber(fProxyNumber);
		user.setfProxySubRate(Double.valueOf(request.getParameter("fProxySubRate")));
        user.setfProxyTradeRate(Double.valueOf(request.getParameter("fProxyTradeRate")));
		this.userService.updateObj(user);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("callbackType","closeCurrent");
		modelAndView.addObject("message", "Success");
		return modelAndView;
	}
	
	@RequestMapping("/ssadmin/cancelPhone")
	public ModelAndView cancelPhone() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fuser user = this.userService.findById(fid);
		user.setFtelephone(null);
		user.setFisTelephoneBind(false);
		user.setFisTelValidate(false);
		this.userService.updateObj(user);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "Reset the handset binding success");
		return modelAndView;
	}
	
	@RequestMapping("/ssadmin/addUsers")
	public ModelAndView addUsers() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		
		for (int i=0;i<10;i++) {
			Fuser fuser = new Fuser() ;
			
			String regName = Utils.getRandomString(10)+"@163.com";
			fuser.setSalt(Utils.getUUID());
			fuser.setFrealName("系统生成");
			fuser.setfIntroUser_id(null) ;
			fuser.setFregType(RegTypeEnum.EMAIL_VALUE);
			fuser.setFemail(regName) ;
			fuser.setFisMailValidate(true) ;
			fuser.setFnickName(regName.split("@")[0]) ;
			fuser.setFloginName(regName) ;
			
			
			fuser.setFregisterTime(Utils.getTimestamp()) ;
			fuser.setFloginPassword(Utils.MD5("123456abc",fuser.getSalt())) ;
			fuser.setFtradePassword(null) ;
			String ip = getIpAddr(request) ;
			fuser.setFregIp(ip);
			fuser.setFlastLoginIp(ip);
			fuser.setFstatus(UserStatusEnum.NORMAL_VALUE) ;
			fuser.setFlastLoginTime(Utils.getTimestamp()) ;
			fuser.setFlastUpdateTime(Utils.getTimestamp()) ;
			boolean saveFlag = false ;
			try {
				saveFlag = this.frontUserService.saveRegister(fuser) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "Success");
		return modelAndView;
	}
	
	@RequestMapping("/ssadmin/setTiger")
	public ModelAndView setTiger() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ssadmin/comm/ajaxDone");
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fuser user = this.userService.findById(fid);
		if(user.isFistiger()){
			user.setFistiger(false);
		}else{
			user.setFistiger(true);
		}
		this.userService.updateObj(user);

		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "Success");
		return modelAndView;
	}
	
	
	@RequestMapping("/ssadmin/auditUserALL")
	public ModelAndView auditUserALL() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		String ids = request.getParameter("ids");
		String[] idString = ids.split(",");
		int type = Integer.parseInt(request.getParameter("type"));
		for(int i=0;i<idString.length;i++){
			int id = Integer.parseInt(idString[i]);
			Fuser user = this.userService.findById(id);
			Fscore fscore = user.getFscore();
			Fuser fintrolUser = null;
			Fvirtualwallet fvirtualwallet = null;
			String[] auditSendCoin = this.systemArgsService.getValue("auditSendCoin").split("#");
			int coinID = Integer.parseInt(auditSendCoin[0]);
			double coinQty = Double.valueOf(auditSendCoin[1]);
			Fintrolinfo introlInfo = null;
			if (type == 1) {
				if(!user.getFhasRealValidate()){
					if(!fscore.isFissend() && user.getfIntroUser_id() != null){
						fintrolUser = this.userService.findById(user.getfIntroUser_id().getFid());
						fintrolUser.setfInvalidateIntroCount(fintrolUser.getfInvalidateIntroCount()+1);
						fscore.setFissend(true);
					}
					if(coinQty >0){
						fvirtualwallet = this.frontUserService.findVirtualWalletByUser(user.getFid(), coinID);
						fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()+coinQty);
						introlInfo = new Fintrolinfo();
						introlInfo.setFcreatetime(Utils.getTimestamp());
						introlInfo.setFiscny(false);
						introlInfo.setFqty(coinQty);
						introlInfo.setFuser(user);
						introlInfo.setFname(fvirtualwallet.getFvirtualcointype().getFname());
						introlInfo.setFtitle("实名认证成功，奖励"+fvirtualwallet.getFvirtualcointype().getFname()+coinQty+"个！");
					}
				}
				
				user.setFhasRealValidateTime(Utils.getTimestamp());
				user.setFhasRealValidate(true);
				user.setFisValid(true);
			} else {
				user.setFhasRealValidate(false);
				user.setFpostRealValidate(false);
				user.setFidentityNo(null);
				user.setFrealName(null);
			}
			try {
				this.userService.updateObj(user,fscore,fintrolUser,fvirtualwallet,introlInfo);
			} catch (Exception e) {
				continue;
			}
		}
		modelAndView.addObject("statusCode", 200);
		modelAndView.addObject("message", "Approve success");
		return modelAndView;
	}
}
