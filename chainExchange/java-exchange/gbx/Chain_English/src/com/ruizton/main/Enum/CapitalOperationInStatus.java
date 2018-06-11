package com.ruizton.main.Enum;

public class CapitalOperationInStatus {
	public static final int NoGiven = 1 ;//建立了充值，为补全充值信息
	public static final int WaitForComing = 2 ;//等待银行到账
	public static final int Come = 3 ;//到账
	public static final int Invalidate = 4 ;//失效

	public static String getEnumString(int value) {
		String name = "";
		switch (value) {
			case NoGiven:
				name = "Deposit-Unpaid" ;
				break;
			case WaitForComing:
				name = "Deposit-Waiting for bank processing" ;
				break;
			case Come:
				name = "Deposit-Received" ;
				break;
			case Invalidate:
				name = "Deposit-cancelled by user" ;
				break;
		}
		return name;
	}
}
