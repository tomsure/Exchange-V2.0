package com.ruizton.main.Enum;

/*
 * 放款状态
 * **/
public class LendEntrustStatus3Enum {
	public static final int NOT_RETURN = 1;//等待收益
    public static final int PART_RETURN = 2;//部分收益
    public static final int ALL_RETURN = 3;//已收益
    public static final int NO_NEED_RETURN = 4;//无收益
    
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case NOT_RETURN:
			name = "等待收益";
			break;
		case PART_RETURN:
			name = "部分收益";
			break;
		case ALL_RETURN:
			name = "已收益";
			break;
		case NO_NEED_RETURN:
			name = "无收益";
			break;
		}
		return name;
	}
    
}
