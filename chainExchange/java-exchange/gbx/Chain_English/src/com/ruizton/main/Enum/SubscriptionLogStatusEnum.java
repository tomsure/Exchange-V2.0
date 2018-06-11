package com.ruizton.main.Enum;

public class SubscriptionLogStatusEnum {
    public static final int NOT_SEND_YUGOU = 1;//
    public static final int SEND_YUGOU = 2;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == NOT_SEND_YUGOU){
			name = "未发送预购";
		}else if(value == SEND_YUGOU){
			name = "已发送预购";
		}
		return name;
	}
    
}
