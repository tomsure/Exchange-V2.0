package com.ruizton.main.Enum;

public class TrademappingStatusEnum {
	public static final int ACTIVE = 1;//
    public static final int FOBBID = 2;//
    
    public static String getEnumString(int value) {
		String name = "";
		switch (value) {
		case ACTIVE:
			name = "正常";
			break;
		case FOBBID:
			name = "禁用";
			break;
		}
		return name;
	}
    
}
