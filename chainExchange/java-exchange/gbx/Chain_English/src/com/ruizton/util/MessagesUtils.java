package com.ruizton.util;

import java.io.IOException;

public class MessagesUtils {
	
	/*
	 * 100			发送成功
		101			验证失败
		102			手机号码格式不正确
		103			会员级别不够
		104			内容未审核
		105			内容过多
		106			账户余额不足
		107			Ip受限
		108			手机号码发送太频繁
		120			系统升级
	 * 
	 * */
    public static int send(String name,String password,String key,String phone,String content){
    	try {
			sendSMS.send(name,password,key,content,phone) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return 1 ;
    	/*
    	int retCode = ReturnCode.FAIL ;
    	try {
    		String u = "http://sdk2.zucp.net:8060/z_mdsmssend.aspx?sn="+name.trim()+"&pwd="+password.trim()+"&mobile="+phone.trim()+"&content="+URLEncoder.encode(content,"GB2312")+"&ext=&rrid=&stime=";
    		URL url = new URL(u) ;
			System.out.println(url.toString());
    		BufferedReader br = new BufferedReader(new InputStreamReader( url.openStream()) ) ;
			StringBuffer sb = new StringBuffer() ;
			String tmp = null ;
			while((tmp=br.readLine())!=null){
				sb.append(tmp) ;
			}
			if(sb.toString().indexOf("100")!=-1){
				retCode = ReturnCode.SUCCESS ;
			}
			System.out.println(sb.toString());
			br.close() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return retCode ;
    */}
    
//    public static void main(String args[]) throws Exception{
//    	System.out.println(MessagesUtils.send("SDK-WSS-010-08603", "204E814BC12E600A9D202119AD4A286A", "15017549972", "您的验证码是："+33333+"。请不要把验证码泄露给其他人。如非本人操作，可不用理会！"));
//    }
  
}
