package com.ruizton.main.Enum;

public class CapitalOperationOutStatus {
	public static final int WaitForOperation = 1 ;//等待提现
	public static final int OperationLock = 2 ;//锁定，等待处理
	public static final int OperationSuccess = 3 ;//提现成功
	public static final int Cancel = 4 ;//用户取消

	public static String getEnumString(int value) {
		String name = "";
		if(value == WaitForOperation){
			name = "Withdrawal-Waiting";
		}else if(value == OperationLock){
			name = "Withdrawal-Processing";
		}else if(value == OperationSuccess){
			name = "Withdrawal-Funds received";
		}else if(value == Cancel){
			name = "Withdrawal-cancelled by user" ;
		}
		return name;
	}
}
