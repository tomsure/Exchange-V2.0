package com.ruizton.main.Enum;

public class ActivityStatusEnum {
    public static final int ACTIVE = 1;//
    public static final int NOT_ACTIVE = 2;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == ACTIVE){
			name = "normal";
		}else if(value == NOT_ACTIVE){
			name = "Disable";
		}
		return name;
	}
    
}
