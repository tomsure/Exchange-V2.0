package com.ruizton.main.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ruizton.main.service.front.FrontVirtualCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruizton.main.Enum.SubFrozenTypeEnum;
import com.ruizton.main.Enum.SubStatusEnum;
import com.ruizton.main.Enum.SubscriptionTypeEnum;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fsubscription;
import com.ruizton.main.model.Fsubscriptionlog;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.service.admin.SubscriptionLogService;
import com.ruizton.main.service.admin.SubscriptionService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.util.Utils;

import net.sf.json.JSONObject;

@Controller
public class FrontZhongchouJsonController extends BaseController{
	@Autowired
	private FrontUserService frontUserService;
	@Autowired
	private SubscriptionService subscriptionService;
	@Autowired
	private SubscriptionLogService subscriptionLogService;
	@Autowired
	private ConstantMap map;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;

	@ResponseBody
	@RequestMapping(value="json/crowd/submit",produces={JsonEncode})
	public String crowd(
			HttpServletRequest request,
			@RequestParam(required=true)int fid,
			@RequestParam(required=true)int buyAmount,
			@RequestParam(required=true)String pwd,
			@RequestParam(required=true)double price,
			@RequestParam(required=true)int costId
	) throws Exception {
		JSONObject jsonObject = new JSONObject();

		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}

		try {
			if (0 == price) {
				jsonObject.accumulate("code", -1);
				jsonObject.accumulate("msg", "Illegal operation");
				return jsonObject.toString();
			}
			Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
			Fsubscription fsubscription = this.subscriptionService.findById(fid);
			fsubscription.setFprice(price);
			if (fsubscription == null || fsubscription.getFtype() != SubscriptionTypeEnum.RMB || buyAmount <= 0) {
				jsonObject.accumulate("code", -1);
				jsonObject.accumulate("msg", "Illegal operation");
				return jsonObject.toString();
			}

			if (fuser.getFtradePassword() == null || fuser.getFtradePassword().trim().length() == 0) {
				jsonObject.accumulate("code", -1);
				jsonObject.accumulate("msg", "Please set the transaction password first");
				return jsonObject.toString();
			}

			if (!fuser.getFtradePassword().equals(Utils.MD5(pwd, fuser.getSalt()))) {
				jsonObject.accumulate("code", -1);
				jsonObject.accumulate("msg", "Incorrect transaction password");
				return jsonObject.toString();
			}

			if (buyAmount < fsubscription.getFminbuyCount()) {
				jsonObject.accumulate("code", -1);
				jsonObject.accumulate("msg", "Least support" + fsubscription.getFminbuyCount());
				return jsonObject.toString();
			}

			if (fsubscription.getFtotal() - fsubscription.getfAlreadyByCount() < buyAmount) {
				jsonObject.accumulate("code", -1);
				jsonObject.accumulate("msg", "Insufficient surplus");
				return jsonObject.toString();
			}

			int begin = 0;
			long now = Utils.getTimestamp().getTime();
			if (fsubscription.getFbeginTime().getTime() > now) {
				//没开始
				begin = 0;
			}

			if (fsubscription.getFbeginTime().getTime() < now && fsubscription.getFendTime().getTime() > now) {
				//进行中
				begin = 1;
			}

			if (fsubscription.getFendTime().getTime() < now) {
				//结束
				begin = 2;
			}

			if (begin == 0 || !fsubscription.getFisopen()) {
				jsonObject.accumulate("code", -1);
				jsonObject.accumulate("msg", "Preheating");
				return jsonObject.toString();
			} else if (begin == 2) {
				jsonObject.accumulate("code", -1);
				jsonObject.accumulate("msg", "Ended");
				return jsonObject.toString();
			}

			//众筹记录
			String filter = "where fuser.fid=" + fuser.getFid() + " and fsubscription.fid=" + fsubscription.getFid();
			List<Fsubscriptionlog> fsubscriptionlogs = this.subscriptionLogService.list(0, 0, filter, false);
			//可购买数量
			int buyCount = fsubscription.getFbuyCount();
			if (fsubscriptionlogs.size() > 0) {
				for (int i = 0; i < fsubscriptionlogs.size(); i++) {
					buyCount -= fsubscriptionlogs.get(i).getFcount();
				}
			}

			buyCount = buyCount < 0 ? 0 : buyCount;
			//可购买次数
			int buyTimes = fsubscription.getFbuyTimes() - fsubscriptionlogs.size();
			buyTimes = buyTimes < 0 ? 0 : buyTimes;

			if (!fuser.isFistiger()) {
				if (fsubscription.getFbuyCount() != 0 && buyCount < buyAmount) {
					jsonObject.accumulate("code", -1);
					jsonObject.accumulate("msg", "You have exceeded the number you can support");
					return jsonObject.toString();
				}

				if (fsubscription.getFbuyTimes() != 0 && buyTimes == 0) {
					jsonObject.accumulate("code", -1);
					jsonObject.accumulate("msg", "You have exceeded the number of times you can support");
					return jsonObject.toString();
				}
			}

			Double cost = 0d;
			if (fsubscription.isFisICO()) {
				cost = Double.valueOf(buyAmount);
			} else {
				cost = buyAmount * price;
			}

			Fvirtualwallet fvirtualwallet1 = null;
			Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), fsubscription.getFvirtualcointype().getFid());
			fvirtualwallet1 = this.frontUserService.findVirtualWalletByUser(fuser.getFid(), costId);
			if (fvirtualwallet1.getFtotal() < cost) {
				jsonObject.accumulate("code", -1);
				jsonObject.accumulate("msg", "Your" + this.frontVirtualCoinService.findFvirtualCoinById(costId).getfShortName() + " is running low");
				return jsonObject.toString();
			}
			fvirtualwallet1.setFtotal(fvirtualwallet1.getFtotal() - cost);

			Fsubscriptionlog fsubscriptionlog = new Fsubscriptionlog();
			fsubscriptionlog.setFcount(buyAmount + 0.0);
			fsubscriptionlog.setFcreatetime(Utils.getTimestamp());
			fsubscriptionlog.setFprice(price);
			fsubscriptionlog.setFsubscription(fsubscription);
			fsubscriptionlog.setFtotalCost(cost);


			fsubscriptionlog.setFuser(fuser);
			fsubscriptionlog.setFissend(false);
			fsubscriptionlog.setFischarge(null);
			if (fsubscription.getFfrozenType() == SubFrozenTypeEnum.NO_VALUE) {
				fsubscriptionlog.setFlastcount(0d);
				fsubscriptionlog.setFstatus(SubStatusEnum.YES);
				fvirtualwallet.setFtotal(fvirtualwallet.getFtotal() + buyAmount);
			} else {
				fsubscriptionlog.setFlastcount(buyAmount);
				fsubscriptionlog.setFstatus(SubStatusEnum.INIT);
				fvirtualwallet.setFfrozen(fvirtualwallet.getFfrozen() + buyAmount);
			}

			fsubscription.setfAlreadyByCount(fsubscription.getfAlreadyByCount() + buyAmount);
			try {
				this.subscriptionService.updateSubscription(fvirtualwallet1, fvirtualwallet, fsubscriptionlog,
						fsubscription);
			} catch (Exception e) {
				jsonObject.accumulate("code", -1);
				jsonObject.accumulate("msg", "Network anomaly");
				return jsonObject.toString();
			}
			jsonObject.accumulate("code", 0);
			jsonObject.accumulate("msg", "Success");
			return jsonObject.toString();
		} catch (Exception e) {
			jsonObject.accumulate("code", -1);
			jsonObject.accumulate("msg", "Network anomaly");
			return jsonObject.toString();
		}
	}

}
