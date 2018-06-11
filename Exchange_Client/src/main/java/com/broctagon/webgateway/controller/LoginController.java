package com.broctagon.webgateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import com.broctagon.webgateway.constant.Constant;
import com.broctagon.webgateway.constant.Path;
import com.broctagon.webgateway.model.manage.ForgetLoginPwdRequest;
import com.broctagon.webgateway.model.manage.RegisterRequest;
import com.broctagon.webgateway.service.ManageServService;
import com.broctagon.webgateway.util.MessageUtil;

@RestController
public class LoginController {
	
	@Autowired
	private MessageUtil messageUtil;

	@Autowired
	private ManageServService manageServService;

	@Autowired
	private SimpMessageSendingOperations messageSender;		

	@MessageMapping("/user/login")
	public void login(String message, @Header("simpSessionId") String sessionId) {
		System.err.println(messageUtil.addTitleToMsg(message, sessionId));
		manageServService.sendMessageToManagerSer(messageUtil.addTitleToMsg(message, sessionId));
	}
	
	@MessageMapping("/user/logout")
	public void logout(String message, @Header("simpSessionId") String sessionId) {
		manageServService.sendMessageToManagerSer(messageUtil.addTitleToMsg(message, sessionId));
	}
	
	@MessageMapping("/user/registry")
	public void registry(String message, @Header("simpSessionId") String sessionId) {
		String email = MessageUtil.getContentFromMsg(message, Constant.EMAIL);
		String emailCode = MessageUtil.getContentFromMsg(message, Constant.EMAIL_CODE);
 		if(emailCode.equals(Constant.emailMaps.get(email))) {
			RegisterRequest registerRequest = new RegisterRequest(Integer.parseInt(MessageUtil.getTag(message)), email, MessageUtil.getContentFromMsg(message, "Password"), sessionId);
			manageServService.sendMessageToManagerSer(messageUtil.addTitleToMsg(Constant.GSON.toJson(registerRequest), sessionId));
		}else {
			messageSender.convertAndSend(Path.PREFIX + Path.REGISTRY + sessionId, "{\"Tag\":65553, \"UserID\":10026, \"SessionID\":\"" + sessionId + "\",\"Status\":\"-100\"}");
		}
	}
	
	@MessageMapping("/user/resetPwd")
	public void resetPwd(String message, @Header("simpSessionId") String sessionId) {
		String email = MessageUtil.getContentFromMsg(message, Constant.EMAIL);
		String emailCode = MessageUtil.getContentFromMsg(message, Constant.EMAIL_CODE);
		if(emailCode.equals(Constant.emailMaps.get(email))) {
			ForgetLoginPwdRequest forgetLoginPwdRequest = new ForgetLoginPwdRequest();
			forgetLoginPwdRequest.setEMail(email);
			forgetLoginPwdRequest.setPassword(MessageUtil.getContentFromMsg(message, "Password"));
			manageServService.sendMessageToManagerSer(messageUtil.addTitleToMsg(Constant.GSON.toJson(forgetLoginPwdRequest), sessionId));
		}else {
			messageSender.convertAndSend(Path.PREFIX + Path.FORGET_PWD + sessionId, "{\"Tag\":147457,\"SessionID\":\"" + sessionId + "\",\"Status\":-100}");
		}
	}
	
}
