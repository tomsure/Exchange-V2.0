package com.broctagon.webgateway.listener;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.broctagon.webgateway.constant.Constant;
import com.broctagon.webgateway.constant.Path;
import com.broctagon.webgateway.constant.Tag;
import com.broctagon.webgateway.model.manage.DepositAddrResponse;
import com.broctagon.webgateway.model.manage.DepositAddrResponseToFrontEnd;
import com.broctagon.webgateway.service.ReportServService;
import com.broctagon.webgateway.util.MessageUtil;
import com.broctagon.webgateway.util.QrCodeUtil;
import com.google.zxing.WriterException;

@Service
public class ManageServListener {
	
	@Value("${default.baseCoin}")
	private String baseCoin;
	
	@Value("${default.token}")
	private String token;
	
	@Value("${default.timeframe}")
	private String timeframe;
	
	@Autowired
	private ReportServService reportServService;

	@Autowired
	private SimpMessageSendingOperations messageSender;		
	
	@RabbitListener(queues="MANAGE.ANS." + "${id}")
	public void listener(Message rabbitMessage) throws WriterException, IOException {
		String message = new String(rabbitMessage.getBody());
		System.err.println("Manage Recievced: " + message);
		String tag = MessageUtil.getTag(message);
		String sessionId = MessageUtil.getSessionId(message);
		switch (Integer.parseInt(tag)) {
			case Tag.LOGIN_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.LOGIN + sessionId, message);
				if(Integer.parseInt(MessageUtil.getContentFromMsg(message, Constant.STATUS)) == 0) {
					String requestId = MessageUtil.getRequestId(message);
					int wsId = Integer.parseInt(MessageUtil.getContentFromMsg(message, "WSID"));
					int userId = Integer.parseInt(MessageUtil.getContentFromMsg(message, Constant.USER_ID));
					reportServService.getTradeTokens(wsId, sessionId, requestId);
					reportServService.getPendingOrdersByToken(wsId, sessionId, requestId, token);
					reportServService.getAssetsByToken(wsId, sessionId, requestId, userId, baseCoin, token.split("/")[0]);
					reportServService.getPendingOrdersByUserId(wsId, sessionId, requestId, userId);
					reportServService.getHistroicalOrdersByUserId(wsId, "All", sessionId, requestId, userId);
					reportServService.getMarketInfoByToken(wsId, sessionId, requestId, token);
					reportServService.getHistoricalBarsByToken(wsId, sessionId, requestId, token, timeframe);	
				}
				break;
			case Tag.REGISTRY_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.REGISTRY + sessionId, message);
				break;
			case Tag.CHANGE_MOBILE_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.CHANGE_MOBILE + sessionId, message);
				break;
			case Tag.CHANGE_LOGIN_PWD_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.CHANGE_LOGIN_PWD + sessionId, message);
				break;
			case Tag.CHANGE_TRANS_PWD_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.CHANGE_TRANS_PWD + sessionId, message);
				break;
			case Tag.KYC_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.KYC + sessionId, message);
				break;
			case Tag.WITHDRAWAL_ADDR_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.WITHDRAWAL_ADDR + sessionId, message);
				break;
			case Tag.ADD_WITHDRAWAL_ADDR_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.ADD_WITHDRAWAL_ADDR + sessionId, message);
				break;
			case Tag.WITHDRAWAL_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.WITHDRAWAL + sessionId, message);
				break;
			case Tag.DEPOSIT_ADDR_RESPONSE:
				DepositAddrResponse depositAddrResponse = Constant.GSON.fromJson(message, DepositAddrResponse.class);
				String path = "src/main/webapp/" + depositAddrResponse.getUserID() + ".jpg";
				QrCodeUtil.generateQrCode(String.valueOf(depositAddrResponse.getUserID()), depositAddrResponse.getDepsositAddr(), path);
				DepositAddrResponseToFrontEnd depositAddrResponseToFrontEnd = new DepositAddrResponseToFrontEnd(depositAddrResponse);
				depositAddrResponseToFrontEnd.setImgAddr("/" + depositAddrResponse.getUserID() + ".jpg");
				messageSender.convertAndSend(Path.PREFIX + Path.DEPOSIT_ADDR + sessionId, Constant.GSON.toJson(depositAddrResponseToFrontEnd));
				break;
			case Tag.ADD_BANK_ACCOUNT_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.ADD_BANK_ACCOUNT + sessionId, message);
				break;
			case Tag.DELETE_WITHDRAWAL_ADDR_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.DELETE_WITHDRAWAL_ADDR + sessionId, message);
				break;
			case Tag.BANK_ACCOUNT_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.BANK_ACCOUNT + sessionId, message);
				break;
			case Tag.DELETE_BANK_ACCOUNT_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.DELETE_BANK_ACCOUNT + sessionId, message);
				break;
			case Tag.FORGET_PWD_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.FORGET_PWD + sessionId, message);
				break;
			case Tag.LOGOUT_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.LOGOUT + sessionId, message);
				break;
			case Tag.USER_KYC_INFO_RESPONSE:
				messageSender.convertAndSend(Path.PREFIX + Path.USER_KYC_INFO + sessionId, message);
				break;
			default:
				break;
			}
	}
	
	
	public static void main(String[] args) {
		System.err.println(ManageServListener.class.getResource("").getPath() );
	}
}
