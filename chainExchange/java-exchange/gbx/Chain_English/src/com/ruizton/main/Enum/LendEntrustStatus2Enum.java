package com.ruizton.main.Enum;

/**
 * 借款状态
 * */
public class LendEntrustStatus2Enum {
	public static final int NOT_REPAY = 1;//等待还款
    public static final int PART_REPAY = 2;//部分还款
    public static final int ALL_REPAY = 3;//已还款
    public static final int NO_NEED_REPAY = 4;//不需要还款
    
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case NOT_REPAY:
			name = "等待还款";
			break;
		case PART_REPAY:
			name = "部分还款";
			break;
		case ALL_REPAY:
			name = "已还清";
			break;
		case NO_NEED_REPAY:
			name = "不需要还款";
			break;
		}
		return name;
	}
    
}
