package com.ruizton.main.comm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ruizton.main.model.Emailvalidate;
import com.ruizton.main.service.comm.redis.RedisUtil;

public class ValidateMap implements Serializable {
	
	@Autowired
	private RedisUtil redisUtil ;
	
	String messageSecret = "SENDMESSAGE_" ;
	String mailSecret = "SENDMAIL_" ;
	public void init(){
		this.redisUtil.removePattern(messageSecret+"*");
		this.redisUtil.removePattern(mailSecret+"*");
	}
	
	//短信
	public synchronized void putMessageMap(String key,MessageValidate messageValidate){
		key = messageSecret + key ;
		this.redisUtil.set(key, messageValidate) ;
	}
	
	public MessageValidate getMessageMap(String key){
		key = messageSecret + key ;
		return (MessageValidate)this.redisUtil.get(key) ;
	}
	public void removeMessageMap(String key){
		key = messageSecret + key ;
		this.redisUtil.remove(key) ;
	}
	
	//邮件
	public synchronized void putMailMap(String key,Emailvalidate messageValidate){
		key = mailSecret + key ;
		this.redisUtil.set(key, messageValidate) ;
	}
	
	public Emailvalidate getMailMap(String key){
		key = mailSecret + key ;
		return (Emailvalidate)this.redisUtil.get(key) ;
	}
	public void removeMailMap(String key){
		key = mailSecret + key ;
		this.redisUtil.remove(key) ;
	}
}
