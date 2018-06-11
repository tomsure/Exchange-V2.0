package com.ruizton.main.service.comm.listener;

import org.springframework.beans.factory.annotation.Autowired;

import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.service.comm.redis.RedisConstant;

import redis.clients.jedis.JedisPubSub;

public class RedisMQHandler extends JedisPubSub {

	@Autowired
	private ConstantMap constantMap;

	@Override
	// 接收到消息后进行分发执行
	public void onMessage(String channel, String message) {
//		System.out.println("接收消息：" + channel + "," + message);
		String[] channelNames = ChannelConstant.names;

		for (String name : channelNames) {
			if (name.equals(channel)) {
				if (name.equals(ChannelConstant.constantmap)) {
					RedisConstant.updateCacheHeader();
					// 新增订单
					if ("SystemArgs".equalsIgnoreCase(message)) {
						constantMap.SystemArgs();
					} else if ("virtualCoinType"
							.equalsIgnoreCase(message)) {
						constantMap.virtualCoinType();
					} else if ("allWithdrawCoins"
							.equalsIgnoreCase(message)) {
						constantMap.allWithdrawCoins();
					} else if ("allRechargeCoins"
							.equalsIgnoreCase(message)) {
						constantMap.allRechargeCoins();
					} else if ("ffriendlinks"
							.equalsIgnoreCase(message)) {
						constantMap.ffriendlinks();
					} else if ("quns"
							.equalsIgnoreCase(message)) {
						constantMap.quns();
					} else if ("webinfo"
							.equalsIgnoreCase(message)) {
						constantMap.webinfo();
					} else if ("tradehistory"
							.equalsIgnoreCase(message)) {
						constantMap.tradehistory();
					} else if ("news"
							.equalsIgnoreCase(message)) {
						constantMap.news();
					} else if ("fbs"
							.equalsIgnoreCase(message)) {
						constantMap.fbs();
					}else if ("tradeMappings"
							.equalsIgnoreCase(message)) {
						constantMap.tradeMappings();
					}else if ("ftradehistory7D"
							.equalsIgnoreCase(message)) {
						constantMap.ftradehistory7D();
					}else if ("totalActUser"
							.equalsIgnoreCase(message)) {
						constantMap.totalActUser();
					}else if ("totalActAmt"
							.equalsIgnoreCase(message)) {
						constantMap.totalActAmt();
					}
				}else if(name.equals(ChannelConstant.expireCache)){
					RedisConstant.updateCacheHeader();
				}
				break;
			}
		}
	}
}