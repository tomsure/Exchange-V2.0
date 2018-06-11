package com.ruizton.main.Enum;

public class PopcornBetStatusEnum {
    public static final int NO_OPEN_VALUE = 1;
    public static final int LOST_VALUE = 2;
    public static final int WAIT_VALUE = 3;
    public static final int SEND_VALUE = 4;
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == NO_OPEN_VALUE){
			name = "未开奖";
		}else if(value == LOST_VALUE){
			name = "未中奖";
		}else if(value == WAIT_VALUE){
			name = "等待发奖";
		}else if(value == SEND_VALUE){
			name = "已发奖";
		}
		return name;
	}
    
}
