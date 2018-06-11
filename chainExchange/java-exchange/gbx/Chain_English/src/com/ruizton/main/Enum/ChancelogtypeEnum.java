package com.ruizton.main.Enum;

public class ChancelogtypeEnum {
    public static final int A_VALUE = 1;//正常
    public static final int B_VALUE = 2;//禁用
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == A_VALUE){
			name = "金豆换金券";
		}else if(value == B_VALUE){
			name = "金券换金豆";
		}
		return name;
	}
    
}
