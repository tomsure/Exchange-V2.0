package com.ruizton.main.controller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.QuestionTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fquestion;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.service.front.FrontQuestionService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.util.Mobilutils;
import com.ruizton.util.PaginUtil;

@Controller
public class FrontQuestionController extends BaseController{
	
	@Autowired
	private FrontUserService frontUserService ;
	@Autowired
	private FrontQuestionService frontQuestionService ;
	
	@RequestMapping("/question/question")
	public ModelAndView question(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		Fuser fuser = this.frontUserService.findById(GetSession(request).getFid()) ;
		
		Map<Integer,String> map = new HashMap<Integer,String>() ;
		for(int i=1;i<=QuestionTypeEnum.OTHERS;i++){
			map.put(i, QuestionTypeEnum.getEnumString(i));
		}
		
		modelAndView.addObject("fuser", fuser) ;
		modelAndView.addObject("question_list", map) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/question/question") ;
		return modelAndView ;
	}
	
	@RequestMapping("/question/questionlist")
	public ModelAndView questionlist(
			HttpServletRequest request,
//			@RequestParam(required=false,defaultValue="1")int type,
			@RequestParam(required=false,defaultValue="1")int currentPage
			) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		
		Map<String, Object> param = new HashMap<String, Object>() ;
//		param.put("fstatus", type) ;
		param.put("fuser.fid", GetSession(request).getFid()) ;
		int totalCount = this.frontQuestionService.findByParamCount(param) ;
		int totalPage = totalCount/maxResults +(totalCount%maxResults==0?0:1) ;
		currentPage = currentPage<1?1:currentPage ;
		currentPage = currentPage>totalPage?totalCount:currentPage ;
		
		List<Fquestion> list = this.frontQuestionService.findByParam(param, PaginUtil.firstResult(currentPage, maxResults),maxResults, "fcreateTime desc") ;
		
		String pagin = PaginUtil.generatePagin(totalPage, currentPage,"/question/questionlist.html?") ;
		modelAndView.addObject("pagin", pagin) ;
		modelAndView.addObject("list",list) ;
//		modelAndView.addObject("type", type) ;
		modelAndView.addObject("currentPage", currentPage) ;
		modelAndView.setViewName("front"+Mobilutils.M(request)+"/question/questioncolumn") ;
		return modelAndView ;
	}
	
//	
//
//	
//	@RequestMapping("/question/message")
//	public ModelAndView message(
//			HttpServletRequest request,
//			@RequestParam(required=false,defaultValue="1")int type,
//			@RequestParam(required=false,defaultValue="1")int currentPage
//			) throws Exception{
//		ModelAndView modelAndView = new ModelAndView() ;
//		
//		
//		Map<String, Object> param = new HashMap<String, Object>() ;
//		param.put("fstatus", type) ;
//		param.put("freceiver.fid", GetSession(request).getFid()) ;
//		int totalCount = this.frontQuestionService.findFmessageByParamCount(param) ;
//		
//		int totalPage = totalCount/maxResults +(totalCount%maxResults==0?0:1) ;
//		currentPage = currentPage<1?1:currentPage ;
//		currentPage = currentPage>totalPage?totalCount:currentPage ;
//		
//		List<Fmessage> list = this.frontQuestionService.findFmessageByParam(param, PaginUtil.firstResult(currentPage, maxResults), "fcreateTime desc") ;
//		
//		String pagin = super.generatePagin(totalPage, currentPage,"/question/message.html?type="+type+"&") ;
//	
//		
//		modelAndView.addObject("list", list) ;
//		modelAndView.addObject("pagin", pagin) ;
//		modelAndView.addObject("type", type) ;
//		modelAndView.setViewName("front"+Mobilutils.M(request)+"/question/message") ;
//		return modelAndView ;
//	}
//	
//	@ResponseBody
//	@RequestMapping(value="/question/messagedetail",produces={JsonEncode})
//	public String messagedetail(
//			HttpServletRequest request,
//			@RequestParam(required=true)int id
//			) throws Exception{
//		JSONObject jsonObject = new JSONObject() ;
//		
//		Fmessage fmessage = this.frontQuestionService.findFmessageById(id) ;
//		if(fmessage==null || fmessage.getFreceiver().getFid()!=GetSession(request).getFid()){
//			jsonObject.accumulate("result", false) ;
//			return jsonObject.toString() ;
//		}
//		
//		if(fmessage.getFstatus()==MessageStatusEnum.NOREAD_VALUE){
//			fmessage.setFstatus(MessageStatusEnum.READ_VALUE) ;
//			this.frontQuestionService.updateFmessage(fmessage) ;
//		}
//		
//		jsonObject.accumulate("result", true) ;
//		jsonObject.accumulate("title", fmessage.getFtitle()) ;
//		jsonObject.accumulate("content", fmessage.getFcontent()) ;
//		return jsonObject.toString() ;
//	}
}
