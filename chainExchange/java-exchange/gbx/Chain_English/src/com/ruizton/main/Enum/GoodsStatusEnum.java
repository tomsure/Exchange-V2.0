package com.ruizton.main.Enum;

public class GoodsStatusEnum {
    public static final int SAVE = 1;
    public static final int NORMAL = 2;
    public static final int OUT = 3;
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == SAVE){
			name = "暂存";
		}else if(value == NORMAL){
			name = "热销中";
		}else if(value == OUT){
			name = "已下架";
		}
		return name;
	}
    
}
