package com.ruizton.main.Enum;

public class SubFrozenTypeEnum {
    public static final int NO_VALUE = 0;//正常
    public static final int DAY_VALUE = 1;//禁用
    public static final int MON_VALUE = 2;//禁用
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == NO_VALUE){
			name = "Time limit";
		}else if(value == DAY_VALUE){
			name = "Thawing by day";
		}else if(value == MON_VALUE){
			name = "Thawing by month";
		}
		return name;
	}
    
}
