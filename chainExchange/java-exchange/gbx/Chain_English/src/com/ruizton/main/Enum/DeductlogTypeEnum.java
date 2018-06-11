package com.ruizton.main.Enum;

public class DeductlogTypeEnum {
    public static final int TRADE_VALUE = 1;
    public static final int EGG_VALUE = 2;
    public static final int LEADER_EGG_VALUE = 3;
    public static final int LEADER_TRADE_VALUE = 4;
    public static final int LEADER_MONEY_VALUE = 5;
    public static final int MONEY_VALUE = 6;
    public static final int POPCORN_VALUE = 7;
    public static final int LEADER_POPCORN_VALUE = 8;
    public static final int RECHARGE_VALUE = 9;
    public static final int LEADER_RECHARGE_VALUE = 10;
    public static final int P2P_VALUE = 11;
    public static final int LEADER_P2P_VALUE = 12;
    public static final int GRADE_VALUE = 13;
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == TRADE_VALUE){
			name = "提现手续费";
		}else if(value == EGG_VALUE){
			name = "砸金蛋分成";
		}else if(value == LEADER_TRADE_VALUE){
			name = "领导奖励-提现手续费";
		}else if(value == LEADER_EGG_VALUE){
			name = "领导奖励-砸金蛋";
		}else if(value == LEADER_MONEY_VALUE){
			name = "领导奖励-借贷利息";
		}else if(value == MONEY_VALUE){
			name = "借贷利息";
		}else if(value == POPCORN_VALUE){
			name = "疯狂寻宝分成";
		}else if(value == LEADER_POPCORN_VALUE){
			name = "领导奖励-疯狂寻宝";
		}else if(value == RECHARGE_VALUE){
			name = "充值分成";
		}else if(value == LEADER_RECHARGE_VALUE){
			name = "领导奖励-充值分成";
		}else if(value == P2P_VALUE){
			name = "融资融豆分成";
		}else if(value == LEADER_P2P_VALUE){
			name = "领导奖励-融资融豆分成";
		}else if(value == GRADE_VALUE){
			name = "级差奖励-充值分成";
		}
		return name;
	}
    
}
