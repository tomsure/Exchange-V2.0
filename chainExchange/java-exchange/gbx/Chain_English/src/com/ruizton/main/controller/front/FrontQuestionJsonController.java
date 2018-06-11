package com.ruizton.main.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.ruizton.main.Enum.QuestionStatusEnum;
import com.ruizton.main.Enum.QuestionTypeEnum;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.Fquestion;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.service.front.FrontQuestionService;
import com.ruizton.main.service.front.FrontUserService;
import com.ruizton.util.Utils;

import net.sf.json.JSONObject;

@Controller
public class FrontQuestionJsonController extends BaseController{
	
	@Autowired
	private FrontQuestionService frontQuestionService ;
	@Autowired
	private FrontUserService frontUserService;
	/*
	 * var param={questionType:questionType,desc:desc,name:name,phone:phone};
	 * */
	@ResponseBody
	@RequestMapping("/question/submitQuestion")
	public String submitQuestion(
			HttpServletRequest request,
			@RequestParam(required=true)int questiontype,
			@RequestParam(required=true)String questiondesc
			)throws Exception{
		JSONObject js = new JSONObject();
		String type = QuestionTypeEnum.getEnumString(questiontype);
		if(type == null || type.trim().length() ==0){
			js.accumulate("code", -1);
			js.accumulate("msg", "Illegal submission");
			return js.toString();
		}
		
		questiondesc = HtmlUtils.htmlEscape(questiondesc) ;
        if(questiondesc.length() >500){
        	js.accumulate("code", -1);
			js.accumulate("msg", "content are too long");
			return js.toString();
        }
        
        Fuser fuser = this.frontUserService.findById(GetSession(request).getFid());
        if(!fuser.isFisTelephoneBind()){
        	js.accumulate("code", -1);
			js.accumulate("msg", "Please bind the phone first");
			return js.toString();
        }
        if(fuser.getFtradePassword() == null ||fuser.getFtradePassword().trim().length() ==0){
        	js.accumulate("code", -1);
			js.accumulate("msg", "Please set the transaction password first");
			return js.toString();
        }
        if(!fuser.getFpostRealValidate()){
        	js.accumulate("code", -1);
			js.accumulate("msg", "Please carry out real name authentication first");
			return js.toString();
        }
		
		Fquestion fquestion = new Fquestion() ;
		fquestion.setFuser(GetSession(request)) ;
		fquestion.setFcreateTime(Utils.getTimestamp()) ;
		fquestion.setFdesc(questiondesc) ;
		fquestion.setFstatus(QuestionStatusEnum.NOT_SOLVED) ;
		fquestion.setFtype(questiontype) ;

		this.frontQuestionService.save(fquestion) ;
		js.accumulate("code",0);
		js.accumulate("msg", "The problem is submitted successfully. Please wait patiently for the administrator to reply");
		return js.toString();
	}
	
	/* * /question/cancelQuestion.html?qid="+id+"&currentPage type=
	 * */
	@ResponseBody
	@RequestMapping("/question/delquestion")
	public String delquestion(
			HttpServletRequest request,
			@RequestParam(required=true)int fid
			) throws Exception{
		JSONObject js = new JSONObject();
		try {
			Fquestion fquestion = this.frontQuestionService.findById(fid) ;
			if(fquestion!=null && fquestion.getFuser().getFid()==GetSession(request).getFid()){
				this.frontQuestionService.delete(fquestion) ;
			}else{
				js.accumulate("code", -1);
				js.accumulate("msg", "Illegal operation");
				return js.toString();
			}
		} catch (Exception e) {
			js.accumulate("code", -1);
			js.accumulate("msg", "Network anomaly");
			return js.toString();
		}
		
		js.accumulate("code",0);
		js.accumulate("msg", "Delete successfully");
		return js.toString();
	}
}
