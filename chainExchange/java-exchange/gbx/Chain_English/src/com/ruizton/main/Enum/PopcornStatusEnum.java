package com.ruizton.main.Enum;

public class PopcornStatusEnum {
    public static final int GOING_VALUE = 1;
    public static final int END_VALUE = 2;
    public static final int OPEN_VALUE = 3;
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == GOING_VALUE){
			name = "进行中";
		}else if(value == END_VALUE){
			name = "已结束";
		}else if(value == OPEN_VALUE){
			name = "开奖中";
		}
		return name;
	}
    
}
