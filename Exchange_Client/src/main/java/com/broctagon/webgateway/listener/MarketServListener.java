package com.broctagon.webgateway.listener;

import java.util.Set;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.broctagon.webgateway.constant.Constant;
import com.broctagon.webgateway.constant.Path;
import com.broctagon.webgateway.constant.Tag;
import com.broctagon.webgateway.util.MessageUtil;

@Service
public class MarketServListener {

	@Autowired
	private SimpMessageSendingOperations messageSender;
	
	@RabbitListener(queues="MARKET.ANS." + "${id}")
	public void listener(Message rabbitMessage) {
		String message = new String(rabbitMessage.getBody());
		System.err.println(message);
		String tag = MessageUtil.getTag(message);
		String token = MessageUtil.getContentFromMsg(message, "Symbol");
		Set<String> sessionIds = Constant.needToPushUsers.get(token);
		switch (Integer.parseInt(tag)) {
			case Tag.COIN_MARKET_RESPONSE:
				sessionIds.parallelStream().forEach(sessionId -> {
					messageSender.convertAndSend(Path.PREFIX + Path.COIN_MARKET + sessionId, message);
				});
				break;
			case Tag.HISTORICAL_BARS_RESPONSE:
				sessionIds.parallelStream().forEach(sessionId -> {
					messageSender.convertAndSend(Path.PREFIX + Path.HISTORICAL_BARS + sessionId, message);
				});
				break;
		default:
			break;
		}
	}
	
}
