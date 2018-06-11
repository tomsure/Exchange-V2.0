package com.ruizton.main.Enum;

public class ActivityCompleteStatusEnum {
	public static final int NOT_GET_REWARD = 1;//
    public static final int GET_REWARD = 2;//
    
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case NOT_GET_REWARD:
			name = "Not accepting the award";
			break;
		case GET_REWARD:
			name = "Receive the prize";
			break;
		}
		return name;
	}
    
}
