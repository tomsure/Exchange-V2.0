package com.broctagon.webgateway.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.broctagon.webgateway.constant.Constant;
import com.broctagon.webgateway.constant.Path;
import com.broctagon.webgateway.model.manage.ChangeMobileRequest;
import com.broctagon.webgateway.model.manage.Identity1Request;
import com.broctagon.webgateway.model.manage.KycRequest;
import com.broctagon.webgateway.service.ManageServService;
import com.broctagon.webgateway.service.ReportServService;
import com.broctagon.webgateway.util.MessageUtil;

@Controller
public class SecurityController {
	
	@Autowired
	private MessageUtil messageUtil;

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private SimpMessageSendingOperations messageSender;		
	
	@Autowired
	private ReportServService reportServService;
	
	@Autowired
	private ManageServService manageServService;

	@MessageMapping("/security/home")
	public void securityHome(String message,  @Header("simpSessionId") String sessionId) {
		reportServService.sendMessageToReportServ(messageUtil.addTitleToMsg(message, sessionId));
	}
	
	@MessageMapping("/security/changeMobile")
	public void changeMobile(String message,  @Header("simpSessionId") String sessionId) {
		String email = MessageUtil.getContentFromMsg(message, Constant.EMAIL);
		String emailCode = MessageUtil.getContentFromMsg(message, Constant.EMAIL_CODE);
		if(emailCode.equals(Constant.emailMaps.get(email))) {
			ChangeMobileRequest changeMobileRequest = new ChangeMobileRequest(Integer.parseInt(MessageUtil.getTag(message)), MessageUtil.getRequestId(message), Integer.parseInt(MessageUtil.getContentFromMsg(message, Constant.USER_ID)), email,  MessageUtil.getContentFromMsg(message, "MobilePhone"), MessageUtil.getContentFromMsg(message, "SmsCode"));
			manageServService.sendMessageToManagerSer(messageUtil.addTitleToMsg(Constant.GSON.toJson(changeMobileRequest), sessionId));
		}else {
			messageSender.convertAndSend(Path.PREFIX + Path.CHANGE_MOBILE + sessionId, "{\"Tag\":81921,\"SessionID\":\"" + sessionId + "\",\"Status\":\"-100\"}");
		}
	}
	
	@MessageMapping("/security/changeLoginPwd")
	public void changeLoginPwd(String message,  @Header("simpSessionId") String sessionId) {
		manageServService.sendMessageToManagerSer(messageUtil.addTitleToMsg(message, sessionId));
	}
	
	@MessageMapping("/security/changeTransactionPwd")
	public void changeTransactionPwd(String message,  @Header("simpSessionId") String sessionId) {
		manageServService.sendMessageToManagerSer(messageUtil.addTitleToMsg(message, sessionId));
	}
	
	@MessageMapping("/security/bindGoogleAuth")
	public void bindGoogleAuth(String message,  @Header("simpSessionId") String sessionId) {
		manageServService.sendMessageToManagerSer(messageUtil.addTitleToMsg(message, sessionId));
	}
	
	@MessageMapping("/security/identity")
	public void identity(String message,  @Header("simpSessionId") String sessionId) {
		manageServService.sendMessageToManagerSer(messageUtil.addTitleToMsg(message, sessionId));
	}
	
	@MessageMapping("/security/identity1")
	public void identity1(String message,  @Header("simpSessionId") String sessionId) {
		redisTemplate.opsForValue().set(MessageUtil.getContentFromMsg(message, Constant.USER_ID), message);
	}
	
	@MessageMapping("/security/identity2")
	public void identity2(String message,  @Header("simpSessionId") String sessionId) {
		String identity1 = redisTemplate.opsForValue().get(MessageUtil.getContentFromMsg(message, Constant.USER_ID));
		if(StringUtils.isNotEmpty(identity1)){
			KycRequest kycRequest = new KycRequest(Constant.GSON.fromJson(identity1, Identity1Request.class));
			kycRequest.setImg1(MessageUtil.getContentFromMsg(message, "img1"));
			kycRequest.setImg2(MessageUtil.getContentFromMsg(message, "img2"));
			kycRequest.setImg3(MessageUtil.getContentFromMsg(message, "img3"));
			manageServService.sendMessageToManagerSer(messageUtil.addTitleToMsg(Constant.GSON.toJson(kycRequest), sessionId));
		}
	}
	
	@MessageMapping("/security/getLoginHistory")
	public void getLoginHistory(String message,  @Header("simpSessionId") String sessionId) {
		reportServService.sendMessageToReportServ(messageUtil.addTitleToMsg(message, sessionId));
	}
	
	@MessageMapping("/security/getSettingsHistory")
	public void getSettingsHistory(String message,  @Header("simpSessionId") String sessionId) {
		reportServService.sendMessageToReportServ(messageUtil.addTitleToMsg(message, sessionId));	
	}
	
}
