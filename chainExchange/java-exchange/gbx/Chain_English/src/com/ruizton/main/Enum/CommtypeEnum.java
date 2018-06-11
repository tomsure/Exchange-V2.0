package com.ruizton.main.Enum;

public class CommtypeEnum {
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;

    
    public static String getEnumString(int value) {
		String name = "";
		if(value == ONE){
			name = "一星";
		}else if(value == TWO){
			name = "二星";
		}else if(value == THREE){
			name = "三星";
		}else if(value == FOUR){
			name = "四星";
		}else if(value == FIVE){
			name = "五星";
		}
		return name;
	}
    
}
