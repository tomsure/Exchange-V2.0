package com.broctagon.webgateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.broctagon.webgateway.constant.Constant;
import com.broctagon.webgateway.service.C2cServService;
import com.broctagon.webgateway.util.MessageUtil;

@Controller
public class C2cController {
	
	@Value("${id}")
	private String wsId;
	
	@Value("${default.count}")
	private String count;
	
	@Value("${default.c2cBaseCoin}")
	private String defaultToken;
	
	@Autowired
	private MessageUtil messageUtil;

	@Autowired
	private C2cServService c2cServService;
	
	@MessageMapping("/c2c/home")
	public void c2cHome(String message, @Header("simpSessionId") String sessionId) {
		String requestId = MessageUtil.getRequestId(message);
		String token = MessageUtil.getContentFromMsg(message, Constant.TOKEN);
		String userIdStr = MessageUtil.getContentFromMsg(message, Constant.USER_ID);
		if("null".equals(token)) {
			token = defaultToken;
		}
		c2cServService.getC2cTokens(wsId, sessionId, requestId);
		c2cServService.getPendingOrder(wsId, sessionId, requestId, token, count);
		c2cServService.getMarketInfo(wsId, sessionId, requestId, token);
		c2cServService.getHistoricalMarketInfo(wsId, sessionId, requestId, token);
		if(userIdStr != null) {
			int userId = Integer.parseInt(userIdStr);
			c2cServService.getOpenOrderByUserId(wsId, sessionId, requestId, userId, token);
			c2cServService.getPendingOrderByUserId(wsId, sessionId, requestId, userId, token);
			c2cServService.getHistoricalOrderByUserId(wsId, sessionId, requestId, userId, token);
		}
	}
	
	@MessageMapping("/c2c/entrust")
	public void entrust(String message, @Header("simpSessionId") String sessionId) {
		c2cServService.sendMessageToC2cServ(messageUtil.addTitleToMsg(message, sessionId));
	}
	
	@MessageMapping("/c2c/trade")
	public void buy(String message, @Header("simpSessionId") String sessionId) {
		c2cServService.sendMessageToC2cServ(messageUtil.addTitleToMsg(message, sessionId));
	}
	
	@MessageMapping("/c2c/cancel")
	public void cancel(String message, @Header("simpSessionId") String sessionId) {
		c2cServService.sendMessageToC2cServ(messageUtil.addTitleToMsg(message, sessionId));
	}
}
