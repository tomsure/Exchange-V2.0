package com.ruizton.main.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fasset;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.front.UtilsService;
import com.ruizton.util.Utils;

@Controller
public class AssetsrecordController extends BaseController {
	@Autowired
	private UtilsService utilsService;
	@Autowired
	private VirtualCoinService virtualCoinService ;
	@Autowired
	private HttpServletRequest request ;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/ssadmin/assetList")
	public ModelAndView assetList() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/assetList") ;
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
		filter.append(" where 1=1 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			try {
				int fid = Integer.parseInt(keyWord);
				filter.append("and fuser.fid =" + fid + " \n");
			} catch (Exception e) {
				filter.append("and (fuser.floginName like '%" + keyWord
						+ "%' OR \n");
				filter.append("fuser.fnickName like '%" + keyWord + "%' OR \n");
				filter.append("fuser.frealName like '%" + keyWord + "%' ) \n");
			}
			modelAndView.addObject("keywords", keyWord);
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
		
		List<Fvirtualcointype> fvirtualcointypes = this.virtualCoinService.findAll(CoinTypeEnum.FB_CNY_VALUE, 1);
		modelAndView.addObject("fvirtualcointypes", fvirtualcointypes) ;
		
		int total = this.utilsService.count(filter.toString(), Fasset.class) ;
		List<Fasset> list= this.utilsService.list_admin((currentPage-1)*numPerPage, numPerPage,filter.toString(), true, Fasset.class) ;
		
		//处理json
		for (Fasset fasset : list) {
			fasset.parseJson(fvirtualcointypes);
		}
		
		modelAndView.addObject("assetList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("currentPage", currentPage);
		modelAndView.addObject("rel", "assetList");
		//总数量
		modelAndView.addObject("totalCount",total);
		return modelAndView ;
	}
	
}
