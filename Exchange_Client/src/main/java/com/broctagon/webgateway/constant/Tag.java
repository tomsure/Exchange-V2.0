package com.broctagon.webgateway.constant;

public class Tag {

	public static String TAG = "Tag";	
	
	// report server
	public static final int COIN_LIST_REQUEST = 24611;
	
	public static final int COIN_LIST_RESPONSE = 24612;
	
	public static final int GET_ALL_TOKENS_REQUEST = 24613;
	
	public static final int GET_ALL_TOKENS_RESPONSE = 24614;
	
	public static final int HISTORICAL_BARS_REQUEST = 24601;
	
	public static final int HISTORICAL_BARS_RESPONSE = 24608;
	
	public static final int COIN_MARKET_REQUEST = 24583;
	
	public static final int COIN_MARKET_RESPONSE = 24584;
	
	public static final int COIN_MARKET_SUMMARY_REQUEST = 24593;
	
	public static final int COIN_MARKET_SUMMARY_RESPONSE = 24594;
	
	public static final int PENDING_ORDER_REQUEST = 393232;
	
	public static final int PENDING_ORDER_RESPONSE = 393233;
	
	public static final int USER_ASSERTS_REQUEST = 24581;
	
	public static final int USER_ASSERTS_RESPONSE = 24582;
	
	public static final int USER_ASSETS_BY_ONE_TOKEN_REQUEST = 24595;
	
	public static final int USER_ASSETS_BY_ONE_TOKEN_RESPONSE = 24596;
	
	public static final int USER_LOGIN_HISTORY_REQUEST = 24599;
	
	public static final int USRE_LOGIN_HISTORY_RESPONSE = 24600;
	
	public static final int USER_PENDING_ORDERS_REQUEST = 24579;
	
	public static final int USER_PENDING_ORDERS_RESPONSE = 24580;
	
	public static final int USER_HISTORICAL_ORDERS_REQUEST = 24577;
	
	public static final int USER_HISTORICAL_ORDERS_RESPONSE = 24578;
	
	public static final int USER_KYC_STATUS_REQUEST = 24609;
	
	public static final int USER_KYC_STATUS_RESPONSE = 24610;
	
	// manage server
	public static final int REGISTRY_REQUEST = 4097;
	
	public static final int REGISTRY_RESPONSE = 65553;
	
	public static final int LOGIN_REQUEST = 8193;
	
	public static final int LOGIN_RESPONSE = 8194;
	
	public static final int LOGOUT_REQUEST = 12354;
	
	public static final int LOGOUT_RESPONSE = 123456;
	
	public static final int SEND_SMS_NEED_MOBILE_NUMBER_REQUEST = 69634;
	
	public static final int CHANGE_MOBILE_REQUEST = 81920;
	
	public static final int CHANGE_MOBILE_RESPONSE = 81921;
	
	public static final int CHANGE_LOGIN_PWD_REQUEST = 73728;
	
	public static final int CHANGE_LOGIN_PWD_RESPONSE = 73729;
	
	public static final int CHANGE_TRANS_PWD_REQUEST = 77824;
	
	public static final int CHANGE_TRANS_PWD_RESPONSE = 77825;
	
	public static final int USER_KYC_INFO_RESPONSE = 155649;
	
	public static final int KYC_REQUEST = 139264;
	
	public static final int KYC_RESPONSE = 139265;
	
	public static final int ADD_WITHDRAWAL_ADDR_REQUEST = 90112;
	
	public static final int ADD_WITHDRAWAL_ADDR_RESPONSE = 90113;
	
	public static final int WITHDRAWAL_REQUEST = 135168;
	
	public static final int WITHDRAWAL_RESPONSE = 135169;
	
	public static final int WITHDRAWAL_ADDR_REQUEST = 151552;
	
	public static final int WITHDRAWAL_ADDR_RESPONSE = 151553;
	
	public static final int DEPOSIT_ADDR_REQUEST = 86016;
	
	public static final int DEPOSIT_ADDR_RESPONSE = 86017;
	
	public static final int DELETE_WITHDRAWAL_ADDR_REQUEST = 94208;
	
	public static final int DELETE_WITHDRAWAL_ADDR_RESPONSE = 94209;
	
	public static final int ADD_BANK_ACCOUNT_RESPONSE = 102401;
	
	public static final int BANK_ACCOUNT_REQUEST = 98304;
	
	public static final int BANK_ACCOUNT_RESPONSE = 98305;
	
	public static final int DELETE_BANK_ACCOUNT_REQUEST = 131072;
	
	public static final int DELETE_BANK_ACCOUNT_RESPONSE = 131073;
	
	public static final int FORGET_PWD_REQUEST = 147456;
	
	public static final int FORGET_PWD_RESPONSE = 147457;
	
	
	// order server
	public static final int ORDER_REQUEST = 12289;
	
	public static final int ORDER_RESPONSE = 12295;
	
	public static final int ORDER_FAIL_BY_TRANS_PWD = 12290;

	public static final int CANCEL_ORDER_RESPONSE = 16385;
	
	public static final int FAIL_CANCEL_ORDER = 12293;
	
	public static final int MATCHING_SUCCESS = 196865;

	// C2C server
	public static final int C2C_OPEN_ORDER_REQUEST = 20481;
	
	public static final int C2C_OPEN_ORDER_RESPONSE = 20738;
	
	public static final int C2C_CANCEL_OPEN_ORDER_REQUEST = 20739;
	
	public static final int C2C_CANCEL_OPEN_ORDER_RESPONSE = 20740;
	
	public static final int C2C_GET_OPEN_ORDER_REQUEST = 20483;
	
	public static final int C2C_GET_OPEN_ORDER_RESPONSE = 20484;
	
	public static final int C2C_GET_USER_PENDING_ORDER_REQUEST = 20485;

	public static final int C2C_GET_USER_PENDING_ORDER_RESPONSE = 20486;
	
	public static final int C2C_GET_USER_OPEN_ORDER_REQUEST = 20745;

	public static final int C2C_GET_USER_OPEN_ORDER_RESPONSE = 20746;
	
	public static final int C2C_GET_USER_HISTORICAL_ORDER_REQUEST = 20487;

	public static final int C2C_GET_USER_HISTORICAL_ORDER_RESPONSE = 20488;
	
	public static final int C2C_ENTRUST_RESPONSE = 20482;
	
	public static final int C2C_TRADE_REQUEST = 20773;
	
	public static final int C2C_TRADE_RESPONSE = 20774;
	
	public static final int C2C_CANCEL_TRADE_REQUEST = 20775;
	
	public static final int C2C_CANCEL_TRADE_RESPONSE = 20776;
	
	public static final int C2C_PAY_REQUEST = 20489;
	
	public static final int C2C_PAY_RESPONSE = 20490;
	
	public static final int C2C_PAY_CONFIRM_REQUEST = 21001;
	
	public static final int C2C_PAY_CONFIRM_RESPONSE = 21002;
	
	public static final int C2C_COIN_LIST_REQUEST = 20497;
	
	public static final int C2C_COIN_LIST_RESPONSE = 20498;
	
	public static final int C2C_COIN_MARKET_REQUEST = 20499;
	
	public static final int C2C_COIN_MARKET_RESPONSE = 20500;
	
	public static final int C2C_COIN_HISTORICAL_MARKET_REQUEST = 20771;
	
	public static final int C2C_COIN_HISTORICAL_MARKET_RESPONSE = 20772;
	
	
	// security server
	public static final int SEND_EMAIL_REQUEST = 69636;
}
