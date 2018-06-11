package com.broctagon.webgateway.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.broctagon.webgateway.constant.Constant;
import com.broctagon.webgateway.service.OrderService;
import com.broctagon.webgateway.service.PushService;
import com.broctagon.webgateway.service.ReportServService;
import com.broctagon.webgateway.util.MessageUtil;
@Controller
public class TradingController {
	
	@Value("${id}")
	private String wsIdStr;
	
	@Value("${default.baseCoin}")
	private String defaultBaseCoin;
	
	@Value("${default.count}")
	private String count;
	
	@Value("${default.timeframe}")
	private String defaultTimeframe;
	
	@Value("${default.token}")
	private String defaultToken;
	
	@Autowired
	private MessageUtil messageUtil;

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ReportServService reportServService;
	
	@MessageMapping("/token/home")
	public void home(String message, @Header("simpSessionId") String sessionId) {
		int wsId = Integer.parseInt(wsIdStr);
		String requestId = MessageUtil.getRequestId(message);
		String token = MessageUtil.getContentFromMsg(message, Constant.TOKEN);
		String userId = MessageUtil.getContentFromMsg(message, Constant.USER_ID);
		String timeframe = MessageUtil.getContentFromMsg(message, "Timeframe");
		String baseCoin = defaultBaseCoin;
		if("null".equals(token)) {
			token = defaultToken;
		}else {
			baseCoin = token.split("/")[1];
		}
		if(StringUtils.isEmpty(timeframe)) {
			timeframe = defaultTimeframe;
		}
		PushService.addPushSessionId(token, sessionId);
		reportServService.getTradeTokens(wsId, sessionId, requestId);
		reportServService.getPendingOrdersByToken(wsId, sessionId, requestId, token);
		reportServService.getMarketInfoByToken(wsId, sessionId, requestId, baseCoin);
		reportServService.getMarketSummary(wsId, sessionId, requestId, token);
		reportServService.getHistoricalBarsByToken(wsId, sessionId, requestId, token, timeframe);
		if(!StringUtils.isEmpty(userId) || !"null".equals(userId)) {
			reportServService.getAssetsByToken(wsId, sessionId, requestId, Integer.parseInt(userId), baseCoin, token.split("/")[0]);
			reportServService.getPendingOrdersByUserId(wsId, sessionId, requestId, Integer.parseInt(userId));
			reportServService.getHistroicalOrdersByUserId(wsId, "All", sessionId, requestId, Integer.parseInt(userId));
		}
	}
	
	@MessageMapping("/token/trade")
	public void buy(String message, @Header("simpSessionId") String sessionId) {
		orderService.sendMessageToOrderServ(messageUtil.addTitleToMsg(message, sessionId));
	}
	
	@MessageMapping("/token/reportService")
	public void reportService(String message, @Header("simpSessionId") String sessionId) {
		reportServService.sendMessageToReportServ(messageUtil.addTitleToMsg(message, sessionId));
	}
	
	@MessageMapping("/token/cancelPendingOrder")
	public void cancelPendingOrder(String message, @Header("simpSessionId") String sessionId) {
		orderService.sendMessageToOrderServ(messageUtil.addTitleToMsg(message, sessionId));
	}
	
	@MessageMapping("/token/pro")
	public void proVersion(String message, @Header("simpSessionId") String sessionId) {
		int wsId = Integer.parseInt(wsIdStr);
		String token = MessageUtil.getContentFromMsg(message, Constant.TOKEN);
		String requestId = MessageUtil.getRequestId(message);
		String userId = MessageUtil.getContentFromMsg(message, Constant.USER_ID);
		String timeframe = MessageUtil.getContentFromMsg(message, "Timeframe");
		if("null".equals(token)) {
			token = defaultToken;
		}
		if("null".equals(timeframe)) {
			timeframe = defaultTimeframe;
		}
		reportServService.getMarketInfoByToken(wsId, sessionId, requestId, token);
		reportServService.getHistoricalBarsByToken(wsId, sessionId, requestId, token, timeframe);
		reportServService.getPendingOrdersByToken(wsId, sessionId, requestId, token);
		reportServService.getHistroicalOrdersByUserId(wsId, token, sessionId, requestId, userId == null ? 0 : Integer.parseInt(userId));
	}
	
	@MessageMapping("/token/getPendingOrders")
	public void getPendingOrders(String message, @Header("simpSessionId") String sessionId) {
		int wsId = Integer.parseInt(wsIdStr);
		String requestId = MessageUtil.getRequestId(message);
		String userId = MessageUtil.getContentFromMsg(message, Constant.USER_ID);
		reportServService.getPendingOrdersByUserId(wsId, sessionId, requestId, Integer.parseInt(userId));
	}
	
	@MessageMapping("/token/getHistoricalOrders")
	public void getHistoricalOrders(String message, @Header("simpSessionId") String sessionId) {
		int wsId = Integer.parseInt(wsIdStr);
		String requestId = MessageUtil.getRequestId(message);
		String userId = MessageUtil.getContentFromMsg(message, Constant.USER_ID);
		String token = MessageUtil.getContentFromMsg(message, Constant.TOKEN);
		reportServService.getHistroicalOrdersByUserId(wsId, token, sessionId, requestId, Integer.parseInt(userId));
	}
	
}
