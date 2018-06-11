package com.ruizton.main.service.comm.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.Jedis;

public class MessageSender {
	@Autowired
	private JedisConnectionFactory jedisConnectionFactory ;

	public void publish(String channel, String message) {  
			System.out.println("发送："+channel+","+message);
	        Jedis jedis = null ;
	        try {  
	        	jedis = jedisConnectionFactory.getConnection().getNativeConnection() ;  
	            jedis.publish(channel, message);  
	        }catch(Exception e){
	        	e.printStackTrace(); 
	        } finally {  
	            jedis.close();  
	        }  
	    }  
	  
	    /** 
	     * 推入消息到redis消息通道 
	     *  
	     * @param byte[] 
	     *            channel 
	     * @param byte[] 
	     *            message 
	     */  
	    public void publish(byte[] channel, byte[] message) {  
	        Jedis jedis = null;  
	        try {  
	        	jedis = jedisConnectionFactory.getConnection().getNativeConnection() ;  
	            jedis.publish(channel, message);  
	        }catch(Exception e){
	        	e.printStackTrace(); 
	        } finally {  
	            jedis.close();  
	        }  
	  
	    } 
}
