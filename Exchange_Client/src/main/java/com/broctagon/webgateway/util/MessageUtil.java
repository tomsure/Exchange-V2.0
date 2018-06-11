package com.broctagon.webgateway.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.broctagon.webgateway.constant.Constant;
import com.broctagon.webgateway.constant.Tag;

@Component
public class MessageUtil {

	@Value("${id}")
	private String wsId;
	
	public String addTitleToMsg(String message, String sessionId) {
		if(StringUtils.isEmpty(message)) {
			return null;
		}else if(message.indexOf("[") == 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("{ ")
			  .append("\"").append(Constant.WS_ID).append("\" : ").append(wsId).append(", ")
			  .append("\"").append(Constant.SESSION_ID).append("\" : \"").append(sessionId).append("\", ")
			  .append("\"").append(Constant.CONTENT).append("\" : ").append(message)
			  .append("}");
			return sb.toString();
		}else if(message.contains("{")) {
			return message.replace("{", "{ \"" + Constant.WS_ID + "\" : " + wsId + ", \""+ Constant.SESSION_ID + "\" : \"" + sessionId + "\", ");
		}else {
			throw new IllegalArgumentException("Message is not json format. The message is : " + message);
		}
		 
	}

	
	public String addTitleToMsgWsSend(String message, String sessionId, String requestId) {
		if(StringUtils.isEmpty(message)) {
			return null;
		}else if(message.indexOf("[") == 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("{ ")
			  .append("\"").append(Constant.WS_ID).append("\" : ").append(wsId).append(", ")
			  .append("\"").append(Constant.SESSION_ID).append("\" : \"").append(sessionId).append("\", ")
			  .append("\"").append(Constant.REQUEST_ID).append("\" : \"").append(requestId).append("\", ")
			  .append("\"").append(Constant.CONTENT).append("\" : ").append(message)
			  .append("}");
			return sb.toString();
		}else if(message.contains("{")) {
			return message.replace("{", "{ \"" + Constant.WS_ID + "\" : " + wsId + ", \""+ Constant.SESSION_ID + "\" : \"" + sessionId + "\", \"" + Constant.REQUEST_ID + "\" : \"" + requestId + "\", ");
		}else {
			throw new IllegalArgumentException("Message is not json format. The message is : " + message);
		}
		 
	}
	
	public static String getTag(String message) {
		if(message.contains(Tag.TAG)) {
			return getContentFromMsg(message, Tag.TAG);
		}else {
			throw new IllegalArgumentException("The request doesn't contain tag. Please check. The request is : " + message);
		}
	}

	public static String getContentFromMsg(String message, String mark) {
		String content = null;
		if(message.contains(mark)) {
			content = message.substring(message.indexOf(mark));
			int start = content.indexOf(":");
			int end = content.indexOf(",");
			if(end == -1) {
				end = content.length() - 1;
			}
			content = content.substring(start + 1, end).trim().replaceAll("\"", "");
		}
		return content;
	}
	
	public static String getSessionId(String message) {
		if(message.contains(Constant.SESSION_ID)) {
			return getContentFromMsg(message, Constant.SESSION_ID);
		}else {
			throw new IllegalArgumentException("The request doesn't contain sessionId. Please check. The request is : " + message);
		}
	}
	
	public static String getRequestId(String message) {
		if(message.contains(Constant.REQUEST_ID)) {
			return getContentFromMsg(message, Constant.REQUEST_ID);
		}else {
			throw new IllegalArgumentException("The request doesn't contain requestId. Please check. The request is : " + message);
		}
	}
}
