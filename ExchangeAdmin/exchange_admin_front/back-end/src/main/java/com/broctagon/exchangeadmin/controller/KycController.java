package com.broctagon.exchangeadmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.broctagon.exchangeadmin.message.BaseResponse;
import com.broctagon.exchangeadmin.message.listrequest.KycListRequest;
import com.broctagon.exchangeadmin.model.Kyc;
import com.broctagon.exchangeadmin.service.KycService;
import com.broctagon.exchangeadmin.vo.KycVo;

import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/kyc")
public class KycController {

	@Autowired
	private KycService kycService;
	
	@PostMapping("/list")
	@ResponseBody
	public List<KycVo> list(@RequestBody KycListRequest listRequest){
		return kycService.list(listRequest);
	}
	
	@GetMapping("/details")
	@ResponseBody
	public Kyc details(@RequestParam("userId") int userId) {
		return kycService.details(userId);
	}
	
	@GetMapping("/auth")
	@ResponseBody
	@ApiParam(allowableValues="status 2:Success, 3:Failed")
	public BaseResponse auth(@RequestParam("userId") int userId, @RequestParam("status") int status) {
		return kycService.auth(userId, status);
	}
}
