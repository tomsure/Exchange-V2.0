package com.ruizton.main.Enum;

public class BalanceflowTypeEnum {
    public static final int IN_VALUE = 1;//转入
    public static final int OUT_VALUE = 2;//转出
    
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
