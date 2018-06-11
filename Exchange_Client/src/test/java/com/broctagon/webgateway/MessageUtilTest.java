package com.broctagon.webgateway;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.broctagon.webgateway.constant.Constant;
import com.broctagon.webgateway.util.MessageUtil;

@SpringBootTest(classes=WebgatewayApp.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MessageUtilTest {

	@Value("${id}")
	private String wsId;

	@Autowired
	private MessageUtil messageUtil;
	
	@Test
	public void addTitleToMsgWsSendTest() {
		String message = null;
		Assert.assertNull(messageUtil.addTitleToMsgWsSend(message, "2005", "testtest"));
		message = "";
		Assert.assertNull(messageUtil.addTitleToMsgWsSend(message, "2005", "testtest"));
		message = "[{\"title\" : \"hello\"}, {\"title\": \"world\"}]";
		Assert.assertEquals(messageUtil.addTitleToMsgWsSend(message, "2005", "testtest"), "{ \"" + Constant.WS_ID + "\" : " + wsId + ", \""+ Constant.SESSION_ID +"\" : \"2005\", \"" + Constant.REQUEST_ID + "\" : \"testtest\"" + ", \"" + Constant.CONTENT + "\" : [{\"title\" : \"hello\"}, {\"title\": \"world\"}]}");
		message = "{\"title\" : \"test\"}";
		Assert.assertEquals(messageUtil.addTitleToMsgWsSend(message, "2005", "testtest"), "{ \"" + Constant.WS_ID + "\" : " + wsId + ", \"" + Constant.SESSION_ID + "\" : \"2005\", \"" + Constant.REQUEST_ID + "\" : \"testtest\", \"title\" : \"test\"}");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addTitleToMsgWsSendException() {
		String message = "test";
		messageUtil.addTitleToMsgWsSend(message, "2007", "testtest");
	}
	
	@Test
	public void getContentFromMsgTest() {
		String message = "{\"userId\" : 2222, \"test\" : \"test\"}";
		Assert.assertEquals(MessageUtil.getContentFromMsg(message, "userId"), "2222");
		Assert.assertTrue("test".equals(MessageUtil.getContentFromMsg(message, "test")));
	}
}
