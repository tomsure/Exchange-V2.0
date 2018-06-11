package com.ruizton.main.service.comm.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class MessageSubscriptor {
	
	@Autowired
	private JedisConnectionFactory jedisConnectionFactory ;
	
	public void subscribe(BinaryJedisPubSub jedisPubSub, byte[]... channels) {  
        Jedis jedis = null;  
        try {  
        	jedis = jedisConnectionFactory.getConnection().getNativeConnection() ;   
            jedis.subscribe(jedisPubSub, channels);  
        }catch(Exception e){
        	e.printStackTrace(); 
        } finally {  
            jedis.close();  
        }  
    }  
  
    /** 
     * 监听消息通道 
     * @param jedisPubSub - 监听任务 
     * @param channels - 要监听的消息通道 
     */  
    public void subscribe(JedisPubSub jedisPubSub, String... channels) {  
        Jedis jedis = null;  
        try {  
        	jedis = jedisConnectionFactory.getConnection().getNativeConnection() ;    
            jedis.subscribe(jedisPubSub, channels);  
        }catch(Exception e){
        	e.printStackTrace(); 
        } finally {  
            jedis.close();  
        }  
    }  
}
