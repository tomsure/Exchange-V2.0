package com.ruizton.main.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.AutotradeStatusEnum;
import com.ruizton.main.Enum.SynTypeEnum;
import com.ruizton.main.Enum.TradeTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fautotrade;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.AutotradeService;
import com.ruizton.main.service.admin.TradeMappingService;
import com.ruizton.main.service.admin.UserService;
import com.ruizton.util.Utils;

@Controller
public class AutotradeController extends BaseController {
	@Autowired
	private AutotradeService autotradeService;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private UserService userService;
	@Autowired
	private HttpServletRequest request ;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	@Autowired
	private TradeMappingService tradeMappingService;
	
	@RequestMapping("/ssadmin/autotradeList")
	public ModelAndView autotradeList() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/autotradeList") ;
		//当前页
		int currentPage = 1;
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");

		List<Fautotrade> list = this.autotradeService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("autotradeList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "autotradeList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fautotrade", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("ssadmin/goAutotradeJSP")
	public ModelAndView goAutotradeJSP() throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fautotrade fautotrade = this.autotradeService.findById(fid);
			modelAndView.addObject("fautotrade", fautotrade);
		}
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, TradeTypeEnum.getEnumString(1));
		modelAndView.addObject("map", map);
		
		String sql = "where fstatus=1";
		List<Ftrademapping> allType = this.tradeMappingService.list(0, 0, sql, false);
		modelAndView.addObject("allType", allType);
		
		Map<Integer, String> typemap = new HashMap<Integer, String>();
		typemap.put(0, SynTypeEnum.getEnumString(0));
		typemap.put(1, SynTypeEnum.getEnumString(1));
		typemap.put(2, SynTypeEnum.getEnumString(2));
		typemap.put(3, SynTypeEnum.getEnumString(3));
		modelAndView.addObject("typemap", typemap);
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/saveAutotrade")
	public ModelAndView saveAutotrade() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
        Fautotrade autotrade = new Fautotrade();
        int userid = Integer.parseInt(request.getParameter("fuserid"));
        List<Fuser> fusers = this.userService.list(0, 0, "where fid="+userid, false);
		if(fusers == null || fusers.size() == 0){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","UUID does not exist");
			return modelAndView;
		}
		
		autotrade.setFsynType(Integer.parseInt(request.getParameter("fsynType")));

		autotrade.setFstatus(AutotradeStatusEnum.NORMAL);
		autotrade.setFuser(fusers.get(0));
		autotrade.setFcreatetime(Utils.getTimestamp());
		autotrade.setFmaxprice(Double.valueOf(request.getParameter("fmaxprice")));
		autotrade.setFminprice(Double.valueOf(request.getParameter("fminprice")));
		autotrade.setFmaxqty(Double.valueOf(request.getParameter("fmaxqty")));
		autotrade.setFminqty(Double.valueOf(request.getParameter("fminqty")));
		autotrade.setFtype(Integer.parseInt(request.getParameter("ftype")));
		autotrade.setFtimes(Integer.parseInt(request.getParameter("ftimes")));
		autotrade.setFstoptimes(Integer.parseInt(request.getParameter("fstoptimes")));
		int vid = Integer.parseInt(request.getParameter("vid"));
		Ftrademapping trademapping = this.tradeMappingService.findById(vid);
		autotrade.setFtrademapping(trademapping);
		this.autotradeService.saveObj(autotrade);

		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Add Success");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/updateAutotrade")
	public ModelAndView updateAutotrade() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		int uid = Integer.parseInt(request.getParameter("uid"));
        Fautotrade autotrade = this.autotradeService.findById(uid);
        int userid = Integer.parseInt(request.getParameter("fuserid"));
        List<Fuser> fusers = this.userService.list(0, 0, "where fid="+userid, false);
		if(fusers == null || fusers.size() == 0){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","UUID does not exist");
			return modelAndView;
		}
		
		autotrade.setFsynType(Integer.parseInt(request.getParameter("fsynType")));
		
		autotrade.setFstatus(AutotradeStatusEnum.NORMAL);
		autotrade.setFuser(fusers.get(0));
		autotrade.setFcreatetime(Utils.getTimestamp());
		autotrade.setFmaxprice(Double.valueOf(request.getParameter("fmaxprice")));
		autotrade.setFminprice(Double.valueOf(request.getParameter("fminprice")));
		autotrade.setFmaxqty(Double.valueOf(request.getParameter("fmaxqty")));
		autotrade.setFminqty(Double.valueOf(request.getParameter("fminqty")));
		autotrade.setFtype(Integer.parseInt(request.getParameter("ftype")));
		autotrade.setFtimes(Integer.parseInt(request.getParameter("ftimes")));
		autotrade.setFstoptimes(Integer.parseInt(request.getParameter("fstoptimes")));
		int vid = Integer.parseInt(request.getParameter("vid"));
		Ftrademapping trademapping = this.tradeMappingService.findById(vid);
		autotrade.setFtrademapping(trademapping);
		this.autotradeService.updateObj(autotrade);

		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Modify Success");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/deleteAutotrade")
	public ModelAndView deleteAutotrade() throws Exception{
		int fid = Integer.parseInt(request.getParameter("uid"));
		ModelAndView modelAndView = new ModelAndView() ;
		this.autotradeService.deleteObj(fid);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Delete Success");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/doAutotrade")
	public ModelAndView doAutotrade() throws Exception{
		int fid = Integer.parseInt(request.getParameter("uid"));
		int type = Integer.parseInt(request.getParameter("type"));
		ModelAndView modelAndView = new ModelAndView() ;
		Fautotrade autotrade = this.autotradeService.findById(fid);
		if(type == 1){
			autotrade.setFstatus(AutotradeStatusEnum.FORBIN);
		}else{
			autotrade.setFstatus(AutotradeStatusEnum.NORMAL);
		}
		
		this.autotradeService.updateObj(autotrade);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Success");
		return modelAndView;
	}
}
