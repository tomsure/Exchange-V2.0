package com.ruizton.main.Enum;

public class AdminLevelEnum {
    public static final int NORMAL_VALUE = 1;//
    public static final int SUPER_VALUE = 2;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == NORMAL_VALUE){
			name = "普通管理员";
		}else if(value == SUPER_VALUE){
			name = "超级管理员";
		}
		return name;
	}
    
}
