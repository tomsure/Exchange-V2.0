package com.ruizton.main.Enum;

public class UserGradeEnum {
	public static final int LEVEL0 = 1;//未委托
    public static final int LEVEL1 = 2;//未委托
    public static final int LEVEL2 = 3 ;//已委托
    public static final int LEVEL3 = 4;//取消
    public static final int LEVEL4 = 5;//取消
    public static final int LEVEL5 = 6;//取消
    
 //  有效用户； 金豆初级玩家（个数），金豆资深玩家（个数），金豆圈子成员（个数），金豆圈子领袖（个数）
    public static String getEnumString(int value) {
		String name = "";
		if(value == LEVEL0){
			name = "VIP1";
		}else if(value == LEVEL1){
			name = "VIP2";
		}else if(value == LEVEL2){
			name = "VIP3";
		}else if(value == LEVEL3){
			name = "VIP4";
		}else if(value == LEVEL4){
			name = "VIP5";
		}else if(value == LEVEL5){
			name = "VIP6";
		}
		return name;
	}
    
}
