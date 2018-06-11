package com.ruizton.main.Enum;

public class ShareActivityStatusEnum {
    public static final int EFFECTIVE_VALUE = 1;//
    public static final int EXPIRY_VALUE = 2;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == EXPIRY_VALUE){
			name = "失效";
		}else if(value == EFFECTIVE_VALUE){
			name = "生效";
		}
		return name;
	}
    
}
