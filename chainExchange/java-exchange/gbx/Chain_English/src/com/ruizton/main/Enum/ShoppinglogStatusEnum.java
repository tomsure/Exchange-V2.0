package com.ruizton.main.Enum;

public class ShoppinglogStatusEnum {
    public static final int PAY = 1;
    public static final int USED = 2;
    public static final int SEND = 3;
    public static final int CHARGE = 4;
    public static final int CANCEL = 5;
    public static final int COMM = 6;
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == PAY){
			name = "等待发货";
		}else if(value == USED){
			name = "已使用";
		}else if(value == SEND){
			name = "已发货";
		}else if(value == CHARGE){
			name = "已结算";
		}else if(value == CANCEL){
			name = "用户取消";
		}else if(value == COMM){
			name = "已确认";
		}
		return name;
	}
    
}
