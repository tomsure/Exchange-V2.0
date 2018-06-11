package com.ruizton.main.Enum;

public class EntrustTypeEnum {
    public static final int BUY = 0;//买
    public static final int SELL = 1 ;//卖
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == BUY){
			name = "Buy";
		}else if(value == SELL){
			name = "Sell";
		}

		return name;
	}
    
}
