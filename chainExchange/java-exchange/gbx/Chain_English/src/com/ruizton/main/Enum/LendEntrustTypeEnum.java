package com.ruizton.main.Enum;

public class LendEntrustTypeEnum {
	public static final int LEND = 1;//放款
    public static final int BORROW = 2;//借款
    
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case LEND:
			name = "放款";
			break;
		case BORROW:
			name = "借款";
			break;
		}
		return name;
	}
    
}
