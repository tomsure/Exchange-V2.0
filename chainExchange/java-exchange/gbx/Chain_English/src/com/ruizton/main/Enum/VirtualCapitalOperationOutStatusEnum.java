package com.ruizton.main.Enum;

public class VirtualCapitalOperationOutStatusEnum {
	public static final int WaitForOperation = 1 ;//等待提现
	public static final int OperationLock = 2 ;//锁定，正在处理
	public static final int OperationSuccess = 3 ;//提现成功
	public static final int Cancel = 4 ;//用户取消
	
	public static String getEnumString(int value) {
		String name = "";
		if(value == WaitForOperation){
			name = "Pending";
		}else if(value == OperationLock){
			name = "Being processed";
		}else if(value == OperationSuccess){
			name = "Success";
		}else if(value == Cancel){
			name = "Cancelled" ;
		}
		return name;
	}
}
