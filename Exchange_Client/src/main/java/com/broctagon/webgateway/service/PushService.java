package com.broctagon.webgateway.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.broctagon.webgateway.constant.Constant;

public class PushService {

	public static void addPushSessionId(String token, String sessionId) {
		if(Constant.needToPushUsers.containsKey(token)) {
			Constant.needToPushUsers.get(token).add(sessionId);
		}else {
			Set<String> sessionIds = Collections.synchronizedSet(new HashSet<String>());
			sessionIds.add(sessionId);
			Constant.needToPushUsers.put(token, sessionIds);
		}
	}
	
	public static void removePushSessionId(String sessionId) {
		Constant.needToPushUsers.entrySet().parallelStream().forEach(entry ->{
    		if(entry.getValue().contains(sessionId)) {
    			entry.getValue().remove(sessionId);
    		}
    	});
	}
	
}
