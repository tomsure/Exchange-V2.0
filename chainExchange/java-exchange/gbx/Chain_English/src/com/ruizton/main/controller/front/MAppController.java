package com.ruizton.main.controller.front;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.TrademappingStatusEnum;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Ftradehistory;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.service.comm.redis.RedisConstant;
import com.ruizton.main.service.comm.redis.RedisUtil;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.main.service.front.FtradeMappingService;
import com.ruizton.main.service.front.UtilsService;
import com.ruizton.util.Utils;

@Controller
public class MAppController extends BaseController {

	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private FtradeMappingService ftradeMappingService ;
	@Autowired
	private ConstantMap constantMap ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private RedisUtil redisUtil ;
	
	@RequestMapping("/m/top5paihang")
	public ModelAndView top5paihang() throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		
		List<Ftrademapping> ftrademappings = this.utilsService
				.list(
						0,
						0,
						" where fstatus=? order by fid asc ",
						false, Ftrademapping.class ,
						TrademappingStatusEnum.ACTIVE);
		
		//24小时涨跌
		TreeSet<Ftrademapping> type1 = new TreeSet<Ftrademapping>(new Comparator<Ftrademapping>() {

			public int compare(Ftrademapping o1, Ftrademapping o2) {
				// TODO Auto-generated method stub
				int i= o2.getRose().compareTo(o1.getRose());
				if(i==0)return -1 ;
				return i;
			}
			
			
		}) ;
//		7D涨跌
		TreeSet<Ftrademapping> type2 = new TreeSet<Ftrademapping>(new Comparator<Ftrademapping>() {

			public int compare(Ftrademapping o1, Ftrademapping o2) {
				// TODO Auto-generated method stub
				int i= o2.getRose7().compareTo(o1.getRose7());
				if(i==0)return -1 ;
				return i;
			}
		}) ;
		//24小时成交额
		TreeSet<Ftrademapping> type3 = new TreeSet<Ftrademapping>(new Comparator<Ftrademapping>() {

			public int compare(Ftrademapping o1, Ftrademapping o2) {
				// TODO Auto-generated method stub
				int i= o2.getTotalCny24().compareTo(o1.getTotalCny24());
				if(i==0)return 1 ;
				return i;
			}

			
			
		}) ;
		//24小时成交量
		TreeSet<Ftrademapping> type4 = new TreeSet<Ftrademapping>(new Comparator<Ftrademapping>() {

			public int compare(Ftrademapping o1, Ftrademapping o2) {
				// TODO Auto-generated method stub
				int i= o2.getTotal24().compareTo(o1.getTotal24());
				if(i==0)return 1 ;
				return i;
			}
			
		}) ;
		for (int i = 0; i < ftrademappings.size(); i++) {
			Ftrademapping ftrademapping = ftrademappings.get(i) ;
			
			double s = (Double)this.redisUtil.get(RedisConstant.getstart24Price(ftrademapping.getFid())) ; ;
			double s7 = -8888d;
			List<Ftradehistory> ftradehistorys = (List<Ftradehistory>)constantMap.get("tradehistory");
			for (Ftradehistory ftradehistory : ftradehistorys) {
				if(ftradehistory.getFtrademapping().getFid().intValue() == ftrademapping.getFid().intValue()){
					s= ftradehistory.getFprice();
					break;
				}
			}
			List<Ftradehistory> ftradehistoryss = (List<Ftradehistory>)constantMap.get("ftradehistory7D");
			for (Ftradehistory ftradehistory : ftradehistoryss) {
				if(ftradehistory.getFtrademapping().getFid().intValue() == ftrademapping.getFid().intValue()){
					s7= ftradehistory.getFprice();
					break;
				}
			}
			
			double price = (Double)this.redisUtil.get(RedisConstant.getLatestDealPrizeKey(ftrademapping.getFid())) ;
			double last = 0d;
			double last7 = 0d;
			try {
				last = Utils.getDouble((price-s)/s*100, 2);
			} catch (Exception e) {}
			try {
				if(s7==-8888d){
					last7=0d;
				}else{
					last7 = Utils.getDouble((price-s7)/s7*100, 2);
				}
			} catch (Exception e) {}
			
			ftrademapping.setFvirtualcointypeByFvirtualcointype2(this.frontVirtualCoinService.findFvirtualCoinById(ftrademapping.getFvirtualcointypeByFvirtualcointype2().getFid()));
			ftrademapping.setRose(last);
			ftrademapping.setRose7(last7);
			ftrademapping.setTotal24( Utils.getDouble((Double)this.redisUtil.get(RedisConstant.getTotal(ftrademapping.getFid())), 2));
			ftrademapping.setTotalCny24(Utils.getDouble((Double)this.redisUtil.get(RedisConstant.get24Total(ftrademapping.getFid())), 2));
			
			type1.add(ftrademapping) ;
			type2.add(ftrademapping) ;
			type3.add(ftrademapping) ;
			type4.add(ftrademapping) ;
		}
		
		modelAndView.addObject("type1", type1) ;
		modelAndView.addObject("type2", type2) ;
		modelAndView.addObject("type3", type3) ;
		modelAndView.addObject("type4", type4) ;
		modelAndView.setViewName("/front/app/top5paihangbang");
		return modelAndView ;
	}
	
	@RequestMapping("/m/tradePwd")
	public ModelAndView tradePwd(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		if(fuser.getFtradePassword() !=null ){
			modelAndView.setViewName("redirect:/m/realId.html");
			return modelAndView ;
		}
		modelAndView.setViewName("/front/app/security/tradePwd");
		return modelAndView ;
	}
	@RequestMapping("/m/realId")
	public ModelAndView realId(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		if(fuser.getFpostRealValidate()==true ){
			modelAndView.setViewName("redirect:/m/index.html");
			return modelAndView ;
		}
		modelAndView.setViewName("/front/app/security/realId");
		
		return modelAndView ;
	}
	@RequestMapping("/m/regSuc")
	public ModelAndView regSuc(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		modelAndView.addObject("fuser", fuser) ;
		
		modelAndView.setViewName("/front/app/security/regsuc");
		return modelAndView ;
	}
	
	@RequestMapping("/m/validate/resetPwd")
	public ModelAndView resetPwd() throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		
		modelAndView.setViewName("/front/app/user/resetPwd1");
		return modelAndView ;
	}
	@RequestMapping("/m/validate/resetPwd2")
	public ModelAndView resetPwd2() throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		
		modelAndView.setViewName("/front/app/user/resetPwd2");
		return modelAndView ;
	}
	@RequestMapping("/m/validate/resetPwd3")
	public ModelAndView resetPwd3() throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		
		modelAndView.setViewName("/front/app/user/resetPwd3");
		return modelAndView ;
	}
	@RequestMapping("/m/reg-notice")
	public ModelAndView regNotice() throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		
		modelAndView.setViewName("/front/app/user/reg-notice");
		return modelAndView ;
	}
}
