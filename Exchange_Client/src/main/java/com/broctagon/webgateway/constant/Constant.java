package com.broctagon.webgateway.constant;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;

public class Constant {
	
	public static final String WS_ID = "WSID";
	
	public static final String SESSION_ID = "SessionID";
	
	public static final String TOKEN = "Token";
	
	public static final String USER_ID = "UserID";
	
	public static final String CONTENT = "Content";
	
	public static final String REQUEST_ID = "RequestID";
	
	public static final String VERIFY_CODE = "vcode";
	
	public static final String EMAIL = "Email";
	
	public static final String EMAIL_CODE = "EmailCode";
	
	public static final String STATUS = "Status";
	
	public static Map<String, Set<String>> needToPushUsers = new ConcurrentHashMap<>();
	
	public static Map<String, String> emailMaps = new ConcurrentHashMap<>();

	public static final Gson GSON = new Gson();
}
