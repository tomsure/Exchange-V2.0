package com.ruizton.main.service.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruizton.main.Enum.CountLimitTypeEnum;
import com.ruizton.main.Enum.SendMailTypeEnum;
import com.ruizton.main.Enum.ValidateMailStatusEnum;
import com.ruizton.main.auto.TaskList;
import com.ruizton.main.comm.ValidateMap;
import com.ruizton.main.dao.EmailvalidateDAO;
import com.ruizton.main.dao.FcountlimitDAO;
import com.ruizton.main.dao.FsystemargsDAO;
import com.ruizton.main.dao.FuserDAO;
import com.ruizton.main.dao.FvalidateemailDAO;
import com.ruizton.main.dao.FvalidatemessageDAO;
import com.ruizton.main.model.Emailvalidate;
import com.ruizton.main.model.Fcountlimit;
import com.ruizton.main.model.Fsystemargs;
import com.ruizton.main.model.Fuser;
import com.ruizton.main.model.Fvalidateemail;
import com.ruizton.main.model.Fvalidatemessage;
import com.ruizton.main.service.BaseService;
import com.ruizton.util.Constant;
import com.ruizton.util.ConstantKeys;
import com.ruizton.util.Utils;

@Service
public class FrontValidateService extends BaseService{
	@Autowired
	protected EmailvalidateDAO emailvalidateDAO ;
	@Autowired
	protected FvalidateemailDAO validateemailsDAO ;
	@Autowired
	private FuserDAO fuserDAO ;
	@Autowired
	private FcountlimitDAO fcountlimitDAO ;
	@Autowired
	private FvalidatemessageDAO fvalidatemessageDAO ;
	@Autowired
	private TaskList taskList ;
	@Autowired
	private FsystemargsDAO fsystemargsDAO ;
	@Autowired
	private ValidateMap validateMap ;
	@Autowired
	private FvalidateemailDAO fvalidateemailDAO ;
	
	
	//用户注册邮件验证
	public boolean updateMailValidate(int uid,String uuid) throws Exception{
		boolean flag = false ;
		try{
			Emailvalidate emailvalidate = this.emailvalidateDAO.findByUidUuid(uid, uuid,SendMailTypeEnum.ValidateMail) ;
			emailvalidate.setFvalidateTime(Utils.getTimestamp()) ;
			this.emailvalidateDAO.attachDirty(emailvalidate) ;
				
			Fuser fuser = emailvalidate.getFuser() ;
			if(!fuser.getFisMailValidate()){
				fuser.setFisMailValidate(true) ;
				fuser.setFemail(emailvalidate.getFmail());
				this.fuserDAO.attachDirty(fuser) ;
				flag = true ;
			}
			return flag ;
		}catch(Exception e){
			e.printStackTrace() ;
			throw new RuntimeException() ;
		}
	}
	
	public String getSystemArgs(String key){
		String value = null ;
		List<Fsystemargs> list = this.fsystemargsDAO.findByFkey(key) ;
		if(list.size()>0){
			value = list.get(0).getFvalue() ;
		}
		return value ;
	}
	
	public boolean saveSendFindPasswordMail(String ip,Fuser fuser,String email,HttpServletRequest request){
		//发送激活邮件
		boolean flag = false ;
		try {
			String UUID = Utils.UUID() ;
			//发送给用户的邮件
			Fvalidateemail validateemails = new Fvalidateemail() ;
			validateemails.setFtitle(this.getSystemArgs(ConstantKeys.englishName)+" Login Password Recovery.") ;
			validateemails.setFcontent(
					this.getSystemArgs(ConstantKeys.findPasswordMail)
					.replace("#firstLevelDomain#", this.getSystemArgs(ConstantKeys.firstLevelDomain))
					.replace("#url#", new Constant().Domain+"validate/resetPwd.html?uid="+fuser.getFid()+"&&uuid="+UUID)
					.replace("#fulldomain#", this.getSystemArgs(ConstantKeys.fulldomain))
					.replace("#englishName#", this.getSystemArgs(ConstantKeys.englishName))) ;
			validateemails.setFcreateTime(Utils.getTimestamp()) ;
			validateemails.setFstatus(ValidateMailStatusEnum.Not_send) ;
			validateemails.setFuser(fuser) ;
			this.validateemailsDAO.save(validateemails) ;
			
			//验证并对应到用户的UUID
			Emailvalidate emailvalidate = new Emailvalidate() ;
			emailvalidate.setFcreateTime(Utils.getTimestamp()) ;
			emailvalidate.setFmail(email) ;
			emailvalidate.setFuser(fuser) ;
			emailvalidate.setFuuid(UUID) ;
			emailvalidate.setType(SendMailTypeEnum.FindPassword) ;
			this.emailvalidateDAO.save(emailvalidate) ;
			
			//加入邮件队列
			this.taskList.returnMailList(validateemails.getFid()) ;
			
			this.validateMap.putMailMap(ip+"_"+SendMailTypeEnum.FindPassword, emailvalidate) ;
			flag = true ;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException() ;
		}
		return flag ;
		
	}
	
	//找回密码邮件
	public Emailvalidate updateFindPasswordMailValidate(int uid,String uuid) throws Exception{
		Emailvalidate emailvalidate = null ;
		try{
			emailvalidate = this.emailvalidateDAO.findByUidUuid(uid, uuid,SendMailTypeEnum.FindPassword) ;
			
			if(emailvalidate==null || emailvalidate.getfNewUUid()!=null){
				return null ;
			}else{
				emailvalidate.setFvalidateTime(Utils.getTimestamp()) ;
				emailvalidate.setfNewUUid(Utils.UUID()) ;
				this.emailvalidateDAO.attachDirty(emailvalidate) ;
			}
			return emailvalidate ;
		}catch(Exception e){
			e.printStackTrace() ;
			throw new RuntimeException() ;
		}
	}
	
	//是否可以重试
	public int getLimitCount(String ip,int type){
		int maxLimit = new Constant().ErrorCountLimit ;
		if(type==CountLimitTypeEnum.AdminLogin){
			maxLimit = new Constant().ErrorCountAdminLimit ;
		}
		
		List<Fcountlimit> list = this.fcountlimitDAO.findByIpType(ip, type) ;
		if(list.size()==0){
			return maxLimit ;
		}else{
			Fcountlimit fcountlimit = list.get(0) ;
			if(Utils.getTimestamp().getTime()- fcountlimit.getFcreateTime().getTime()<2L*60*60*1000){
				return maxLimit - fcountlimit.getFcount() ;
			}else{
				return maxLimit ;
			}
		}
	}
	
	//记录一次错误记录
	public void updateLimitCount(String ip,int type){
		if(new Constant().closeLimit){return;}
		List<Fcountlimit> list = this.fcountlimitDAO.findByIpType(ip, type) ;
		if(list.size()==0){
			Fcountlimit fcountlimit = new Fcountlimit() ;
			fcountlimit.setFcount(1) ;
			fcountlimit.setFcreateTime(Utils.getTimestamp()) ;
			fcountlimit.setFip(ip) ;
			fcountlimit.setFtype(type) ;
			this.fcountlimitDAO.save(fcountlimit) ;
		}else{
			Fcountlimit fcountlimit = list.get(0) ;
			if(Utils.getTimestamp().getTime()- fcountlimit.getFcreateTime().getTime()<2*60*60*1000L){
				fcountlimit.setFcount(fcountlimit.getFcount()+1) ;
				fcountlimit.setFcreateTime(Utils.getTimestamp()) ;
				this.fcountlimitDAO.attachDirty(fcountlimit) ;
			}else{
				fcountlimit.setFcount(1) ;
				fcountlimit.setFcreateTime(Utils.getTimestamp()) ;
				this.fcountlimitDAO.attachDirty(fcountlimit) ;
			}
		}
	}
	
	public void deleteCountLimite(String ip,int type){
		List<Fcountlimit> list = this.fcountlimitDAO.findByIpType(ip, type) ;
		for(int i=0;i<list.size();i++){
			this.fcountlimitDAO.delete(list.get(i)) ;
		}
	}
	
	public Fvalidatemessage findFvalidateMessageById(int id){
		return this.fvalidatemessageDAO.findById(id) ;
	}
	public List<Fvalidatemessage> findFvalidateMessageByProperty(String key,Object value){
		return this.fvalidatemessageDAO.findByProperty(key, value) ;
	}
	
	public void updateFvalidateMessage(Fvalidatemessage fvalidatemessage){
		this.fvalidatemessageDAO.attachDirty(fvalidatemessage) ;
	}
	
	public void addFvalidateMessage(Fvalidatemessage fvalidatemessage){
		this.fvalidatemessageDAO.save(fvalidatemessage) ;
	}
	
	public void addFvalidateemail(Fvalidateemail fvalidateemail){
		this.fvalidateemailDAO.save(fvalidateemail) ;
	}
	
	public Fvalidateemail findValidateMailsById(int id){
		Fvalidateemail fvalidateemail = this.validateemailsDAO.findById(id) ;
		return fvalidateemail ;
	}
	public List<Fvalidateemail> findValidateMailsByProperty(String key,Object value){
		return this.validateemailsDAO.findByProperty(key, value) ;
	}
	
	public void updateValidateMails(Fvalidateemail validateemails){
		this.validateemailsDAO.attachDirty(validateemails) ;
	}
	
	public boolean canSendFindPwdMsg(
			int fid,
			int ev_id,
			String newuuid
			){
		boolean flag = false ;
		List<Emailvalidate> emailvalidates = this.emailvalidateDAO.canSendFindPwdMsg(fid, ev_id, newuuid) ;
		if(emailvalidates.size()>0){
			Emailvalidate emailvalidate = emailvalidates.get(0) ;
			if(Utils.getTimestamp().getTime() - emailvalidate.getFvalidateTime().getTime()<15*60*1000L){
				flag = true ;
			}
		}
		return flag ;
	}
	
}
