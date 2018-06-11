package com.broctagon.webgateway.listener;

import java.util.Set;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.broctagon.webgateway.constant.Constant;
import com.broctagon.webgateway.constant.Path;
import com.broctagon.webgateway.util.MessageUtil;

@Service
public class OrderBookListener {

	@Autowired
	private SimpMessageSendingOperations messageSender;
	
	@RabbitListener(queues="ORDERBOOK." + "${id}")
	public void listener(Message rabbitMessage) {
		String message = new String(rabbitMessage.getBody());
		String token = MessageUtil.getTag(message);
		if(Constant.needToPushUsers.containsKey(token)) {
			Set<String> sessionIds = Constant.needToPushUsers.get(token);
			sessionIds.parallelStream().forEach(sessionId -> {
				messageSender.convertAndSend(Path.PREFIX + Path.PENDING_ORDERS + sessionId, message);
			});
		}
	}
}
