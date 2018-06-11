package com.broctagon.webgateway.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;



@Configuration
public class RabbitConfig {
	
	@Autowired
	private Environment env;

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public ConnectionFactory rabbitConnectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setHost(env.getProperty("mq.host"));
		connectionFactory.setPort(Integer.parseInt(env.getProperty("mq.port")));
		connectionFactory.setUsername(env.getProperty("mq.username"));
		connectionFactory.setPassword(env.getProperty("mq.password"));
		connectionFactory.setVirtualHost(env.getProperty("mq.vhost"));
		connectionFactory.setRequestedHeartBeat(0);
		connectionFactory.setChannelCacheSize(100);
		connectionFactory.setPublisherConfirms(false);
		connectionFactory.setPublisherReturns(false);
		return connectionFactory;
	}
	
	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(env.getProperty("mq.exchange"), false, false);
	}
	
	@Bean
	public RabbitAdmin rabbitAdmin() {
		RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitConnectionFactory());
		rabbitAdmin.declareQueue();
		rabbitAdmin.setAutoStartup(true);
		return rabbitAdmin;
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = rabbitAdmin().getRabbitTemplate();
		rabbitTemplate.setExchange(env.getProperty("mq.exchange"));
		return rabbitTemplate;
	}
	
}
