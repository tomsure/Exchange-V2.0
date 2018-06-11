package com.ruizton.main.Enum;

public class CoinagelogStatusEnum {
    public static final int SEND = 1;//
    public static final int NOT_SEND = 2;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == SEND){
			name = "已发送";
		}else if(value == NOT_SEND){
			name = "待发送";
		}
		return name;
	}
    
}
