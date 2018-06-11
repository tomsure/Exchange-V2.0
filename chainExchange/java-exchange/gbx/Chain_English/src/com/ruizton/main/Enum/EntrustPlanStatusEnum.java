package com.ruizton.main.Enum;

public class EntrustPlanStatusEnum {
    public static final int Not_entrust = 1;//未委托
    public static final int Entrust = 2 ;//已委托
    public static final int Cancel = 3;//取消
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == Not_entrust){
			name = "Not entrusted";
		}else if(value == Entrust){
			name = "Entrusted";
		}else if(value == Cancel){
			name = "User revocation";
		}
		return name;
	}
    
}
