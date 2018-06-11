package com.ruizton.main.Enum;

public class ShareActivityTypeEnum {
    public static final int LOGIN_VALUE = 1;//
    public static final int SPECIAL_VALUE = 2;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == LOGIN_VALUE){
			name = "登陆";
		}else if(value == SPECIAL_VALUE){
			name = "特殊活动";
		}
		return name;
	}
    
}
