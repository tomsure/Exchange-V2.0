package com.ruizton.main.Enum;

public class PaytypeEnum {
    public static final int CNY_YPC = 1;
    public static final int CNY_YPQ = 2;
    public static final int CNY_YCC = 3;
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == CNY_YPC){
			name = "CNY_YPC";
		}else if(value == CNY_YPQ){
			name = "CNY_YPQ";
		}else if(value == CNY_YCC){
			name = "CNY_YCC";
		}
		return name;
	}
    
}
