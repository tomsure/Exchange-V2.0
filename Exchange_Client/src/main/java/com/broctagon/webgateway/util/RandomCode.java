package com.broctagon.webgateway.util;

public class RandomCode {

	private static String code = "0123456789";
	
	public static String getRandomCode() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			sb.append(code.charAt((int) (Math.random() * 10)));
		}
		return sb.toString();
	}
	
}
