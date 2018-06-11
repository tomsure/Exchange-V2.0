package com.ruizton.main.Enum;

public class CanPayTypeEnum {
    public static final int CNY = 0;
    public static final int YPC = 1;
    public static final int YPQ = 2;
    public static final int ALL = 200;
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == CNY){
			name = "人民币";
		}else if(value == YPC){
			name = "云普币";
		}else if(value == YPQ){
			name = "云普券";
		}else if(value == ALL){
			name = "全部";
		}
		return name;
	}
    
}
