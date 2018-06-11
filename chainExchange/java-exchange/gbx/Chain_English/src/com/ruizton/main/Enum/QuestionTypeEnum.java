package com.ruizton.main.Enum;

public class QuestionTypeEnum {
    public static final int COIN_RECHARGE = 1;
    public static final int COIN_WITHDRAW = 2;
    public static final int CNY_RECHARGE = 3;
    public static final int CNY_WITHDRAW = 4;
    public static final int OTHERS = 5;
    
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case COIN_RECHARGE:
			name = "Recharge problem";
			break;
		case COIN_WITHDRAW:
			name = "Withdrawal problem";
			break;
		case CNY_RECHARGE:
			name = "Business problems";
			break;
		case CNY_WITHDRAW:
			name = "Binding problem";
			break;
		case OTHERS:
			name = "Other problems";
			break;
		}
		return name;
	}
    
}
