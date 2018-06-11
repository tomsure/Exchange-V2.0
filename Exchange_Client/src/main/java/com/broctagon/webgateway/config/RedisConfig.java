package com.broctagon.webgateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
	
	@Autowired
	private Environment env;
	
	@Bean
	public JedisConnectionFactory redisConnectionFactory() {
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
		connectionFactory.setHostName(env.getProperty("redis.host"));
		connectionFactory.setPort(Integer.parseInt(env.getProperty("redis.port")));
		connectionFactory.setPassword(env.getProperty("redis.password"));
		connectionFactory.setUsePool(true);
		connectionFactory.setDatabase(Integer.parseInt(env.getProperty("redis.database")));
		connectionFactory.setUsePool(true);
		JedisPoolConfig poolConfig = new JedisPoolConfig();  
		poolConfig.setMaxIdle(Integer.parseInt(env.getProperty("redis.pool.maxIdle")));  
	    poolConfig.setMinIdle(Integer.parseInt(env.getProperty("redis.pool.minIdle"))); 
	    connectionFactory.setPoolConfig(poolConfig);
		return connectionFactory;
	}
	
	@Bean
	public RedisTemplate<String, String> redisTemplate(){
		RedisTemplate<String, String> redisTemplate = new StringRedisTemplate();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setDefaultSerializer(new StringRedisSerializer());
		return redisTemplate;
	}	
}
