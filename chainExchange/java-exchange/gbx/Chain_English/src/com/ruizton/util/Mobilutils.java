package com.ruizton.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Mobilutils {//moo

	public final static String CONS_IS_FORCE_PC = "CONS_IS_FORCE_PC" ;
	
	public static String Redirect(HttpServletRequest request,String uri){
		HttpSession session = request.getSession() ;
		Boolean isMobil = Mobilutils.JudgeIsMoblie(request) ;
		Object IS_FORCE_PC_OBJ = session.getAttribute(CONS_IS_FORCE_PC) ;
		if(isMobil == true && 
				IS_FORCE_PC_OBJ==null &&
				uri.startsWith("/m/")==false
				){
			return "/m"+uri ;
		}else if(uri.startsWith("/m/")==true && IS_FORCE_PC_OBJ==null){
			session.setAttribute(CONS_IS_FORCE_PC, false);;
		}
		
		return null ;
	}
	
	public static boolean isMURL(HttpServletRequest request) {
		String uri = request.getRequestURI().toLowerCase().trim() ;
		boolean flag = uri.startsWith("/m/") ;
		return flag ;
	}
	public static String M(HttpServletRequest request) {
		String uri = request.getRequestURI().toLowerCase().trim() ;
		boolean flag = uri.startsWith("/m/") ;
		if(flag)return "/app" ;
		return "" ;
	}
 	public static boolean JudgeIsMoblie(HttpServletRequest request) {
		boolean isMoblie = false;
		String[] mobileAgents = { "iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
				"opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
				"nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
				"docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
				"techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
				"wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
				"pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
				"240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
				"blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
				"kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
				"mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
				"prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
				"smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
				"voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
				"Googlebot-Mobile" };
		if (request.getHeader("User-Agent") != null) {
			for (String mobileAgent : mobileAgents) {
				if (request.getHeader("User-Agent").toLowerCase().indexOf(mobileAgent) >= 0) {
					isMoblie = true;
					break;
				}
			}
		}
		return isMoblie;
	}
}
