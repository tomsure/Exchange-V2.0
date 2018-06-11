package com.ruizton.main.Enum;

public class CoinEnum {
    public static final int ONE_VALUE = 1;//正常
    public static final int TWO_VALUE = 2;//禁用
    public static final int THREE_VALUE = 3;//禁用
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == ONE_VALUE){
			name = "币主板";
		}else if(value == TWO_VALUE){
			name = "币创板";
		}else if(value == THREE_VALUE){
			name = "考察区";
		}
		return name;
	}
    
}
