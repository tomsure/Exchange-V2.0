package com.ruizton.main.Enum;

public class KillHistoryTypeEnum {
    public static final int ONE = 1;
    public static final int PART = 2;
    public static final int ALL = 3;

    
    public static String getEnumString(int value) {
		String name = "";
		if(value == ONE){
			name = "一个";
		}else if(value == PART){
			name = "部分";
		}else if(value == ALL){
			name = "全部";
		}
		return name;
	}
    
}
