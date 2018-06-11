package com.ruizton.main.Enum;

public class GameharvestlogStatusEnum {
    public static final int FROZEN = 0;//买
    public static final int COMPLETE = 1 ;//卖
    public static final int WAIT = 2 ;//卖
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == FROZEN){
			name = "冻结";
		}else if(value == COMPLETE){
			name = "已完成";
		}

		return name;
	}
    
}
