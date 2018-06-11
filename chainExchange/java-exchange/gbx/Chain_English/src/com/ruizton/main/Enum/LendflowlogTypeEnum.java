package com.ruizton.main.Enum;

public class LendflowlogTypeEnum {
    public static final int IN_VALUE = 1;//正常
    public static final int OUT_VALUE = 2;//禁用
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == IN_VALUE){
			name = "转入";
		}else if(value == OUT_VALUE){
			name = "转出";
		}
		return name;
	}
    
}
