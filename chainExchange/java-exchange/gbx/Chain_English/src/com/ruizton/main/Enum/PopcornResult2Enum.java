package com.ruizton.main.Enum;

public class PopcornResult2Enum {
    public static final int BIG_VALUE = 1;
    public static final int SMALL_VALUE = 2;
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == BIG_VALUE){
			name = "绿";
		}else if(value == SMALL_VALUE){
			name = "红";
		}
		return name;
	}
    
}
