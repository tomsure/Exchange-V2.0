package com.ruizton.main.Enum;

public class ReturnTypeEnum {
    public static final int USER_VALUE = 1;//正常
    public static final int SYSTEM_VALUE = 2;//禁用
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == USER_VALUE){
			name = "用户下单";
		}else if(value == SYSTEM_VALUE){
			name = "系统下单";
		}
		return name;
	}
    
}
