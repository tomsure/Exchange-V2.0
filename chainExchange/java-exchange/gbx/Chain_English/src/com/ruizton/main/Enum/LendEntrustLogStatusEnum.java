package com.ruizton.main.Enum;

public class LendEntrustLogStatusEnum {
    public static final int NOT_RETURN = 1;//未还款
    public static final int PART_RETURN = 2 ;//部分还款
    public static final int ALL_RETURN = 3 ;//已还款
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == NOT_RETURN){
			name = "未还款";
		}else if(value == PART_RETURN){
			name = "部分还款";
		}else if(value == ALL_RETURN){
			name = "已还清";
		}

		return name;
	}
    
}
