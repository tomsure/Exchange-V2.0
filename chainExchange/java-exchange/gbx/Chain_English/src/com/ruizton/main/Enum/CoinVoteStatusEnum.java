package com.ruizton.main.Enum;

public class CoinVoteStatusEnum {
    public static final int NORMAL_VALUE = 1;//正常
    public static final int ABNORMAL_VALUE = 2;//禁用
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == NORMAL_VALUE){
			name = "Enable";
		}else if(value == ABNORMAL_VALUE){
			name = "Disable";
		}
		return name;
	}
    
}
