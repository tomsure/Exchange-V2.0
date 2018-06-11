package com.ruizton.main.Enum;

public class SalescontractStatusEnum {
    public static final int SAVE_VALUE = 1;//
    public static final int AUDIT_VALUE = 2;//
    public static final int CANCEL_VALUE = 3;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == SAVE_VALUE){
			name = "保存";
		}else if(value == AUDIT_VALUE){
			name = "已审核";
		}else if(value == CANCEL_VALUE){
			name = "取消";
		}
		return name;
	}
    
}
