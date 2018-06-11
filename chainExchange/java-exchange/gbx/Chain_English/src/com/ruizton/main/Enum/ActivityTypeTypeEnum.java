package com.ruizton.main.Enum;

public class ActivityTypeTypeEnum {
    public static final int TRADE = 1;//
    public static final int WITHDRAW = 2;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == TRADE){
			name = "Trade";
		}else if(value == WITHDRAW){
			name = "Recharge";
		}
		return name;
	}
    
}
