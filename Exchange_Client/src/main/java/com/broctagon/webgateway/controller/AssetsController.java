package com.broctagon.webgateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.broctagon.webgateway.service.ManageServService;
import com.broctagon.webgateway.service.ReportServService;
import com.broctagon.webgateway.util.MessageUtil;

@Controller
public class AssetsController {

	@Autowired
	private MessageUtil messageUtil;
	
	@Autowired
	private ReportServService reportServService;
	
	@Autowired
	private ManageServService manageServService;

	
	@MessageMapping("/assets/home")
	public void home(String message, @Header("simpSessionId") String sessionId) {
		reportServService.sendMessageToReportServ(messageUtil.addTitleToMsg(message, sessionId));
	}
	
	@MessageMapping("/assets/withdrawal")
	public void withdraw(String message, @Header("simpSessionId") String sessionId) {
		manageServService.sendMessageToManagerSer(messageUtil.addTitleToMsg(message, sessionId));
	}
	
	@MessageMapping("/assets/deposit")
	public void deposit(String message, @Header("simpSessionId") String sessionId) {
		manageServService.sendMessageToManagerSer(messageUtil.addTitleToMsg(message, sessionId));
	}
	
	@MessageMapping("/assets/withdrawalAddr")
	public void withdrawalAddr(String message, @Header("simpSessionId") String sessionId) {
		manageServService.sendMessageToManagerSer(messageUtil.addTitleToMsg(message, sessionId));
	}
	
	@MessageMapping("/assets/addWalletAddr") 
	public void addWalletAddr(String message, @Header("simpSessionId") String sessionId) {
		manageServService.sendMessageToManagerSer(messageUtil.addTitleToMsg(message, sessionId));
	}
	
	@MessageMapping("/assets/deleteWalletAddr") 
	public void deleteWalletAddr(String message, @Header("simpSessionId") String sessionId) {
		manageServService.sendMessageToManagerSer(messageUtil.addTitleToMsg(message, sessionId));
	}
	
	@MessageMapping("/assets/addBankAccount") 
	public void addBankAccount(String message, @Header("simpSessionId") String sessionId) {
		manageServService.sendMessageToManagerSer(messageUtil.addTitleToMsg(message, sessionId));
	}
	
	
	@MessageMapping("/assets/getBankAccount") 
	public void getBankAccount(String message, @Header("simpSessionId") String sessionId) {
		manageServService.sendMessageToManagerSer(messageUtil.addTitleToMsg(message, sessionId));
	}
	
	@MessageMapping("/assets/deleteBankAccount") 
	public void deleteBankAccount(String message, @Header("simpSessionId") String sessionId) {
		manageServService.sendMessageToManagerSer(messageUtil.addTitleToMsg(message, sessionId));
	}
}
