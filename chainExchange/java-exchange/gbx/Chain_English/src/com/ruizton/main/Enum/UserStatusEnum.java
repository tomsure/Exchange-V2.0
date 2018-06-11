package com.ruizton.main.Enum;

public class UserStatusEnum {
    public static final int NORMAL_VALUE = 1;//正常
    public static final int FORBBIN_VALUE = 2;//禁用
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == NORMAL_VALUE){
			name = "Enabled";
		}else if(value == FORBBIN_VALUE){
			name = "Disabled";
		}
		return name;
	}
    
}
