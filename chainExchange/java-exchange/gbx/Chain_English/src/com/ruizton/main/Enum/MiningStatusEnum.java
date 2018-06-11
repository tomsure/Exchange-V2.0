package com.ruizton.main.Enum;

public class MiningStatusEnum {
    public static final int DOING_VALUE = 1;//正常
    public static final int END_VALUE = 2;//禁用
    public static final int WAIT_VALUE = 3;//禁用
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == DOING_VALUE){
			name = "挖矿中";
		}else if(value == END_VALUE){
			name = "已结束";
		}else if(value == WAIT_VALUE){
			name = "结算中";
		}
		return name;
	}
    
}
