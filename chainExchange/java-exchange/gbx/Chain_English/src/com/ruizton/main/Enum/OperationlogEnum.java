package com.ruizton.main.Enum;

public class OperationlogEnum {
    public static final int SAVE = 1;//保存
    public static final int AUDIT = 2;//审核
    public static final int FFROZEN = 3;//审核
   
    public static String getEnumString(int value) {
		String name = "";
		if(value == SAVE){
			name = "Temporary";
		}else if(value == AUDIT){
			name = "Approved";
		}else if(value == FFROZEN){
			name = "Frozen";
		}
		return name;
	}
}
