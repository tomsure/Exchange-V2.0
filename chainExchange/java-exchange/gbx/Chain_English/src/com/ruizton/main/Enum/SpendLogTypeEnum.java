package com.ruizton.main.Enum;

public class SpendLogTypeEnum {
    public static final int EGG_CHANGE = 1;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == EGG_CHANGE){
			name = "玩大转盘";
		}
		return name;
	}
    
}
