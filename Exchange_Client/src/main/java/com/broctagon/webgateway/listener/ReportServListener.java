package com.broctagon.webgateway.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.broctagon.webgateway.constant.Path;
import com.broctagon.webgateway.constant.Tag;
import com.broctagon.webgateway.util.MessageUtil;

@Service
public class ReportServListener {
	
	@Autowired
	private SimpMessageSendingOperations messageSender;

	@RabbitListener(queues="WebReportQueue." + "${id}")
	public void listener(Message rabbitMessage) {
		String message = new String(rabbitMessage.getBody());
		System.err.println("Report recieved: " + message);
		String tag = MessageUtil.getTag(message);
		String sessionId = MessageUtil.getSessionId(message);
		switch (Integer.parseInt(tag)) {
			case Tag.COIN_LIST_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.COIN_LIST + sessionId, message);
				break;
			case Tag.GET_ALL_TOKENS_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.GET_ALL_TOKENS + sessionId, message);
				break;
			case Tag.HISTORICAL_BARS_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.HISTORICAL_BARS + sessionId, message);
				break;
			case Tag.COIN_MARKET_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.COIN_MARKET + sessionId, message);
				break ;
			case Tag.COIN_MARKET_SUMMARY_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.COIN_MARKET_SUMMARY + sessionId, message);
				break ;
			case Tag.PENDING_ORDER_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.PENDING_ORDERS + sessionId, message);
				break;
			case Tag.USER_ASSERTS_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.USER_ASSERTS + sessionId, message);
				break;
			case Tag.USRE_LOGIN_HISTORY_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.USRE_LOGIN_HISTORY + sessionId, message);
				break;
			case Tag.USER_PENDING_ORDERS_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.USER_PENDING_ORDERS + sessionId, message);
				break;
			case Tag.USER_HISTORICAL_ORDERS_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.USER_HISTORICAL_ORDERS + sessionId, message);
				break;
			case Tag.USER_KYC_STATUS_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.USER_KYC_STATUS + sessionId, message);
				break;
			case Tag.USER_ASSETS_BY_ONE_TOKEN_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.TOKEN_ASSETS + sessionId, message);
				break;
			default:
				break;
		}
	}
}
