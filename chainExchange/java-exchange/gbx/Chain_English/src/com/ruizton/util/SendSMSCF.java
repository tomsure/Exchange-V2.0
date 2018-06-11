package com.ruizton.util;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class SendSMSCF {

    /** 发送短信 **/
    public  boolean send(String mob, String msg,String url,String userCode,String userPass) {
        String str = "";
        try {
            // 创建HttpClient实例
            HttpClient httpclient =new DefaultHttpClient();

            //构造一个post对象
            HttpPost httpPost = new HttpPost(url);
            //添加所需要的post内容
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("userCode", userCode));
            nvps.add(new BasicNameValuePair("userPass", userPass));
            nvps.add(new BasicNameValuePair("DesNo", mob));
            nvps.add(new BasicNameValuePair("Msg", msg));
            if(mob.indexOf("+") > -1)
            {
                nvps.add(new BasicNameValuePair("Channel", "999"));
            }else
            {
                nvps.add(new BasicNameValuePair("Channel", "0"));
            }


            httpPost.setEntity( new UrlEncodedFormEntity(nvps, "UTF-8") );
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instreams = entity.getContent();
                str = convertStreamToString(instreams);
                System.out.println(str);
            }

//        	HttpRequestSender httpRequest = new HttpRequestSender();
//        	//发送 POST 请求
//        	 String sr=WeixinUtil.httpRequest(
//        			 "http://h.1069106.com:1210/Services/MsgSend.asmx/SendMsg",
//        			 "POST",
//             		 "userCode=XXXXX&userPass=XXXXX&DesNo="+mob+"&Msg="+msg+"&Channel=1");

            Document doc = null;
            doc = DocumentHelper.parseText(str); // 将字符串转为XML

            if (doc == null ) return false;
            Element rootElt = doc.getRootElement(); // 获取根节点
            if (rootElt == null ) return false;
            //System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
            System.out.println("根节点的值：" + rootElt.getText()); // 拿到根节点的名称
            if (rootElt.getText() == null || "".equals(rootElt.getText())) return false;
            if (Long.parseLong(rootElt.getText()) > 0 ) {
                return true;
            } else {
                return false;
            }

        } catch (DocumentException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** 发送短信 **/
    public  boolean sendGJ(String mob, String msg,String url,String userCode,String userPass) {
        String str = "";
        try {
            // 创建StringBuffer对象用来操作字符串
            StringBuffer sb = new StringBuffer("http://sdk2.entinfo.cn:8060/gjWebService.asmx/mdSmsSend_g");

            sb.append("?sn="+"SDK-SYO-010-00023");

            sb.append("&pwd="+Utils.MD5("SDK-SYO-010-00023]8dcef7-d7b","0").toUpperCase());

            sb.append("&mobile="+mob);

            sb.append("&content="+msg);
            sb.append("&ext="+"");
            sb.append("&stime="+"");
            sb.append("&rrid="+"");

            // 创建url对象
            URL urlhttp = new URL(sb.toString());

            // 打开url连接
            HttpURLConnection connection = (HttpURLConnection) urlhttp.openConnection();

            // 设置url请求方式 ‘get’ 或者 ‘post’
            connection.setRequestMethod("POST");

            // 发送
            BufferedReader in = new BufferedReader(new InputStreamReader(urlhttp.openStream()));

            // 返回发送结果
            String inputline = in.readLine();

            // 输出结果
            System.out.println(inputline);
            in.close();

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
//        SendSMSCF smsSender = new SendSMSCF();
//        smsSender.send("18576659165", "您的验证码是：351736。请不要把验证码泄露给其他人。如非本人操作，请及时修改密码以防被盗！【chain】");
//        smsSender.sendGJ("+918800629454", "123456 is your verification code.","","","");
//        String sn = "SDK-SYO-010-00023";// 请替换为自己的序列号和密码
//        String pwd = "]8dcef7-d7b";// 替换自己的密码
//        Client client = new Client(sn, pwd);
//        String result_send = client.mdSmsSend_g("0085252618359", "12345 is your verification code.【GBX】", "",
//                "", "");
//        if (!result_send.startsWith("-") && !result_send.equals("")) {
//            System.out.println("发送成功,返回值为:" + result_send);
//        } else {
//            System.out.println("发送失败,返回值为:" + result_send);
//        }
        String test = "+85252618359";
        System.out.println(test.replaceAll("\\+",""));
    }
}
