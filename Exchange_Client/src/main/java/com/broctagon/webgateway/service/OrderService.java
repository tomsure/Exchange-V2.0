package com.broctagon.webgateway.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

	@Value("${mq.routingKey.orderServ}")
	private String routingKey;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void sendMessageToOrderServ(String message) {
		System.err.println(message);
		rabbitTemplate.convertAndSend(routingKey, message);
	}

}
