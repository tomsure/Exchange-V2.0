package com.ruizton.main.Enum;

public class QuestionStatusEnum {
    public static final int NOT_SOLVED = 1;//未解决
    public static final int SOLVED = 2;//解决
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == NOT_SOLVED){
			name = "Unsolved";
		}else if(value == SOLVED){
			name = "Solve";
		}
		return name;
	}
    
}
