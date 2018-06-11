package com.ruizton.main.Enum;

public class AutotradeStatusEnum {
    public static final int NORMAL = 1;//
    public static final int FORBIN = 2;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == NORMAL){
			name = "Valid";
		}else if(value == FORBIN){
			name = "Invalid";
		}
		return name;
	}
    
}
