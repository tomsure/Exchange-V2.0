package com.ruizton.main.Enum;

public class VirtualCapitalOperationInStatusEnum {
	public static final int WAIT_0 = 0 ;
	public static final int WAIT_1 = 1 ;
	public static final int WAIT_2 = 2 ;
	public static final int SUCCESS = 3 ;
	
	public static String getEnumString(int value) {
		String name = "";
		if(value == WAIT_0){
			name = "0 Confirmation";
		}else if(value == WAIT_1){
			name = "1 Confirmation";
		}else if(value == WAIT_2){
			name = "Confirming";
		}else if(value == SUCCESS){
			name = "Successful";
		}
		return name;
	}
}
