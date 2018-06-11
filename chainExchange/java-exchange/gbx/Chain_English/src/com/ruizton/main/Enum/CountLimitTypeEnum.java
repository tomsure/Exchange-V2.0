package com.ruizton.main.Enum;

public class CountLimitTypeEnum {
    public static final int GOOGLE = 0;//
    public static final int LOGIN_PASSWORD = 1;//
    public static final int TRADE_PASSWORD = 2;//
    public static final int TELEPHONE = 3;//
    public static final int AdminLogin = 4;//
    public static final int REG_MAIL = 5;//
    
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case GOOGLE:
			name = "Google Authentication" ;
			break;
		case LOGIN_PASSWORD:
			name = "Login Password" ;
			break;
		case TRADE_PASSWORD:
			name = "Transaction Password" ;
			break;
		case TELEPHONE:
			name = "SMS authentication" ;
			break;
		case AdminLogin:
			name = "Admin Login" ;
		case REG_MAIL:
			name = "Email" ;
		}
		return name;
	}
    
}
