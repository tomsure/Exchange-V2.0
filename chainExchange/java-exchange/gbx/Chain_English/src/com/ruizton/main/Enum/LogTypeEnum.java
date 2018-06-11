package com.ruizton.main.Enum;

public class LogTypeEnum {
    public static final int User_LOGIN = 1;//
    public static final int User_BIND_PHONE = 2;//
    public static final int User_BIND_EMAIL = 3;//
    public static final int User_SET_TRADE_PWD = 4;//
    public static final int User_UPDATE_TRADE_PWD = 5;//
    public static final int User_UPDATE_LOGIN_PWD = 6;//
    public static final int User_UPDATE_GOOGLE = 7;//
    public static final int User_BIND_GOOGLE = 8;//
    public static final int User_CERT = 9;//
    public static final int User_RESET_PWD = 10;//
    public static final int Admin_LOGIN = 11;//
    public static final int Admin_ADD = 12;//
    public static final int Admin_UPDATE = 13;//
    public static final int Admin_ROLE = 14;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == User_LOGIN){
			name = "User Login";
		}else if(value == User_BIND_PHONE){
			name = "Binding Phone";
		}else if(value == User_BIND_EMAIL){
			name = "Binding Email";
		}else if(value == User_SET_TRADE_PWD){
			name = "Setting Transaction Password";
		}else if(value == User_UPDATE_TRADE_PWD){
			name = "Update Transaction Password";
		}else if(value == User_UPDATE_LOGIN_PWD){
			name = "Update Login Password";
		}else if(value == User_UPDATE_GOOGLE){
			name = "Update Google Verifier";
		}else if(value == User_BIND_GOOGLE){
			name = "Binding Google Verifier";
		}else if(value == User_CERT){
			name = "Real Name Verification";
		}else if(value == User_RESET_PWD){
			name = "Reset Login Password";
		}else if(value == Admin_LOGIN){
			name = "Administrator";
		}else if(value == Admin_ADD){
			name = "Add Admin";
		}else if(value == Admin_UPDATE){
			name = "Modify Admin";
		}else if(value == Admin_ROLE){
			name = "Modify The Role of Admin";
		}
		
		return name;
	}
    
}
