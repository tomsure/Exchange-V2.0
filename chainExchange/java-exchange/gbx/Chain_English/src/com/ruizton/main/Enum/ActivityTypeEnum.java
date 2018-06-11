package com.ruizton.main.Enum;

public class ActivityTypeEnum {
	public static final int REGISTER = 1;//
    public static final int LOGIN = 2;//
    public static final int ACTIVE_MAIL = 3;//
    public static final int REAL_NAME = 4;//
    public static final int BINNDING_MOBIL = 5;//
    public static final int BINNDING_GOOGLE = 6;//
    public static final int RECHARGE_CNY = 7;//
    public static final int NORMAL_ACTIVITY = 8;//
    
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case REGISTER:
			name = "Register";
			break;
		case LOGIN:
			name = "Sign In";
			break;
		case ACTIVE_MAIL:
			name = "Activate Email";
			break;
		case REAL_NAME:
			name = "Real Name Authentication";
			break;
		case BINNDING_MOBIL:
			name = "Bind Mobile Phone";
			break;
		case BINNDING_GOOGLE:
			name = "Bind Google";
			break;
		case RECHARGE_CNY:
			name = "CNY Recharge";
			break;
		case NORMAL_ACTIVITY:
			name = "Ordinary Activities";
			break;
		}
		return name;
	}
    
}
