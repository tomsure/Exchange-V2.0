package com.ruizton.main.controller.admin;

import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.comm.ParamArray;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fadmin;
import com.ruizton.main.model.Farticle;
import com.ruizton.main.model.Farticletype;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.ArticleService;
import com.ruizton.main.service.admin.ArticleTypeService;
import com.ruizton.main.service.comm.listener.ChannelConstant;
import com.ruizton.main.service.comm.listener.MessageSender;
import com.ruizton.main.service.front.FrontOthersService;
import com.ruizton.util.Constant;
import com.ruizton.util.OSSPostObject;
import com.ruizton.util.Utils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ArticleController extends BaseController {
	@Autowired
	private ArticleService articleService ;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private ArticleTypeService articleTypeService ;
	@Autowired
	private HttpServletRequest request ;
	@Autowired
	private FrontOthersService frontOtherService;
	@Autowired
	private ConstantMap map;
	@Autowired
	private MessageSender messageSender ;
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/ssadmin/articleList")
	public ModelAndView Index() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/articleList") ;
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
		filter.append("where 1=1 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and (fTitle like '%"+keyWord+"%' OR \n");
			filter.append("fkeyword like '%"+keyWord+"%' ) \n");
			modelAndView.addObject("keywords", keyWord);
		}
		if(request.getParameter("ftype") != null){
			int type = Integer.parseInt(request.getParameter("ftype"));
			if(type != 0){
				filter.append("and farticletype.fid="+request.getParameter("ftype")+"\n");
			}
			modelAndView.addObject("ftype", request.getParameter("ftype"));
		}
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by fcreateDate \n");
		}
		
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("desc \n");
		}
		
		Map typeMap = new HashMap();
		typeMap.put(0, "ALL");
		List<Farticletype> all = this.articleTypeService.findAll();
		for (Farticletype farticletype : all) {
			typeMap.put(farticletype.getFid(), farticletype.getFname());
		}
		modelAndView.addObject("typeMap", typeMap);
		
		List<Farticle> list = this.articleService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("articleList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "articleList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Farticle", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("ssadmin/goArticleJSP")
	public ModelAndView goArticleJSP() throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Farticle article = this.articleService.findById(fid);
			modelAndView.addObject("farticle", article);
		}
		return modelAndView;
	}
	
	
	@RequestMapping(value="ssadmin/upload")
	@ResponseBody
	public String upload(ParamArray param) throws Exception{
		MultipartFile multipartFile = param.getFiledata() ;
		InputStream inputStream = multipartFile.getInputStream() ;
		String realName = multipartFile.getOriginalFilename() ;
		
		if(realName!=null && realName.trim().toLowerCase().endsWith("jsp")){
			return "" ;
		}
		
		String[] nameSplit = realName.split("\\.") ;
		String ext = nameSplit[nameSplit.length-1] ;
		String realPath = request.getSession().getServletContext().getRealPath("/")+ Constant.uploadPicDirectory;
		String fileName = Utils.getRandomImageName()+"."+ext;
		boolean flag = Utils.saveFile(realPath,fileName, inputStream, Constant.uploadPicDirectory) ;
		String result = "";
		if(!flag){
			result = "上传失败";
		}
		JSONObject resultJson = new JSONObject() ;
		resultJson.accumulate("err",result) ;
		if(Constant.IS_OPEN_OSS.equals("false")){
			resultJson.accumulate("msg","/"+ Constant.uploadPicDirectory+"/"+fileName) ;
		}else{
			resultJson.accumulate("msg", OSSPostObject.URL+"/"+ Constant.uploadPicDirectory+"/"+fileName) ;
		}
		
		return resultJson.toString();
	}
	
	@RequestMapping("ssadmin/saveArticle")
	public ModelAndView saveArticle(
			@RequestParam(required=false) MultipartFile filedata,
			@RequestParam(required=false) String ftitle,
			@RequestParam(required=false) String ftitleEn,
			@RequestParam(required=false) String fKeyword,
			@RequestParam("articleLookup.id") int articleLookupid,
			@RequestParam(required=false) String fcontent,
			@RequestParam(required=false) String fcontentEn,
			@RequestParam(required=false) String fisout,
			@RequestParam(required=false) String foutUrl
			) throws Exception{
		Farticle article = new Farticle();
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		Farticletype articletype = this.articleTypeService.findById(articleLookupid);
		article.setFarticletype(articletype);
		article.setFtitle(ftitle);
		article.setFtitleEn(ftitleEn);
		article.setFkeyword(fKeyword);
		article.setFoutUrl(foutUrl);
		if(fisout == null || fisout.trim().length() ==0){
			article.setFisout(false);
		}else{
			article.setFisout(true);
		}
		article.setFcontent(fcontent);
		article.setFcontentEn(fcontentEn);
		article.setFlastModifyDate(Utils.getTimestamp());
		article.setFcreateDate(Utils.getTimestamp());
		Fadmin admin = (Fadmin)request.getSession().getAttribute("login_admin");
		article.setFadminByFcreateAdmin(admin);
		article.setFadminByFmodifyAdmin(admin);
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
				String realPath = request.getSession().getServletContext().getRealPath("/")+ Constant.uploadPicDirectory;
				String fileName = Utils.getRandomImageName()+"."+ext;
				boolean flag = Utils.saveFile(realPath,fileName, inputStream, Constant.uploadPicDirectory) ;
				if(flag){
					if(Constant.IS_OPEN_OSS.equals("false")){
						fpictureUrl = "/"+ Constant.uploadPicDirectory+"/"+fileName ;
					}else{
						fpictureUrl = OSSPostObject.URL+"/"+ Constant.uploadPicDirectory+"/"+fileName ;
					}
					isTrue = true;
				}
			}
		}
		if(isTrue){
			article.setFurl(fpictureUrl);
		}
		this.articleService.saveObj(article);
		
		this.messageSender.publish(ChannelConstant.constantmap, "news");

		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Add Success");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/deleteArticle")
	public ModelAndView deleteArticle() throws Exception{
		int fid = Integer.parseInt(request.getParameter("uid"));
		this.articleService.deleteObj(fid);
		
		this.messageSender.publish(ChannelConstant.constantmap, "news");
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Delete Success");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/dingArticle")
	public ModelAndView dingArticle() throws Exception{
		int fid = Integer.parseInt(request.getParameter("uid"));
		Farticle a = this.articleService.findById(fid);
		if(a.isFisding()){
			a.setFisding(false);
		}else{
			a.setFisding(true);
		}
		
		this.articleService.updateObj(a);
		
		this.messageSender.publish(ChannelConstant.constantmap, "news");
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Success");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/updateArticle")
	public ModelAndView updateArticle(
			@RequestParam(required=false) MultipartFile filedata,
			@RequestParam(required=false) String ftitle,
			@RequestParam(required=false) String ftitleEn,
			@RequestParam(required=false) String fKeyword,
			@RequestParam("articleLookup.id") int articleLookupid,
			@RequestParam(required=false) int fid,
			@RequestParam(required=false) String fcontent,
			@RequestParam(required=false) String fcontentEn,
			@RequestParam(required=false) String fisout,
			@RequestParam(required=false) String foutUrl
			) throws Exception{
		Farticle article = this.articleService.findById(fid);
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		Farticletype articletype = this.articleTypeService.findById(articleLookupid);
		article.setFarticletype(articletype);
		article.setFtitle(ftitle);
		article.setFtitleEn(ftitleEn);
		article.setFoutUrl(foutUrl);
		if(fisout == null || fisout.trim().length() ==0){
			article.setFisout(false);
		}else{
			article.setFisout(true);
		}
		article.setFkeyword(fKeyword);
		article.setFcontent(fcontent);
		article.setFcontentEn(fcontentEn);
		article.setFlastModifyDate(Utils.getTimestamp());
		article.setFcreateDate(Utils.getTimestamp());
		Fadmin admin = (Fadmin)request.getSession().getAttribute("login_admin");
		article.setFadminByFcreateAdmin(admin);
		article.setFadminByFmodifyAdmin(admin);
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
				String realPath = request.getSession().getServletContext().getRealPath("/")+ Constant.uploadPicDirectory;
				String fileName = Utils.getRandomImageName()+"."+ext;
				boolean flag = Utils.saveFile(realPath,fileName, inputStream, Constant.uploadPicDirectory) ;
				if(flag){
					if(Constant.IS_OPEN_OSS.equals("false")){
						fpictureUrl = "/"+ Constant.uploadPicDirectory+"/"+fileName ;
					}else{
						fpictureUrl = OSSPostObject.URL+"/"+ Constant.uploadPicDirectory+"/"+fileName ;
					}
					isTrue = true;
				}
			}
		}
		if(isTrue){
			article.setFurl(fpictureUrl);
		}
		this.articleService.updateObj(article);
		
		this.messageSender.publish(ChannelConstant.constantmap, "news");

		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Modify Success");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
}
