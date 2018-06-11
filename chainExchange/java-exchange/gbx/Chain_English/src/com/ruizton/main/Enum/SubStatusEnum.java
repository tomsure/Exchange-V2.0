package com.ruizton.main.Enum;

public class SubStatusEnum {
    public static final int INIT = 1;//
    public static final int YES = 2;//
    public static final int NO = 3;//
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == INIT){
			name = "In Progress";
		}else if(value == YES){
			name = "ICO Succeeded";
		}else if(value == NO){
			name = "ICO Failed";
		}
		return name;
	}
    
}
