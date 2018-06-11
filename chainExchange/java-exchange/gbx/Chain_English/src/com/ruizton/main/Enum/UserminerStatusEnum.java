package com.ruizton.main.Enum;

public class UserminerStatusEnum {
    public static final int NORMAL = 1;//
    public static final int OUT = 2;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == NORMAL){
			name = "正常";
		}else if(value == OUT){
			name = "已过期";
		}
		return name;
	}
    
}
