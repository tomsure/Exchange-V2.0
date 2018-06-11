package com.ruizton.main.Enum;

public class DeductStatusEnum {
    public static final int WAIT_VALUE = 1;
    public static final int AUDIT_VALUE = 2;
    public static final int COMPLETE_VALUE = 3;
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == WAIT_VALUE){
			name = "待审核";
		}else if(value == AUDIT_VALUE){
			name = "已审核";
		}else if(value == COMPLETE_VALUE){
			name = "已发放";
		}
		return name;
	}
    
}
