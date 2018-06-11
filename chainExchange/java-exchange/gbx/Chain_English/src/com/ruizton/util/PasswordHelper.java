package com.ruizton.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordHelper {

	private static String algorithmName = "md5";
	private static int hashIterations = 2;

	public static String encryString(String pwd, String salt) {
	    String newPassword = new SimpleHash(algorithmName,Utils.getMD5_32_xx(pwd+"hello, moto"), ByteSource.Util.bytes(salt), hashIterations).toHex();

	    return newPassword;
	}
	
	public static void main(String args[]) throws Exception {
//		String s = new SimpleHash("md5", "sbicipw2006", "eec39414ce656592a880a6c8987882c7", 2).toHex() ;
//		System.out.println();
//		System.out.println(encryString("admin123", "b96443abf8334a44ad8e8e61aa346398"));
		//a41501e904337a2a1ad067270c0cec62
		//eec39414ce656592a880a6c8987882c7
	}
}