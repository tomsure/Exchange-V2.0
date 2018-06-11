package com.ruizton.main.Enum;

public class LendEntrustStatus1Enum {
	public static final int NOT_DEAL = 1;//未成交
    public static final int PART_DEAL = 2;//部分成交
    public static final int ALL_DEAL = 3;//全部成交
    public static final int CANCEL = 4;//撤销
    
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case NOT_DEAL:
			name = "未成交";
			break;
		case PART_DEAL:
			name = "部分成交";
			break;
		case ALL_DEAL:
			name = "全部成交";
			break;
		case CANCEL:
			name = "撤销";
			break;
		}
		return name;
	}
    
}
