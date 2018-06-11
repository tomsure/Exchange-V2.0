package com.ruizton.main.controller.admin;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.CoinVoteStatusEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fcoinvote;
import com.ruizton.main.model.Fcoinvotelog;
import com.ruizton.main.service.admin.CoinVoteService;
import com.ruizton.main.service.admin.CoinVotelogService;
import com.ruizton.main.service.front.UtilsService;
import com.ruizton.util.Constant;
import com.ruizton.util.OSSPostObject;
import com.ruizton.util.Utils;

@Controller
public class CoinVoteController extends BaseController {
	@Autowired
	private HttpServletRequest request ;
	@Autowired
	private UtilsService utilsService ;
	@Autowired
	private CoinVoteService coinVoteService ;
	@Autowired
	private CoinVotelogService coinVotelogService ;
	
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/ssadmin/coinVoteLogList")
	public ModelAndView coinVoteLogList() throws Exception{
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/coinvote/coinVoteLogList") ;
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
			filter.append("and (fcnName like '%"+keyWord+"%' OR \n");
			filter.append("fenName like '%"+keyWord+"%' OR \n");
			
			filter.append("fuser.floginName like '%"+keyWord+"%' OR \n");
			filter.append("fuser.fnickName like '%"+keyWord+"%' OR \n");
			filter.append("fuser.fid like '%"+keyWord+"%' OR \n");
			
			filter.append("fshortName like '%"+keyWord+"%' )\n");
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
		List<Fcoinvotelog> list = this.coinVotelogService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("coinVoteLogList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "coinVoteLogList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.utilsService.count(filter+"",Fcoinvotelog.class));
		return modelAndView ;
	}
	
	
	@RequestMapping("/ssadmin/coinVoteList")
	public ModelAndView Index() throws Exception{
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/coinvote/coinVoteList") ;
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
			filter.append("and (fcnName like '%"+keyWord+"%' OR \n");
			filter.append("fenName like '%"+keyWord+"%' OR \n");
			filter.append("fshortName like '%"+keyWord+"%' )\n");
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
		List<Fcoinvote> list = this.coinVoteService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("coinVoteList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "coinVoteList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.utilsService.count(filter+"",Fcoinvote.class));
		return modelAndView ;
	}
	
	
	@RequestMapping("ssadmin/goVirtualCoinVoteJSP")
	public ModelAndView goVirtualCoinVoteJSP() throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fcoinvote fcoinvote = this.coinVoteService.findById(fid) ;
			modelAndView.addObject("fcoinvote", fcoinvote);
		}
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/saveCoinVote")
	public ModelAndView saveCoinVote(
			@RequestParam(required=false) MultipartFile filedata,
			String fcnName,
			String fshortName,
			String fenName,
			String fdesc
			) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		Fcoinvote fcoinvote = new Fcoinvote() ;
		fcoinvote.setFenName(fenName);
		fcoinvote.setFcnName(fcnName) ;
		fcoinvote.setFdesc(fdesc);
		fcoinvote.setFshortName(fshortName) ;
		fcoinvote.setFstatus(CoinVoteStatusEnum.ABNORMAL_VALUE) ;
		String fpictureUrl = "";
		boolean isTrue = false;
		 if(filedata != null && !filedata.isEmpty()){
			InputStream inputStream = filedata.getInputStream() ;
			String fileRealName = filedata.getOriginalFilename() ;
			if(fileRealName != null && fileRealName.trim().length() >0){
				String[] nameSplit = fileRealName.split("\\.") ;
				String ext = nameSplit[nameSplit.length-1] ;
				if(ext!=null 
				 && !ext.trim().toLowerCase().endsWith("jpg")
				 && !ext.trim().toLowerCase().endsWith("png")){
					modelAndView.addObject("statusCode",300);
					modelAndView.addObject("message","非jpg、png文件格式");
					return modelAndView;
				}
				String realPath = request.getSession().getServletContext().getRealPath("/")+Constant.uploadPicDirectory;
				String fileName = Utils.getRandomImageName()+"."+ext;
				boolean flag = Utils.saveFile(realPath,fileName, inputStream,Constant.uploadPicDirectory) ;
				if(flag){
					if(Constant.IS_OPEN_OSS.equals("false")){
						fpictureUrl = "/"+Constant.uploadPicDirectory+"/"+fileName ;
					}else{
						fpictureUrl = OSSPostObject.URL+"/"+Constant.uploadPicDirectory+"/"+fileName ;
					}
					isTrue = true;
				}
			}
		}
		if(isTrue){
			fcoinvote.setFurl(fpictureUrl);
		}
		this.coinVoteService.saveObj(fcoinvote) ;

		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Add Success");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/updateCoinVote")
	public ModelAndView updateCoinVote(
			@RequestParam(required=false) MultipartFile filedata,
			String fcnName,
			String fshortName,
			String fenName,
			String fdesc,
		    int fid
			) throws Exception{
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		Fcoinvote fcoinvote = this.coinVoteService.findById(fid) ;
		fcoinvote.setFenName(fenName);
		fcoinvote.setFcnName(fcnName) ;
		fcoinvote.setFdesc(fdesc);
		fcoinvote.setFshortName(fshortName) ;
		fcoinvote.setFstatus(CoinVoteStatusEnum.ABNORMAL_VALUE) ;
		String fpictureUrl = "";
		boolean isTrue = false;
		 if(filedata != null && !filedata.isEmpty()){
			InputStream inputStream = filedata.getInputStream() ;
			String fileRealName = filedata.getOriginalFilename() ;
			if(fileRealName != null && fileRealName.trim().length() >0){
				String[] nameSplit = fileRealName.split("\\.") ;
				String ext = nameSplit[nameSplit.length-1] ;
				if(ext!=null 
				 && !ext.trim().toLowerCase().endsWith("jpg")
				 && !ext.trim().toLowerCase().endsWith("png")){
					modelAndView.addObject("statusCode",300);
					modelAndView.addObject("message","非jpg、png文件格式");
					return modelAndView;
				}
				String realPath = request.getSession().getServletContext().getRealPath("/")+Constant.uploadPicDirectory;
				String fileName = Utils.getRandomImageName()+"."+ext;
				boolean flag = Utils.saveFile(realPath,fileName, inputStream,Constant.uploadPicDirectory) ;
				if(flag){
					if(Constant.IS_OPEN_OSS.equals("false")){
						fpictureUrl = "/"+Constant.uploadPicDirectory+"/"+fileName ;
					}else{
						fpictureUrl = OSSPostObject.URL+"/"+Constant.uploadPicDirectory+"/"+fileName ;
					}
					isTrue = true;
				}
			}
		}
		if(isTrue){
			fcoinvote.setFurl(fpictureUrl);
		}
		this.coinVoteService.updateObj(fcoinvote) ;

		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Modify Success");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/updateCoinVoteStatus1")
	public ModelAndView updateCoinVoteStatus1() throws Exception{
		int fid = Integer.parseInt(request.getParameter("uid"));
		ModelAndView modelAndView = new ModelAndView() ;
		
		Fcoinvote fcoinvote = this.coinVoteService.findById(fid) ;
		fcoinvote.setFstatus(CoinVoteStatusEnum.NORMAL_VALUE) ;
		this.coinVoteService.updateObj(fcoinvote) ;
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Modify Success");
		/*modelAndView.addObject("callbackType","closeCurrent");*/
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/updateCoinVoteStatus2")
	public ModelAndView updateCoinVoteStatus2() throws Exception{
		int fid = Integer.parseInt(request.getParameter("uid"));
		ModelAndView modelAndView = new ModelAndView() ;
		
		Fcoinvote fcoinvote = this.coinVoteService.findById(fid) ;
		fcoinvote.setFstatus(CoinVoteStatusEnum.ABNORMAL_VALUE) ;
		this.coinVoteService.updateObj(fcoinvote) ;
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Modify Success");
		/*modelAndView.addObject("callbackType","closeCurrent");*/
		return modelAndView;
	}


}
