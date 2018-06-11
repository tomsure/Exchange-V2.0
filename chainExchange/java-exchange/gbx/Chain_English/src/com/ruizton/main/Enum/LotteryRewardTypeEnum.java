package com.ruizton.main.Enum;

public class LotteryRewardTypeEnum {
    public static final int SHIWU = 1;//
    public static final int XUNIBI = 2;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == SHIWU){
			name = "实物";
		}else if(value == XUNIBI){
			name = "虚拟币";
		}
		return name;
	}
    
}
