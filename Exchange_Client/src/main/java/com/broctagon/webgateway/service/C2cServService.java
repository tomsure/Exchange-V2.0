package com.broctagon.webgateway.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.broctagon.webgateway.constant.Constant;
import com.broctagon.webgateway.model.c2c.C2cCoinsRequest;
import com.broctagon.webgateway.model.c2c.C2cHisotricalMarketInfoRequest;
import com.broctagon.webgateway.model.c2c.C2cHistoricalOrderByUserIdRequest;
import com.broctagon.webgateway.model.c2c.C2cMarketInfoRequest;
import com.broctagon.webgateway.model.c2c.C2cOpenOrderByUserIdRequest;
import com.broctagon.webgateway.model.c2c.C2cPendingOrderByUserIdRequest;
import com.broctagon.webgateway.model.c2c.C2cPendingOrderRequest;

@Service
public class C2cServService {

	@Value("${mq.routingKey.c2cServ}")
	private String routingKey;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void getC2cTokens(String wsId, String sessionId, String requestId) {
		C2cCoinsRequest c2cCoinsRequest = new C2cCoinsRequest(wsId, sessionId, requestId);
		System.err.println(Constant.GSON.toJson(c2cCoinsRequest));
		rabbitTemplate.convertAndSend(routingKey, Constant.GSON.toJson(c2cCoinsRequest));
	}

	public void getPendingOrder(String wsId, String sessionId, String requestId, String token, String count) {
		C2cPendingOrderRequest c2cPendingOrderRequest = new C2cPendingOrderRequest();
		c2cPendingOrderRequest.setWsId(wsId);
		c2cPendingOrderRequest.setSessionId(sessionId);
		c2cPendingOrderRequest.setRequestId(requestId);
		c2cPendingOrderRequest.setCoinName(token);
		System.err.println(Constant.GSON.toJson(c2cPendingOrderRequest));
		rabbitTemplate.convertAndSend(routingKey, Constant.GSON.toJson(c2cPendingOrderRequest));
	}

	public void getOpenOrderByUserId(String wsId, String sessionId, String requestId, int userId, String token) {
		C2cOpenOrderByUserIdRequest openOrderByUserIdRequest = new C2cOpenOrderByUserIdRequest(wsId, sessionId, requestId, userId, token);
		System.err.println(Constant.GSON.toJson(openOrderByUserIdRequest));
		rabbitTemplate.convertAndSend(routingKey, Constant.GSON.toJson(openOrderByUserIdRequest));
	}

	public void getHistoricalOrderByUserId(String wsId, String sessionId, String requestId, int userId, String token) {
		C2cHistoricalOrderByUserIdRequest historicalOrderByUserIdRequest = new C2cHistoricalOrderByUserIdRequest(wsId, sessionId, requestId, userId, token);
		System.err.println(Constant.GSON.toJson(historicalOrderByUserIdRequest));
		rabbitTemplate.convertAndSend(routingKey, Constant.GSON.toJson(historicalOrderByUserIdRequest));
	}

	public void getMarketInfo(String wsId, String sessionId, String requestId, String token) {
		C2cMarketInfoRequest marketInfoRequest = new C2cMarketInfoRequest(wsId, sessionId, requestId, token);
		System.err.println(Constant.GSON.toJson(marketInfoRequest));
		rabbitTemplate.convertAndSend(routingKey, Constant.GSON.toJson(marketInfoRequest));
	}

	public void sendMessageToC2cServ(String message) {
		System.err.println(message);
		rabbitTemplate.convertAndSend(routingKey, message);
	}

	public void getHistoricalMarketInfo(String wsId, String sessionId, String requestId, String token) {
		C2cHisotricalMarketInfoRequest historicalMarketInfoRequest = new C2cHisotricalMarketInfoRequest(wsId, sessionId, requestId, token);
		System.err.println(Constant.GSON.toJson(historicalMarketInfoRequest));
		rabbitTemplate.convertAndSend(routingKey, Constant.GSON.toJson(historicalMarketInfoRequest));
	}

	public void getPendingOrderByUserId(String wsId, String sessionId, String requestId, int userId, String token) {
		C2cPendingOrderByUserIdRequest c2cPendingOrderByUserIdRequest = new C2cPendingOrderByUserIdRequest();
		c2cPendingOrderByUserIdRequest.setWsId(wsId);
		c2cPendingOrderByUserIdRequest.setSessionId(sessionId);
		c2cPendingOrderByUserIdRequest.setRequestId(requestId);
		c2cPendingOrderByUserIdRequest.setUserId(userId);
		c2cPendingOrderByUserIdRequest.setCoinName(token);
		rabbitTemplate.convertAndSend(routingKey, Constant.GSON.toJson(c2cPendingOrderByUserIdRequest));
	}
}
