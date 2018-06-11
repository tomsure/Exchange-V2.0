package com.ruizton.main.auto;

import java.util.TimerTask;

import com.ruizton.util.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.ruizton.main.Enum.ValidateMessageStatusEnum;
import com.ruizton.main.model.Fvalidatemessage;
import com.ruizton.main.service.front.FrontSystemArgsService;
import com.ruizton.main.service.front.FrontValidateService;

public class AutoSendMessage extends TimerTask {

	@Autowired
	private TaskList taskList ;
	@Autowired
	private FrontSystemArgsService frontSystemArgsService ;
	@Autowired
	private FrontValidateService frontValidateService ;
	
	
	@Override
	public void run() {
		Integer id = this.taskList.getOneMessage() ;
		if(id!=null){
			Fvalidatemessage fvalidatemessage = this.frontValidateService.findFvalidateMessageById(id) ;
			if(fvalidatemessage==null){
				return ;
			}
			
			boolean retCode;
			try {
//				retCode = MessagesUtils.send(this.frontSystemArgsService.getSystemArgs(ConstantKeys.MESSAGE_NAME).trim(),
//						this.frontSystemArgsService.getSystemArgs(ConstantKeys.MESSAGE_PASSWORD).trim(),
//						this.frontSystemArgsService.getSystemArgs(ConstantKeys.MESSAGE_KEY).trim(),
//						fvalidatemessage.getFphone(), fvalidatemessage.getFcontent());
				//调用chain提供短信通道
				SendSMSCF sms = new SendSMSCF();
				if((fvalidatemessage.getFphone()).indexOf("+") > -1)
				{
//					retCode = sms.send(fvalidatemessage.getFphone(),
//							fvalidatemessage.getFcontent(),
//							this.frontSystemArgsService.getSystemArgs(ConstantKeys.chainsmsurl).trim(),
//							this.frontSystemArgsService.getSystemArgs(ConstantKeys.chainusercodeGJ).trim(),
//							this.frontSystemArgsService.getSystemArgs(ConstantKeys.chainuserPassGJ).trim());
					Client client = new Client(this.frontSystemArgsService.getSystemArgs(ConstantKeys.chainusercodeGJ).trim(), this.frontSystemArgsService.getSystemArgs(ConstantKeys.chainuserPassGJ).trim());
					client.mdSmsSend_g("00"+fvalidatemessage.getFphone().replaceAll("\\+",""), fvalidatemessage.getFcontent(), "",
							"", "");
				}else
				{
					retCode = sms.send(fvalidatemessage.getFphone(),
							fvalidatemessage.getFcontent(),
							this.frontSystemArgsService.getSystemArgs(ConstantKeys.chainsmsurl).trim(),
							this.frontSystemArgsService.getSystemArgs(ConstantKeys.chainusercode).trim(),
							this.frontSystemArgsService.getSystemArgs(ConstantKeys.chainuserPass).trim());
				}
				fvalidatemessage.setFsendTime(Utils.getTimestamp()) ;
				fvalidatemessage.setFstatus(ValidateMessageStatusEnum.Send) ;
				this.frontValidateService.updateFvalidateMessage(fvalidatemessage) ;
				
				System.out.println(fvalidatemessage.getFcontent());
			} catch (Exception e) {
				taskList.returnMessageList(id) ;
				e.printStackTrace();
			}
		}
		
	}
}
