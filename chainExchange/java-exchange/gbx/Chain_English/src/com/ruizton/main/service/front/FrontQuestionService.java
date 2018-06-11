package com.ruizton.main.service.front;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruizton.main.dao.FmessageDAO;
import com.ruizton.main.dao.FquestionDAO;
import com.ruizton.main.dao.FuserDAO;
import com.ruizton.main.model.Fmessage;
import com.ruizton.main.model.Fquestion;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.service.BaseService;

@Service
public class FrontQuestionService extends BaseService {

	@Autowired
	public FquestionDAO fquestionDAO ;
	@Autowired
	public FuserDAO fuserDAO ;
	@Autowired
	private FmessageDAO fmessageDAO ;
	
	public void save(Fquestion fquestion){
		this.fquestionDAO.save(fquestion) ;
	}
	
	public List<Fquestion> findByUserId(int uid){
		return this.fquestionDAO.findByProperty("fuser.fid", uid) ;
	}
	
	public List<Fquestion> findAll(int uid,int status){
		Fuser fuser = this.fuserDAO.findById(uid) ;
		Fquestion fquestion = new Fquestion() ;
		fquestion.setFuser(fuser) ;
		fquestion.setFstatus(status) ;
		return this.fquestionDAO.findByExample(fquestion) ;
	}
	
	public int findByTodayQuestionCount(){
		return this.fquestionDAO.findByTodayQuestionCount() ;
	}
	
	public List<Fquestion> findByProperty(String key,Object value){
		return this.fquestionDAO.findByProperty(key, value) ;
	}
	
	public int findByParamCount(Map<String, Object> param) {
		return this.fquestionDAO.findByParamCount(param) ;
	}
	
	public List<Fquestion> findByParam(Map<String, Object> param,int firstResult,int maxResult,String order) {
		return this.fquestionDAO.findByParam(param, firstResult, maxResult, order) ;
	}
	public int findFmessageByParamCount(Map<String, Object> param) {
		return this.fmessageDAO.findFmessageByParamCount(param) ;
	}
	
	public List<Fmessage> findFmessageByParam(Map<String, Object> param,int firstResult,int maxResult,String order) {
		return this.fmessageDAO.findFmessageByParam(param, firstResult, maxResult, order) ;
	}
	
	public Fquestion findById(int id){
		Fquestion fquestion = this.fquestionDAO.findById(id) ;
		return fquestion ;
	}
	public void delete(Fquestion fquestion){
		this.fquestionDAO.delete(fquestion) ;
	}
	
	public Fmessage findFmessageById(int id){
		return this.fmessageDAO.findById(id) ;
	}
	public void updateFmessage(Fmessage fmessage){
		this.fmessageDAO.attachDirty(fmessage) ;
	}
}
