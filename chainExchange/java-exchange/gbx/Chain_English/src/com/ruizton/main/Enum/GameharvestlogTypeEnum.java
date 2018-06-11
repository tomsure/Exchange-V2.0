package com.ruizton.main.Enum;

public class GameharvestlogTypeEnum {
    public static final int OWNER = 0;//买
    public static final int INTROL = 1 ;//卖
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == OWNER){
			name = "本人种植";
		}else if(value == INTROL){
			name = "推广奖励";
		}

		return name;
	}
    
}
