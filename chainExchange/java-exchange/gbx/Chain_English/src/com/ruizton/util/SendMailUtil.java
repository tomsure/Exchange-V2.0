package com.ruizton.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class SendMailUtil {
	public static boolean send(String smtp, String name, String password,
							   String toAddress, String title, String content) {
		boolean flag = false ;

		IClientProfile profile = DefaultProfile.getProfile("ap-southeast-1", name, password);
		try {
			DefaultProfile.addEndpoint("dm.ap-southeast-1.aliyuncs.com", "ap-southeast-1", "Dm",  "dm.ap-southeast-1.aliyuncs.com");
		} catch (ClientException e) {
			e.printStackTrace();
		}
		IAcsClient client = new DefaultAcsClient(profile);
		SingleSendMailRequest request = new SingleSendMailRequest();
		try {
			request.setAccountName(smtp);
			request.setFromAlias(new Constant().MailName);
			request.setAddressType(1);
			request.setTagName(null);
			request.setReplyToAddress(true);
			request.setToAddress(toAddress);
			request.setSubject(title);
			request.setHtmlBody(content);
			SingleSendMailResponse httpResponse = client.getAcsResponse(request);
			flag = true ;
		} catch (ServerException e) {
			e.printStackTrace();
			flag = false ;
		} catch (ClientException e) {
			e.printStackTrace();
			flag = false ;
		}


		return flag ;
	}

	public static void main(String[] args) {
		send("admin@sendmail.chpd8.com", "LTAIzygwRnDZboeK", "7xrwJkr2hcLBfUA492YqWR3JzyP8NC", "491492956@qq.com", "test", "test...");
	}
}
