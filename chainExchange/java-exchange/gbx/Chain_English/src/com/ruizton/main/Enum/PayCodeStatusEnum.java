package com.ruizton.main.Enum;



public class PayCodeStatusEnum {
    public static final int PAYCODE_CREATE = 1;//创建
    public static final int PAYCODE_USER_CONFIRM = 2 ;//用户确认
    public static final int PAYCODE_SUCCESS = 3;//充值成功
    public static final int PAYCODE_FAILURE = 4;//充值失败
    public static final int PAYCODE_CANCEL = 5;//充值失败
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == PAYCODE_CREATE){
			name = "Created";
		}else if(value == PAYCODE_USER_CONFIRM){
			name = "User Confirmed";
		}else if(value == PAYCODE_SUCCESS){
			name = "Succeeded";
		}else if(value == PAYCODE_FAILURE){
			name = "Failed";
		}else if(value == PAYCODE_CANCEL){
			name = "Abandoned";
		}
		return name;
	}
    
}
