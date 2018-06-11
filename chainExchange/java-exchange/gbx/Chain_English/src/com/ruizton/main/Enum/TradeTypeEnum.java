package com.ruizton.main.Enum;

public class TradeTypeEnum {
    public static final int BUY = 1;//
    public static final int SELL = 2;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == BUY){
			name = "Buy+Sell";
		}else if(value == SELL){
			name = "Buy+Sell";
		}
		return name;
	}
    
}
