package com.ruizton.main.Enum;

public class SynTypeEnum {
    public static final int NORMAL_VALUE = 0;//正常
    public static final int OKCOIN_LTC_VALUE = 1;//禁用
    public static final int OKCOIN_BTC_VALUE = 2;//禁用
    public static final int OKCOIN_ETH_VALUE = 3;//禁用
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == NORMAL_VALUE){
			name = "Platform Defined";
		}else if(value == OKCOIN_LTC_VALUE){
			name = "OKCOIN-LTC";
		}else if(value == OKCOIN_BTC_VALUE){
			name = "OKCOIN-BTC";
		}else if(value == OKCOIN_ETH_VALUE){
			name = "OKCOIN-ETH";
		}
		return name;
	}
    
}
