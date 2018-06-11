package com.ruizton.main.Enum;

public class UserTypeEnum {
    public static final int VIP0 = 0;//正常
    public static final int VIP1 = 1;//禁用
    public static final int VIP2 = 2;//禁用
    public static final int VIP3 = 3;//禁用
    public static final int VIP4 = 4;//禁用
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == VIP0){
			name = "普通用户";
		}else if(value == VIP1){
			name = "普通服务中心";
		}else if(value == VIP2){
			name = "VIP服务中心";
		}else if(value == VIP3){
			name = "钻石服务中心";
		}else if(value == VIP4){
			name = "分公司";
		}
		return name;
	}
    
}
