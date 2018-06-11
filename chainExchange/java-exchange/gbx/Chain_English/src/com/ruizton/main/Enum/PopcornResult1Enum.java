package com.ruizton.main.Enum;

public class PopcornResult1Enum {
    public static final int SINGLE_VALUE = 1;
    public static final int DOUBLE_VALUE = 2;
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == SINGLE_VALUE){
			name = "深";
		}else if(value == DOUBLE_VALUE){
			name = "浅";
		}
		return name;
	}
    
}
