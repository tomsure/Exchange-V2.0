package com.ruizton.main.Enum;

public class CnyOrCoinEnum {
    public static final int CNY = 1;//
    public static final int COIN = 2;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == CNY){
			name = "人民币";
		}else if(value == COIN){
			name = "虚拟币";
		}
		return name;
	}
    
}
