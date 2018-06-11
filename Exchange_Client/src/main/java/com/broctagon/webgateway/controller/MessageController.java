package com.broctagon.webgateway.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import com.broctagon.webgateway.constant.Constant;
import com.broctagon.webgateway.model.util.EmailSendRequest;
import com.broctagon.webgateway.service.ManageServService;
import com.broctagon.webgateway.util.MessageUtil;
import com.broctagon.webgateway.util.RandomCode;

@RestController
public class MessageController {
	
	@Value("${id}")
	private String wsId;
	
	@Value("${mq.routingKey.securityServ}")
	private String routingKey;
	
	@Autowired
	private MessageUtil messageUtil;
	
	@Autowired
	private ManageServService manageServService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@MessageMapping("/message/sendEmail")
	public void sendEmail(String message, @Header("simpSessionId") String sessionId) {
		String randonCode = RandomCode.getRandomCode();
		String email = MessageUtil.getContentFromMsg(message, Constant.EMAIL);
		EmailSendRequest emailSendRequest = new EmailSendRequest();
		emailSendRequest.setRequestID(MessageUtil.getRequestId(Constant.REQUEST_ID));
		emailSendRequest.setDest(email);
		emailSendRequest.setCode(randonCode);
		emailSendRequest.setwSID(Integer.parseInt(wsId));
		emailSendRequest.setSessionID(sessionId);
		Constant.emailMaps.put(email, randonCode); 
		System.err.println(Constant.GSON.toJson(emailSendRequest));
		rabbitTemplate.convertAndSend(routingKey, Constant.GSON.toJson(emailSendRequest));
	}
	
	@MessageMapping("/message/sendSms")
	public void sendSms(String message, @Header("simpSessionId") String sessionId) {
		manageServService.sendMessageToManagerSer(messageUtil.addTitleToMsg(message, sessionId));
	}

}
