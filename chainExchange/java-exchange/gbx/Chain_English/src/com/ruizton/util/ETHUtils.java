package com.ruizton.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.ruizton.main.model.BTCInfo;
import com.ruizton.main.model.BTCMessage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ETHUtils  implements IWalletUtil {
	//用户名
	private  String ACCESS_KEY = null;
	//密码
	private  String SECRET_KEY = null;
	//钱包IP地址
	private  String IP = null;
	//端口
	private  String PORT = null;
	//比特币钱包密码
	private  String PASSWORD = null;
	
	
	public static boolean validateaddress(String address){
		if (address.toLowerCase().matches("^(0x)?[0-9a-f]{40}$")==false) {
	        return false;
	    }
		return true ;
	}
	public ETHUtils(BTCMessage btcMessage) {
		this.ACCESS_KEY = btcMessage.getACCESS_KEY();
		this.SECRET_KEY = btcMessage.getSECRET_KEY();
		this.IP = btcMessage.getIP();
		this.PORT = btcMessage.getPORT();
		this.PASSWORD = btcMessage.getSECRET_KEY();
	}
	
	public JSONObject getbalance(String address) throws Exception {
		String s = main("eth_getBalance", "[\""+address+"\", \"latest\"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	public JSONObject eth_accounts() throws Exception {
		String s = main("eth_accounts", "[]");
		JSONObject json = JSONObject.fromObject(s); 
		return json;
	}
	
	public List<String> eth_accountsValue() throws Exception {
		JSONObject s = eth_accounts();
		List<String> list = new ArrayList<String>() ;
		if(s.containsKey("result")){
			JSONArray arr = s.getJSONArray("result") ;
			for (int i = 0; i < arr.size(); i++) {
				list.add(arr.getString(i)) ;
			}
		}
		return list;
	}
	public double getbalanceValue(String address) throws Exception {
		double result = 0d;
		JSONObject s = getbalance(address);
        if(s.containsKey("result")){
        	result =parseAmount(s.getString("result"));
        }
		return result;
	}
	public double getbalanceValue() throws Exception {
		double result = 0d;
		List<String> address = this.eth_accountsValue() ;
		for (String string : address) {
			result += getbalanceValue(string);
		}
		return result;
	}
	public JSONObject getNewaddress() throws Exception {
		String s = main("personal_newAccount", "[\""+PASSWORD+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	
	public String getNewaddressValue() throws Exception {
		String result = null;
		JSONObject s = getNewaddress();
        if(s.containsKey("result")){
        	result = s.get("result").toString();
        	if(result.equals("null")){
        		result = null;
        	}
        }
		return result;
	}
	
	public JSONObject eth_getTransactionByHash(String hash) throws Exception {
		String s = main("eth_getTransactionByHash", "[\""+hash+"\"]");
		JSONObject json = JSONObject.fromObject(s);
		return json;
	}
	public BTCInfo eth_getTransactionByHashValue(String hash) throws Exception {
		JSONObject jsonObject = eth_getTransactionByHash(hash) ;
		if(jsonObject.containsKey("result")){
			JSONObject item = jsonObject.getJSONObject("result") ;
			if(item.containsKey("hash") == false ){
				return null ;
			}
			String from = item.getString("from") ;
			String to = item.getString("to") ;
			String value = item.getString("value") ;
			
			BTCInfo info = new BTCInfo();
			info.setAddress(to);
			info.setAmount(parseAmount(value));
			info.setConfirmations(0);
			info.setTime(Utils.getTimestamp());
			info.setTxid(item.getString("hash"));
			info.setBlockNumber(Long.parseLong(item.getString("blockNumber").substring(2),16)) ;
			info.setConfirmations((int) (eth_blockNumberValue()-Long.parseLong(item.getString("blockNumber").substring(2),16))) ;
			return info ;
		}
		return null ;
	}
	//区块高度
	public JSONObject eth_blockNumber() throws Exception {
		String s = main("eth_blockNumber", "[]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	public long eth_blockNumberValue() throws Exception {
		JSONObject jsonObject = eth_blockNumber() ;
		int count = 0 ;
		if(jsonObject.containsKey("result")){
			count = Integer.parseInt(jsonObject.getString("result").substring(2),16) ;
			
			BigInteger valD = new BigInteger(jsonObject.getString("result").substring(2),16) ;
			return Long.parseLong(valD.toString(10)) ;
			
		}
		return count ;
	}
	
	//区块交易数量
	public JSONObject eth_getBlockTransactionCountByNumber(long id) throws Exception {
		String s = main("eth_getBlockTransactionCountByNumber", "[\"0x"+Long.toHexString(id)+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
        return json;
	}
	public long eth_getBlockTransactionCountByNumberValue(long id) throws Exception {
		JSONObject jsonObject = eth_getBlockTransactionCountByNumber(id) ;
		long count = 0 ;
		if(jsonObject.containsKey("result")){
			count = Long.parseLong(jsonObject.getString("result").substring(2),16) ;
		}
		return count ;
	}
	
	
	//区块交易记录
	public JSONObject eth_getTransactionReceipt(String hash) throws Exception {
		String s = main("eth_getTransactionReceipt", "[\""+hash+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
		return json;
	}
	public BTCInfo eth_getTransactionReceiptValue(String hash) throws Exception {
		JSONObject json = eth_getTransactionReceipt(hash); 
		
		if(json.containsKey("result")){
			JSONObject item = json.getJSONObject("result") ;
			if(item.containsKey("hash") == false ){
				return null ;
			}
			String from = item.getString("from") ;
			String to = item.getString("to") ;
			String value = item.getString("value") ;
			
			BTCInfo info = new BTCInfo();
			info.setAddress(to);
			info.setAmount(parseAmount(value));
			info.setConfirmations(0);
			info.setTime(Utils.getTimestamp());
			info.setTxid(item.getString("hash"));
		}
		return null ;
	}
	//区块交易记录
	public JSONObject getTransactionByBlockNumberAndIndex(long number,int index) throws Exception {
		String s = main("eth_getTransactionByBlockNumberAndIndex", "[\"0x"+Long.toHexString(number)+"\",\"0x"+Integer.toHexString(index)+"\"]");
		JSONObject json = JSONObject.fromObject(s); 
		return json;
	}
	public BTCInfo getTransactionByBlockNumberAndIndexValue(long number,int index) throws Exception {
		BTCInfo btcInfo = null ;
		JSONObject jsonObject = getTransactionByBlockNumberAndIndex(number,index) ;
		if(jsonObject.containsKey("result")){
			JSONObject item = jsonObject.getJSONObject("result") ;
			String from = item.getString("from") ;
			String to = item.getString("to") ;
			String value = item.getString("value") ;

			BTCInfo info = new BTCInfo();
			info.setAddress(to);
			info.setAmount(parseAmount(value));
			info.setConfirmations(0);
			info.setTime(Utils.getTimestamp());
			info.setTxid(item.getString("hash"));
			return info ;
		
		}
		return btcInfo ;
	}
	
	public List<BTCInfo> listtransactionsValue(long number) throws Exception {
		long count = eth_getBlockTransactionCountByNumberValue(number) ;
		List<BTCInfo> all = new ArrayList();
		for (int i = 0; i < count; i++) {
			BTCInfo info = getTransactionByBlockNumberAndIndexValue(number, i) ;
			all.add(info) ;
		}
        return all;
	}
	
	public boolean lockAccount(String account)throws Exception{
		
		try {
			String s = main("personal_lockAccount", "["+
  "\""+account+"\""+
"]");
			JSONObject json = JSONObject.fromObject(s); 
			return json.getBoolean("result");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false ;
	
	}
	public boolean walletpassphrase(String account)throws Exception{
		
		try {
			String s = main("personal_unlockAccount", "["+
					"\""+account+"\","+"\""+ PASSWORD+"\""+
					"]");
			JSONObject json = JSONObject.fromObject(s); 
			return json.getBoolean("result");
		} catch (Exception e) {
		}
		return false ;
		
	}
	
	public JSONObject eth_sendTransaction(String from,String to,double amount,long gas ) throws Exception {
		walletpassphrase(from) ;
		
		String s = main("eth_gasPrice", "[]");
		JSONObject json = JSONObject.fromObject(s); 
		String gasprice = json.getString("result");
		
		s = main("eth_sendTransaction", "[{"+
 " \"from\": \""+from+"\","+
  "\"to\": \""+to+"\","+
 " \"gas\": \"0x"+Long.toHexString(gas)+"\","+
  "\"gasPrice\": \""+gasprice+"\","+
 " \"value\": \""+parseAmountHex(amount)+"\" "+
"}]");
		json = JSONObject.fromObject(s); 
		System.out.println(json);
		lockAccount(from) ;
		return json;
	
	}
	
	public String sendtoaddressValue(String from,String to,double amount ,long gas) throws Exception {
		String result = "";
		try {
			JSONObject s = eth_sendTransaction( from, to, amount ,gas);
			System.out.println(s);
			if(s.containsKey("result")){
				if(!s.get("result").toString().equals("null")){
					result = s.get("result").toString();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	
	//	'{"jsonrpc":"2.0","method":"eth_getBalance","params":["0x407d73d8a49eeb85d32cf465507dd71d507100c1", "latest"],"id":1}'
	private String main(String method,String condition) throws Exception {
        String result = "";
		String tonce = "" + (System.currentTimeMillis() * 1000);
		String params = "tonce=" + tonce.toString() + "&accesskey="
				+ ACCESS_KEY
				+ "&requestmethod=post&id=1&method="+method+"&params="+condition;

		String url = "http://"+IP+":"+PORT;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("Json-Rpc-Tonce", tonce.toString());
		con.setRequestProperty("Content-Type", "application/json");
		String postdata = "{\"jsonrpc\":\"2.0\",\"method\":\""+method+"\", \"params\":"+condition+", \"id\": 1}";
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(postdata);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		if(responseCode != 200){
			return "{\"result\":null,\"error\":"+responseCode+",\"id\":1}";
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		inputLine = in.readLine();
		response.append(inputLine);
		in.close();
		result = response.toString();
		return result;
	}
	
	
	public String parseAmountHex(double amount){
		BigDecimal valD = new BigDecimal(amount*Math.pow(10, 18)) ;
		
		BigInteger val = valD.toBigInteger() ;
		System.out.println(val);
		return "0x"+val.toString(16) ;
	}
	
	public double parseAmount(String hexval){
		String val = hexval.substring(2) ;
		BigInteger intVal = new BigInteger(val,16) ;
		
		return intVal.doubleValue()/Math.pow(10, 18) ;
	}
	
	public double fee(long gas) throws Exception {
		String s = main("eth_gasPrice", "[]");
		JSONObject json = JSONObject.fromObject(s); 
		Long gasprice = Long.parseLong( json.getString("result").substring(2),16);
		return gasprice*gas/Math.pow(10, 18) ;
	}
	public long EXfee(double ffee) throws Exception {
		String s = main("eth_gasPrice", "[]");
		JSONObject json = JSONObject.fromObject(s); 
		Long gasprice = Long.parseLong( json.getString("result").substring(2),16);
		return ((long)(Math.pow(10, 18)*ffee))/gasprice ;
	}
	public void sendMain(String mainAddr) throws Exception {
		List<String> address = this.eth_accountsValue() ;
		long gas = 21000;
		for (String string : address) {
			if(mainAddr.equals(string) == false ){
				try {
					double fee= fee(gas) ;
					double balance = getbalanceValue(string);
					if(balance>fee){
						String tx = sendtoaddressValue(string, mainAddr, balance-fee,gas) ;
						System.out.println(tx);
					}
				} catch (Exception e) {
				}
			}
		}
	}
	public static void main(String args[]) throws Exception{
		BTCMessage btcMessage = new BTCMessage() ;
		btcMessage.setIP("119.23.108.120") ;
		btcMessage.setPORT("8545") ;
		btcMessage.setPASSWORD("123456") ;
		ETHUtils ethUtils = new ETHUtils(btcMessage) ;
		/*System.out.println(ethUtils.fee(21000L));
		System.out.println(ethUtils.EXfee(4.2E-4));*/
		System.out.println(ethUtils.eth_blockNumberValue());
		System.out.println(ethUtils.parseAmountHex(500));
		
		System.out.println(ethUtils.getbalanceValue("0xd42d4a48594461727cc94369c5f58f63b8ae91bb")); ;
		System.out.println(ethUtils.getbalanceValue("0x62222531c61969c6be4935a27485e87a5d2d49c7")); ;
		/*System.out.println(ethUtils.getbalanceValue("0xdc36ded7e37274a771ac568f781519c09fb56746")); ;
		System.out.println(ethUtils.getbalanceValue());*/
//		List<String> list = ethUtils.eth_accountsValue() ;
		
//		String address = ethUtils.getNewaddressValue() ;
//		System.out.println(address);
		
//		System.out.println(ethUtils.eth_blockNumberValue());
		/*System.out.println(ethUtils.eth_getBlockTransactionCountByNumberValue(2698888));
		System.out.println(ethUtils.getTransactionByBlockNumberAndIndexValue(2698888, 12));*/
//		ethUtils.eth_getTransactionReceiptValue("0xe1d97d2d16579bd5ae137716d9b6388d4eddd044a1df6f387c89a0f6d616aecc") ;
//		ethUtils.lockAccount("0x07848a7df98197eb87c49f7d020a141871398bec", "sbicrgw");
//		ethUtils.eth_sendTransaction("0x93fe5f658c9ddbdc5a469aa7c856075e1d45bdc9", "0x07848a7df98197eb87c49f7d020a141871398bec", 0.1418-ethUtils.fee(21000),21000) ;
//		ethUtils.eth_getTransactionByHashValue("0x64d80be348af7b957ef70d4e4ced537c6d42e925676514e3fceeb0378b0772c8") ;
		System.out.println(ethUtils.validateaddress("0x986F597A08DD25a5cb5cC8a2EC4a162d27EB1838"));
//		System.out.println(ethUtils.fee(21000));
//		ethUtils.sendMain("0x07848a7df98197eb87c49f7d020a141871398bec") ;
//		ethUtils.sendMain("0xefd7c381350b43667d9a18d3392b4abb9fde8c2a") ;
		/*System.out.println();
		*/
		/*long gas = 21000;
		double fee= ethUtils.fee(gas) ;
		double balance = ethUtils.getbalanceValue("0xef9564a43ecb90a62e18f2f7eadd959e91401f39");
		if(balance>fee){
			String tx = ethUtils.sendtoaddressValue("0xef9564a43ecb90a62e18f2f7eadd959e91401f39", "0x62222531c61969c6be4935a27485e87a5d2d49c7", balance-fee,gas) ;
			System.out.println(tx);
		}*/
	}
	
}