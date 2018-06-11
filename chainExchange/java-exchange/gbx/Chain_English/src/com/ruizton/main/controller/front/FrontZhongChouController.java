package com.ruizton.main.controller.front;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ruizton.main.model.*;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.SubscriptionTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.SubscriptionLogService;
import com.ruizton.main.service.admin.SubscriptionService;
import com.ruizton.main.service.admin.UserService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.util.Constant;
import com.ruizton.util.Mobilutils;
import com.ruizton.util.PaginUtil;
import com.ruizton.util.Utils;

@Controller
public class FrontZhongChouController extends BaseController {
	@Autowired
	private SubscriptionLogService subscriptionLogService;
	@Autowired
	private SubscriptionService subscriptionService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userSerivce;
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;

	@RequestMapping("/crowd/index")
	public ModelAndView index(
			HttpServletRequest request
	) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;

		String filter = "where ftype="+SubscriptionTypeEnum.RMB+"  order by fid desc";
		List<Fsubscription> fsubscriptions = this.subscriptionService.list(0, 0, filter, false);
		if(fsubscriptions == null || fsubscriptions.size() ==0){
			modelAndView.setViewName("redirect:/") ;
			return modelAndView ;
		}

		long now = Utils.getTimestamp().getTime() ;
		for (Fsubscription fsubscription : fsubscriptions) {
			String status = "";
			if(fsubscription.getFbeginTime().getTime()>now){
				status = "Preheating";
			}
			if(fsubscription.getFbeginTime().getTime()<now && fsubscription.getFendTime().getTime()>now){
				status = "Ongoing";
			}
			if(fsubscription.getFendTime().getTime()<now){
				status = "Ended";
			}
			fsubscription.setFstatus(status);
		}

		modelAndView.addObject("fsubscriptions", fsubscriptions) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/zhongchou/index") ;
		return modelAndView ;
	}

	@RequestMapping("/crowd/view")
	public ModelAndView view(
			HttpServletRequest request,
			@RequestParam(required=true,defaultValue="0")int fid
	) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;

		Fsubscription fsubscription = this.subscriptionService.findById(fid) ;
		if(fsubscription==null || fsubscription.getFtype() !=SubscriptionTypeEnum.RMB){
			modelAndView.setViewName("redirect:/crowd/index.html") ;
			return modelAndView ;
		}

		String status = "";
		long now = Utils.getTimestamp().getTime() ;
		if(fsubscription.getFbeginTime().getTime()>now){
			status = "Preheating";
		}
		if(fsubscription.getFbeginTime().getTime()<now && fsubscription.getFendTime().getTime()>now){
			status = "Ongoing";
		}
		if(fsubscription.getFendTime().getTime()<now){
			status = "Ended";
		}
		fsubscription.setFstatus(status);

		long s = fsubscription.getFbeginTime().getTime()-now;
		long e = fsubscription.getFendTime().getTime()-now;

		modelAndView.addObject("s", s/1000L) ;
		modelAndView.addObject("e", e/1000L) ;

		String url = null;
		double totalAmt =0d;
		Fuser fuser = this.userSerivce.findById(GetSession(request).getFid());
		if(fsubscription.getFvirtualcointypeCost().getFtype() == CoinTypeEnum.FB_CNY_VALUE){
			url = "/account/rechargeCny.html";
		}else{
			url = "/account/rechargeBtc.html?symbol="+fsubscription.getFvirtualcointypeCost().getFid();
		}
//		Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fsubscription.getFvirtualcointypeCost().getFid());
//		totalAmt = fvirtualwallet.getFtotal();

		String fcost_vi_ids = fsubscription.getFcost_vi_ids();
		String [] vi_ids = fcost_vi_ids.split(",");
		List<Fvirtualcointype> coinType = new ArrayList<Fvirtualcointype>();
		List<Fvirtualwallet> walletList = new ArrayList<Fvirtualwallet>();
		for(String ids : vi_ids)
		{
			coinType.add(this.frontVirtualCoinService.findFvirtualCoinById(Integer.parseInt(ids)));
			walletList.add(this.frontUserService.findVirtualWalletByUser(fuser.getFid(), Integer.parseInt(ids)));
		}
		modelAndView.addObject("coinType", coinType) ;

		String prices = fsubscription.getFprices();
		String [] priceArray = prices.split("/");
		List priceList = new ArrayList();
		for(String price : priceArray)
		{
			priceList.add(price);
		}
		modelAndView.addObject("priceList", priceList) ;

		modelAndView.addObject("walletList", walletList) ;
		modelAndView.addObject("rechargeUrl", url) ;
		modelAndView.addObject("fsubscription", fsubscription) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/zhongchou/detail") ;
		return modelAndView ;
	}

	@RequestMapping("/crowd/logs")
	public ModelAndView logs(
			@RequestParam(required=false,defaultValue="1")int currentPage,
			HttpServletRequest request
	) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;

		String filter = "where fuser.fid="+GetSession(request).getFid()+" order by fid desc";
		List<Fsubscriptionlog> subscriptionlogs = this.subscriptionLogService.list((currentPage-1)*Constant.RecordPerPage, Constant.RecordPerPage, filter, true);
		int total = this.adminService.getAllCount("Fsubscriptionlog", filter);
		String pagin = PaginUtil.generatePagin(total/Constant.RecordPerPage+(total%Constant.RecordPerPage==0?0:1), currentPage, "/crowd/logs.html?") ;
		modelAndView.addObject("pagin", pagin) ;
		modelAndView.addObject("subscriptionlogs", subscriptionlogs) ;

		modelAndView.setViewName("front"+Mobilutils.M(request)+"/zhongchou/logs") ;
		return modelAndView ;
	}

}
