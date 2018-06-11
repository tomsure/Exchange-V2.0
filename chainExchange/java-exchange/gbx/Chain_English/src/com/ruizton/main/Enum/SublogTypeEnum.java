package com.ruizton.main.Enum;

public class SublogTypeEnum {
	public static final int N = 0;//转出
    public static final int T = 1;//转入
    public static final int F = 2;//转出
    public static final int O = 3;//转出
    public static final int W = 4;//转出
    
    public static String getEnumString(int value) {
		String name = "";
		if(value == T){
			name = "200";
		}else if(value == F){
			name = "500";
		}else if(value == O){
			name = "1000";
		}else if(value == N){
			name = "无";
		}else if(value == W){
			name = "10000";
		}else{
			name = String.valueOf(value);
		}
		return name;
	}
    
}
