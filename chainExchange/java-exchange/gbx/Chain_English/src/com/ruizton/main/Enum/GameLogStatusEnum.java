package com.ruizton.main.Enum;

public class GameLogStatusEnum {
    public static final int GOING = 1;//
    public static final int ENDED = 2;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == GOING){
			name = "进行中";
		}else if(value == ENDED){
			name = "结束";
		}
		return name;
	}
    
}
