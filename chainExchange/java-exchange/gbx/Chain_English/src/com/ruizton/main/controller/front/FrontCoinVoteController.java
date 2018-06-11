package com.ruizton.main.controller.front;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.CoinVoteStatusEnum;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fcoinvote;
import com.ruizton.main.model.Fcoinvotelog;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.Fvirtualwallet;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.CoinVoteService;
import com.ruizton.main.service.admin.CoinVotelogService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.util.Constant;
import com.ruizton.util.Mobilutils;
import com.ruizton.util.PaginUtil;
import com.ruizton.util.Utils;

import net.sf.json.JSONObject;

@Controller
public class FrontCoinVoteController extends BaseController {

	@Autowired
	private CoinVotelogService coinVotelogService;
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private CoinVoteService coinVoteService;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService;
	@Autowired
	private ConstantMap map;
	
	@RequestMapping("/vote/list")
	public ModelAndView voteList(
			@RequestParam(required=false,defaultValue="1")int currentPage,
            HttpServletRequest request
			) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/coinvote/voteList") ;
		
		boolean flag = true;
		if(currentPage >1){
			flag = false;
		}
		modelAndView.addObject("flag", flag) ;
		
		String filter = " where fstatus="+CoinVoteStatusEnum.NORMAL_VALUE+" order by (fyes-fno) desc ";
		List<Fcoinvote> coinvotes = this.coinVoteService.list((currentPage-1)*Constant.RecordPerPage, Constant.RecordPerPage, filter, true);
		int total = this.adminService.getAllCount("Fcoinvote", filter);
		String pagin = PaginUtil.generatePagin(total/Constant.RecordPerPage+(total%Constant.RecordPerPage==0?0:1), currentPage, "/vote/list.html?") ;
		modelAndView.addObject("pagin", pagin) ;
		modelAndView.addObject("coinvotes", coinvotes) ;
		
		return modelAndView ;
	}
	
	@RequestMapping("/vote/details")
	public ModelAndView details(
			HttpServletRequest request,
			@RequestParam(required=false,defaultValue="0")int id
			) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/coinvote/voteDetailList") ;
		
		Fcoinvote vote = this.coinVoteService.findById(id);
		if(vote==null || vote.getFstatus()==CoinVoteStatusEnum.ABNORMAL_VALUE){
			modelAndView.setViewName("redirect:/") ;
			return modelAndView ;
		}
		
		boolean islogin = false;
		if(GetSession(request) != null){
			islogin = true;
		}
		modelAndView.addObject("islogin", islogin) ;
		modelAndView.addObject("vote", vote) ;
		return modelAndView ;
	}
	
	@ResponseBody
	@RequestMapping(value="/json/coinVote",produces=JsonEncode)
	public String coinVote(
			HttpServletRequest request,
			@RequestParam(required=true)int id,
			@RequestParam(required=true)int vote
			) throws Exception {
		
		JSONObject jsonObject = new JSONObject() ;
		
		if(vote !=0 && vote !=1){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "非法操作！") ;
			return jsonObject.toString() ;
		}

		if(GetSession(request) == null){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "请先登陆！") ;
			return jsonObject.toString() ;
		}
		
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
		if(!fuser.getFhasRealValidate()){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "您还未通过实名认证！") ;
			return jsonObject.toString() ;
		}
		
		String[] args = this.map.getString("oneXiaoFei").trim().split("#") ;//消耗1TMC
		double xiaofei = Double.valueOf(args[1]);
		int vid = Integer.parseInt(args[0]);
		int times = Integer.parseInt(args[2]);
		//TMC
		Fvirtualcointype fvirtualcointype = this.frontVirtualCoinService.findFvirtualCoinById(vid) ;
		Fvirtualwallet fvirtualwallet = this.frontUserService.findVirtualWalletByUser(GetSession(request).getFid(), fvirtualcointype.getFid()) ;
		if(fvirtualwallet.getFtotal()<xiaofei){
			jsonObject.accumulate("code", -1) ;
			jsonObject.accumulate("msg", "投票需要消耗"+xiaofei+fvirtualcointype.getFname()+"，您的余额不足！") ;
			return jsonObject.toString() ;
		}
		fvirtualwallet.setFtotal(fvirtualwallet.getFtotal()-xiaofei) ;
		
		Fcoinvote fcoinvote = this.coinVoteService.findById(id);
		if(fcoinvote==null || fcoinvote.getFstatus()==CoinVoteStatusEnum.ABNORMAL_VALUE){
			jsonObject.accumulate("code", -12) ;
			jsonObject.accumulate("msg", "无效的投票！") ;
			return jsonObject.toString() ;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String key = sdf.format(new Date());
		String sql = "where fuser.fid="+GetSession(request).getFid()+" and DATE_FORMAT(fcreatetime,'%Y-%m-%d') ='"+key+"' and fcoinvote.fid="+fcoinvote.getFid();
		int count = this.adminService.getAllCount("Fcoinvotelog", sql);
		if(count+1 > times && !fuser.isFistiger()){
			jsonObject.accumulate("code", -12) ;
			jsonObject.accumulate("msg", "每天只允许投票"+times+"次！") ;
			return jsonObject.toString() ;
		}
		
		Fcoinvotelog fcoinvotelog = new Fcoinvotelog() ;
		fcoinvotelog.setFcoinvote(fcoinvote) ;
		fcoinvotelog.setFcreatetime(Utils.getTimestamp()) ;
		fcoinvotelog.setFuser(GetSession(request)) ;
		fcoinvotelog.setVote(vote) ;
		
		if(vote==0){
			fcoinvote.setFno(fcoinvote.getFno()+1) ;
		}else{
			fcoinvote.setFyes(fcoinvote.getFyes()+1) ;
		}
		
		
		boolean flag = false ;
		try{
			this.coinVoteService.updateFcoinvote(fvirtualwallet, fcoinvote, fcoinvotelog) ;
			flag = true ;
		}catch(Exception e){}
		
		if(flag == true ){
			jsonObject.accumulate("code", 0) ;
			jsonObject.accumulate("msg", "投票成功！") ;
			return jsonObject.toString() ;
		}else{
			jsonObject.accumulate("code", -3) ;
			jsonObject.accumulate("msg", "网络错误，请稍后再试！") ;
			return jsonObject.toString() ;
		}
		
	}
}
