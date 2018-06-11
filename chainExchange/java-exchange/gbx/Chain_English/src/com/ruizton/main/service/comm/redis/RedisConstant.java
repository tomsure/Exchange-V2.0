package com.ruizton.main.service.comm.redis;

import com.ruizton.util.Utils;

public class RedisConstant {

	public static String cacheHeader = "sub"+Utils.getRandomString(20)+"_" ;
	public static void updateCacheHeader(){
		cacheHeader = "sub"+Utils.getRandomString(20)+"_" ;
	}
	
	public static String getIndexJsonKey(int ftrademapping,int key){
		StringBuffer sb = new StringBuffer("getIndexJsonKey-") ;
		sb
		.append(ftrademapping)
		.append("-")
		.append(key);
		return sb.toString() ;
	}
	public static String getfullperiodKey(int ftrademapping,int key){
		StringBuffer sb = new StringBuffer("getfullperiodKey-") ;
		sb
		.append(ftrademapping)
		.append("-")
		.append(key);
		return sb.toString() ;
	}
	public static String getDepthKey(int ftrademapping,boolean isbuy,int count){
		StringBuffer sb = new StringBuffer("getDepthKey-") ;
		sb
		.append(ftrademapping)
		.append("-")
		.append(isbuy)
		.append("-")
		.append(count) ;
		return sb.toString() ;
	}
	public static String getSuccessKey(int ftrademapping,int count){
		StringBuffer sb = new StringBuffer("getSuccessKey-") ;
		sb
		.append(ftrademapping)
		.append("-")
		.append(count) ;
		return sb.toString() ;
	}
	public static String getLatestDealPrizeKey(int ftrademapping){
		StringBuffer sb = new StringBuffer("getLatestDealPrizeKey-") ;
		sb
		.append(ftrademapping) ;
		return sb.toString() ;
	}
	public static String getHighestPrizeKey(int ftrademapping){
		StringBuffer sb = new StringBuffer("getHighestPrizeKey-") ;
		sb
		.append(ftrademapping) ;
		return sb.toString() ;
	}
	public static String getLowestPrizeKey(int ftrademapping){
		StringBuffer sb = new StringBuffer("getLowestPrizeKey-") ;
		sb
		.append(ftrademapping) ;
		return sb.toString() ;
	}
	public static String getTotal(int ftrademapping){
		StringBuffer sb = new StringBuffer("getTotal-") ;
		sb
		.append(ftrademapping) ;
		return sb.toString() ;
	}
	public static String get24Total(int ftrademapping){
		StringBuffer sb = new StringBuffer("get24Total-") ;
		sb
		.append(ftrademapping) ;
		return sb.toString() ;
	}
	public static String getstart24Price(int ftrademapping){
		StringBuffer sb = new StringBuffer("getstart24Price-") ;
		sb
		.append(ftrademapping) ;
		return sb.toString() ;
	}
	public static String getHighestBuyPrizeKey(int ftrademapping){
		StringBuffer sb = new StringBuffer("getHighestBuyPrizeKey-") ;
		sb
		.append(ftrademapping) ;
		return sb.toString() ;
	}
	public static String getLowestSellPrizeKey(int ftrademapping){
		StringBuffer sb = new StringBuffer("LowestSellPrize-") ;
		sb
		.append(ftrademapping) ;
		return sb.toString() ;
	}
	public static String getaskBidJsonKey(int ftrademapping){
		StringBuffer sb = new StringBuffer("getaskBidJsonKey-") ;
		sb
		.append(ftrademapping) ;
		return sb.toString() ;
	}
	public static String getperiodJsonKey(int ftrademapping,int step){
		StringBuffer sb = new StringBuffer("getperiodJsonKey-") ;
		sb
		.append(ftrademapping) 
		.append("-") 
		.append(step) ;
		return sb.toString() ;
	}
	
	public static String getlatestKlinePeroidKey(int ftrademapping,int step){
		StringBuffer sb = new StringBuffer("getlatestKlinePeroidKey-") ;
		sb
		.append(ftrademapping) 
		.append("-") 
		.append(step) ;
		return sb.toString() ;
	}
	
}
