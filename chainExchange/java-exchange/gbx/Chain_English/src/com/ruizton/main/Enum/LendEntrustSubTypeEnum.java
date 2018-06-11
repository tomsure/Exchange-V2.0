package com.ruizton.main.Enum;

public class LendEntrustSubTypeEnum {
	public static final int IMMIDIATELY = 1;//立即借款
    public static final int APPOINTMENT = 2;//预约借款
    
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case IMMIDIATELY:
			name = "立即借款";
			break;
		case APPOINTMENT:
			name = "预约借款";
			break;
		}
		return name;
	}
    
}
