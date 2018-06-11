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
public class OrderServListener {

	@Autowired
	private SimpMessageSendingOperations messageSender;
	
	@RabbitListener(queues="ORDER.ANS." + "${id}")
	public void listener(Message rabbitMessage) {
		String message = new String(rabbitMessage.getBody());
		String tag = MessageUtil.getTag(message);
		String sessionId = MessageUtil.getSessionId(message);
		switch (Integer.parseInt(tag)) {
			case Tag.ORDER_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.ORDER + sessionId, message);
				break;
			case Tag.ORDER_FAIL_BY_TRANS_PWD:
				messageSender.convertAndSend(Path.PREFIX + Path.ORDER_FAIL_BY_TRANS_PWD + sessionId, message);
				break;
			case Tag.FAIL_CANCEL_ORDER:
				messageSender.convertAndSend(Path.PREFIX + Path.FAIL_CANCEL_ORDER + sessionId, message);
				break;
			case Tag.MATCHING_SUCCESS:
				messageSender.convertAndSend(Path.PREFIX + Path.MATCHING_SUCCESS + sessionId, message);
				break;
			case Tag.CANCEL_ORDER_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.CANCEL_ORDER + sessionId, message);
				break;
			default:
				break;
			}
	}
	
}
