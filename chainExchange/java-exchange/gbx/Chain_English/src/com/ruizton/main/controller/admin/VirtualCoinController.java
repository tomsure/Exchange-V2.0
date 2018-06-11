package com.ruizton.main.controller.admin;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.TrademappingStatusEnum;
import com.ruizton.main.Enum.VirtualCoinTypeStatusEnum;
import com.ruizton.main.comm.ConstantMap;
import com.ruizton.main.controller.BaseController;
import com.ruizton.main.model.BTCMessage;
import com.ruizton.main.model.Fpool;
import com.ruizton.main.model.Ftrademapping;
import com.ruizton.main.model.Fvirtualcointype;
import com.ruizton.main.model.Fwithdrawfees;
import com.ruizton.main.service.admin.AboutService;
import com.ruizton.main.service.admin.AdminService;
import com.ruizton.main.service.admin.PoolService;
import com.ruizton.main.service.admin.TradeMappingService;
import com.ruizton.main.service.admin.VirtualCoinService;
import com.ruizton.main.service.admin.WithdrawFeesService;
import com.ruizton.main.service.comm.listener.ChannelConstant;
import com.ruizton.main.service.comm.listener.MessageSender;
import com.ruizton.main.service.front.FrontVirtualCoinService;
import com.ruizton.util.BTCUtils;
import com.ruizton.util.Constant;
import com.ruizton.util.ETHUtils;
import com.ruizton.util.OSSPostObject;
import com.ruizton.util.Utils;

@Controller
public class VirtualCoinController extends BaseController {
	@Autowired
	private VirtualCoinService virtualCoinService ;
	@Autowired
	private AdminService adminService ;
	@Autowired
	private WithdrawFeesService withdrawFeesService;
	@Autowired
	private PoolService poolService;
	@Autowired
	private HttpServletRequest request ;
	@Autowired
	private FrontVirtualCoinService frontVirtualCoinService ;
	@Autowired
	private ConstantMap map;
	@Autowired
	private AboutService aboutService;
	@Autowired
	private TradeMappingService tradeMappingService;
	@Autowired
	private MessageSender messageSender ;
	
	
	//每页显示多少条数据
	private int numPerPage = Utils.getNumPerPage();
	
	@RequestMapping("/ssadmin/virtualCoinTypeList")
	public ModelAndView Index() throws Exception{
		
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/virtualCoinTypeList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		String orderField = request.getParameter("orderField");
		String orderDirection = request.getParameter("orderDirection");
		
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and (fname like '%"+keyWord+"%' OR \n");
			filter.append("fShortName like '%"+keyWord+"%' OR \n");
			filter.append("fdescription like '%"+keyWord+"%' )\n");
			modelAndView.addObject("keywords", keyWord);
		}
		
		filter.append(" and ftype <>"+CoinTypeEnum.FB_CNY_VALUE+" \n");
		
		if(orderField != null && orderField.trim().length() >0){
			filter.append("order by "+orderField+"\n");
		}else{
			filter.append("order by fid \n");
		}
		if(orderDirection != null && orderDirection.trim().length() >0){
			filter.append(orderDirection+"\n");
		}else{
			filter.append("asc \n");
		}
		List<Fvirtualcointype> list = this.virtualCoinService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("virtualCoinTypeList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "virtualCoinTypeList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.adminService.getAllCount("Fvirtualcointype", filter+""));
		return modelAndView ;
	}
	
	@RequestMapping("/ssadmin/walletAddressList")
	public ModelAndView walletAddressList() throws Exception{
		
		
		
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/walletAddressList") ;
		//当前页
		int currentPage = 1;
		//搜索关键字
		String keyWord = request.getParameter("keywords");
		
		if(request.getParameter("pageNum") != null){
			currentPage = Integer.parseInt(request.getParameter("pageNum"));
		}
		StringBuffer filter = new StringBuffer();
		filter.append("where 1=1 \n");
		if(keyWord != null && keyWord.trim().length() >0){
			filter.append("and b.fname like '%"+keyWord+"%'\n");
			modelAndView.addObject("keywords", keyWord);
		}
		filter.append("and (a.fstatus=0 or a.fstatus is null)\n");
		
		List list = this.poolService.list((currentPage-1)*numPerPage, numPerPage, filter+"",true);
		modelAndView.addObject("walletAddressList", list);
		modelAndView.addObject("numPerPage", numPerPage);
		modelAndView.addObject("rel", "walletAddressList");
		modelAndView.addObject("currentPage", currentPage);
		//总数量
		modelAndView.addObject("totalCount",this.poolService.list((currentPage-1)*numPerPage, numPerPage,filter+"",false).size());
		return modelAndView ;
	}
	
	
	@RequestMapping("ssadmin/goVirtualCoinTypeJSP")
	public ModelAndView goVirtualCoinTypeJSP() throws Exception{
		String url = request.getParameter("url");
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName(url) ;
		if(request.getParameter("uid") != null){
			int fid = Integer.parseInt(request.getParameter("uid"));
			Fvirtualcointype virtualCoinType = this.virtualCoinService.findById(fid);
			modelAndView.addObject("virtualCoinType", virtualCoinType);
			
			String filter = "where fvirtualcointype.fid="+fid+" order by flevel asc";
			List<Fwithdrawfees> allFees = this.withdrawFeesService.list(0, 0, filter, false);
			modelAndView.addObject("allFees", allFees);
		}
		
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/saveVirtualCoinType")
	public ModelAndView saveVirtualCoinType(
			@RequestParam(required=false) MultipartFile filedata,
			@RequestParam(required=false) String fdescription,
			@RequestParam(required=false) String fname,
			@RequestParam(required=false) String fShortName,
			@RequestParam(required=false) String faccess_key,
			@RequestParam(required=false) String fsecrt_key,
			@RequestParam(required=false) String fip,
			@RequestParam(required=false) String fport,
			@RequestParam(required=false) String fSymbol,
			@RequestParam(required=false) String faddress,
			@RequestParam(required=false) String fisother,
			@RequestParam(required=false) String FIsWithDraw,
			@RequestParam(required=false) String fweburl,
			@RequestParam(required=false) String fisEth,
			@RequestParam(required=false) String fisToken,
			@RequestParam(required=false) boolean ftype,
			@RequestParam(required=false,defaultValue="") String mainAddr,
			@RequestParam(required=false) String fisauto,
			@RequestParam(required=false) String fisautosend,
			@RequestParam(required=false) String fpassword,
			@RequestParam(required=false) String fisrecharge,
			@RequestParam(required=false) String fregDesc,
			@RequestParam(required=false) String fwidDesc,
			@RequestParam(required=false) double ftotalqty
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		Fvirtualcointype virtualCoinType = new Fvirtualcointype();
		String fpictureUrl = "";
		boolean isTrue = false;
		 if(filedata != null && !filedata.isEmpty()){
			InputStream inputStream = filedata.getInputStream() ;
			String fileRealName = filedata.getOriginalFilename() ;
			if(fileRealName != null && fileRealName.trim().length() >0){
				String[] nameSplit = fileRealName.split("\\.") ;
				String ext = nameSplit[nameSplit.length-1] ;
				if(ext!=null 
				 && !ext.trim().toLowerCase().endsWith("jpg")
				 && !ext.trim().toLowerCase().endsWith("png")){
					modelAndView.addObject("statusCode",300);
					modelAndView.addObject("message","非jpg、png文件格式");
					return modelAndView;
				}
				String realPath = request.getSession().getServletContext().getRealPath("/")+Constant.uploadPicDirectory;
				String fileName = Utils.getRandomImageName()+"."+ext;
				boolean flag = Utils.saveFile(realPath,fileName, inputStream,Constant.uploadPicDirectory) ;
				if(flag){
					if(Constant.IS_OPEN_OSS.equals("false")){
						fpictureUrl = "/"+Constant.uploadPicDirectory+"/"+fileName ;
					}else{
						fpictureUrl = OSSPostObject.URL+"/"+Constant.uploadPicDirectory+"/"+fileName ;
					}
					isTrue = true;
				}
			}
		}
		if(isTrue){
			virtualCoinType.setFurl(fpictureUrl);
		}
		virtualCoinType.setFtotalqty(ftotalqty);
		virtualCoinType.setFaddTime(Utils.getTimestamp());
		virtualCoinType.setFdescription(fdescription);
		virtualCoinType.setFname(fname);
		virtualCoinType.setfShortName(fShortName);
		virtualCoinType.setFstatus(VirtualCoinTypeStatusEnum.Abnormal);
		virtualCoinType.setFaccess_key(faccess_key);
		virtualCoinType.setFsecrt_key(fsecrt_key);
		virtualCoinType.setFip(fip);
		if(ftype==true){
			virtualCoinType.setFtype(CoinTypeEnum.FB_COIN_VALUE);
		}else{
			virtualCoinType.setFtype(CoinTypeEnum.COIN_VALUE);
		}		
		virtualCoinType.setFport(fport);
		virtualCoinType.setfSymbol(fSymbol);
		if(fisToken != null && fisToken.trim().length() >0){
			virtualCoinType.setFisToken(true);
		}else{
			virtualCoinType.setFisToken(false);
		}
		if(fisEth != null && fisEth.trim().length() >0){
			virtualCoinType.setFisEth(true);
			
			if("".equals(mainAddr.trim())){
				virtualCoinType.setMainAddr("") ;
			}else{
				boolean valid = ETHUtils.validateaddress(mainAddr.trim()) ;
				if(valid == false ){
					modelAndView.addObject("statusCode",500);
					modelAndView.addObject("message","ETH Summary Address Error");
					return modelAndView;
				}
				virtualCoinType.setMainAddr(mainAddr) ;
			}
		}else{
			virtualCoinType.setFisEth(false);
			virtualCoinType.setMainAddr(mainAddr) ;
		}
		if(FIsWithDraw != null && FIsWithDraw.trim().length() >0){
			virtualCoinType.setFIsWithDraw(true);
		}else{
			virtualCoinType.setFIsWithDraw(false);
		}
		if(fisautosend != null && fisautosend.trim().length() >0){
			virtualCoinType.setFisautosend(true);
		}else{
			virtualCoinType.setFisautosend(false);
		}
		virtualCoinType.setFpassword(fpassword);
		if(fisauto != null && fisauto.trim().length() >0){
			virtualCoinType.setFisauto(true);
		}else{
			virtualCoinType.setFisauto(false);
		}
		if(fisrecharge != null && fisrecharge.trim().length() >0){
			virtualCoinType.setFisrecharge(true);
		}else{
			virtualCoinType.setFisrecharge(false);
		}
		virtualCoinType.setFregDesc(fregDesc);
		virtualCoinType.setFwidDesc(fwidDesc);
//		if(fistransport != null && fistransport.trim().length() >0){
//			virtualCoinType.setFistransport(true);
//		}else{
//			virtualCoinType.setFistransport(false);
//		}
//		Fabout about = new Fabout();
//		about.setFcontent(".");
//		about.setFtitle(virtualCoinType.getFname());
//		about.setFtype("帮助分类");
//		this.aboutService.saveObj(about);
//		virtualCoinType.setFweburl(String.valueOf(about.getFid()));
		this.virtualCoinService.saveObj(virtualCoinType);
		
		for(int i=1;i<=Constant.VIP;i++){
			Fwithdrawfees fees = new Fwithdrawfees();
			fees.setFlevel(i);
			fees.setFvirtualcointype(virtualCoinType);
			fees.setFfee(0d);
			this.withdrawFeesService.saveObj(fees);
		}
		
		this.messageSender.publish(ChannelConstant.constantmap, "virtualCoinType");
		this.messageSender.publish(ChannelConstant.constantmap, "allWithdrawCoins");
		this.messageSender.publish(ChannelConstant.constantmap, "allRechargeCoins");
		this.messageSender.publish(ChannelConstant.constantmap, "tradeMappings");

		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Add Success");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/updateVirtualCoinType")
	public ModelAndView updateVirtualCoinType(
			@RequestParam(required=false) MultipartFile filedata,
			@RequestParam(required=false) String fdescription,
			@RequestParam(required=false) String fname,
			@RequestParam(required=false) String fShortName,
			@RequestParam(required=false) String faccess_key,
			@RequestParam(required=false) String fsecrt_key,
			@RequestParam(required=false) String fip,
			@RequestParam(required=false) String fport,
			@RequestParam(required=false) String fSymbol,
			@RequestParam(required=false) String FIsWithDraw,
			@RequestParam(required=false) String faddress,
			@RequestParam(required=false) String fisautosend,
			@RequestParam(required=false) String fpassword,
			@RequestParam(required=false) String fisother,
			@RequestParam(required=false) int fid,
			@RequestParam(required=false) String fisEth,
			@RequestParam(required=false) String fisToken,
			@RequestParam(required=false) boolean ftype,
			@RequestParam(required=false,defaultValue="") String mainAddr,
			@RequestParam(required=false) String fweburl,
			@RequestParam(required=false) String fisauto,
			@RequestParam(required=false) String fisrecharge,
			@RequestParam(required=false) String fregDesc,
			@RequestParam(required=false) String fwidDesc,
			@RequestParam(required=false) double ftotalqty
			) throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		Fvirtualcointype virtualCoinType = this.virtualCoinService.findById(fid);
		String fpictureUrl = "";
		boolean isTrue = false;
		 if(filedata != null && !filedata.isEmpty()){
			InputStream inputStream = filedata.getInputStream() ;
			String fileRealName = filedata.getOriginalFilename() ;
			if(fileRealName != null && fileRealName.trim().length() >0){
				String[] nameSplit = fileRealName.split("\\.") ;
				String ext = nameSplit[nameSplit.length-1] ;
				if(ext!=null 
				 && !ext.trim().toLowerCase().endsWith("jpg")
				 && !ext.trim().toLowerCase().endsWith("png")){
					modelAndView.addObject("statusCode",300);
					modelAndView.addObject("message","非jpg、png文件格式");
					return modelAndView;
				}
				String realPath = request.getSession().getServletContext().getRealPath("/")+Constant.uploadPicDirectory;
				String fileName = Utils.getRandomImageName()+"."+ext;
				boolean flag = Utils.saveFile(realPath,fileName, inputStream,Constant.uploadPicDirectory) ;
				if(flag){
					if(Constant.IS_OPEN_OSS.equals("false")){
						fpictureUrl = "/"+Constant.uploadPicDirectory+"/"+fileName ;
					}else{
						fpictureUrl = OSSPostObject.URL+"/"+Constant.uploadPicDirectory+"/"+fileName ;
					}
					isTrue = true;
				}
			}
		}
		if(isTrue){
			virtualCoinType.setFurl(fpictureUrl);
		}
		if(fisautosend != null && fisautosend.trim().length() >0){
			virtualCoinType.setFisautosend(true);
		}else{
			virtualCoinType.setFisautosend(false);
		}
		if(fisToken != null && fisToken.trim().length() >0){
			virtualCoinType.setFisToken(true);
		}else{
			virtualCoinType.setFisToken(false);
		}
		if(fisEth != null && fisEth.trim().length() >0){
			virtualCoinType.setFisEth(true);
			
			if("".equals(mainAddr.trim())){
				virtualCoinType.setMainAddr("") ;
			}else{
				boolean valid = ETHUtils.validateaddress(mainAddr.trim()) ;
				if(valid == false ){
					modelAndView.addObject("statusCode",500);
					modelAndView.addObject("message","ETH Summary Address Error");
					return modelAndView;
				}
				virtualCoinType.setMainAddr(mainAddr) ;
			}
		}else{
			virtualCoinType.setFisEth(false);
			virtualCoinType.setMainAddr(mainAddr) ;
		}
		virtualCoinType.setFregDesc(fregDesc);
		virtualCoinType.setFwidDesc(fwidDesc);
		virtualCoinType.setFpassword(fpassword);
//		virtualCoinType.setFweburl(fweburl);
		virtualCoinType.setFtotalqty(ftotalqty);
		virtualCoinType.setFaddTime(Utils.getTimestamp());
		virtualCoinType.setFdescription(fdescription);
		virtualCoinType.setFname(fname);
		virtualCoinType.setfShortName(fShortName);
		virtualCoinType.setFaccess_key(faccess_key);
		virtualCoinType.setFsecrt_key(fsecrt_key);
		virtualCoinType.setFip(fip);
		if(ftype==true){
			virtualCoinType.setFtype(CoinTypeEnum.FB_COIN_VALUE);
		}else{
			virtualCoinType.setFtype(CoinTypeEnum.COIN_VALUE);
		}	
		virtualCoinType.setFport(fport);
		virtualCoinType.setfSymbol(fSymbol);
		if(FIsWithDraw != null && FIsWithDraw.trim().length() >0){
			virtualCoinType.setFIsWithDraw(true);
		}else{
			virtualCoinType.setFIsWithDraw(false);
		}
		if(fisauto != null && fisauto.trim().length() >0){
			virtualCoinType.setFisauto(true);
		}else{
			virtualCoinType.setFisauto(false);
		}
		if(fisrecharge != null && fisrecharge.trim().length() >0){
			virtualCoinType.setFisrecharge(true);
		}else{
			virtualCoinType.setFisrecharge(false);
		}
//		if(fistransport != null && fistransport.trim().length() >0){
//			virtualCoinType.setFistransport(true);
//		}else{
//			virtualCoinType.setFistransport(false);
//		}
		if(virtualCoinType.getFtype() ==CoinTypeEnum.FB_CNY_VALUE){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","人民币不允许修改");
			return modelAndView;
		}
		this.virtualCoinService.updateObj(virtualCoinType);
		
//		Fabout about = this.aboutService.findById(Integer.parseInt(virtualCoinType.getFweburl()));
//		if(about != null){
//			about.setFtitle(virtualCoinType.getFname());
//			this.aboutService.updateObj(about);
//		}
		
		
		this.messageSender.publish(ChannelConstant.constantmap, "virtualCoinType");
		this.messageSender.publish(ChannelConstant.constantmap, "allWithdrawCoins");
		this.messageSender.publish(ChannelConstant.constantmap, "allRechargeCoins");
		this.messageSender.publish(ChannelConstant.constantmap, "tradeMappings");
		
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Modify Success");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/goWallet")
	public ModelAndView goWallet() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		String password = request.getParameter("passWord");
		Fvirtualcointype virtualcointype = this.virtualCoinService.findById(fid);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		boolean flag = false;
		virtualcointype.setFstatus(VirtualCoinTypeStatusEnum.Normal);
		String msg = "";
		try {
			flag = this.virtualCoinService.updateCoinType(virtualcointype,password);
			flag = true;
		} catch (Exception e) {
			 msg =e.getMessage();
		}
		
		this.messageSender.publish(ChannelConstant.constantmap, "virtualCoinType");
		this.messageSender.publish(ChannelConstant.constantmap, "allWithdrawCoins");
		this.messageSender.publish(ChannelConstant.constantmap, "allRechargeCoins");
		this.messageSender.publish(ChannelConstant.constantmap, "tradeMappings");
		
		if(!flag){
			modelAndView.addObject("message",msg);
			modelAndView.addObject("statusCode",300);
		}else{
			modelAndView.addObject("message","Enable Success");
			modelAndView.addObject("statusCode",200);
			modelAndView.addObject("callbackType","closeCurrent");
		}
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/deleteVirtualCoinType")
	public ModelAndView deleteVirtualCoinType() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fvirtualcointype virtualcointype = this.virtualCoinService.findById(fid);
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		
		if(virtualcointype.getFtype() ==CoinTypeEnum.FB_CNY_VALUE){
			modelAndView.addObject("statusCode",300);
			modelAndView.addObject("message","人民币不允许禁用");
			return modelAndView;
		}
		
		virtualcointype.setFstatus(VirtualCoinTypeStatusEnum.Abnormal);
		this.virtualCoinService.updateObj(virtualcointype);
		
		this.messageSender.publish(ChannelConstant.constantmap, "virtualCoinType");
		this.messageSender.publish(ChannelConstant.constantmap, "allWithdrawCoins");
		this.messageSender.publish(ChannelConstant.constantmap, "allRechargeCoins");
		this.messageSender.publish(ChannelConstant.constantmap, "tradeMappings");
		
		modelAndView.addObject("message","Disable Success");
		modelAndView.addObject("statusCode",200);
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/testWallet")
	public ModelAndView testWallet() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		Fvirtualcointype type = this.virtualCoinService.findById(fid);
		BTCMessage btcMessage = new BTCMessage() ;
		btcMessage.setACCESS_KEY(type.getFaccess_key()) ;
		btcMessage.setIP(type.getFip()) ;
		btcMessage.setPORT(type.getFport()) ;
		btcMessage.setSECRET_KEY(type.getFsecrt_key()) ;
		if(btcMessage.getACCESS_KEY()==null
				||btcMessage.getIP()==null
				||btcMessage.getPORT()==null
				||btcMessage.getSECRET_KEY()==null){
			modelAndView.addObject("message","Wallet connection failed. Please check the configuration");
			modelAndView.addObject("statusCode",300);
		}
		try {
			if(type.isFisEth() == true ){

				ETHUtils ethUtils = new ETHUtils(btcMessage) ;
				if(type.getMainAddr()!=null){
					long blocks = ethUtils.eth_blockNumberValue() ;
					double balance = ethUtils.getbalanceValue(type.getMainAddr());
					modelAndView.addObject("message","Test succeeded. Wallet balance:"+balance+",Block height: "+blocks);
					modelAndView.addObject("statusCode",200);
				}else{
					modelAndView.addObject("message","The main address is not set or the network connection is not accessible");
					modelAndView.addObject("statusCode",500);
				}
				
			}else{

				BTCUtils btcUtils = new BTCUtils(btcMessage) ;
				double balance = btcUtils.getbalanceValue();
				
				JSONObject json = btcUtils.getInfo() ;
				int blocks = json.getJSONObject("result").getInt("blocks") ;
				modelAndView.addObject("message","Test succeeded. Wallet balance:"+balance+",Block height: "+blocks);
				modelAndView.addObject("statusCode",200);
				
			}
		} catch (Exception e) {
			modelAndView.addObject("message","Wallet connection failed. Please check the configuration.");
			modelAndView.addObject("statusCode",300);
		}
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/updateCoinFee")
	public ModelAndView updateCoinFee() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		int fid = Integer.parseInt(request.getParameter("fid"));
		List<Fwithdrawfees> all = this.withdrawFeesService.findByProperty("fvirtualcointype.fid", fid);
		
		//add by hank
		for (Fwithdrawfees ffees : all) {
			String feeKey = "fee"+ffees.getFid();
			double fee = Double.valueOf(request.getParameter(feeKey));
			
			if(fee>=1 || fee<0){
				modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
				modelAndView.addObject("statusCode",300);
				modelAndView.addObject("message","The rate can only be a decimal less than 1.");
				return modelAndView;
			}
		}
		
		for (Fwithdrawfees ffees : all) {
			String feeKey = "fee"+ffees.getFid();
			double fee = Double.valueOf(request.getParameter(feeKey));
			ffees.setFfee(fee);
			this.withdrawFeesService.updateObj(ffees);
		}
		
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("message","Modify Success");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	@RequestMapping("ssadmin/createWalletAddress")
	public ModelAndView createWalletAddress() throws Exception{
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		int fid = Integer.parseInt(request.getParameter("uid"));
		type = this.virtualCoinService.findById(fid);
		if(!type.isFIsWithDraw() && !type.isFisrecharge()){
			modelAndView.addObject("message","Virtual currency types that are not allowed to be repaid and presented can not generate virtual addresses!");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}
		btcMessage = new BTCMessage() ;
		btcMessage.setACCESS_KEY(type.getFaccess_key()) ;
		btcMessage.setIP(type.getFip()) ;
		btcMessage.setPORT(type.getFport()) ;
		btcMessage.setSECRET_KEY(type.getFsecrt_key()) ;
		btcMessage.setPASSWORD(request.getParameter("passWord"));
		if(btcMessage.getACCESS_KEY()==null
				||btcMessage.getIP()==null
				||btcMessage.getPORT()==null
				||btcMessage.getSECRET_KEY()==null){
			modelAndView.addObject("message","Wallet connection failed. Please check the configuration.");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}
		

		try {
			Fvirtualcointype fvirtualcointype = this.virtualCoinService.findById(fid) ;
			if(fvirtualcointype.isFisEth()){
				ETHUtils ethUtils = new ETHUtils(btcMessage) ;
				if(fvirtualcointype.getStartBlockId()==0){
					fvirtualcointype.setStartBlockId(ethUtils.eth_blockNumberValue()) ;
					this.virtualCoinService.updateObj(fvirtualcointype) ;
				}
			}
		} catch (Exception e1) {
			modelAndView.addObject("message","Abnormal wallet!");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}
		
		try {
			new Thread(new Work()).start() ;
		} catch (Exception e) {
			modelAndView.addObject("message","Abnormal wallet!");
			modelAndView.addObject("statusCode",300);
			return modelAndView;
		}
		
		
		modelAndView.addObject("message","In the backstage execution!");
		modelAndView.addObject("statusCode",200);
		modelAndView.addObject("rel", "createWalletAddress");
		modelAndView.addObject("callbackType","closeCurrent");
		return modelAndView;
	}
	
	private BTCMessage btcMessage;
	private Fvirtualcointype type;
	class Work implements Runnable{
		public void run() {
			createAddress(btcMessage, type);
		}
	}
	
	private void createAddress(BTCMessage btcMessage,Fvirtualcointype type) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
		
		if(type.isFisEth() == true ){

			ETHUtils ethUtils = null;
			try {
				ethUtils = new ETHUtils(btcMessage) ;
				for(int i=0;i<200;i++){
					String address = ethUtils.getNewaddressValue() ;
					if(address == null || address.trim().length() ==0){
						break;
					}
					Fpool poolInfo = new Fpool();
					poolInfo.setFaddress(address);
					poolInfo.setFvirtualcointype(type);
					poolService.saveObj(poolInfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{
			BTCUtils btcUtils = null;
			try {
				btcUtils = new BTCUtils(btcMessage) ;
				for(int i=0;i<200;i++){
					String address = btcUtils.getNewaddressValueForAdmin(sdf.format(Utils.getTimestamp()));
					if(address == null || address.trim().length() ==0){
						break;
					}
					Fpool poolInfo = new Fpool();
					poolInfo.setFaddress(address);
					poolInfo.setFvirtualcointype(type);
					poolService.saveObj(poolInfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					btcUtils.walletlock();
				} catch (Exception e) {}
			}
		}

	}
	


	
	@RequestMapping("ssadmin/etcMainAddr")
	public ModelAndView etcMainAddr(
			@RequestParam(required=true )int uid,
			@RequestParam(required=true )final String password
			) throws Exception {
		ModelAndView modelAndView = new ModelAndView() ;
		modelAndView.setViewName("ssadmin/comm/ajaxDone") ;
		final Fvirtualcointype fvirtualcointype = this.virtualCoinService.findById(uid) ;
		if(fvirtualcointype==null ||fvirtualcointype.isFisEth()==false ){
			modelAndView.addObject("message","No ETH Address can not be collected");
			modelAndView.addObject("statusCode",500);
			modelAndView.addObject("rel", "etcMainAddr");
			return modelAndView;
		}

		if(fvirtualcointype.getMainAddr()==null||"".equals(fvirtualcointype.getMainAddr().trim())){
			modelAndView.addObject("message","Not set the Summary Address");
			modelAndView.addObject("statusCode",500);
			modelAndView.addObject("rel", "etcMainAddr");
			return modelAndView;
		}
		
		BTCMessage msg = new BTCMessage();
		msg.setACCESS_KEY(fvirtualcointype .getFaccess_key());
		msg.setIP(fvirtualcointype.getFip());
		msg.setPORT(fvirtualcointype .getFport());
		msg.setSECRET_KEY(fvirtualcointype .getFsecrt_key());
		msg.setPASSWORD(password) ;
		final ETHUtils ethUtils = new ETHUtils(msg) ;
		boolean flag = false ;
		try {
			flag = ethUtils.walletpassphrase(fvirtualcointype.getMainAddr().trim());
			ethUtils.lockAccount(fvirtualcointype.getMainAddr().trim()) ;
		} catch (Exception e1) {
			
		}
		if(flag == false ){
			modelAndView.addObject("message","Wallet link error, or password error");
			modelAndView.addObject("statusCode",500);
			modelAndView.addObject("rel", "etcMainAddr");
			return modelAndView;
		}
		
		{
			new Thread(new Runnable() {
				public void run() {
					try {
						
						ethUtils.sendMain(fvirtualcointype.getMainAddr().trim()) ;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start() ;
			modelAndView.addObject("message","In the background execution, the execution time is determined by the number of addresses in the purse. Please do not repeat the function in a short time.");
			modelAndView.addObject("statusCode",200);
			modelAndView.addObject("rel", "etcMainAddr");
			modelAndView.addObject("callbackType","closeCurrent");
			return modelAndView;
		}
	}

}
