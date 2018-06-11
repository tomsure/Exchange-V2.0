package com.ruizton.main.Enum;

public class LendRuleTypeEnum {
	public static final int LEND_TIME = 1;//放款时长
	public static final int BORROW_TIME = 2;//杠杆倍数
    
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case LEND_TIME:
			name = "放款时长";
			break;
		case BORROW_TIME:
			name = "杠杆倍数";
			break;	
		}
		return name;
	}
    
}
